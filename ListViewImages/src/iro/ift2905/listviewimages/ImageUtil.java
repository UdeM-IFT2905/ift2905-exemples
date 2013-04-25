package iro.ift2905.listviewimages;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ImageUtil {

	//
	// Lecteur d'image dans une tache de fond
	//
	// mieux que asynctask:
	//
	// - un seul thread fait le travail
	// - un imageview doit avoir un tag avec son url associe
	// - si l'image view est recycle, ce n'est pas grave... le tage change et
	// c'est tout
	// - gere une cache
	//
	// -- pour utiliser --
	//
	// dans le thread principal:
	// ImageUtil.ImageLoaderQueue ilq = new ImageLoaderQueue();
	// ilq.start();
	//
	// dans le bindview ou on a un imageview iv et un url a lire...
	// iv.setTag(url); // associe cet url avec cet image
	// imageQ.addTask(iv); // lance le load si necessaire
	//
	// Pour arreter la tache: stop()
	//

	public static class ImageLoaderQueue {
		private class ImageAndLoader
		{
			public final ImageView iv;
			public final ProgressBar pb;
			
			public ImageAndLoader(ImageView iv, ProgressBar pb)
			{
				this.iv = iv;
				this.pb = pb;
			}
		}
		
		private LinkedList<ImageAndLoader> tasks;
		private Thread thread;
		private boolean running;
		private Runnable internalRunnable;
		private Bitmap defaultBM;

		// cache d'images
		private HashMap<String, Bitmap> cache;

		private final String ME = "TaskQueue";

		private class InternalRunnable implements Runnable {
			public void run()  {
						internalRun();
			}
		}

		public ImageLoaderQueue() {
			this(null);
		}

		public ImageLoaderQueue(Bitmap defaultBM) {
			tasks = new LinkedList<ImageAndLoader>();
			internalRunnable = new InternalRunnable();
			cache = new HashMap<String, Bitmap>();

			this.defaultBM = defaultBM;
		}

		// demarre le thread interne
		public void start() {
			if (!running) {
				thread = new Thread(internalRunnable);
				thread.setDaemon(true);
				running = true;
				thread.start();
			}
		}

		// arrete le thread interne
		public void stop() {
			running = false;
		}

		// appel de l'UI pour ajouter un ImageView a traiter
		// On va charger l'url qui est dans gettag()
		//
		public void addTask(ImageView iv) {
			addTask(iv, null);
		}
		public void addTask(ImageView iv, ProgressBar pb) {
			if (iv == null)
				return;
			iv.setVisibility(View.INVISIBLE);
			// check cache
			String url = (String) iv.getTag();
			if (url == null)
				return;
			Bitmap b;
			synchronized (cache) {
				b = cache.get(url);
			}
			if (b != null) {
				iv.setImageBitmap(b);
				iv.setVisibility(View.VISIBLE);
				if(pb != null)
					pb.setVisibility(View.GONE);
				return;
			}
			
			if(pb != null)
				pb.setVisibility(View.VISIBLE);
			
			// must load the image...
			if (!running)
				start();
			synchronized (tasks) {
				tasks.addLast(new ImageAndLoader(iv, pb));
				tasks.notify(); // notify any waiting threads
			}
		}

		// retourne le prochain imageview a traiter pour le thread interne
		private ImageAndLoader getNextTask() {
			int s;
			synchronized (tasks) {
				s = tasks.size();
			}
			int cs;
			synchronized (cache) {
				cs = cache.size();
			}
			// Log.d(ME, "getNextTask " + s + " todo, cache size is " + cs);
			synchronized (tasks) {
				if (tasks.isEmpty()) {
					try {
						tasks.wait();
					} catch (InterruptedException e) {
						Log.e(ME, "Task interrupted", e);
						stop();
					}
				}
				return tasks.removeFirst();
			}
		}

		// boucle principale du thread interne
		private void internalRun() {
			while (running) {
				final ImageAndLoader ivpb = getNextTask();
				final String url = (String) ivpb.iv.getTag();
				if (url == null)
					continue;
				// check cache again

				final Bitmap tmp;
				synchronized (cache) {
					tmp = cache.get(url);
				}
				if (tmp != null) {
					// update the view
					ivpb.iv.post(new Runnable() {
						public void run() {
							if (((String) ivpb.iv.getTag()).equals(url))
								ivpb.iv.setImageBitmap(tmp);
							ivpb.iv.setVisibility(View.VISIBLE);
							if(ivpb.pb != null)
								ivpb.pb.setVisibility(View.GONE);
						};
					});
					continue;
				}
				
				Bitmap b = null;
				try {
					b = NetUtil.loadHttpImage(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					b = null;
				} catch (IOException e) {
					e.printStackTrace();
					b = null;
				}
				
				// update cache
				if(b != null)
				{
					synchronized (cache) {
						cache.put(url, b);
					}
				}
				
				final Bitmap bf = b == null ? defaultBM : b;
				
				// update UI thread... only if tag did not change
				ivpb.iv.post(new Runnable() {
					public void run() {
						if (((String) ivpb.iv.getTag()).equals(url))
						{
							if(bf != null)
								ivpb.iv.setImageBitmap(bf);
							else
								ivpb.iv.setImageResource(android.R.drawable.stat_notify_error);
						}
						ivpb.iv.setVisibility(View.VISIBLE);
						if(ivpb.pb != null)
							ivpb.pb.setVisibility(View.GONE);
					};
				});
			}
		}
	}
}

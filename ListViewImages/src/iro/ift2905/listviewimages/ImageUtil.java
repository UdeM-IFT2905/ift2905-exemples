package iro.ift2905.listviewimages;

import java.util.HashMap;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
		private LinkedList<ImageView> tasks;
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
			tasks = new LinkedList<ImageView>();
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
				return;
			}
			// must load the image...
			if (!running)
				start();
			synchronized (tasks) {
				tasks.addLast(iv);
				tasks.notify(); // notify any waiting threads
			}
		}

		// retourne le prochain imageview a traiter pour le thread interne
		private ImageView getNextTask() {
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
				final ImageView iv = getNextTask();
				final String url = (String) iv.getTag();
				if (url == null)
					continue;
				// check cache again

				final Bitmap tmp;
				synchronized (cache) {
					tmp = cache.get(url);
				}
				if (tmp != null) {
					// Log.d(ME, "in cache " + url);
					// update the view
					iv.post(new Runnable() {
						public void run() {
							if (((String) iv.getTag()).equals(url))
								iv.setImageBitmap(tmp);
							iv.setVisibility(View.VISIBLE);
						};
					});
					continue;
				}
				//Log.d(ME, "loading  " + url);
				try {
					final Bitmap b = NetUtil.loadHttpImage(url);
					// update UI thread... only if tag did not change
					iv.post(new Runnable() {
						public void run() {
							if (((String) iv.getTag()).equals(url))
								iv.setImageBitmap(b);
							iv.setVisibility(View.VISIBLE);
						};
					});
					// update cache
					synchronized (cache) {
						cache.put(url, b);
					}
					// } catch (ClientProtocolException e) {
					// } catch (IOException e) {
					// } catch (IllegalStateException e) {
				} catch (Exception e) {
					//Log.d("imageutil","exception: "+e.getClass()+":"+e.getMessage());
					if( e.getClass().equals(java.lang.SecurityException.class) ) {
						Log.d("imageutil",e.getMessage());
					}
					// si on a un bitap par defaut, sinon on laisse invisible
					if (defaultBM != null) {
						iv.post(new Runnable() {
							public void run() {
								iv.setImageBitmap(defaultBM);
								iv.setVisibility(View.VISIBLE);
							};
						});
					}
				}
			}
		}
	}

	// //
	// // loader asynchrone d'images...
	// //
	// // on suppose que le tag du ImageView est l'URL a lire.
	// // si le view est recycle, ce n'est pas grave... on laisse tomber.
	// //
	// //
	// //
	// private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {
	// private final ImageView iv;
	// private final String url;
	//
	// public ImageLoadTask(ImageView iv) {
	// this.iv = iv;
	// url = (String) iv.getTag();
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// if (url != null) {
	// Log.d("asyncimage", "loading " + url);
	// }
	// }
	//
	// @Override
	// protected Bitmap doInBackground(String... param) {
	// if (url == null)
	// return null;
	// try {
	// final Bitmap b = loadHttpImage(url);
	// // iv.post(new Runnable() {
	// // public void run() { iv.setImageBitmap(b); };
	// // });
	// return b;
	// } catch (ClientProtocolException e) {
	// } catch (IOException e) {
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Bitmap result) {
	// Log.d("asyncimage", "done loading " + url);
	// if (result == null)
	// return;
	// imageCache.rememberBitMap(url, result); // ne pas reloader 2 fois la
	// // meme image
	// String newurl = (String) iv.getTag();
	// if (newurl.equals(url)) {
	// iv.setImageBitmap(result);
	// } else {
	// Log.d("asyncimage", "recycled");
	// }
	// }
	//
	// }

}

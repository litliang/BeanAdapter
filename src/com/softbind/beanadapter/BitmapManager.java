package com.softbind.beanadapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.softbind.beanadapter.util.ImageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * 瀵倹顒炵痪璺ㄢ柤閸旂姾娴囬崶鍓у瀹搞儱鍙块敓锟�* 娴ｈ法鏁ょ拠瀛樻閿燂拷 * BitmapManager bmpManager; bmpManager = new
 * BitmapManager(BitmapFactory.decodeResource(context.getResources(),
 * R.drawable.loading)); bmpManager.loadBitmap(imageURL, imageView);
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-6-25
 */
public class BitmapManager {

	private static HashMap<String, SoftReference<Bitmap>> cache;
	private static ExecutorService pool;
	private static Map<ImageView, String> imageViews = new HashMap<ImageView, String>();
	private Bitmap defaultBmp;

	static {
		cache = new HashMap<String, SoftReference<Bitmap>>();
		pool = Executors.newFixedThreadPool(5); // 閸ュ搫鐣剧痪璺ㄢ柤閿燂拷 imageViews =
												// Collections.synchronizedMap(new
												// WeakHashMap<ImageView,
												// String>());
	}

	public BitmapManager() {
	}

	public BitmapManager(Bitmap def) {
		this.defaultBmp = def;
	}

	/**
	 * 鐠佸墽鐤嗘妯款吇閸ュ墽锟�
	 * 
	 * @param bmp
	 */
	public void setDefaultBmp(Bitmap bmp) {
		defaultBmp = bmp;
	}

	/**
	 * 閸旂姾娴囬崶鍓у
	 * 
	 * @param url
	 * @param imageView
	 */
	public void loadBitmap(String url, ImageView imageView) {
		loadBitmap(url, imageView, this.defaultBmp, 0, 0);
	}

	/**
	 * 閸旂姾娴囬崶鍓у-閸欘垵顔曠純顔煎鏉炶棄銇戠拹銉ユ倵閺勫墽銇氶惃鍕帛鐠併倕娴橀敓锟�* @param url
	 * 
	 * @param imageView
	 * @param defaultBmp
	 */
	public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp) {
		loadBitmap(url, imageView, defaultBmp, 0, 0);
	}

	/**
	 * 閸旂姾娴囬崶鍓у-閸欘垱瀵氾拷?姘▔锟�锟斤拷娴橀悧鍥╂畱妤傛ê锟�
	 * 
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 */
	public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp,
			int width, int height) {
		imageViews.put(imageView, url);
		Bitmap bitmap = getBitmapFromCache(url);

		if (bitmap != null) {
			// 閺勫墽銇氱紓鎾崇摠閸ュ墽锟�
			imageView.setImageBitmap(bitmap);
		} else {
			// 閸旂姾娴嘢D閸楋拷?鑵戦惃鍕禈閻楀洨绱﹂敓锟�
			String filename = getFileName(url);
			String filepath = imageView.getContext().getFilesDir()
					+ File.separator + filename;
			File file = new File(filepath);
			if (file.exists()) {
				// 閺勫墽銇歋D閸楋拷?鑵戦惃鍕禈閻楀洨绱﹂敓锟�
				Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(),
						filename);
				imageView.setImageBitmap(bmp);
			} else {
				// 缁捐法鈻奸崝鐘烘祰缂冩垹绮堕崶鍓у
				imageView.setImageBitmap(defaultBmp);
				queueJob(url, imageView, width, height);
			}
		}
	}

	/**
	 * 娴犲海绱︼拷?妯硅厬閼惧嘲褰囬崶鍓у
	 * 
	 * @param url
	 */
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap = null;
		if (cache.containsKey(url)) {
			bitmap = cache.get(url).get();
		}
		return bitmap;
	}

	/**
	 * 娴犲海缍夌紒锟�锟斤拷閸旂姾娴囬崶鍓у
	 * 
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 */
	public void queueJob(final String url, final ImageView imageView,
			final int width, final int height) {
		/* Create handler in UI thread. */
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				String tag = imageViews.get(imageView);
				if (tag != null && tag.equals(url)) {
					if (msg.obj != null) {
						imageView.setImageBitmap((Bitmap) msg.obj);
						try {
							// 閸氭叀D閸楋拷?鑵戦崘娆忓弳閸ュ墽澧栫紓鎾崇摠
							ImageUtils.saveImage(imageView.getContext(),
									getFileName(url), (Bitmap) msg.obj);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};

		pool.execute(new Runnable() {
			public void run() {
				Message message = Message.obtain();
				message.obj = downloadBitmap(url, width, height);
				handler.sendMessage(message);
			}
		});
	}

	/**
	 * 娑撳娴囬崶鍓у-閸欘垱瀵氾拷?姘▔锟�锟斤拷娴橀悧鍥╂畱妤傛ê锟�
	 * 
	 * @param url
	 * @param width
	 * @param height
	 */
	private Bitmap downloadBitmap(String url, int width, int height) {
		Bitmap bitmap = null;
		try {
			// http閸旂姾娴囬崶鍓у
			bitmap = getNetBitmap(url);
			if (width > 0 && height > 0) {
				// 閹稿洤鐣鹃弰鍓с仛閸ュ墽澧栭惃鍕彯閿燂拷
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			}
			// 锟�锟斤拷鍙嗙紓鎾崇摠
			cache.put(url, new SoftReference<Bitmap>(bitmap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static String getFileName(String filePath) {
		if (TextUtils.isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 鑾峰彇缃戠粶鍥剧墖
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getNetBitmap(String urlstring) throws Exception {
		URL url = new URL(urlstring);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setReadTimeout(5000);

		InputStream input = conn.getInputStream();
		Bitmap bitmap = BitmapFactory.decodeStream(input);
		return bitmap;
	}

}

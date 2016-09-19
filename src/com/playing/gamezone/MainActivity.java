package com.playing.gamezone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.playing.gamezone.utils.JavaScriptinterface;

public class MainActivity extends FragmentActivity {

	private LinearLayout linear;
	private TextView net;
	private static WebView webView;
	private long mExitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		linear = (LinearLayout) findViewById(R.id.linear);
		webView = (WebView) findViewById(R.id.webView);
		net = (TextView) findViewById(R.id.text_net);
	}

	// 检查网络连接状态，Monitor network connections (Wi-Vi, GPRS, UMTS, etc.)
	public static boolean checkNetWorkStatus(Context context) {
		boolean result;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo != null && netinfo.isConnected()) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean needBackConfirm = true;
		if (needBackConfirm && keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(getBaseContext(),
						"Press again to exit the application",
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!checkNetWorkStatus(MainActivity.this)) {
			webView.setVisibility(View.INVISIBLE);
			net.setVisibility(View.VISIBLE);
		} else {
//			webView.loadUrl("file:///android_asset/wap/index.html");
//			webView.loadUrl("http:///10.0.2.2/v10/indexV10.html");
			webView.loadUrl("http://h5.iappgame.com/v2/index2.html");
//			webView.loadUrl("https://s3-ap-southeast-1.amazonaws.com/mfhtml5game/v2/index2.html");
//			webView.setWebViewClient(new WebViewClient());
			webView.setWebChromeClient(new myWebChromeClient());
			webView.setDownloadListener(new myDownloadListener());
			webView.getSettings().setJavaScriptEnabled(true);
			webView.addJavascriptInterface(new JavaScriptinterface(this), "app");
		}
	}

	class myWebChromeClient extends WebChromeClient{
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			super.onProgressChanged(webView, newProgress);
		}
	}
    private	class myDownloadListener implements DownloadListener{

		@Override
		public void onDownloadStart(String url, String userAgent,
				String contentDisposition, String mimetype, long contentLength) {
			// TODO Auto-generated method stub
			Uri uri = Uri.parse(url);  
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
            startActivity(intent);  
		}
    	
    }
	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // 添加此方法
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // 添加此方法
	}
}

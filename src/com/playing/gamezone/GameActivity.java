package com.playing.gamezone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.analytics.tracking.android.EasyTracker;
import com.playing.gamezone.MainActivity.myWebChromeClient;

public class GameActivity extends Activity {

	private WebView webView1;
	private String vurl;
	//private static String aurl = "http:///10.0.2.2/v10/";
	 private static String aurl = "http://h5.iappgame.com/v2/";
	// private static String aurl = "https://s3-ap-southeast-1.amazonaws.com/mfhtml5game/v2/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		Intent intent = getIntent();
		vurl = intent.getStringExtra("url");
		webView1 = (WebView) findViewById(R.id.webView1);
		webView1.setWebChromeClient(new myWebChromeClient());// 所有连接都用webview打开
		webView1.loadUrl(aurl + vurl);
		webView1.getSettings().setJavaScriptEnabled(true);
	}

	class myWebChromeClient extends WebChromeClient{
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(webView1, newProgress);
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		GameActivity.this.finish();
		if (webView1!=null) {
			try {
				webView1.destroy();
				} catch (Exception e) {
				System.out.println("��̨no kills");
				}
		}
	}
}

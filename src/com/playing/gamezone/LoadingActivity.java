package com.playing.gamezone;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.webkit.WebView;

public class LoadingActivity extends Activity {
	
	private SharedPreferences preferences;
	private static Handler handler = new Handler();
	private Editor editor;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final EasyTracker easyTracker = EasyTracker.getInstance(this);
		setContentView(R.layout.activity_wait);
		context = this;
		WebView webview = new WebView(context);
		//webview.loadUrl("http:///192.168.2.94/v11/index2.html");
		preferences = getSharedPreferences("loading",Context.MODE_PRIVATE);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(preferences.getBoolean("firststart",true)){
					editor = preferences.edit();
					editor.putBoolean("firststart",false);
					editor.commit();
					startActivity(new Intent(LoadingActivity.this,GuideActivity.class));
					LoadingActivity.this.finish();
					easyTracker.send(MapBuilder.createEvent("app","app_guide",
							null, null).build());
				}else {
					startActivity(new Intent(LoadingActivity.this,GuideActivity.class));
					LoadingActivity.this.finish();
					easyTracker.send(MapBuilder.createEvent("app","app_main",
							null, null).build());
				}
			}
		},2000);
		
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

package com.playing.gamezone.utils;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.m.ms.api.pay.GPay;
import com.m.ms.api.pay.GPayAPI;
import com.playing.gamezone.GameActivity;
import com.playing.gamezone.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JavaScriptinterface {
	private Context mContext;
	SharedPreferences gameStore;
	private static long lastClickTime;
	private boolean isIdle;

	// int count;

	/** Instantiate the interface and set the context */
	public JavaScriptinterface(Context c) {
		mContext = c;
		gameStore = mContext.getSharedPreferences("config",
				mContext.MODE_PRIVATE);
		isIdle = true;
	}

	@JavascriptInterface
	public boolean isUnlock(String gameid) {
		synchronized (gameStore) {//¼ÓËø×´Ì¬
			boolean ret;
			if (gameStore.contains(gameid)) {
				ret = true;
			} else {
				ret = false;
			}
			Log.d("JavaScriptinterface", "gameid:" + gameid + " isUnlock:"
					+ ret);
			return ret;
		}
	}

	private void pay(final String gameid, final String url) {
		if (!isIdle) {
			return;
		}
		isIdle = false;
		/****************************************************/
		AnimRoundProcessDialog.showRoundProcessDialog(mContext,
				R.layout.activity_wait);
		GPay.getAPI().pay((Activity) mContext, "FDB93380",
				new GPayAPI.GPayCallbackEx() {
					@Override
					public void onResult(int code, String arg1) {
//						EasyTracker easyTracker = EasyTracker
//								.getInstance(mContext);
						AnimRoundProcessDialog.closeRoundProcessDialog();
						if (code == GPayAPI.SUCCESS) {
							gameStore.edit().putBoolean(gameid, true).commit();
							isUnlock(gameid);
							Intent intent = new Intent();
							intent.putExtra("url", url);
							intent.setClass(mContext, GameActivity.class);
							mContext.startActivity(intent);
							Log.d("Gpay", "success");
							Log.d("JavaScriptinterface", "gameid:" + gameid
									+ " committed");
//							easyTracker
//									.send(MapBuilder.createEvent("gameClick",
//											gameid, "payed", null).build());
//							easyTracker.send(MapBuilder.createEvent("pay",
//									gameid, "success", null).build());
						} else if (code == -2) {
							Log.d("Gpay", "unuse");
							Toast.makeText(mContext, "Users pay unsuccessful",
									Toast.LENGTH_LONG).show();
//							easyTracker.send(MapBuilder.createEvent("pay",
//									gameid, "cancel", null).build());
						} else {
							Log.d("Gpay", "fail" + arg1);
							Toast.makeText(mContext, "No Pay",
									Toast.LENGTH_LONG).show();
//							easyTracker.send(MapBuilder.createEvent("pay",
//									gameid, "fail:" + code, null).build());
							gameStore.edit().putBoolean(gameid, true).commit();
							isUnlock(gameid);
							Intent intent = new Intent();
							intent.putExtra("url", url);
							intent.setClass(mContext, GameActivity.class);
							mContext.startActivity(intent);
						}
						isIdle = true;
					}

					@Override
					public void onPaymentBegan() {

					}
				});
	}


	@JavascriptInterface
	public void goToMainPage(final String gameid, final String url,
			final boolean isFree) {
		 
		synchronized (gameStore) {
			EasyTracker easyTracker = EasyTracker.getInstance(mContext);
			if (isUnlock(gameid)) {// ÒÑ½âËø
				Log.d("Gpay", "payed");
				Intent intent = new Intent();
				intent.putExtra("url", url);
				intent.setClass(mContext, GameActivity.class);
				mContext.startActivity(intent);
//				easyTracker.send(MapBuilder.createEvent("gameClick", gameid,
//						"payed", null).build());
			} else if (isFree) {
				Log.d("Gpay", "free");
				Intent intent = new Intent();
				intent.putExtra("url", url);
				intent.setClass(mContext, GameActivity.class);
				mContext.startActivity(intent);
//				easyTracker.send(MapBuilder.createEvent("gameClick", gameid,
//						"free", null).build());
			} else {
//				easyTracker.send(MapBuilder.createEvent("gameClick", gameid,
//						"payStart", null).build());
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						pay(gameid, url);
					}
				});
			}
		}
	}
	
	@JavascriptInterface
	public void alert(String text){
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

}

package com.playing.gamezone;

import com.m.ms.api.pay.app.PaySdkApplication;

public class MyApp extends PaySdkApplication {/*
	private Context mContext;
	private static ArrayList<Data> adList = null;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		final EasyTracker easyTracker = EasyTracker.getInstance(mContext);
		Log.d("native", "onCreate");
		Mp.getAPI().getDatas(new GetDatasCallback() {
			public void onResult(String arg0) {
				easyTracker
				.send(MapBuilder.createEvent("native",
						arg0, null, null).build());
				Log.d("native", "arg0:"+arg0);
				if(arg0 == null){
					return;
				}
				GetDataResponse response = new Gson().fromJson(arg0, GetDataResponse.class);
				if(response != null){
					adList = response.adv;
					Log.d("native", "adList:"+adList);
				}
			}
		});
	}
	
	public static ArrayList<Data> getAdList(){
		return adList;
	}

*/}

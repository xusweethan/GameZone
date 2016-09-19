package com.playing.gamezone.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

public class AnimRoundProcessDialog {
	private static Dialog mDialog;

	public static void showRoundProcessDialog(Context mContext, int layout) {
		mDialog = new AlertDialog.Builder(mContext).create();
		mDialog.setCancelable(false);
		mDialog.show();
		mDialog.setContentView(layout);
	}

	public static void closeRoundProcessDialog() {
		mDialog.dismiss();
	}
}

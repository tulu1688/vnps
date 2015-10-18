package com.tulu.vnpetrolstation.ui.activity;

import com.tulu.vnpetrolstation.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseAdActivity extends AppCompatActivity {
	protected static final String TAG = "BaseActivity";

	protected boolean isActive;
	protected ProgressDialog mLoadingDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLoadingDialog = new ProgressDialog(this);
		
		initElements();
		initViews();
		setupListeners();
	}

	protected abstract void initElements();
	protected abstract void initViews();
	protected abstract void setupListeners();

	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onResume() {
		super.onResume();
		isActive = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isActive = false;
		// TODO: Add timeout, app should be logged out after close for a while
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	public void showLoadingDialog() {
		showLoadingDialog(null);
	}

	public void showLoadingDialog(String message) {
		if (!isActive)
			return;
		try {
			if (mLoadingDialog.isShowing())
				return;
			if (TextUtils.isEmpty(message)) {
				message = getResources().getString(R.string.loading);
			}
			mLoadingDialog.setMessage(message);
			mLoadingDialog.setCanceledOnTouchOutside(false);
			mLoadingDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// QuanNH22 - add one more attribute - is cancel?
	public void showLoadingDialog(boolean isCancelable) {
		showLoadingDialog(null, isCancelable);
	}

	public void showLoadingDialog(String message, boolean isCanceledOnTouchOutside) {
		if (!isActive)
			return;
		try {
			if (mLoadingDialog.isShowing())
				return;
			if (TextUtils.isEmpty(message)) {
				message = getResources().getString(R.string.loading);
			}
			mLoadingDialog.setMessage(message);
			mLoadingDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
			mLoadingDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dismissLoadingDialog() {
		mLoadingDialog.dismiss();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// hide the keyboard when user click on the view
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// Check if no view has focus:
			View view = this.getCurrentFocus();
			if (view != null) {
				InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			return true;
		} else {
			return false;
		}
	}
}
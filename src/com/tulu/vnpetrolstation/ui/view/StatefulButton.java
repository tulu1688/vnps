package com.tulu.vnpetrolstation.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 
 * @author Long Nguyen Tien
 *
 */

public class StatefulButton extends Button {

	public StatefulButton(Context context) {
		super(context);
	}

	public StatefulButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StatefulButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
	@Override
	public void setBackground(Drawable background) {
		MultiStateDrawable msd = new MultiStateDrawable(background);
		if (Build.VERSION.SDK_INT < 16)
			super.setBackgroundDrawable(msd);
		else
			super.setBackground(msd);
	}	
}

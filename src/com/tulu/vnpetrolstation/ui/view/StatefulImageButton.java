package com.tulu.vnpetrolstation.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * 
 * @author Long Nguyen Tien
 *
 */

public class StatefulImageButton extends ImageButton {
	public StatefulImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public StatefulImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public StatefulImageButton(Context context) {
		super(context);
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
	
	@Override
	public void setImageDrawable(Drawable drawable) {
		MultiStateDrawable msd = new MultiStateDrawable(drawable);
		super.setImageDrawable(msd);
	}
}

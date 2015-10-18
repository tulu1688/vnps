package com.tulu.vnpetrolstation.ui.view;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/**
 * 
 * @author Long Nguyen Tien
 *
 */

public class MultiStateDrawable extends LayerDrawable {

	// The color filter to apply when the button is pressed
	protected ColorFilter _pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);

	public MultiStateDrawable(Drawable d) {
		super(new Drawable[] { d });
	}

	@Override
	protected boolean onStateChange(int[] states) {
		boolean enabled = false;
		boolean pressed = false;

		for (int state : states) {
			if (state == android.R.attr.state_enabled)
				enabled = true;
			else if (state == android.R.attr.state_pressed)
				pressed = true;
		}

		mutate();
		if (enabled && pressed) {
			setColorFilter(_pressedFilter);
		} else {
			setColorFilter(null);
		}

		invalidateSelf();

		return super.onStateChange(states);
	}

	@Override
	public boolean isStateful() {
		return true;
	}
}

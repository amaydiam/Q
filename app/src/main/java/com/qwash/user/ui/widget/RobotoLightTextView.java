package com.qwash.user.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.qwash.user.utils.FontCache;

public class RobotoLightTextView extends android.support.v7.widget.AppCompatTextView {

    public RobotoLightTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public RobotoLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public RobotoLightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface(FontCache.ROBOTO_LIGHT, context);
        setTypeface(customFont);
    }
}
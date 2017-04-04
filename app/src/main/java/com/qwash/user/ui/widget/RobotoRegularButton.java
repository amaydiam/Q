package com.qwash.user.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.qwash.user.utils.FontCache;

public class RobotoRegularButton extends android.support.v7.widget.AppCompatButton {

    public RobotoRegularButton(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public RobotoRegularButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public RobotoRegularButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface(FontCache.ROBOTO_REGULAR, context);
        setTypeface(customFont);
    }
}
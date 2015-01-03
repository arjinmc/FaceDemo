package com.arjinmc.widget;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.TextView;

import com.arjinmc.facedemo.FaceUtil;

/**
 * @usage 支持显示表情的textview
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 * @create 2014年12月26日
 */
public class FaceTextView extends TextView {

    public FaceTextView(Context context) {
        super(context);
    }

    public FaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
    	SpannableString spannableString = new SpannableString(text);
    	spannableString = FaceUtil.getExpressionString(getContext(), spannableString.toString());
        super.setText(spannableString, type);
    }
}

package com.arjinmc.widget;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.EditText;

import com.arjinmc.facedemo.FaceUtil;

/**
 * @usage 支持自定义表情的EditText
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 * @create 2014年12月26日
 */
public class FaceEditText extends EditText {

    public FaceEditText(Context context) {
        super(context);
    }

    public FaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    @Override
    public void setText(CharSequence text, BufferType type) {
    	SpannableString spannableString = new SpannableString(text);
    	spannableString = FaceUtil.getExpressionString(getContext(), spannableString.toString());
        super.setText(spannableString, type);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
    		if (id == android.R.id.paste) {
	    		ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
	            String value = clipboard.getText().toString();
	            Editable edit = getEditableText();
	            int cursorSelect = getSelectionStart();
	            edit.insert(cursorSelect, FaceUtil.getExpressionString(getContext(), value));
	            return true;
    		}
    		return super.onTextContextMenuItem(id);
    }
}

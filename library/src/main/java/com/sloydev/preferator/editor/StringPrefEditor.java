package com.sloydev.preferator.editor;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sloydev.preferator.R;

public class StringPrefEditor extends FrameLayout {

    private EditText valueView;
    private OnStringValueChangeListener onStringValueChangeListener;

    public StringPrefEditor(Context context) {
        this(context, null);
    }

    public StringPrefEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StringPrefEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_editor_string, this, true);
        valueView = (EditText) findViewById(R.id.pref_value);
        valueView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (onStringValueChangeListener != null) {
                    onStringValueChangeListener.onValueChange(charSequence.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setValue(String value) {
        valueView.setText(value);
    }

    public String getValue() {
        return valueView.getText().toString();
    }

    public void setOnStringValueChangeListener(OnStringValueChangeListener onStringValueChangeListener) {
        this.onStringValueChangeListener = onStringValueChangeListener;
    }

    public interface OnStringValueChangeListener {
        void onValueChange(String newValue);
    }
}

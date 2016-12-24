package com.sloydev.preferator.editor;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sloydev.preferator.R;

public class IntPrefEditor extends FrameLayout {

    private EditText valueView;
    private OnIntValueChangeListener onIntValueChangeListener;

    public IntPrefEditor(Context context) {
        this(context, null);
    }

    public IntPrefEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntPrefEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_editor_int, this, true);
        valueView = (EditText) findViewById(R.id.pref_value);
        valueView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (onIntValueChangeListener != null) {
                    try {
                        Integer number;
                        if (charSequence != null) {
                            number = Integer.parseInt(charSequence.toString());
                        } else {
                            number = 0;
                        }
                        onIntValueChangeListener.onValueChange(number);
                        valueView.setError(null);
                    } catch (NumberFormatException e) {
                        valueView.setError("Wrong integer format");
                    }
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

    public void setValue(Integer value) {
        valueView.setText(value.toString());
    }

    public Integer getValue() {
        return Integer.parseInt(valueView.getText().toString());
    }

    public void setOnIntValueChangeListener(OnIntValueChangeListener onLongValueChangeListener) {
        this.onIntValueChangeListener = onLongValueChangeListener;
    }

    public interface OnIntValueChangeListener {
        void onValueChange(Integer newValue);
    }
}

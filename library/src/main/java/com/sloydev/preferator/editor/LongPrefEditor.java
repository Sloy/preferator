package com.sloydev.preferator.editor;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sloydev.preferator.R;

public class LongPrefEditor extends FrameLayout {

    private EditText valueView;
    private OnLongValueChangeListener onLongValueChangeListener;

    public LongPrefEditor(Context context) {
        this(context, null);
    }

    public LongPrefEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongPrefEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_editor_int, this, true);
        valueView = (EditText) findViewById(R.id.pref_value);
        valueView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (onLongValueChangeListener != null) {
                    try {
                        Long number;
                        if (charSequence != null) {
                            number = Long.parseLong(charSequence.toString());
                        } else {
                            number = 0L;
                        }
                        onLongValueChangeListener.onValueChange(number);
                        valueView.setError(null);
                    } catch (NumberFormatException e) {
                        valueView.setError("Wrong long format");
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

    public void setValue(Long value) {
        valueView.setText(value.toString());
    }

    public Long getValue() {
        return Long.parseLong(valueView.getText().toString());
    }

    public void setOnLongValueChangeListener(OnLongValueChangeListener onLongValueChangeListener) {
        this.onLongValueChangeListener = onLongValueChangeListener;
    }

    public interface OnLongValueChangeListener {
        void onValueChange(Long newValue);
    }
}

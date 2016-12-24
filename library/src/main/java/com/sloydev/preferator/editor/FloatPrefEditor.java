package com.sloydev.preferator.editor;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sloydev.preferator.R;

public class FloatPrefEditor extends FrameLayout {

    private EditText valueView;
    private OnFloatValueChangeListener onFloatValueChangeListener;

    public FloatPrefEditor(Context context) {
        this(context, null);
    }

    public FloatPrefEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatPrefEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_editor_float, this, true);
        valueView = (EditText) findViewById(R.id.pref_value);
        valueView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (onFloatValueChangeListener != null) {
                    try {
                        Float number;
                        if (charSequence != null) {
                            number = Float.parseFloat(charSequence.toString());
                        } else {
                            number = 0f;
                        }
                        onFloatValueChangeListener.onValueChange(number);
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

    public void setValue(Float value) {
        valueView.setText(value.toString());
    }

    public Float getValue() {
        return Float.parseFloat(valueView.getText().toString());
    }

    public void setOnFloatValueChangeListener(OnFloatValueChangeListener onLongValueChangeListener) {
        this.onFloatValueChangeListener = onLongValueChangeListener;
    }

    public interface OnFloatValueChangeListener {
        void onValueChange(Float newValue);
    }
}

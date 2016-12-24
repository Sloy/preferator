package com.sloydev.preferator.editor;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.sloydev.preferator.R;

public class BooleanPrefEditor extends FrameLayout {

    private Switch valueView;
    private OnBooleanValueChangeListener onBooleanValueChangeListener;

    public BooleanPrefEditor(Context context) {
        this(context, null);
    }

    public BooleanPrefEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BooleanPrefEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_editor_boolean, this, true);
        valueView = (Switch) findViewById(R.id.pref_value_boolean);
        valueView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (onBooleanValueChangeListener != null) {
                    onBooleanValueChangeListener.onValueChange(isChecked);
                }
            }
        });
    }

    public void setValue(Boolean value) {
        valueView.setChecked(value);
    }

    public Boolean getValue() {
        return valueView.isChecked();
    }

    public void setOnBooleanValueChangeListener(OnBooleanValueChangeListener onBooleanValueChangeListener) {
        this.onBooleanValueChangeListener = onBooleanValueChangeListener;
    }

    public interface OnBooleanValueChangeListener {
        void onValueChange(Boolean newValue);
    }
}

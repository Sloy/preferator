package com.sloydev.preferator.editor;


import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sloydev.preferator.R;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class SetPrefEditor extends FrameLayout {

    private EditText valueView;
    private OnSetValueChangeListener onSetValueChangeListener;

    public SetPrefEditor(Context context) {
        this(context, null);
    }

    public SetPrefEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetPrefEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_editor_string, this, true);
        valueView = (EditText) findViewById(R.id.pref_value);
        valueView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (onSetValueChangeListener != null) {
                    onSetValueChangeListener.onValueChange(stringToSet(charSequence.toString()));
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

    public void setValue(Set<String> value) {
        valueView.setText(setToString(value));
    }

    public Set<String> getValue() {
        String rawValue = valueView.getText().toString();
        return stringToSet(rawValue);
    }

    public void setOnSetValueChangeListener(OnSetValueChangeListener onSetValueChangeListener) {
        this.onSetValueChangeListener = onSetValueChangeListener;
    }

    private Set<String> stringToSet(String rawValue) {
        String[] items = rawValue.split(",");
        return new HashSet<>(asList(items));
    }

    private String setToString(Set<String> set) {
        return TextUtils.join(",", set);
    }

    public interface OnSetValueChangeListener {
        void onValueChange(Set<String> newValue);
    }
}

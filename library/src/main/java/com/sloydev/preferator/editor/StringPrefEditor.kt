package com.sloydev.preferator.editor

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import com.sloydev.preferator.R

class StringPrefEditor @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private val valueView: EditText
  var onStringValueChangeListener: ((newValue: String) -> Unit)? = null

  init {
    LayoutInflater.from(context).inflate(R.layout.item_editor_string, this, true)
    valueView = findViewById(R.id.pref_value) as EditText
    valueView.addTextChangedListener(object : TextWatcher {
      override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
          onStringValueChangeListener?.invoke(charSequence.toString())
      }

      override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
      override fun afterTextChanged(editable: Editable) {}
    })
  }

  var value: String?
    get() = valueView.text.toString()
    set(value) {
      valueView.setText(value)
    }

}
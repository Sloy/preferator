package com.sloydev.preferator.editor

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import com.sloydev.preferator.R

class IntPrefEditor @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private var valueView: EditText
  var onIntValueChangeListener: ((newValue: Int)->Unit)? = null
  init {
    LayoutInflater.from(context).inflate(R.layout.item_editor_int, this, true)
    valueView = findViewById(R.id.pref_value) as EditText
    valueView.addTextChangedListener(object : TextWatcher {
      override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        onIntValueChangeListener?.let {
          try {
            val number = charSequence.toString().toInt()
            it.invoke(number)
            valueView.error = null
          } catch (e: NumberFormatException) {
            valueView.error = "Wrong integer format"
          }
        }
      }

      override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
      override fun afterTextChanged(editable: Editable) {}
    })
  }

  var value: Int?
    get() = valueView.text.toString().toInt()
    set(value) {
      valueView.setText(value?.toString())
    }


}
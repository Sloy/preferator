package com.sloydev.preferator.editor

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import com.sloydev.preferator.R

class LongPrefEditor @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private val valueView: EditText
  var onLongValueChangeListener: ((newValue: Long?) -> Unit)? = null

  init {
    LayoutInflater.from(context).inflate(R.layout.item_editor_int, this, true)
    valueView = findViewById(R.id.pref_value) as EditText
    valueView.addTextChangedListener(object : TextWatcher {
      override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        onLongValueChangeListener?.let {
          try {
            val number: Long = charSequence.toString().toLong()
            it.invoke(number)
            valueView.error = null
          } catch (e: NumberFormatException) {
            valueView.error = "Wrong long format"
          }
        }
      }

      override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
      override fun afterTextChanged(editable: Editable) {}
    })
  }

  var value: Long?
    get() = valueView.text.toString().toLong()
    set(value) {
      valueView.setText(value.toString())
    }
}
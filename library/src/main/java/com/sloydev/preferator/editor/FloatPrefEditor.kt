package com.sloydev.preferator.editor

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import com.sloydev.preferator.R

class FloatPrefEditor @JvmOverloads constructor(
  context: Context?,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private var valueView: EditText? = null
  private var onFloatValueChangeListener: OnFloatValueChangeListener? = null
  private fun init() {
    LayoutInflater.from(context).inflate(R.layout.item_editor_float, this, true)
    valueView = findViewById(R.id.pref_value) as EditText
    valueView!!.addTextChangedListener(object : TextWatcher {
      override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        if (onFloatValueChangeListener != null) {
          try {
            val number: Float
            number = charSequence?.toString()?.toFloat() ?: 0f
            onFloatValueChangeListener!!.onValueChange(number)
            valueView!!.error = null
          } catch (e: NumberFormatException) {
            valueView!!.error = "Wrong integer format"
          }
        }
      }

      override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
      override fun afterTextChanged(editable: Editable) {}
    })
  }

  var value: Float
    get() = valueView!!.text.toString().toFloat()
    set(value) {
      valueView!!.setText(value.toString())
    }

  fun setOnFloatValueChangeListener(onLongValueChangeListener: OnFloatValueChangeListener?) {
    onFloatValueChangeListener = onLongValueChangeListener
  }

  interface OnFloatValueChangeListener {
    fun onValueChange(newValue: Float?)
  }

  init {
    init()
  }
}
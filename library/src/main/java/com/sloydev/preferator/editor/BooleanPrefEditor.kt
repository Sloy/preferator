package com.sloydev.preferator.editor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Switch
import com.sloydev.preferator.R

class BooleanPrefEditor @JvmOverloads constructor(
  context: Context?,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private var valueView: Switch? = null
  private var onBooleanValueChangeListener: OnBooleanValueChangeListener? = null
  private fun init() {
    LayoutInflater.from(context).inflate(R.layout.item_editor_boolean, this, true)
    valueView = findViewById(R.id.pref_value_boolean) as Switch
    valueView!!.setOnCheckedChangeListener { compoundButton, isChecked ->
      if (onBooleanValueChangeListener != null) {
        onBooleanValueChangeListener!!.onValueChange(isChecked)
      }
    }
  }

  var value: Boolean?
    get() = valueView!!.isChecked
    set(value) {
      valueView!!.isChecked = value!!
    }

  fun setOnBooleanValueChangeListener(onBooleanValueChangeListener: OnBooleanValueChangeListener?) {
    this.onBooleanValueChangeListener = onBooleanValueChangeListener
  }

  interface OnBooleanValueChangeListener {
    fun onValueChange(newValue: Boolean?)
  }

  init {
    init()
  }
}
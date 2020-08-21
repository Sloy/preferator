package com.sloydev.preferator.editor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Switch
import com.sloydev.preferator.R
import java.lang.Boolean.FALSE

class BooleanPrefEditor @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private var valueView: Switch
  var onBooleanValueChangeListener: ((newValue: Boolean) -> Unit)? = null

  init {
    LayoutInflater.from(context).inflate(R.layout.item_editor_boolean, this, true)
    valueView = findViewById(R.id.pref_value_boolean)
    valueView.setOnCheckedChangeListener { _, isChecked ->
      onBooleanValueChangeListener?.invoke(isChecked)

    }
  }

  var value: Boolean?
    get() = valueView.isChecked
    set(value) {
      valueView.isChecked = value != FALSE
    }
}
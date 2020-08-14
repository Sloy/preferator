package com.sloydev.preferator.editor

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import com.sloydev.preferator.R
import java.util.Arrays
import java.util.HashSet

class SetPrefEditor @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private val valueView: EditText
  var onSetValueChangeListener: ((Set<String?>) -> Unit)? = null

  init {
    LayoutInflater.from(context).inflate(R.layout.item_editor_string, this, true)
    valueView = findViewById(R.id.pref_value) as EditText
    valueView.addTextChangedListener(object : TextWatcher {
      override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
          onSetValueChangeListener?.invoke(stringToSet(charSequence.toString()))
      }

      override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
      override fun afterTextChanged(editable: Editable) {}
    })
  }

  var value: Set<String?>
    get() {
      val rawValue = valueView.text.toString()
      return stringToSet(rawValue)
    }
    set(value) {
      valueView.setText(setToString(value))
    }

  private fun stringToSet(rawValue: String): Set<String?> {
    val items = rawValue.split(",".toRegex()).toTypedArray()
    return HashSet(listOf(*items))
  }

  private fun setToString(set: Set<String?>): String {
    return TextUtils.join(",", set)
  }
}
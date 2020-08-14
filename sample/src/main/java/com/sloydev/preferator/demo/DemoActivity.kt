package com.sloydev.preferator.demo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.sloydev.preferator.Preferator.launch
import java.util.Arrays
import java.util.HashSet

class DemoActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_demo)
    findViewById(R.id.demo_edit).setOnClickListener { launch(this@DemoActivity) }
    findViewById(R.id.demo_prefill).setOnClickListener { prefillPreferences() }
  }

  private fun prefillPreferences() {
    fill(getPreferences(Context.MODE_PRIVATE))
    fill(PreferenceManager.getDefaultSharedPreferences(this))
    fill(getSharedPreferences("another file", Context.MODE_PRIVATE))
  }

  private fun fill(preferences: SharedPreferences) {
    preferences
      .edit()
      .putString("some_string", "a string value")
      .putInt("some_int", 42)
      .putLong("some_long", System.currentTimeMillis())
      .putBoolean("some_boolean", true)
      .putFloat("some_float", 3.14f)
      .putStringSet("some_set", HashSet(Arrays.asList("a", "b", "c")))
      .apply()
    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
  }
}
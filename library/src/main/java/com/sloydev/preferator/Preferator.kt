package com.sloydev.preferator

import android.content.Context
import android.content.Intent

object Preferator {
  @JvmStatic
  fun launch(context: Context) {
    context.startActivity(Intent(context, PreferatorActivity::class.java))
  }
}
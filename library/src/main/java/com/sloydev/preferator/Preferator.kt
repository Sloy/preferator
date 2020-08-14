package com.sloydev.preferator

import android.content.Context
import android.content.Intent

object Preferator {
  fun launch(context: Context) {
    context.startActivity(Intent(context, PreferatorActivity::class.java))
  }
}
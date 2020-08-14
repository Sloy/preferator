package com.sloydev.preferator

import android.content.Context
import android.content.Intent
import com.sloydev.preferator.PreferatorActivity

object Preferator {
  fun launch(context: Context) {
    context.startActivity(Intent(context, PreferatorActivity::class.java))
  }
}
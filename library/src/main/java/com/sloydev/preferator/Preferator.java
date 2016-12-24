package com.sloydev.preferator;


import android.content.Context;
import android.content.Intent;

public class Preferator {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PrefereitorActivity.class));
    }
}

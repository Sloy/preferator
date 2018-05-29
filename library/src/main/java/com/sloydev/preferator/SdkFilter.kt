package com.sloydev.preferator

import org.intellij.lang.annotations.Language

object SdkFilter {

    // HEY! YOU! Add your sdk here if you want it to be ignored :D
    @Language("RegExp")
    val ignoredSdkNamePatterns = listOf(
            // Adobe Analytics
            "APP_MEASUREMENT_CACHE",
            // Apptimize
            "apptimizenet.(.*)",
            // Braze
            "com.appboy.(.*)",
            // Chromium
            "WebViewChromiumPrefs",
            // Chuck
            "chuck_preferences",
            // ComScore
            "cSPrefs",
            // Custom Activity on Crash
            "custom_activity_on_crash",
            // Debug drawer
            "debug_drawer_(.*)",
            // Fabric
            "TwitterAdvertisingInfoPreferences",
            "io.fabric.sdk.android(.*)",
            "com.crashlytics.(.*)",
            // Google
            "com.google.(.*)",
            "fcm.(.*)",
            // Layer
            "layer(.*)",
            // Schibsted
            "com.schibsted.spt.tracking.(.*)",
            // Swrve
            "swrve_(.*)",
            // Xiti
            "ATPrefs"
    )

    private val ignoredSdkNameRegexs = ignoredSdkNamePatterns.map { it.toRegex() }

    fun isSdkPreference(name: String): Boolean {
        return ignoredSdkNameRegexs.find { it.matches(name) } != null
    }

}
package com.sloydev.preferator

import org.intellij.lang.annotations.Language

object SdkFilter {

    // HEY! YOU! Add your sdk here if you want it to be ignored :D
    @Language("RegExp")
    val ignoredSdkNamePatterns = listOf(
            "APP_MEASUREMENT_CACHE",
            "ATPrefs",
            "TwitterAdvertisingInfoPreferences",
            "WebViewChromiumPrefs",
            "cSPrefs",
            "chuck_preferences",
            "custom_activity_on_crash",
            "com.crashlytics.(.*)",
            "io.fabric.sdk.android(.*)",
            "debug_drawer_(.*)",
            "com.google.android.(.*)",
            "com.schibsted.spt.tracking.(.*)",
            "fcm.(.*)",
            "layer(.*)"
    )

    private val ignoredSdkNameRegexs = ignoredSdkNamePatterns.map { it.toRegex() }

    fun isSdkPreference(name: String): Boolean {
        return ignoredSdkNameRegexs.find { it.matches(name) } != null
    }

}
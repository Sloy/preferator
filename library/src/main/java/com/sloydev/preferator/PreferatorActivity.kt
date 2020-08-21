package com.sloydev.preferator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ShareCompat
import com.sloydev.preferator.editor.*
import java.io.File

class PreferatorActivity : AppCompatActivity() {
  private var sectionsView: ViewGroup? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_prefereitor)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    supportActionBar!!.subtitle = getApplicationName()

    sectionsView = findViewById(R.id.sections) as ViewGroup
    parsePreferences()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
      return true
    } else {
      return super.onOptionsItemSelected(item)
    }
  }

  private fun parsePreferences() {
    //TODO
    val rootPath = this.applicationInfo.dataDir + "/shared_prefs"
    val prefsFolder = File(rootPath)
    prefsFolder.list()
      .map {
        truncateXmlExtension(it)
      }
      .sortedWith(compareBy({ SdkFilter.isSdkPreference(it) }, { it }))
      .forEach {
        generateForm(it)
      }
  }

  private fun truncateXmlExtension(it: String): String {
    return if (it.endsWith(".xml"))
      it.substring(0, it.indexOf(".xml"))
    else
      it
  }

  private fun generateForm(prefsName: String) {
    val preferences = getSharedPreferences(prefsName)

    val filterValues = preferences.all.filterValues {
      it != null
    } as Map<String, Any>

    addSection(prefsName, filterValues.toList(), preferences)
  }

  private fun addSection(sectionTitle: String, entries: List<Pair<String, Any>>, preferences: SharedPreferences) {
    val sectionView = LayoutInflater.from(this).inflate(R.layout.item_section, sectionsView, false)
    val sectionNameContainer = sectionView.findViewById<ViewGroup>(R.id.section_name_container)
    val sectionNameView = sectionView.findViewById(R.id.section_name) as TextView
    val sectionArrowView = sectionView.findViewById(R.id.section_arrow) as ImageView
    val itemsView = sectionView.findViewById(R.id.section_items) as ViewGroup

    sectionNameView.text = sectionTitle
    sectionNameContainer.setOnClickListener {
      if (itemsView.visibility == View.VISIBLE) {
        itemsView.visibility = View.GONE
        sectionArrowView.setImageResource(R.drawable.ic_arrow_expand_black_24dp)
      } else {
        itemsView.visibility = View.VISIBLE
        sectionArrowView.setImageResource(R.drawable.ic_arrow_collapse_black_24dp)
      }
    }

    // Auto-collapse sdks
    if (SdkFilter.isSdkPreference(sectionTitle)) {
      itemsView.visibility = View.GONE
      sectionArrowView.setImageResource(R.drawable.ic_arrow_expand_black_24dp)
    }

    for (pref in entries) {
      val prefKey = pref.first
      val prefValue = pref.second
      val prefType = Type.of(prefValue)

      val itemView = LayoutInflater.from(this).inflate(R.layout.item_preference, itemsView, false)
      val nameView = itemView.findViewById<TextView>(R.id.pref_name)
      val typeView = itemView.findViewById<TextView>(R.id.pref_type)
      val moreView = itemView.findViewById<ImageView>(R.id.pref_more)

      nameView.text = prefKey
      typeView.text = prefType.typeName

      val editorContainer = itemView.findViewById(R.id.pref_value_editor_container) as ViewGroup
      val editorView = createEditorView(preferences, prefKey, prefValue, prefType)
      editorContainer.addView(editorView)

      val moreOptionsMenu = PopupMenu(this, moreView)
      moreOptionsMenu.inflate(R.menu.pref_more_options)
      moreOptionsMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
        if (item.itemId == R.id.menu_pref_delete) {
          preferences.edit().remove(prefKey).apply()
          itemsView.removeView(itemView)
          return@OnMenuItemClickListener true
        } else if (item.itemId == R.id.menu_pref_share) {
          val sharedMessage = String.format("\"%s\":\"%s\"", prefKey, prefValue.toString())
          ShareCompat.IntentBuilder.from(this@PreferatorActivity)
            .setText(sharedMessage)
            .setType("text/plain")
            .startChooser()
          return@OnMenuItemClickListener true
        }
        false
      })
      moreView.setOnClickListener { moreOptionsMenu.show() }

      itemsView.addView(itemView)
    }

    sectionsView!!.addView(sectionView)
  }

  private fun createEditorView(preferences: SharedPreferences, prefKey: String, prefValue: Any, prefType: Type): View {
    return when (prefType) {
      Type.STRING -> createStringEditorView(preferences, prefKey, prefValue as String)
      Type.INT -> createIntEditorView(preferences, prefKey, prefValue as Int)
      Type.LONG -> createLongEditorView(preferences, prefKey, prefValue as Long)
      Type.FLOAT -> createFloatEditorView(preferences, prefKey, prefValue as Float)
      Type.BOOLEAN -> createBooleanEditorView(preferences, prefKey, prefValue as Boolean)
      Type.SET -> createSetEditorView(preferences, prefKey, prefValue as Set<String>)
    }
  }

  private fun createStringEditorView(preferences: SharedPreferences, prefKey: String, prefValue: String): StringPrefEditor {
    val stringEditor = StringPrefEditor(this)
    stringEditor.value = prefValue
    stringEditor.onStringValueChangeListener = { newValue -> preferences.edit().putString(prefKey, newValue).apply() }
    return stringEditor
  }

  private fun createIntEditorView(preferences: SharedPreferences, prefKey: String, prefValue: Int?): IntPrefEditor {
    val intEditor = IntPrefEditor(this)
    intEditor.value = prefValue
    intEditor.onIntValueChangeListener = { newValue -> preferences.edit().putInt(prefKey, newValue).apply() }
    return intEditor
  }

  private fun createLongEditorView(preferences: SharedPreferences, prefKey: String, prefValue: Long?): LongPrefEditor {
    val longEditor = LongPrefEditor(this)
    longEditor.value = prefValue
    longEditor.onLongValueChangeListener = { newValue -> preferences.edit().putLong(prefKey, newValue!!).apply() }
    return longEditor
  }

  private fun createFloatEditorView(preferences: SharedPreferences, prefKey: String, prefValue: Float?): FloatPrefEditor {
    val floatEditor = FloatPrefEditor(this)
    floatEditor.value = prefValue
    floatEditor.onFloatValueChangeListener = { newValue -> preferences.edit().putFloat(prefKey, newValue).apply() }
    return floatEditor
  }

  private fun createBooleanEditorView(preferences: SharedPreferences, prefKey: String, prefValue: Boolean?): BooleanPrefEditor {
    val booleanEditor = BooleanPrefEditor(this)
    booleanEditor.value = prefValue
    booleanEditor.onBooleanValueChangeListener = { newValue -> preferences.edit().putBoolean(prefKey, newValue).apply() }
    return booleanEditor
  }

  private fun createSetEditorView(preferences: SharedPreferences, prefKey: String, prefValue: Set<String>): SetPrefEditor {
    val booleanEditor = SetPrefEditor(this)
    booleanEditor.value = prefValue
    booleanEditor.onSetValueChangeListener = { newValue -> preferences.edit().putStringSet(prefKey, newValue).apply() }
    return booleanEditor
  }

  private fun getSharedPreferences(name: String): SharedPreferences {
    return this.getSharedPreferences(name, Context.MODE_MULTI_PROCESS)
  }

  private enum class Type constructor(val typeName: String) {
    BOOLEAN("boolean"),
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    STRING("string"),
    SET("set");

    companion object {

      fun of(value: Any): Type {
        return when (value) {
          is String -> STRING
          is Int -> INT
          is Boolean -> BOOLEAN
          is Long -> LONG
          is Float -> FLOAT
          is Set<*> -> SET
          else -> {
            throw IllegalStateException("Not type matching found for " + value.javaClass.name)
          }
        }
      }
    }
  }

  private fun getApplicationName(): String {
    val applicationInfo = applicationInfo
    val stringId = applicationInfo.labelRes
    return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else getString(stringId)
  }

  companion object {
    private val TAG = "Preferator"
  }
}

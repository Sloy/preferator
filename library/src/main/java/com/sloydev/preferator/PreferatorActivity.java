package com.sloydev.preferator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sloydev.preferator.editor.BooleanPrefEditor;
import com.sloydev.preferator.editor.FloatPrefEditor;
import com.sloydev.preferator.editor.IntPrefEditor;
import com.sloydev.preferator.editor.LongPrefEditor;
import com.sloydev.preferator.editor.SetPrefEditor;
import com.sloydev.preferator.editor.StringPrefEditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PreferatorActivity extends AppCompatActivity {

    private static final String TAG = "Preferator";
    private ViewGroup sectionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefereitor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sectionsView = (ViewGroup) findViewById(R.id.sections);
        parsePreferences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void parsePreferences() {
        //TODO
        String rootPath = this.getApplicationInfo().dataDir + "/shared_prefs";
        File prefsFolder = new File(rootPath);
        String[] children = prefsFolder.list();
        if (children == null) {
            //TODO
            return;
        }
        for (int i = 0; i < children.length; i++) {
            String prefFileName = children[i];
            String prefName = prefFileName.endsWith(".xml")
                    ? prefFileName.substring(0, prefFileName.indexOf(".xml"))
                    : prefFileName;
            generateForm(prefName);
        }
    }

    private void generateForm(String prefsName) {
        SharedPreferences preferences = getSharedPreferences(prefsName);
        List<Pair<String, ?>> entries = new ArrayList<>();
        for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
            Log.d(TAG, String.format("(%s) %s = %s", prefsName, entry.getKey(), entry.getValue().toString()));
            entries.add(Pair.create(entry.getKey(), entry.getValue()));
        }

        addSection(prefsName, entries, preferences);
    }

    private void addSection(String sectionTitle, List<Pair<String, ?>> entries, final SharedPreferences preferences) {
        View sectionView = LayoutInflater.from(this).inflate(R.layout.item_section, sectionsView, false);
        TextView sectionNameView = (TextView) sectionView.findViewById(R.id.section_name);
        ViewGroup itemsView = (ViewGroup) sectionView.findViewById(R.id.section_items);

        sectionNameView.setText(sectionTitle);
        for (final Pair<String, ?> pref : entries) {
            final String prefKey = pref.first;
            Object prefValue = pref.second;
            Type prefType = Type.of(prefValue);

            View itemView = LayoutInflater.from(this).inflate(R.layout.item_preference, itemsView, false);
            TextView nameView = (TextView) itemView.findViewById(R.id.pref_name);
            TextView typeView = (TextView) itemView.findViewById(R.id.pref_type);

            nameView.setText(prefKey);
            typeView.setText(prefType.name);

            ViewGroup editorContainer = (ViewGroup) itemView.findViewById(R.id.pref_value_editor_container);
            View editorView = createEditorView(preferences, prefKey, prefValue, prefType);
            editorContainer.addView(editorView);

            itemsView.addView(itemView);
        }

        sectionsView.addView(sectionView);
    }

    private View createEditorView(final SharedPreferences preferences, final String prefKey, Object prefValue, Type prefType) {
        switch (prefType) {
            case STRING:
                return createStringEditorView(preferences, prefKey, (String) prefValue);
            case INT:
                return createIntEditorView(preferences, prefKey, (Integer) prefValue);
            case LONG:
                return createLongEditorView(preferences, prefKey, (Long) prefValue);
            case FLOAT:
                return createFloatEditorView(preferences, prefKey, (Float) prefValue);
            case BOOLEAN:
                return createBooleanEditorView(preferences, prefKey, (Boolean) prefValue);
            case SET:
                return createSetEditorView(preferences, prefKey, (Set<String>) prefValue);
        }
        throw new IllegalStateException("No editor view found for type " + prefType.name);
    }

    private StringPrefEditor createStringEditorView(final SharedPreferences preferences, final String prefKey, String prefValue) {
        StringPrefEditor stringEditor = new StringPrefEditor(this);
        stringEditor.setValue(prefValue);
        stringEditor.setOnStringValueChangeListener(new StringPrefEditor.OnStringValueChangeListener() {
            @Override
            public void onValueChange(String newValue) {
                preferences.edit().putString(prefKey, newValue).apply();
            }
        });
        return stringEditor;
    }

    private IntPrefEditor createIntEditorView(final SharedPreferences preferences, final String prefKey, Integer prefValue) {
        IntPrefEditor intEditor = new IntPrefEditor(this);
        intEditor.setValue(prefValue);
        intEditor.setOnIntValueChangeListener(new IntPrefEditor.OnIntValueChangeListener() {
            @Override
            public void onValueChange(Integer newValue) {
                preferences.edit().putInt(prefKey, newValue).apply();
            }
        });
        return intEditor;
    }

    private LongPrefEditor createLongEditorView(final SharedPreferences preferences, final String prefKey, Long prefValue) {
        LongPrefEditor longEditor = new LongPrefEditor(this);
        longEditor.setValue(prefValue);
        longEditor.setOnLongValueChangeListener(new LongPrefEditor.OnLongValueChangeListener() {
            @Override
            public void onValueChange(Long newValue) {
                preferences.edit().putLong(prefKey, newValue).apply();
            }
        });
        return longEditor;
    }

    private FloatPrefEditor createFloatEditorView(final SharedPreferences preferences, final String prefKey, Float prefValue) {
        FloatPrefEditor floatEditor = new FloatPrefEditor(this);
        floatEditor.setValue(prefValue);
        floatEditor.setOnFloatValueChangeListener(new FloatPrefEditor.OnFloatValueChangeListener() {
            @Override
            public void onValueChange(Float newValue) {
                preferences.edit().putFloat(prefKey, newValue).apply();
            }
        });
        return floatEditor;
    }

    private BooleanPrefEditor createBooleanEditorView(final SharedPreferences preferences, final String prefKey, Boolean prefValue) {
        BooleanPrefEditor booleanEditor = new BooleanPrefEditor(this);
        booleanEditor.setValue(prefValue);
        booleanEditor.setOnBooleanValueChangeListener(new BooleanPrefEditor.OnBooleanValueChangeListener() {
            @Override
            public void onValueChange(Boolean newValue) {
                preferences.edit().putBoolean(prefKey, newValue).apply();
            }
        });
        return booleanEditor;
    }

    private SetPrefEditor createSetEditorView(final SharedPreferences preferences, final String prefKey, Set<String> prefValue) {
        SetPrefEditor booleanEditor = new SetPrefEditor(this);
        booleanEditor.setValue(prefValue);
        booleanEditor.setOnSetValueChangeListener(new SetPrefEditor.OnSetValueChangeListener() {
            @Override
            public void onValueChange(Set<String> newValue) {
                preferences.edit().putStringSet(prefKey, newValue).apply();
            }
        });
        return booleanEditor;
    }


    private SharedPreferences getSharedPreferences(String name) {
        return this.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
    }

    private enum Type {
        BOOLEAN("boolean"),
        INT("int"),
        LONG("long"),
        FLOAT("float"),
        STRING("string"),
        SET("set");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public static Type of(Object value) {
            if (value instanceof String) {
                return STRING;
            } else if (value instanceof Integer) {
                return INT;
            } else if (value instanceof Boolean) {
                return BOOLEAN;
            } else if (value instanceof Long) {
                return LONG;
            } else if (value instanceof Float) {
                return FLOAT;
            } else if (value instanceof Set) {
                return SET;
            } else {
                throw new IllegalStateException("Not type matching found for " + value.getClass().getName());
            }

        }
    }
}

package com.example.tarea3_1;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class Fragmento_Preferencias extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
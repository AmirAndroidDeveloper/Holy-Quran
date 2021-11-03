package com.example.holyquran.ui.settings

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.holyquran.R

import androidx.core.content.ContextCompat.getSystemService
import android.media.AudioManager
import androidx.core.app.NotificationCompat
import androidx.preference.SwitchPreferenceCompat


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

    }
}
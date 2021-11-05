package com.example.holyquran.ui.settings

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.holyquran.R
import androidx.preference.SwitchPreferenceCompat


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val myPrefSwitch = findPreference<SwitchPreferenceCompat>("vibrate")
        myPrefSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { pref, newValue ->

                if (pref is SwitchPreferenceCompat) {
                    val value = newValue as Boolean
                    if (value) Toast.makeText(requireContext(), "ON", Toast.LENGTH_SHORT)
                        .show()
                    else Toast.makeText(requireContext(), "OFF", Toast.LENGTH_SHORT).show()
                }
                true
            }
        val myPref = findPreference("backUp") as Preference?
        myPref!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                Toast.makeText(activity, "WORK", Toast.LENGTH_SHORT).show()
                true
            }

        val passwordPrf = findPreference("password") as Preference?
        passwordPrf!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {

                val passKet = "Password"
                val sharedPreference =
                    requireActivity().getSharedPreferences(passKet, Context.MODE_PRIVATE)
                sharedPreference.getString("password", "defaultName")
                if (sharedPreference.getBoolean("passwordStatus", false)) {
                    this.findNavController()
                        .navigate(SettingsFragmentDirections.actionSettingsFragmentToCheckPasswordFragment())
                    Toast.makeText(activity, "has password", Toast.LENGTH_SHORT).show()
                    true
                } else {
                    this.findNavController()
                        .navigate(SettingsFragmentDirections.actionSettingsFragmentToCreatePasswordFragment())
                    true
                }


            }

    }
}
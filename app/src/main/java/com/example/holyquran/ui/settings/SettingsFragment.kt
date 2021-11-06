package com.example.holyquran.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.ebner.roomdatabasebackup.core.RoomBackup
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.ui.mainPage.SplashActivity
import kotlinx.android.synthetic.main.item.*
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.text.Editable
import android.text.InputType
import androidx.core.widget.doOnTextChanged
import androidx.preference.*
import androidx.test.core.app.ApplicationProvider


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val myPrefSwitch = findPreference<SwitchPreferenceCompat>("vibrate")
        myPrefSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { pref, newValue ->

                if (pref is SwitchPreferenceCompat) {
                    val value = newValue as Boolean
                    if (value) {
                        Toast.makeText(requireContext(), "ON", Toast.LENGTH_SHORT).show()

                        val passKey = "vibratePhone"
                        val sharedPreference =
                            requireActivity().getSharedPreferences(passKey, Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.putBoolean(passKey, true)
                        editor.apply()

                    } else {
                        Toast.makeText(requireContext(), "OFF", Toast.LENGTH_SHORT).show()
                        val passKey = "vibratePhone"
                        val sharedPreference =
                            requireActivity().getSharedPreferences(passKey, Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.putBoolean(passKey, false)
                        editor.apply()
                    }
                }
                true
            }
        val myPref = findPreference("backUp") as Preference?
        myPref!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                Toast.makeText(activity, "backup", Toast.LENGTH_SHORT).show()
                backup()
                true
            }

        val passwordPrf = findPreference("password") as Preference?
        passwordPrf!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {

                val passKey = "Password"
                val sharedPreference =
                    requireActivity().getSharedPreferences(passKey, Context.MODE_PRIVATE)
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

        val myPrefRestore = findPreference("restore") as Preference?
        myPrefRestore!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                Toast.makeText(activity, "restore", Toast.LENGTH_SHORT).show()
                restore()
                true
            }
        val editTextPreference =
            preferenceManager.findPreference<EditTextPreference>("appName")
        editTextPreference!!.setOnBindEditTextListener { editText ->
            val appName: String = resources.getString(R.string.app_name)
            editText.setText(appName)
        }
//        val myPrefList = findPreference("currency") as Preference?
//        myPrefList!!.onPreferenceClickListener =
//            Preference.OnPreferenceClickListener {
//                Toast.makeText(activity, "HI", Toast.LENGTH_SHORT).show()
//
//                true
//            }
        val listPreference = findPreference("currency") as ListPreference?
        listPreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                val entry = preference.entries.get(index)
                val entryvalue = preference.entryValues.get(index)
                Log.i(
                    "selected val",
                    " position - $index value - $entry, entryvalue - $entryvalue "
                )
                if (index == 1) {
                    val passKey = "currency_status"
                    val sharedPreference =
                        requireActivity().getSharedPreferences(passKey, Context.MODE_PRIVATE)
                    val editor = sharedPreference.edit()
                    editor.putString(passKey, " ریال")
                    editor.apply()
                } else if (index == 0) {
                    val passKey = "currency_status"
                    val sharedPreference =
                        requireActivity().getSharedPreferences(passKey, Context.MODE_PRIVATE)
                    val editor = sharedPreference.edit()
                    editor.putString(passKey, " تومان")
                    editor.apply()
                }
            }
            true
        }
    }


    private fun backup() {
        context?.let {
            RoomBackup()
                .context(it)
                .database(UserDatabase.getInstance(it))
                .enableLogDebug(true)
                .backupIsEncrypted(true)
                .customEncryptPassword("Amir")
                .useExternalStorage(true)
                .maxFileCount(5)
                .apply {
                    onCompleteListener { success, message ->
                        Log.d("TAG", "success: $success, message: $message")
                    }
                }
                .backup()
        }
    }

    private fun restore() {
        activity?.let {
            RoomBackup()
                .context(it)
                .database(UserDatabase.getInstance(it))
                .enableLogDebug(true)
                .backupIsEncrypted(true)
                .customEncryptPassword("Amir")
                .useExternalStorage(true)
                .apply {
                    onCompleteListener { success, message ->
                        Log.d("TAG", "success: $success, message: $message")
                        if (success) restartApp(Intent(context, SplashActivity::class.java))
                    }

                }
                .restore()
        }
    }


}

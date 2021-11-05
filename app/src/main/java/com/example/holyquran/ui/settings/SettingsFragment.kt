package com.example.holyquran.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.holyquran.R
import androidx.preference.SwitchPreferenceCompat
import com.ebner.roomdatabasebackup.core.RoomBackup
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.ui.introPages.IntroActivity
import com.example.holyquran.ui.mainPage.MainActivity
import com.example.holyquran.ui.mainPage.SplashActivity
import ir.androidexception.roomdatabasebackupandrestore.OnWorkFinishListener

import ir.androidexception.roomdatabasebackupandrestore.Backup
import kotlinx.android.synthetic.main.item.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


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
                Toast.makeText(activity, "backup", Toast.LENGTH_SHORT).show()
                backup()
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

        val myPrefRestore = findPreference("restore") as Preference?
        myPrefRestore!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                Toast.makeText(activity, "restore", Toast.LENGTH_SHORT).show()
                restore()
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
//                        if (success) restartApp(Intent(context, SplashActivity::class.java))
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

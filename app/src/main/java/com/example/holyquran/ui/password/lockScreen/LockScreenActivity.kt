package com.example.holyquran.ui.password.lockScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.holyquran.R
import com.example.holyquran.databinding.ActivityLockScreenBinding
import com.example.holyquran.ui.mainPage.MainActivity

class LockScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)
        val binding =
            DataBindingUtil.setContentView<ActivityLockScreenBinding>(this, R.layout.activity_lock_screen)

        val passKet = "Password"
        val sharedPreference = getSharedPreferences(passKet, Context.MODE_PRIVATE)
        sharedPreference.getString("password", "defaultName")
        Toast.makeText(this, sharedPreference.getString("password", ""), Toast.LENGTH_SHORT).show()



        binding.submit.setOnClickListener {
            if (binding.password.text.toString() == sharedPreference.getString("password", "")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
    override fun onBackPressed() {

    }

}
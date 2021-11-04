package com.example.holyquran.ui.lockScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.holyquran.R
import com.example.holyquran.databinding.ActivityLogInBinding
import com.example.holyquran.databinding.ActivityMainBinding
import com.example.holyquran.ui.mainPage.MainActivity

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val binding =
            DataBindingUtil.setContentView<ActivityLogInBinding>(this, R.layout.activity_log_in)

        val editTextValue = binding.password.text.toString()
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
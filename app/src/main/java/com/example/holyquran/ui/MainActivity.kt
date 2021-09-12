package com.example.holyquran.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.holyquran.R
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.holyquran.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var toast: Toast? = null
    private var lastBackPressTime: Long = 0
    lateinit var navController: NavController;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        this.supportActionBar?.hide();

        drawerLayout = binding.drawerLayout
        navController = this.findNavController(R.id.fragmentContainerView)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(binding.navView, navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.fragmentContainerView)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        if (lastBackPressTime < System.currentTimeMillis() - 4000) {
            lastBackPressTime = System.currentTimeMillis()
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            builder.setIcon(R.drawable.warning)
            builder.setTitle("خروج از برنامه ")
            builder.setMessage("از برنامه خارج میشوید؟")
                .setCancelable(false)
                .setPositiveButton("بله",
                    DialogInterface.OnClickListener { dialog, id -> finish() })
                .setNegativeButton("خیر",
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
            val alert: AlertDialog = builder.create()
            alert.show()

        } else {
            toast?.cancel()
            super.onBackPressed()
        }
    }


}


package com.example.holyquran.ui.introPages

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.holyquran.R
import com.example.holyquran.databinding.ActivityIntroBinding
import com.example.holyquran.ui.mainPage.MainActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    val fragment1 = Slider_Fragment()
    val fragment2 = SliderFragment2()
    val fragment3 = SliderFragment3()
    lateinit var mIntroViewModel: IntroViewModel
    lateinit var adapter: IntroAdapter
    val pref_show_intro = "Intro"
    lateinit var preference: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityIntroBinding>(this, R.layout.activity_intro)

//        mIntroViewModel.skipButton.observe(this, Observer {
//          if (it==true) {
//              Toast.makeText(applicationContext, "Hi", Toast.LENGTH_SHORT).show()
//          }
//        })


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        preference = getSharedPreferences("IntroSlider", MODE_PRIVATE)
        this.supportActionBar?.hide();
        if (!preference.getBoolean(pref_show_intro, true)) {
            startActivity(Intent(this@IntroActivity,MainActivity::class.java))
            finish()
        }
        adapter = IntroAdapter(
            supportFragmentManager
        )
        adapter.list.add(fragment1)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)

        view_pager.adapter = adapter

        btn_skip.setOnClickListener {
            skip()
        }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == adapter.list.size - 1) {
                }

                when (view_pager.currentItem) {
                    0 -> {
                        indicator_1.setTextColor(Color.BLACK)
                        indicator_2.setTextColor(Color.GRAY)
                        indicator_3.setTextColor(Color.GRAY)
                    }
                    1 -> {
                        indicator_1.setTextColor(Color.GRAY)
                        indicator_2.setTextColor(Color.BLACK)
                        indicator_3.setTextColor(Color.GRAY)
                    }
                    2 -> {
                        indicator_1.setTextColor(Color.GRAY)
                        indicator_2.setTextColor(Color.GRAY)
                        indicator_3.setTextColor(Color.BLACK)
                    }
                }
            }

        })
    }
    private fun skip() {
        view_pager.currentItem = 2
    }


    fun goToWelcomeScreen() {
        startActivity(Intent(this@IntroActivity, MainActivity::class.java))
        finish()
        val editor = preference.edit()
        editor.putBoolean(pref_show_intro, false)
        editor.apply()
    }

}


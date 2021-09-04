package com.example.holyquran.ui.introPages

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentSlider3Binding
import com.example.holyquran.ui.MainActivity

class SliderFragment3 : Fragment() {
    val pref_show_intro = "Intro"
    lateinit var preference: SharedPreferences
    lateinit var mSliderFragment3Binding: FragmentSlider3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mSliderFragment3Binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_slider_3, container, false)
        mSliderFragment3Binding.finishBtn.setAlpha(0f)
        mSliderFragment3Binding.finishBtn.animate().alpha(1f).setDuration(1500);

        mSliderFragment3Binding.finishBtn.setOnClickListener {
            goToAttract(it)
        }
        preference = activity?.getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)!!
        if (!preference.getBoolean(pref_show_intro, true)) {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        return mSliderFragment3Binding.root
    }
    fun goToAttract(v: View?) {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        val editor = preference.edit()
        editor.putBoolean(pref_show_intro, false)
        editor.apply()
    }

}

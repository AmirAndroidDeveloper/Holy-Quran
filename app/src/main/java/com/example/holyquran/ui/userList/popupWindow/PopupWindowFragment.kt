package com.example.holyquran.ui.userList.popupWindow

import android.app.ActionBar
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holyquran.R
import androidx.appcompat.app.AppCompatActivity




class PopupWindowFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val display: Display = requireActivity().getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        val width: Int = size.x
        val height: Int = size.y
        requireActivity().getWindow().setLayout((width * .8).toInt(), (height * .6).toInt());



        return inflater.inflate(R.layout.fragment_popop_window, container, false)


    }

}
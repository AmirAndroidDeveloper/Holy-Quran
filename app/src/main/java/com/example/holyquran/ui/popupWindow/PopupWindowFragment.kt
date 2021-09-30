package com.example.holyquran.ui.popupWindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentPopupWindowBinding
import android.view.*
import android.widget.PopupWindow
import android.view.WindowManager
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity

import android.R.layout
import android.util.DisplayMetrics


class PopupWindowFragment : Fragment() {
    lateinit var mPopupWindowBinding: FragmentPopupWindowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPopupWindowBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_popup_window,
                container,
                false
            )
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val dm = DisplayMetrics()
        activity?.windowManager!!.defaultDisplay.getMetrics(dm)

        var width = dm.widthPixels
        var height = dm.heightPixels
        requireActivity().window.setLayout(width.times(.8).toInt(), (height.times(.6)).toInt())



//        val popupView: View = LayoutInflater.from(activity).inflate(R.layout.fragment_popup_window, null)
//        val popupWindow = PopupWindow(
//            popupView,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//        popupWindow.showAsDropDown(popupView, 0, 0)



        return mPopupWindowBinding.root
    }

}
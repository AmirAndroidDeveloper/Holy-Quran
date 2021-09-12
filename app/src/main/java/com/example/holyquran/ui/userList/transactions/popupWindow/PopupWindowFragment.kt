package com.example.holyquran.ui.userList.transactions.popupWindow

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentPopopWindowBinding
import android.view.WindowManager

import android.os.Build.VERSION_CODES

import android.os.Build.VERSION

import android.widget.PopupWindow








class PopupWindowFragment : Fragment() {
    lateinit var mPopupWindowBinding: FragmentPopopWindowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPopupWindowBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_popop_window,
                container,
                false
            )

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()


//        val display: Display = requireActivity().getWindowManager().getDefaultDisplay()
//        val size = Point()
//        display.getSize(size)
//        val width: Int = size.x
//        val height: Int = size.y
//        requireActivity().getWindow().setLayout((width * .8).toInt(), (height * .6).toInt());


        return mPopupWindowBinding.root


    }
    fun PopupWindow.dimBehind() {
        val container = contentView.rootView
        val context = contentView.context
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.3f
        wm.updateViewLayout(container, p)

    }

}
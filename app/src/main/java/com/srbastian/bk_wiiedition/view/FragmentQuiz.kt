package com.srbastian.bk_wiiedition.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.srbastian.bk_wiiedition.R
import com.srbastian.bk_wiiedition.databinding.FragmentQuizBinding

class FragmentQuiz : Fragment() {
    lateinit var fragmentQuizBinding : FragmentQuizBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentQuizBinding = FragmentQuizBinding.inflate(inflater, container, false)
        fragmentQuizBinding.btnA.setOnClickListener {  }
        fragmentQuizBinding.btnB.setOnClickListener {  }
        fragmentQuizBinding.btnC.setOnClickListener {  }
        fragmentQuizBinding.btnD.setOnClickListener {  }
        fragmentQuizBinding.btnNext.setOnClickListener {  }

        return fragmentQuizBinding.root
    }
}
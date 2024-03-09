package com.srbastian.bk_wiiedition.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.srbastian.bk_wiiedition.R
import com.srbastian.bk_wiiedition.databinding.FragmentResultBinding

class FragmentResult : Fragment() {
    lateinit var fragmentResultBinding : FragmentResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentResultBinding = FragmentResultBinding.inflate(inflater, container, false)

        fragmentResultBinding.btnNewQuiz.setOnClickListener {

        }

        fragmentResultBinding.btnExit.setOnClickListener {

        }
        // Inflate the layout for this fragment
        return fragmentResultBinding.root
    }
}
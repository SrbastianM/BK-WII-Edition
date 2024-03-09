package com.srbastian.bk_wiiedition.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.srbastian.bk_wiiedition.R
import com.srbastian.bk_wiiedition.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    lateinit var homeBinding: FragmentHomeBinding
    lateinit var btnStart : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        homeBinding.btnStart.setOnClickListener {

        }
        // Inflate the layout for this fragment
        return  homeBinding.root
    }
}
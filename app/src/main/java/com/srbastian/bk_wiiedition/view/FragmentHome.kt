package com.srbastian.bk_wiiedition.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.srbastian.bk_wiiedition.R
import com.srbastian.bk_wiiedition.database.DatabaseCopyHelper
import com.srbastian.bk_wiiedition.databinding.FragmentHomeBinding
import java.lang.Exception

class FragmentHome : Fragment() {
    lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        createAndOpenDataBase()
        Log.d("Pair", createAndOpenDataBase().toString())
        homeBinding.btnStart.setOnClickListener {
            val direction = FragmentHomeDirections.actionFragmentHomeToFragmentQuiz()
            this.findNavController().navigate(direction)
        }
        // Inflate the layout for this fragment
        return  homeBinding.root
    }
    private fun createAndOpenDataBase() : Pair<Boolean,String> {
        return try {
            val helper = DatabaseCopyHelper(requireActivity())
            helper.createDataBase()
            helper.openDataBase()
            Pair(true, "DB Created and open successfully")

        } catch (e: Exception){
            Log.d("bug", "no funciona")
            e.printStackTrace()
            Pair(false, "DB open and creation failed")
        }
    }
}
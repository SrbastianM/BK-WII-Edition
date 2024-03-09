package com.srbastian.bk_wiiedition.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srbastian.bk_wiiedition.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000L)
        setContentView(R.layout.activity_main)
    }
}
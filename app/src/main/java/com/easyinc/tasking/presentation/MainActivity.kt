package com.easyinc.tasking.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easyinc.tasking.R
import com.easyinc.tasking.common.extentions.replaceAnimatedFragment
import com.easyinc.tasking.presentation.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceAnimatedFragment(
            R.id.container,
            HomeFragment())

    }
}

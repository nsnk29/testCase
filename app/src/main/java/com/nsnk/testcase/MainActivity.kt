package com.nsnk.testcase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nsnk.testcase.data.retrofit.ImgurApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
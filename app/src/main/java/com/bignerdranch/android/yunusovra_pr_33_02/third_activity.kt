package com.bignerdranch.android.yunusovra_pr_33_02

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class third_activity : AppCompatActivity() {
    private lateinit var information: TextView
    private lateinit var result: TextView
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        result = findViewById(R.id.result)
        information = findViewById(R.id.info)
        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val fromUnit = pref.getString("from", "")
        val toUnit = pref.getString("to", "DEFAULT")
        val number = pref.getString("number", "DEFAULT")
        val convertedNumber = pref.getString("convert", "DEFAULT")

        val stringInfo = "Введенное число\n $number из $fromUnit в $toUnit"
        information.text = stringInfo
        result.text = convertedNumber
    }
}


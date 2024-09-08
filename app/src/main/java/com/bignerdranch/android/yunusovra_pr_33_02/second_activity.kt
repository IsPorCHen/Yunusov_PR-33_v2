package com.bignerdranch.android.yunusovra_pr_33_02

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class second_activity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var firstSpinner: Spinner
    private lateinit var secondSpinner: Spinner
    private lateinit var button: Button
    private lateinit var result: TextView
    private lateinit var number: EditText
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        firstSpinner = findViewById(R.id.fist_spinner)
        secondSpinner = findViewById(R.id.second_spinner)
        button = findViewById(R.id.calcButton)
        number = findViewById(R.id.number)
        result = findViewById(R.id.textview1)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_select,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        firstSpinner.adapter = adapter
        firstSpinner.onItemSelectedListener = this
        secondSpinner.adapter = adapter
        secondSpinner.onItemSelectedListener = this

        button.setOnClickListener {
            if (number.text.isEmpty()) {
                showAlert("Ошибка", "Обнаружены пустые поля")
            } else {
                val intent = Intent(this, third_activity::class.java)
                startActivity(intent)
            }
        }

        number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convertation()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun convertation() {
        val numberText = number.text.toString().trim()

        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val ed = pref.edit()
        ed.putString("from", firstSpinner.selectedItem.toString())
        ed.putString("to", secondSpinner.selectedItem.toString())
        ed.putString("number", numberText)
        ed.apply()

        val convertedNumber = convertUnits(
            pref.getString("from", ""),
            pref.getString("to", ""),
            (pref.getString("number", "DEFAULT"))?.toDoubleOrNull()?: 0.0)

        result.text = String.format("%.2f", convertedNumber)
        ed.putString("convert", result.text.toString())
        ed.apply()
    }

    private fun convertUnits(fromUnit: String?, toUnit: String?, number: Double): Double {
        if (fromUnit == toUnit) return number

        var numberInBytes = when (fromUnit) {
            "байт" -> number
            "килобайт" -> number * 1024
            "мегабайт" -> number * 1024 * 1024
            "гигабайт" -> number * 1024 * 1024 * 1024
            else -> number
        }

        return when (toUnit) {
            "байт" -> numberInBytes
            "килобайт" -> numberInBytes / 1024
            "мегабайт" -> numberInBytes / (1024 * 1024)
            "гигабайт" -> numberInBytes / (1024 * 1024 * 1024)
            else -> numberInBytes
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        convertation()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun showAlert(title: String, message: String) {
        val alert = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .create()
            .show()
    }
}


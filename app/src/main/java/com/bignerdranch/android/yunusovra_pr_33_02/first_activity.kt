package com.bignerdranch.android.yunusovra_pr_33_02

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class first_activity : AppCompatActivity() {
    private var reentry: Boolean = false
    private lateinit var button: Button
    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var pref: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        button = findViewById(R.id.startButton)
        login = findViewById(R.id.login)
        password = findViewById(R.id.password)

        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val ed = pref.edit()
        var commit = pref.getBoolean("commit", false)

        if (!commit) {
            reentry = true
            login.setText(login.getText().toString() + "ects")
            password.setText(password.getText().toString() + "ects2023")

            val ed = pref.edit()
            ed.putString("login", login.text.toString())
            ed.putString("password", password.text.toString())
            ed.putBoolean("commit", reentry)
            ed.apply()
        }

        button.setOnClickListener {
            if (login.text.isEmpty() || password.text.isEmpty())
            {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Обнаружены пустые поля")
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            }
            else if (login.text.toString() != pref.getString("login", "DEFAULT")
                || password.text.toString() != pref.getString("password", "DEFAULT")
            ) {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Неверный логин или пароль")
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            } else {
                var intent = Intent(this, second_activity::class.java)
                startActivity(intent)
            }
        }
    }
}
package com.example.calc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var btnSimple: Button
    private lateinit var btnAdvanced: Button
    private lateinit var btnAbout: Button
    private lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSimple = findViewById(R.id.btnSimple)
        btnSimple.setOnClickListener {
            val intent: Intent = Intent(this, SimpleCalcActivity::class.java)
            startActivity(intent)
        }

        btnAdvanced = findViewById(R.id.btnAdvanced)
        btnAdvanced.setOnClickListener {
            val intent: Intent = Intent(this, AdvancedCalcActivity::class.java)
            startActivity(intent)
        }

        btnAbout = findViewById(R.id.btnAbout)
        btnAbout.setOnClickListener {
            val intent: Intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        btnExit = findViewById(R.id.btnExit)
        btnExit.setOnClickListener {
            finish()
            exitProcess(0)
        }
    }
}
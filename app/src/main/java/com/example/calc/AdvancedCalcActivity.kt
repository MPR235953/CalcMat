package com.example.calc

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdvancedCalcActivity: SimpleCalcActivity() {
    private val functions: List<String> = listOf("sin(", "cos(", "tan(", "ln(", "log(")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_calc)

        tvResult = findViewById(R.id.tvResult)
        tvCurrent = findViewById(R.id.tvCurrent)
    }

    fun functionOnClick(view: View){
        if(view is Button){
            if(current == "0")
                current = ""
            appendToCurrent((view.text) as String + '(')
        }
    }

    fun bracketOnClick(view: View){
        if(view is Button)
            appendToCurrent(view.text as String)
    }

    override fun clearLastOnClick(view: View){
        val len: Int = current.length
        if(len > 0) {
            var strNumToDel: Int = 1
            for(f in functions){
                if(current.endsWith(f)) {
                    strNumToDel = f.length
                    break
                }
            }
            current = current.subSequence(0, len - strNumToDel) as String
            if (current == "") current = "0"
            tvCurrent.setText(current)
        }
    }

    override fun operationOnClick(view: View){
        if(view is Button) {
            if(current.last() in "/*-+^" && view.text.last() in "/*-+^")
                current = current.subSequence(0, current.length - 1) as String
            appendToCurrent(view.text as String)
        }
    }
}
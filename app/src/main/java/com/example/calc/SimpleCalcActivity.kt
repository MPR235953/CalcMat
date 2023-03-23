package com.example.calc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import org.mariuszgromada.math.mxparser.Expression
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

open class SimpleCalcActivity: AppCompatActivity() {

    protected lateinit var tvResult: TextView
    protected lateinit var tvCurrent: TextView
    protected var current: String = "0"
    //protected var startBracket: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_calc)

        tvResult = findViewById(R.id.tvResult)
        tvCurrent = findViewById(R.id.tvCurrent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val resultSaver: CharSequence = tvResult.text as String
        val currentSaver: CharSequence = tvCurrent.text as String
        outState.putCharSequence("savedResult", resultSaver)
        outState.putCharSequence("savedCurrent", currentSaver)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredResult: CharSequence? = savedInstanceState.getCharSequence("savedResult")
        val restoredCurrent: CharSequence? = savedInstanceState.getCharSequence("savedCurrent")
        tvResult.setText(restoredResult)
        tvCurrent.setText(restoredCurrent)
        current = restoredCurrent as String
    }

    //Done
    fun appendToCurrent(value: String){
        current += value
        tvCurrent.setText(current)
    }

    //Done
    fun clearAllOnClick(view: View){
        current = "0"
        tvCurrent.setText(current)
        tvResult.setText(current)
    }

    //Done
    open fun clearLastOnClick(view: View){
        val len: Int = current.length
        if(len > 0) {
            current = current.subSequence(0, len - 1) as String
            if (current == "") current = "0"
            tvCurrent.setText(current)
        }
    }

    //Done
    fun changeSignOnClick(view: View){
        val len: Int = current.length
        if(len == 0 || current == "0")    // if equation is empty just enter minus
            appendToCurrent("-")
        else if(current.toIntOrNull() != null || current.toFloatOrNull() != null){
            if(current[0] == '-')
                current = current.subSequence(1, current.length) as String
            else current = '-' + current
            tvCurrent.setText(current)
        }
        else{
            val split = current.split("-", "+", "*", "/", "(", ")", "%", "√", "^") // split whole equation
            val subStr = current.subSequence(0, len - split[split.size - 1].length) // delete last number without sign
            val lastChar = subStr.last()    // get operand (minus or sth else)
            if(lastChar == '-') {   // if minus
                current = subStr.subSequence(0, subStr.length - 1) as String  // delete from equation
                tvCurrent.setText(current)  // set current equation to tv
                if(current.length == 0)
                    appendToCurrent(split[split.size - 1])
                else
                    appendToCurrent('+' + split[split.size - 1])  // enter delete last number from equation
            }
            else if(lastChar == '+'){
                current = subStr.subSequence(0, subStr.length - 1) as String  // delete from equation
                tvCurrent.setText(current)  // set current equation to tv
                appendToCurrent('-' + split[split.size - 1])  // enter delete last number from equation
            }
            else if(split[split.size - 1].toIntOrNull() != null || split[split.size - 1].toFloatOrNull() != null){
                current = subStr as String  // delete from equation
                tvCurrent.setText(current)  // set current equation to tv
                appendToCurrent('-' + split[split.size - 1])
            }
            else{
                current = subStr.subSequence(0, subStr.length - 1) as String  // delete from equation
                tvCurrent.setText(current)  // set current equation to tv
                appendToCurrent('-' + split[split.size - 1])  // enter delete last number from equation
            }
        }
    }

    /*
    fun bracketsOnClick(view: View){
        if(startBracket){
            appendToCurrent("(")
            startBracket = false
        }
        else{
            appendToCurrent(")")
            startBracket = true
        }
    }*/

    //Done
    fun dotOnClick(view: View){
        if(view is Button && current.last() != '.') {
            appendToCurrent(view.text as String)
        }
    }

    //Done
    fun numberOnClick(view: View){
        if(view is Button) {
            if(current.last() == '0') current = current.subSequence(0, current.length - 1) as String
            appendToCurrent(view.text as String)
        }
    }

    //Done
    open fun operationOnClick(view: View){
        if(view is Button) {
            if(current.last() in "/*-+" && view.text.last() in "/*-+")
                current = current.subSequence(0, current.length - 1) as String
            appendToCurrent(view.text as String)
        }
    }

    //Done
    fun resultOnClick(view: View){
        val exp = Expression(current)
        val result: String = java.lang.String.valueOf(exp.calculate())
        if(result == "NaN"){
            val toast = Toast.makeText(applicationContext, "Invalid Input", Toast.LENGTH_SHORT)
            toast.show()
        }
        tvResult.setText(result)
    }
}
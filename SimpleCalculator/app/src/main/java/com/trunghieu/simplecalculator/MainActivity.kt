package com.trunghieu.simplecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var ce: Button
    private lateinit var c: Button
    private lateinit var back: Button
    private lateinit var div: Button
    private lateinit var num7: Button
    private lateinit var num8: Button
    private lateinit var num9: Button
    private lateinit var mul: Button
    private lateinit var num4: Button
    private lateinit var num5: Button
    private lateinit var num6: Button
    private lateinit var sub: Button
    private lateinit var num1: Button
    private lateinit var num2: Button
    private lateinit var num3: Button
    private lateinit var add: Button
    private lateinit var oppo: Button
    private lateinit var num0: Button
    private lateinit var dot: Button
    private lateinit var equal: Button
    private lateinit var res: TextView

    private var calculation = ""
    private var calculation1 = ""
    private var calculation2 = ""
    private var state = 1
    private var state1 = 0
    private var op1 = 0
    private var op2 = 0
    private var cal = 0
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ce = findViewById(R.id.button_ce)
        c = findViewById(R.id.button_c)
        back = findViewById(R.id.button_bs)
        div = findViewById(R.id.button_div)
        num7 = findViewById(R.id.button_7)
        num8 = findViewById(R.id.button_8)
        num9 = findViewById(R.id.button_9)
        mul = findViewById(R.id.button_mul)
        num4 = findViewById(R.id.button_4)
        num5 = findViewById(R.id.button_5)
        num6 = findViewById(R.id.button_6)
        sub = findViewById(R.id.button_sub)
        num1 = findViewById(R.id.button_1)
        num2 = findViewById(R.id.button_2)
        num3 = findViewById(R.id.button_3)
        add = findViewById(R.id.button_add)
        oppo = findViewById(R.id.button_sign)
        num0 = findViewById(R.id.button_0)
        dot = findViewById(R.id.button_dot)
        equal = findViewById(R.id.button_eq)
        res = findViewById(R.id.textView)

        fun onClick(v: View?) {
            v?.let {
                val id = it.id
                when (id) {
                    num0.id, num1.id, num2.id, num3.id, num4.id, num5.id,
                    num6.id, num7.id, num8.id, num9.id -> {
                        state1 = 0
                        calculation += (it as Button).text
                        calculation1 += it.text
                        result += it.text
                        res.text = calculation
                    }

                    add.id, sub.id, mul.id, div.id -> {
                        cal = when (id) {
                            add.id -> 1
                            sub.id -> 2
                            mul.id -> 3
                            div.id -> 4
                            else -> 0
                        }

                        if (state == 1) {
                            if (calculation == "" && state1 != 1) {
                                return
                            } else if (state1 == 1) {
                                calculation = res.text.toString() + (it as Button).text
                                res.text = calculation
                                return
                            } else {
                                state = 2
                                op1 = calculation1.toInt()
                            }
                        }

                        calculation1 = ""
                        calculation += (it as Button).text
                        calculation2 = calculation
                        res.text = calculation
                    }

                    equal.id -> {
                        if (calculation == "+" || calculation == "-" || calculation == "*" || calculation == "/") {
                            return
                        }

                        if (state == 1) {
                            res.text = res.text.toString()
                        }
                        if (calculation == "" && state == 1) {
                            res.text = calculation
                            return
                        }
                        if (res.text.toString() == "" && state == 1) {
                            res.text = calculation
                            return
                        }
                        if (state == 2 && calculation1 == "") {
                            return
                        }

                        op2 = calculation1.toInt()
                        when (cal) {
                            1 -> {
                                res.text = (op1 + op2).toString()
                                op1 += op2
                            }

                            2 -> {
                                res.text = (op1 - op2).toString()
                                op1 -= op2
                            }

                            3 -> {
                                res.text = (op1 * op2).toString()
                                op1 *= op2
                            }

                            4 -> {
                                if (op2 == 0) {
                                    res.text = "Syntax error"
                                    return
                                }
                                res.text = (op1.toDouble() / op2).toString()
                                op1 /= op2
                            }
                        }
                        state = 1
                        state1 = 1
                        calculation = ""
                        calculation1 = ""
                    }

                    c.id -> {
                        if (state == 1) {
                            calculation = ""
                            calculation1 = ""
                            res.text = calculation
                        }
                        if (state == 2) {
                            calculation = calculation2
                            calculation1 = ""
                            res.text = calculation
                        }
                    }

                    ce.id -> {
                        calculation = ""
                        calculation1 = ""
                        calculation2 = ""
                        state1 = 0
                        state = 1
                        op1 = 0
                        res.text = calculation
                    }

                    back.id -> {
                        if (state == 1) {
                            if (calculation == "") {
                                res.text = calculation
                                return
                            } else if (res.text.toString() == "") {
                                res.text = calculation
                                return
                            } else {
                                calculation = calculation.substring(0, calculation.length - 1)
                                calculation1 = calculation1.substring(0, calculation1.length - 1)
                                res.text = calculation
                            }
                        } else if (state == 2) {
                            if (calculation1 == "") {
                                state = 1
                                calculation = calculation.substring(0, calculation.length - 1)
                                calculation1 = calculation
                                res.text = calculation
                            } else {
                                calculation = calculation.substring(0, calculation.length - 1)
                                calculation1 = calculation1.substring(0, calculation1.length - 1)
                                res.text = calculation
                            }
                        }
                    }
                }
            }
        }
        ce.setOnClickListener { onClick(it) }
        back.setOnClickListener { onClick(it) }
        mul.setOnClickListener {onClick(it) }
        div.setOnClickListener { onClick(it) }
        num0.setOnClickListener { onClick(it) }
        num1.setOnClickListener { onClick(it) }
        num2.setOnClickListener { onClick(it) }
        num3.setOnClickListener { onClick(it) }
        num4.setOnClickListener { onClick(it) }
        num5.setOnClickListener { onClick(it) }
        num6.setOnClickListener { onClick(it) }
        num7.setOnClickListener { onClick(it) }
        num8.setOnClickListener { onClick(it) }
        num9.setOnClickListener { onClick(it) }
        add.setOnClickListener { onClick(it) }
        equal.setOnClickListener { onClick(it) }
        sub.setOnClickListener { onClick(it) }
        c.setOnClickListener { onClick(it) }
        oppo.setOnClickListener { onClick(it) }
    }
}
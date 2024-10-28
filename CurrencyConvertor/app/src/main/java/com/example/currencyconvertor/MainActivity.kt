package com.example.currencyconvertor

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var sourceAmount: EditText
    private lateinit var targetAmount: EditText
    private lateinit var sourceCurrency: Spinner
    private lateinit var targetCurrency: Spinner

    private val currencyRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "JPY" to 152.885,
        "VND" to 25389.0,
        "YUAN" to 7.11
    )

    private var isUpdating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sourceAmount = findViewById(R.id.sourceAmount)
        targetAmount = findViewById(R.id.targetAmount)
        sourceCurrency = findViewById(R.id.sourceCurrency)
        targetCurrency = findViewById(R.id.targetCurrency)

        setupCurrencies()
        setupListeners()
    }

    private fun setupCurrencies() {
        val currencies = currencyRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceCurrency.adapter = adapter
        targetCurrency.adapter = adapter
    }

    private fun setupListeners() {
        sourceAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                highlightField(sourceAmount)
                unhighlightField(targetAmount)
            } else {
                unhighlightField(sourceAmount)
            }
        }

        targetAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                highlightField(targetAmount)
                unhighlightField(sourceAmount)
            } else {
                unhighlightField(targetAmount)
            }
        }

        sourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating) {
                    convertToTarget()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        targetAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating) {
                    convertToSource()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        sourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (!isUpdating) {
                    convertToTarget()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        targetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (!isUpdating) {
                    convertToSource()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun highlightField(editText: EditText) {
        editText.setBackgroundColor(Color.LTGRAY) // Highlight color
    }

    private fun unhighlightField(editText: EditText) {
        editText.setBackgroundColor(Color.TRANSPARENT) // Reset color
    }

    private fun convertToTarget() {
        val amount = sourceAmount.text.toString().toDoubleOrNull() ?: return
        val sourceCurrencyValue = sourceCurrency.selectedItem.toString()
        val targetCurrencyValue = targetCurrency.selectedItem.toString()

        val sourceRate = currencyRates[sourceCurrencyValue] ?: return
        val targetRate = currencyRates[targetCurrencyValue] ?: return

        val convertedAmount = amount * (targetRate / sourceRate)

        isUpdating = true
        targetAmount.setText(String.format("%.2f", convertedAmount))
        isUpdating = false
    }

    private fun convertToSource() {
        val amount = targetAmount.text.toString().toDoubleOrNull() ?: return
        val targetCurrencyValue = targetCurrency.selectedItem.toString()
        val sourceCurrencyValue = sourceCurrency.selectedItem.toString()

        val targetRate = currencyRates[targetCurrencyValue] ?: return
        val sourceRate = currencyRates[sourceCurrencyValue] ?: return

        val convertedAmount = amount * (sourceRate / targetRate)

        isUpdating = true
        sourceAmount.setText(String.format("%.2f", convertedAmount))
        isUpdating = false
    }
}
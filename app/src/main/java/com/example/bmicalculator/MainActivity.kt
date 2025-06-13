package com.example.bmicalculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnReset: Button
    private lateinit var tvBmiResult: TextView
    private lateinit var tvBmiCategory: TextView
    private lateinit var layoutResult: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        etWeight = findViewById(R.id.etWeight)
        etHeight = findViewById(R.id.etHeight)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnReset = findViewById(R.id.btnReset)
        tvBmiResult = findViewById(R.id.tvBmiResult)
        tvBmiCategory = findViewById(R.id.tvBmiCategory)
        layoutResult = findViewById(R.id.layoutResult)
    }

    private fun setupClickListeners() {
        btnCalculate.setOnClickListener {
            calculateBMI()
        }

        btnReset.setOnClickListener {
            resetForm()
        }
    }

    private fun calculateBMI() {
        val weightStr = etWeight.text.toString().trim()
        val heightStr = etHeight.text.toString().trim()


        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            showToast("Please enter both weight and height")
            return
        }

        val weight = weightStr.toDoubleOrNull()
        val height = heightStr.toDoubleOrNull()

        if (weight == null || height == null || weight <= 0 || height <= 0) {
            showToast("Please enter valid positive numbers")
            return
        }


        val heightInMeters = height / 100


        val bmi = weight / heightInMeters.pow(2)
        displayResults(bmi)
    }

    private fun displayResults(bmi: Double) {
        val bmiFormatted = String.format("%.1f", bmi)
        tvBmiResult.text = bmiFormatted

        val (category, colorResId, backgroundResId) = getBmiCategory(bmi)
        tvBmiCategory.text = category
        tvBmiCategory.setTextColor(ContextCompat.getColor(this, colorResId))
        tvBmiCategory.setBackgroundResource(backgroundResId)

        layoutResult.visibility = LinearLayout.VISIBLE
    }

    private fun getBmiCategory(bmi: Double): Triple<String, Int, Int> {
        return when {
            bmi < 18.5 -> Triple(
                "Underweight",
                R.color.blue_600,
                R.drawable.bg_underweight
            )
            bmi <= 24.9 -> Triple(
                "Normal",
                R.color.green_600,
                R.drawable.bg_normal
            )
            bmi <= 29.9 -> Triple(
                "Overweight",
                R.color.orange_600,
                R.drawable.bg_overweight
            )
            else -> Triple(
                "Obese",
                R.color.red_600,
                R.drawable.bg_obese
            )
        }
    }

    private fun resetForm() {
        etWeight.text.clear()
        etHeight.text.clear()
        layoutResult.visibility = LinearLayout.GONE
        etWeight.requestFocus()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
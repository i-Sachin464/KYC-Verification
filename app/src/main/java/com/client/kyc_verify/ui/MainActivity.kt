package com.client.kyc_verify.ui

import android.R.attr.maxLength
import android.app.Activity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.client.kyc_verify.R
import com.client.kyc_verify.databinding.ActivityMainBinding
import com.client.kyc_verify.util.IdType
import com.client.kyc_verify.util.Util
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This is main activity
 */
class MainActivity : AppCompatActivity() {
    private var activity: Activity = this@MainActivity

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initSpinner();
        initValidation()
    }

    private fun initValidation() {
        validate.setOnClickListener {
            if (input_id_card.text.isNullOrEmpty()) {
                return@setOnClickListener
            }
            val util = Util()
            if (util.validateIdCard(
                    spinner.selectedItem as String,
                    input_id_card.text.toString()
                )
            ) {
                sendData();
            }
        }
    }

    private fun sendData() {
        Toast.makeText(this, "Sending data", Toast.LENGTH_LONG).show()
    }

    private fun initSpinner() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (spinner.selectedItemPosition == 0) {
                    input_id_card.setText("")
                    input_id_card.isEnabled = false
                } else {
                    input_id_card.isEnabled = true
                    when (spinner.selectedItem.toString()) {
                        IdType.AADHAR_CARD.text -> {
                            text_input_layout.error = "Enter 12 digit aadhar number"
                            input_id_card.apply {
                                inputType = InputType.TYPE_CLASS_NUMBER
                                filters = arrayOf<InputFilter>(LengthFilter(12))
                            }
                        }
                        IdType.VOTER_CARD.text -> {
                            text_input_layout.error = "Enter 10 digit voter id card number"
                            input_id_card.apply {
                                inputType = InputType.TYPE_CLASS_TEXT
                                filters = arrayOf<InputFilter>(LengthFilter(10))
                            }

                        }
                        IdType.PAN_CARD.text -> {
                            text_input_layout.error = "Enter 10 digit pan card number"
                            input_id_card.apply {
                                inputType = InputType.TYPE_CLASS_TEXT
                                filters = arrayOf<InputFilter>(LengthFilter(10))
                            }
                        }
                        IdType.DRIVING_LICENSE.text -> {
                            text_input_layout.error = "Enter 16 digit driving license number"
                            input_id_card.apply {
                                inputType = InputType.TYPE_CLASS_TEXT
                                filters = arrayOf<InputFilter>(LengthFilter(16))
                            }
                        }
                        IdType.PASSPORT.text -> {
                            text_input_layout.error = "Enter 8 digit passport number"
                            input_id_card.apply {
                                inputType = InputType.TYPE_CLASS_TEXT
                                filters = arrayOf<InputFilter>(LengthFilter(8))
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}


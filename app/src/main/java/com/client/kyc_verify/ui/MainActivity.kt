package com.client.kyc_verify.ui

import android.app.Activity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.client.kyc_verify.R
import com.client.kyc_verify.databinding.ActivityMainBinding
import com.client.kyc_verify.util.NetworkCall
import com.client.kyc_verify.repo.RepoImpl
import com.client.kyc_verify.util.IdType
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * This is main activity
 */
class MainActivity : AppCompatActivity() {
    private var activity: Activity = this@MainActivity

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var mViewModel: KYCViewModel
    private lateinit var networkCall: NetworkCall
    private lateinit var observer: Observer<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(KYCViewModel::class.java)
        initSpinner();
        initValidation()
    }

    /**
     * This function used for validate button click to validate
     */
    private fun initValidation() {
        validate.setOnClickListener {
            if (input_id_card.text.isNullOrEmpty()) {
                return@setOnClickListener
            }
            if (mViewModel.validateIdCard(
                    spinner.selectedItem as String,
                    input_id_card.text.toString().toUpperCase()
                )
            ) {
                sendData();
            }
            else{
                Toast.makeText(this,"Enter a valid id card number",Toast.LENGTH_SHORT).show()
                Snackbar.make(it,"Enter a valid ID card number.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * This function is used for sending data to server url and observe using observer
     */
    private fun sendData() {
        initObserver()
        networkCall = NetworkCall()
        var repoImpl = RepoImpl(networkCall)

        repoImpl.sendDataUsingNetwork(
            url = applicationContext.getString(R.string.url),
            input_id_card.text.toString()
        )
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    /**
     * This function id used for initialize observer
     */
    private fun initObserver() {
        observer = object : Observer<String?> {
            override fun onError(e: Throwable) {
                Log.e("TAG", "onError");
            }

            override fun onNext(t: String?) {
                Log.e("TAG", "onNext: $t");
            }

            override fun onCompleted() {
                Toast.makeText(this@MainActivity, "Sending data", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * This function is used to initialise spinner and onclick listener
     */
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
                                setText("")
                                inputType = InputType.TYPE_CLASS_NUMBER
                                filters = arrayOf<InputFilter>(LengthFilter(12))
                            }
                        }
                        IdType.VOTER_CARD.text -> {
                            text_input_layout.error = "Enter 10 digit voter id card number"
                            input_id_card.apply {
                                setText("")
                                inputType = InputType.TYPE_CLASS_TEXT
                                filters = arrayOf<InputFilter>(LengthFilter(10))
                            }

                        }
                        IdType.PAN_CARD.text -> {
                            text_input_layout.error = "Enter 10 digit pan card number"
                            input_id_card.apply {
                                setText("")
                                inputType = InputType.TYPE_CLASS_TEXT
                                filters = arrayOf<InputFilter>(LengthFilter(10))
                            }
                        }
                        IdType.DRIVING_LICENSE.text -> {
                            text_input_layout.error = "Enter 16 digit driving license number"
                            input_id_card.apply {
                                setText("")
                                inputType = InputType.TYPE_CLASS_TEXT
                                filters = arrayOf<InputFilter>(LengthFilter(16))
                            }
                        }
                        IdType.PASSPORT.text -> {
                            text_input_layout.error = "Enter 8 digit passport number"
                            input_id_card.apply {
                                setText("")
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


package com.mywings.twolevelqrcodeproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mywings.messmanagementsystem.process.OnSendOptionListener
import com.mywings.messmanagementsystem.process.SendOtpAsync
import com.mywings.twolevelqrcodeproject.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_verify_otp.*
import kotlin.random.Random

class VerifyOtpActivity : AppCompatActivity(), OnSendOptionListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil
    private lateinit var input: String
    private lateinit var number: String
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)
        progressDialogUtil = ProgressDialogUtil(this)
        initotp()

        btnConfirm.setOnClickListener {
            if (txtEnterOtp.text.toString().isNotEmpty() && txtEnterOtp.text.toString().equals(input, false)) {
                Toast.makeText(this@VerifyOtpActivity, "Amount transferred successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@VerifyOtpActivity, "Enter valid otp", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initotp() {
        progressDialogUtil.show()
        number = getRandomNumberString()
        input =
            "https://api.textlocal.in/send/?apiKey=wnl6P220GB4-NLTbWCaPwzfFPHRoSBz16bgyFjAsie&sender=TXTLCL&numbers=${phoneNumber}&message=${number}"
        val sendOtp = SendOtpAsync()
        sendOtp.setSendOtpListener(this, input)
    }

    private fun getRandomNumberString(): String {
        val rnd = Random(100000)
        val number = rnd.nextInt(999999)
        return String.format("%06d", number)
    }

    override fun otpSent(result: String?) {
        progressDialogUtil.hide()
        if (result!!.contains("error")) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        }
    }
}

package com.mywings.twolevelqrcodeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mywings.messmanagementsystem.process.OnSendOptionListener
import com.mywings.messmanagementsystem.process.SendOtpAsync
import com.mywings.twolevelqrcodeproject.process.MakeTransactionAsync
import com.mywings.twolevelqrcodeproject.process.OnMakeTransactionListener
import com.mywings.twolevelqrcodeproject.process.ProgressDialogUtil
import com.mywings.twolevelqrcodeproject.process.UserDataHolder
import kotlinx.android.synthetic.main.activity_verify_otp.*
import org.json.JSONObject
import kotlin.random.Random

class VerifyOtpActivity : AppCompatActivity(), OnSendOptionListener, OnMakeTransactionListener {


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
            if (txtEnterOtp.text.toString().isNotEmpty() && txtEnterOtp.text.toString().equals(number, false)) {
                initMakeTransaction()
            } else {
                Toast.makeText(this@VerifyOtpActivity, "Enter valid otp", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initotp() {
        progressDialogUtil.show()
        number = getRandomNumberString()
        phoneNumber = UserDataHolder.getInstance().selfUser.mobileNo
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
        if (result != null && result.contains("error")) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        }
    }

    private fun initMakeTransaction() {
        progressDialogUtil.show()
        val user = UserDataHolder.getInstance().selfUser
        val makeTransactionAsync = MakeTransactionAsync()
        val request = JSONObject()
        val params = JSONObject()
        params.put("FromAc", user.accountNo)
        params.put("ToAc", intent.extras?.getString("toAccount"))
        params.put("Amount", intent.extras?.getString("toAmount"))
        params.put("RecType", "")
        request.put("request", params)
        makeTransactionAsync.setOnMakeTransactionListener(this, request)
    }

    override fun onTransactionSuccess(inserted: Int?) {
        progressDialogUtil.hide()
        if (inserted != null && inserted > 0) {
            val intent = Intent(this@VerifyOtpActivity, SuccessActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this@VerifyOtpActivity, "Something went wrong", Toast.LENGTH_LONG).show()
        }
    }
}

package com.mywings.twolevelqrcodeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mywings.twolevelqrcodeproject.process.UserDataHolder
import kotlinx.android.synthetic.main.activity_make_atransaction.*

class MakeATransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_atransaction)
        lblFromAccount.text = UserDataHolder.getInstance().selfUser.accountNo
        btnVerify.setOnClickListener {
            if (txtToAccount.text.isNotEmpty()) {
                val intent = Intent(this@MakeATransactionActivity, VerifyOtpActivity::class.java)
                intent.putExtra("toAccount", txtToAccount.text.toString())
                intent.putExtra("toAmount", txtToAmount.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this@MakeATransactionActivity, "Enter account number", Toast.LENGTH_LONG).show()
            }
        }
    }
}

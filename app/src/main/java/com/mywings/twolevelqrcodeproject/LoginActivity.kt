package com.mywings.twolevelqrcodeproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mywings.newtwitterapp.process.LoginAsync
import com.mywings.newtwitterapp.process.OnLoginListener
import com.mywings.twolevelqrcodeproject.model.User
import com.mywings.twolevelqrcodeproject.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), OnLoginListener {



    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressDialogUtil = ProgressDialogUtil(this@LoginActivity)
        btnSignIn.setOnClickListener {
            if (validate()) {
                initLogin()
            } else {
                Toast.makeText(this@LoginActivity, "Enter username and password", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onLoginSuccess(user: User?) {

    }

    private fun initLogin() {
        progressDialogUtil.show()
        var loginAsync = LoginAsync()
        var request = JSONObject()
        var param = JSONObject()
        param.put("Username", txtUsername.text)
        param.put("Password", txtPassword.text)
        request.put("request", param)
        loginAsync.setOnLoginListener(this, request)
    }

    private fun validate(): Boolean = !txtUsername.text.isNullOrEmpty() && !txtPassword.text.isNullOrEmpty()
}

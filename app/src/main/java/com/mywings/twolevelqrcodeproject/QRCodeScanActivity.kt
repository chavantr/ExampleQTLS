package com.mywings.twolevelqrcodeproject

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mywings.newtwitterapp.process.GetProfileAsync
import com.mywings.newtwitterapp.process.OnLoginListener
import com.mywings.twolevelqrcodeproject.model.User
import com.mywings.twolevelqrcodeproject.process.ProgressDialogUtil
import com.mywings.twolevelqrcodeproject.process.UserDataHolder
import de.klimek.scanner.OnDecodedCallback
import kotlinx.android.synthetic.main.activity_qrcode_scan.*

class QRCodeScanActivity : AppCompatActivity(), OnDecodedCallback, OnLoginListener {

    private val EXTERNAL_REQUEST: Int = 1001
    private lateinit var progressDialogUtil: ProgressDialogUtil
    private  var email: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scan)
        progressDialogUtil = ProgressDialogUtil(this)
        email = intent.extras?.getString("email")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                EXTERNAL_REQUEST
            )
        } else {
            startAnim()
        }
    }

    override fun onResume() {
        super.onResume()
        startPreview()
        scanner.setOnDecodedCallback(this)
    }

    override fun onPause() {
        super.onPause()
        stopPreview()
    }

    private fun startAnim() {
        val mAnimation = TranslateAnimation(
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 1 / 4f,
            TranslateAnimation.RELATIVE_TO_PARENT, 2 / 3f
        )
        mAnimation.duration = 500
        mAnimation.repeatCount = -1
        mAnimation.repeatMode = Animation.REVERSE
        mAnimation.interpolator = LinearInterpolator()
        imgLine.animation = mAnimation
    }

    private fun stopPreview() {
        scanner.stopScanning()
    }

    private fun startPreview() {
        scanner.startScanning()
    }

    private fun init(email: String) {
        progressDialogUtil.show()
        val getProfileAsync = GetProfileAsync()
        getProfileAsync.setOnLoginListener(this, "?email=$email")
    }

    override fun onDecoded(decodedData: String?) {
        if (!decodedData?.isNullOrEmpty()!!) {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                v.vibrate(500)
            }
            init(decodedData)
            //init("test@test.com")
        } else {
            Toast.makeText(this@QRCodeScanActivity, "Scan carefully", Toast.LENGTH_LONG).show()
        }
    }

    override fun onLoginSuccess(user: User?) {
        progressDialogUtil.hide()
        if (null != user) {
            UserDataHolder.getInstance().otherUser = user
            val intent = Intent(this@QRCodeScanActivity, ShowDetailsActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
}

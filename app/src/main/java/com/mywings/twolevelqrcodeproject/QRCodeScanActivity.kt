package com.mywings.twolevelqrcodeproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import de.klimek.scanner.OnDecodedCallback
import kotlinx.android.synthetic.main.activity_qrcode_scan.*

class QRCodeScanActivity : AppCompatActivity(), OnDecodedCallback {

    private val EXTERNAL_REQUEST: Int = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scan)

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
        mAnimation.interpolator = LinearInterpolator() as Interpolator?
        imgLine.animation = mAnimation
    }

    private fun stopPreview() {
        scanner.stopScanning()
    }

    private fun startPreview() {
        scanner.startScanning()
    }

    override fun onDecoded(decodedData: String?) {

        if (!decodedData?.isNullOrEmpty()!!) {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                v.vibrate(500)
            }

        } else {
            Toast.makeText(this@QRCodeScanActivity, "Scan carefully", Toast.LENGTH_LONG).show()
        }

    }
}

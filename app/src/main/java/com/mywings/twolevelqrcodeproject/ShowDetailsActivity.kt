package com.mywings.twolevelqrcodeproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mywings.twolevelqrcodeproject.model.User
import com.mywings.twolevelqrcodeproject.process.ProgressDialogUtil
import com.mywings.twolevelqrcodeproject.process.UserDataHolder
import kotlinx.android.synthetic.main.activity_show_details.*

class ShowDetailsActivity : AppCompatActivity() {

    private var isUserSame: Boolean = false
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)
        user = UserDataHolder.getInstance().otherUser
        isUserSame = user.emailId.equals(intent.extras?.getString("email"), true)

        if (isUserSame) {
            setPublic()
            setPrivate()
            setVisibility()
        } else {
            setPublic()
        }
    }

    private fun setPrivate() {
        txtAcNo.setText("Account No :    " + user.accountNo)
        txtPinNo.setText("Pin :    " + user.pinNo)
        txtAtmNo.setText("Atm card :    " + user.atmNo)
        txtCsvNo.setText("CSV No :    " + user.csvNo)
        txtPanCard.setText("Pan card :    " + user.panCard)
        txtVoterCard.setText("Voter card :    " + user.voterCard)
        txtUID.setText("Aadhar No :    " + user.uidCard)
    }

    private fun hideVisibility() {
        tilAcNo.visibility = View.GONE
        tilPinNo.visibility = View.GONE
        tilAtmNo.visibility = View.GONE
        tilCSVNo.visibility = View.GONE
        tilPanNo.visibility = View.GONE
        tilVoterId.visibility = View.GONE
        tilUID.visibility = View.GONE
    }

    private fun setVisibility() {
        tilAcNo.visibility = View.VISIBLE
        tilPinNo.visibility = View.VISIBLE
        tilAtmNo.visibility = View.VISIBLE
        tilCSVNo.visibility = View.VISIBLE
        tilPanNo.visibility = View.VISIBLE
        tilVoterId.visibility = View.VISIBLE
        tilUID.visibility = View.VISIBLE
        lblSecondaryInfo.visibility = View.VISIBLE
        vwLine.visibility = View.VISIBLE
    }

    private fun setPublic() {
        txtEmail.setText("Email :    " + user.emailId)
        txtName.setText("Name :    " + user.firstName.plus(" ").plus(user.middleName).plus(" ").plus(user.lastName))
        txtAddress.setText("Address :    " + user.address)
        lblGender.setText("Gender :    " + user.gender)
        txtMobileNo.setText("Mobile number :   " + user.mobileNo)
        txtDob.setText("Date of birth :    " + user.dob)
    }
}

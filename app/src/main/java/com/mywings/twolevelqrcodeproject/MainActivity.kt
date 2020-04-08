package com.mywings.twolevelqrcodeproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.mywings.twolevelqrcodeproject.model.User
import com.mywings.twolevelqrcodeproject.process.UserDataHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        user = UserDataHolder.getInstance().selfUser
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        setPublic()

    }

    private fun setPrivate() {
        txtAcNo.setText(user.accountNo)
        txtPinNo.setText(user.pinNo)
        txtAtmNo.setText(user.atmNo)
        txtCsvNo.setText(user.csvNo)
        txtPanCard.setText(user.panCard)
        txtVoterCard.setText(user.voterCard)
        txtUID.setText(user.uidCard)
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
    }

    private fun setPublic() {

        txtEmail.setText("Email :    " + user.emailId)

        txtName.setText("Name :    " + user.firstName.plus(" ").plus(user.middleName).plus(" ").plus(user.lastName))
        txtAddress.setText("Address :    " + user.address)
        lblGender.setText("Gender :    " + user.gender)
        txtMobileNo.setText("Mobile number :   " + user.mobileNo)
        txtDob.setText("Date of birth :    " + user.dob)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, QRCodeScanActivity::class.java)
                intent.putExtra("email", user.emailId)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.ic_make_transaction -> {
                val makeTransactionActivity = Intent(this@MainActivity, MakeATransactionActivity::class.java)
                startActivity(makeTransactionActivity)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.ic_transactions -> {
                val makeTransactionActivity = Intent(this@MainActivity, ShowTransactionActivity::class.java)
                startActivity(makeTransactionActivity)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

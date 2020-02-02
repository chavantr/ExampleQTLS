package com.mywings.newtwitterapp.process

import android.os.AsyncTask
import com.mywings.twolevelqrcodeproject.model.User
import com.mywings.twolevelqrcodeproject.process.HttpConnectionUtil
import org.json.JSONObject

class GetProfileAsync : AsyncTask<String?, Void, User?>() {

    private lateinit var onLoginListener: OnLoginListener
    private var response: String? = null
    private val httpConnectionUtil = HttpConnectionUtil()

    override fun doInBackground(vararg params: String?): User? {
        response = httpConnectionUtil.requestGet(HttpConstants.URL.plus(HttpConstants.LOAD_DATA).plus(params[0]))
        response.let {
            if (null != it && !it?.isNullOrEmpty()) {
                val jUser = JSONObject(it)
                if (null != jUser) {
                    val user = User()
                    user.emailId = jUser.getString("EmailId")
                    user.firstName = jUser.getString("FirstName")
                    user.middleName = jUser.getString("MiddleName")
                    user.lastName = jUser.getString("LastName")
                    user.address = jUser.getString("Address")
                    user.gender = jUser.getString("Gender")
                    user.mobileNo = jUser.getString("MobileNo")
                    user.dob = jUser.getString("Dob")
                    user.statusInfo = jUser.getInt("StatusInfo")
                    user.accountNo = jUser.getString("AccountNo")
                    user.pinNo = jUser.getString("PinNo")
                    user.atmNo = jUser.getString("ATMNo")
                    user.csvNo = jUser.getString("CSVNo")
                    user.panCard = jUser.getString("PanCard")
                    user.voterCard = jUser.getString("VoterCard")
                    user.uidCard = jUser.getString("AadharCard")
                    return user
                }
            }
        }
        return null
    }

    override fun onPostExecute(result: User?) {
        super.onPostExecute(result)
        onLoginListener.onLoginSuccess(result)
    }



    fun setOnLoginListener(onLoginListener: OnLoginListener, request: String?) {
        this.onLoginListener = onLoginListener
        super.executeOnExecutor(THREAD_POOL_EXECUTOR, request)
    }
}
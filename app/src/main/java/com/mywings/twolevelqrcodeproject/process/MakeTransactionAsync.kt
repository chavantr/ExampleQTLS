package com.mywings.twolevelqrcodeproject.process

import android.os.AsyncTask
import com.mywings.newtwitterapp.process.HttpConstants
import org.json.JSONObject

class MakeTransactionAsync : AsyncTask<JSONObject, Void, Int?>() {

    private lateinit var onMakeTransactionListener: OnMakeTransactionListener

    private val httpConnectionUtil = HttpConnectionUtil()

    override fun doInBackground(vararg params: JSONObject?): Int? {
        return httpConnectionUtil.requestPost(HttpConstants.URL.plus(HttpConstants.NEW_TRANSACTION), params[0]).toInt()
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        onMakeTransactionListener.onTransactionSuccess(result)
    }

    fun setOnMakeTransactionListener(onMakeTransactionListener: OnMakeTransactionListener, request: JSONObject) {
        this.onMakeTransactionListener = onMakeTransactionListener
        super.executeOnExecutor(THREAD_POOL_EXECUTOR, request)
    }
}
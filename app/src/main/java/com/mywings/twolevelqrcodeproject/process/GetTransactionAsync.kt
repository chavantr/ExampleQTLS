package com.mywings.twolevelqrcodeproject.process

import android.os.AsyncTask
import com.mywings.newtwitterapp.process.HttpConstants
import com.mywings.twolevelqrcodeproject.model.Transaction
import org.json.JSONArray

class GetTransactionAsync : AsyncTask<Void, Void, ArrayList<Transaction>?>() {

    private var httpConnectionUtil = HttpConnectionUtil()
    private lateinit var onTransactionListener: OnTransactionListener

    override fun doInBackground(vararg params: Void?): ArrayList<Transaction>? {
        var response: String? = httpConnectionUtil.requestGet(HttpConstants.URL.plus(HttpConstants.GET_TRANSACTION))
        response.let {
            if (null != it && it.isNotEmpty()) {
                val jTransactionList = JSONArray(it)
                val transactionList = ArrayList<Transaction>()
                for (i in 0 until jTransactionList.length()) {
                    val jNode = jTransactionList.getJSONObject(i)
                    val node = Transaction(
                        jNode.getInt("Id"),
                        jNode.getString("FromAc"), jNode.getString("ToAc"),
                        jNode.getString("Amount"), jNode.getString("RecType")
                    )
                    transactionList.add(node)
                }
                return transactionList
            }
        }
        return null
    }

    fun setOnTransactionListener(onTransactionListener: OnTransactionListener) {
        this.onTransactionListener = onTransactionListener
        super.executeOnExecutor(THREAD_POOL_EXECUTOR)
    }
}
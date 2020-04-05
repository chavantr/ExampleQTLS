package com.mywings.twolevelqrcodeproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mywings.twolevelqrcodeproject.binder.TransactionAdapter
import com.mywings.twolevelqrcodeproject.model.Transaction
import com.mywings.twolevelqrcodeproject.process.GetTransactionAsync
import com.mywings.twolevelqrcodeproject.process.OnTransactionListener
import com.mywings.twolevelqrcodeproject.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_show_transaction.*

class ShowTransactionActivity : AppCompatActivity(), OnTransactionListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_transaction)
        progressDialogUtil = ProgressDialogUtil(this)
        init()
    }

    private fun init() {
        progressDialogUtil.show()
        val transactionAsync = GetTransactionAsync()
        transactionAsync.setOnTransactionListener(this)
    }

    override fun onGetTransaction(list: ArrayList<Transaction>?) {
        progressDialogUtil.hide()
        if (list != null) {
            lstTransactions.layoutManager = LinearLayoutManager(this)
            lstTransactions.adapter = TransactionAdapter(list)
        }
    }
}

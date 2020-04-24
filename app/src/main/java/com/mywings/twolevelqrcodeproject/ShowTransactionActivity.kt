package com.mywings.twolevelqrcodeproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mywings.twolevelqrcodeproject.binder.TransactionAdapter
import com.mywings.twolevelqrcodeproject.model.Transaction
import com.mywings.twolevelqrcodeproject.model.User
import com.mywings.twolevelqrcodeproject.process.GetTransactionAsync
import com.mywings.twolevelqrcodeproject.process.OnTransactionListener
import com.mywings.twolevelqrcodeproject.process.ProgressDialogUtil
import com.mywings.twolevelqrcodeproject.process.UserDataHolder
import kotlinx.android.synthetic.main.activity_show_transaction.*

class ShowTransactionActivity : AppCompatActivity(), OnTransactionListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_transaction)
        progressDialogUtil = ProgressDialogUtil(this)
        init()
        user = UserDataHolder.getInstance().selfUser
    }

    private fun init() {
        progressDialogUtil.show()
        val transactionAsync = GetTransactionAsync()
        transactionAsync.setOnTransactionListener(this)
    }

    override fun onGetTransaction(list: ArrayList<Transaction>?) {
        progressDialogUtil.hide()
        if (list != null) {
            val newList = ArrayList<Transaction>()
            for (node in list) {
                if (node.fromAc.equals(user.accountNo) || node.toAc.equals(user.accountNo)) {
                    newList.add(node)
                }
            }
            lstTransactions.layoutManager = LinearLayoutManager(this)
            lstTransactions.adapter = TransactionAdapter(newList)
        }
    }
}

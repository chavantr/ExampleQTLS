package com.mywings.twolevelqrcodeproject.process

import com.mywings.twolevelqrcodeproject.model.Transaction

interface OnTransactionListener {
    fun onGetTransaction(list: ArrayList<Transaction>?)
}
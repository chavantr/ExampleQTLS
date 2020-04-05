package com.mywings.twolevelqrcodeproject.model

class Transaction(
    var id: Int = 0,
    var fromAc: String?,
    var toAc: String?,
    var amount: String?,
    var type: String?
)
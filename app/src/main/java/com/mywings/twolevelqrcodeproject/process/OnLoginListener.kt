package com.mywings.newtwitterapp.process

import com.mywings.twolevelqrcodeproject.model.User


interface OnLoginListener {
    fun onLoginSuccess(user: User?)
}
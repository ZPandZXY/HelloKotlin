package com.easyar.hellokotlin.data

data class ResponseWrapper<T>(var code: Int, var data: T, var message: String)

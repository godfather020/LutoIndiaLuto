package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Response_Common {

    @SerializedName("code")
    @Expose
    private var code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("error")
    @Expose
    private var error: Response_Error? = null

    @SerializedName("data")
    @Expose
    private var data: Response_Data? = null

    @SerializedName("success")
    @Expose
    private var success: Boolean? = null











    fun getCode(): Int? {
        return code
    }

    fun setCode(code: Int?) {
        this.code = code
    }

//    fun getMessage(): String? {
//        return message
//    }
//
//    fun setMessage(message: String?) {
//        this.message = message
//    }

    fun getError(): Response_Error? {
        return error
    }

    fun setError(error: Response_Error?) {
        this.error = error
    }

    fun getData(): Response_Data? {
        return data
    }

    fun setData(data: Response_Data?) {
        this.data = data
    }

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }


}
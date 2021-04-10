package com.example.merchantapp2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CallToAPI {

    @POST("api/v1/user/login")
    fun userLogin(@Body requestToLogin : RequestToLogin) : Call<ResponseOfLogin>
}
package com.github.yatatsu.samplecartclient

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppApi {

    @POST("charge")
    @FormUrlEncoded
    fun createCharge(
        @Field("amount")
        amount: Int,
        @Field("email")
        email: String
    ): Call<Unit>
}
package com.kiwi.fetchtest

import okhttp3.Call
import retrofit2.Response
import retrofit2.http.GET

interface APIService{
    @GET("/hiring.json")
    suspend fun getItems() : Response<ArrayList<Item>>

    data class Item(
        val id : Int,
        val listId : Int,
        val name : String?
    ) //data class item
}//Interface
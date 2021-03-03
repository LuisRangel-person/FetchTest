package com.kiwi.fetchtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ListViewModel : ViewModel(){
    var itemList = MutableLiveData<ArrayList<APIService.Item>>()

    fun getListOfItems(){
        //Setting up retrofit
        val baseURL = "https://fetch-hiring.s3.amazonaws.com/"

        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).build()
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getItems().body()
            //Filter out items with nulls and blanks in the name field
            withContext(Dispatchers.Main) {
                if (response != null) {
                    val filteredList = response.filter { i -> i.name != null && i.name != "" }
                    //Sort by ListID then by name
                    val sortedList = filteredList.sortedWith(compareBy({ it.listId }, { it.name }))
                    //Section Off the List based off listId
                    var prevSection = 0
                    val sectionedList = ArrayList<APIService.Item>()
                    for(item in sortedList){
                        if(item.listId != prevSection){
                            val sectionNum = "List ID Group " + item.listId.toString()
                            val sectionHeader = APIService.Item(id = Int.MAX_VALUE, name = sectionNum, listId = item.listId)//If the ID is MAX VALUE, the its a section header
                            sectionedList.add(sectionHeader)
                        }//If the previous listID is different, then it's a new section
                        sectionedList.add(item)//Add regular item into sectioned list
                        prevSection = item.listId
                    }//Iterate to find the sections
                    itemList.value = ArrayList(sectionedList)
                }//If Response isn't null
            }//Dispatcher Main
        }//Dispatcher IO
    }//Get the List of Items from the url
}//List View Model
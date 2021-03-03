package com.kiwi.fetchtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //Class Scope Variables
    private lateinit var viewModel: ListViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemListAdapter: ItemListAdapter
    var itemList = ArrayList<APIService.Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set Up the View Model
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getListOfItems()//Get the list of items or failover

        viewModel.itemList.observe(this, Observer { newList ->
                itemListAdapter = ItemListAdapter(this, newList)
                rev_itemList.adapter = itemListAdapter
                val listCount = newList.count()
                tv_itemCount.text = "$listCount Items"
        })//Observable, handle getting the list from the URL

        //Setting up the recycler view
        linearLayoutManager = LinearLayoutManager(this)
        rev_itemList.layoutManager = linearLayoutManager
        itemListAdapter = ItemListAdapter(this, itemList)
        rev_itemList.adapter = itemListAdapter
    }
}
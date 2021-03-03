package com.kiwi.fetchtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemListAdapter(private val context: Context, private val items: ArrayList<APIService.Item>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val CELL = 1
        const val HEADER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == CELL) {
            return CellHolder(LayoutInflater.from(context).inflate(R.layout.recycle_list_row, parent, false))
        }
        else{
            return HeaderHolder(LayoutInflater.from(context).inflate(R.layout.recycle_list_header, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val idString = item.id.toString()
        val listIdString = item.listId.toString()
        if(item.id != Int.MAX_VALUE) {
            (holder as CellHolder).bind(position)
        }//If- Not a Header
        else{
            (holder as HeaderHolder).bind(position)
        }//Else - Is a Header
    }//Bind Data to Holder

    override fun getItemCount(): Int {
        return items.count()
    }//Get number of items in list

    override fun getItemViewType(position: Int): Int {
        if(items[position].id != Int.MAX_VALUE){
            return CELL
        }
        else{
            return HEADER
        }
    }

    //ViewHolder Class!
    inner class CellHolder(v: View): RecyclerView.ViewHolder(v){
        var textViewName: TextView = v.findViewById(R.id.cell_name)
        var textViewId : TextView = v.findViewById(R.id.cell_id)
        var textViewListId : TextView = v.findViewById(R.id.cell_listID)
        fun bind(position: Int){
            val item = items[position]
            val idString = item.id.toString()
            val listIdString = item.listId.toString()
            textViewName.text = item.name
            textViewId.text = "ID: $idString"
            textViewListId.text = "List ID Group: $listIdString"
        }//Bind for the cell

    }//Class Cell holder

    //Section Header Class
    inner class HeaderHolder(v: View) : RecyclerView.ViewHolder(v){
        var sectionTitle : TextView = v.findViewById(R.id.tv_sectionTitle)
        fun bind(position: Int){
            val item = items[position]
            sectionTitle.text = item.name
        }//Bind for the header
    }//Class Header Holder
}
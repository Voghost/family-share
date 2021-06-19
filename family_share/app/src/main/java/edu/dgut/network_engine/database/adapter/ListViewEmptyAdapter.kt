package edu.dgut.network_engine.database.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.UserViewModel

class ListViewEmptyAdapter(
    private val context: Context,
    private var exampleList: List<Memorandum>,
    private val background:Int
) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder : EmptyViewHolder
        view = LayoutInflater.from(context).inflate(R.layout.listview_empty_item,null)
        holder = EmptyViewHolder()
        holder.lv_tips = view.findViewById(R.id.empty_listview_item)
        view.tag = holder

        return view!!
    }

    override fun getItem(position: Int): Any = exampleList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int{
        if(exampleList.isEmpty()){
            return 1
        }
        return exampleList.size
    }

    inner class EmptyViewHolder {
        lateinit var lv_tips:TextView
    }
}
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

class ListViewAdapter(
    private val context: Context,
    private var exampleList: List<Memorandum>,
    private val background:Int
) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder : ViewHolder
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_item,null)
            holder = ViewHolder()
            holder.lv_content = view.findViewById(R.id.listView1)
            holder.lv_name = view.findViewById(R.id.listView2)
            view.tag = holder
        }else {
            holder = (view?.tag) as ViewHolder
        }

        val myItem = exampleList[position]
        if(myItem.content?.length!! > 10){
            holder.lv_content.text = myItem.content?.substring(0, 9) + "....."
            holder.lv_name.text = "By" + myItem.username
        }else{
            holder.lv_content.text = myItem.content
            holder.lv_name.text = "By" + myItem.username
        }

        return view!!
    }

    override fun getItem(position: Int): Any = exampleList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = exampleList.size

    inner class ViewHolder {
        lateinit var lv_content:TextView
        lateinit var lv_name:TextView
    }
}
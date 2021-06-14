package edu.dgut.network_engine.database.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.detail_item.view.*
import kotlinx.android.synthetic.main.empty_item.view.*
import java.text.SimpleDateFormat


//data class MemberItem(val imageResuorce: Int, val text1: String, val text2: String,val text3: String,val text4: String)

class EmptyAdapter(
    private var exampleList: UserWithAccountList,
    private var userViewModel: UserViewModel
) :

    RecyclerView.Adapter<EmptyAdapter.EmptyViewHolder>() {

    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_EMPTY = 0


    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView1: TextView = itemView.empty_view_message
    }

    fun setAllList(exampleList: UserWithAccountList) {
        this.exampleList = exampleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {

        if(exampleList.accountList!!.size == 0){
            val emptyView = LayoutInflater.from(parent.context).inflate(R.layout.empty_item,parent,false)
            Log.v("测试","执行了为空判断")
            return EmptyViewHolder(emptyView)
        }

        Log.v("Adapter里测试",viewType.toString())
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_item, parent, false)

//        val view =LayoutInflater.from(parent.context).inflate(R.layout.wallet_fragment,parent,false)
//        val viewHolder = MemberViewHolder(view)
//
//        viewHolder.itemView.setOnClickListener{
//            val intent = Intent(parent.context,memberDetailActivity::class.java)
//            parent.context.startActivity(intent)
//        }

        return EmptyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {
        Log.v("测试","执行了onBindViewHolder")
        holder.textView1.text = "暂无数据"
    }

    override fun getItemCount(): Int {
        if(exampleList.accountList!!.size == 0){
            return 1
        }
        return exampleList.accountList!!.size
    }


    override fun getItemViewType(position: Int): Int {
        Log.v("测试","执行了getItemViewType")
        if(exampleList.accountList!!.isEmpty()){
            return VIEW_TYPE_EMPTY
        }
        return VIEW_TYPE_ITEM
    }
}
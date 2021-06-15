package edu.dgut.network_engine.database.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.AddAccountActivity
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.memberDetailActivity
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.detail_item.view.*
import java.text.SimpleDateFormat


//data class MemberItem(val imageResuorce: Int, val text1: String, val text2: String,val text3: String,val text4: String)

class DetailAdapter(
    private var context: Context,
    private var exampleList: UserWithAccountList,
    private var userViewModel: UserViewModel
) :

    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_EMPTY = 0


    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView1: TextView = itemView.textView6
        val textView2: TextView = itemView.textView7
        val textView3: TextView = itemView.textView8
        val textView4: TextView = itemView.textView9
    }

    fun setAllList(exampleList: UserWithAccountList) {
        this.exampleList = exampleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {

        if (exampleList.accountList!!.size == 0) {
            val emptyView =
                LayoutInflater.from(parent.context).inflate(R.layout.empty_item, parent, false)
            Log.v("测试", "执行了为空判断")
            return DetailViewHolder(emptyView)
        }

        Log.v("Adapter里测试", viewType.toString())
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_item, parent, false)

//        val view =LayoutInflater.from(parent.context).inflate(R.layout.wallet_fragment,parent,false)
//        val viewHolder = MemberViewHolder(view)
//
//        viewHolder.itemView.setOnClickListener{
//            val intent = Intent(parent.context,memberDetailActivity::class.java)
//            parent.context.startActivity(intent)
//        }

        return DetailViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val currentItem = exampleList.accountList?.get(position)
        Log.v("测试", "执行了onBindViewHolder")
        if (currentItem != null) {
            holder.textView1.text = (position + 1).toString()
//            var time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            time.timeZone =
//            holder.textView2.text
//                .format(currentItem.accountList!![0].createTime)
            if (currentItem.price!!.signum() == 1) {
                holder.textView2.text = "支出:" + currentItem.price!!.toString()
            } else if (currentItem.price!!.signum() == -1) {
                holder.textView2.text = "收入:" + currentItem.price!!.abs().toString()
            }
            holder.textView3.text = currentItem.reason
            holder.textView4.text =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentItem.createTime!! + 8 * 60 * 60 * 1000)
                    .toString()
        }
        holder.itemView.setOnClickListener {
            Log.v("测试点击事件", exampleList.user!!.userId.toString())
//            val bundle = Bundle()
//            bundle.putLong("userId", exampleList.user!!.userId)
//            val intent = Intent("android.intent.action.AddAccountActivity")
//            intent.putExtras(bundle)
//            startActivity(context,intent,bundle)
            val bundle = Bundle()
            exampleList.user!!.userId?.let { it1 -> bundle.putLong("userId", it1) }
            val intent = Intent(context, AddAccountActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
            false
        }
        holder.itemView.setOnLongClickListener {
            var temp = currentItem?.accountId

//            userViewModel.deleteUser(exampleList.user!!.userId)

//            exampleList -= exampleList[position]

//            notifyItemRemoved(position)

            Log.v("长按了", temp.toString())
            false
        }


    }

    override fun getItemCount(): Int {
        if (exampleList.accountList!!.size == 0) {
            return 1
        }
        return exampleList.accountList!!.size
    }


    override fun getItemViewType(position: Int): Int {
        Log.v("测试", "执行了getItemViewType")
        if (exampleList.accountList!!.isEmpty()) {
            return VIEW_TYPE_EMPTY
        }
        return VIEW_TYPE_ITEM
    }
}
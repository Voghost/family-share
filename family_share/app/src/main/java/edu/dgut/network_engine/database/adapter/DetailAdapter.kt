package edu.dgut.network_engine.database.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.MainActivity
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.memberDetailActivity
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.detail_item.view.*
import kotlinx.android.synthetic.main.member_item.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZoneOffset


//data class MemberItem(val imageResuorce: Int, val text1: String, val text2: String,val text3: String,val text4: String)

class DetailAdapter(
    private var exampleList: UserWithAccountList,
    private var userViewModel: UserViewModel
) :

    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

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
        if (currentItem != null) {
            holder.textView1.text = currentItem.accountId.toString()
//            var time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            time.timeZone =
//            holder.textView2.text
//                .format(currentItem.accountList!![0].createTime)
            if(currentItem.price!!.signum() == 1){
                holder.textView2.text = "支出:" + currentItem.price!!.toString()
            }else{
                holder.textView2.text = "收入:" + currentItem.price!!.abs().toString()
            }
            holder.textView3.text = currentItem.reason
            holder.textView4.text =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentItem.createTime + 8 * 60 * 60 * 1000)
                    .toString()
        } else {
            holder.textView2.text = "暂无数据"
        }
        holder.itemView.setOnClickListener {
            var temp = currentItem?.accountId

//            val bundle = Bundle()
//            bundle.putLong("userId", currentItem.user!!.userId)
//            val intent = Intent(context, memberDetailActivity::class.java)
//            intent.putExtras(bundle)
//            context.startActivity(intent)

            Log.v("点击了", temp.toString())
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
        return exampleList.accountList!!.size
    }

}
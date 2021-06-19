package edu.dgut.network_engine.database.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.AddMemorandumActivity
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.memberDetailActivity
import edu.dgut.network_engine.view_model.MemorandumViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.member_item.view.*
import kotlinx.android.synthetic.main.memorandum_item.view.*
import java.text.SimpleDateFormat


//data class MemberItem(val imageResuorce: Int, val text1: String, val text2: String,val text3: String,val text4: String)

class MemorandumAdapter(
    private var context: Context,
    private var exampleList: List<Memorandum>,
    private var memorandumViewModel: MemorandumViewModel,
) :

    RecyclerView.Adapter<MemorandumAdapter.MemorandumViewHolder>() {

    class MemorandumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView1: TextView = itemView.text_view_12
        val textView2: TextView = itemView.text_view_13
    }

    fun setAllList(exampleList: List<Memorandum>) {
        this.exampleList = exampleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemorandumViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.memorandum_item, parent, false)

//        val view =LayoutInflater.from(parent.context).inflate(R.layout.wallet_fragment,parent,false)
//        val viewHolder = MemberViewHolder(view)
//
//        viewHolder.itemView.setOnClickListener{
//            val intent = Intent(parent.context,memberDetailActivity::class.java)
//            parent.context.startActivity(intent)
//        }

        return MemorandumViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: MemorandumViewHolder, position: Int) {
        val currentItem = exampleList[position]


        //        holder.textView2.text = currentItem.createTime.toString()
        holder.textView2.text = currentItem.username

        if (currentItem.content?.length!! > 10) {
            holder.textView1.text = currentItem.content?.substring(0, 8) + ".."
        } else {
            holder.textView1.text = currentItem.content
        }

        if(currentItem.endTime == null) {
            holder.textView2.text = "By" + currentItem.username + "      一直有效"
        }else {
            val dateStr: String =
                android.icu.text.SimpleDateFormat("yyyy 年 MM 月 dd 日").format(currentItem.endTime)
            holder.textView2.text = "By: " + currentItem.username  + "      截止日期:"+ dateStr /*+ currentItem.endTime.toString()*/
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            currentItem.content?.let { it1 -> bundle.putString("Content", it1) }
            bundle.putLong("Id",currentItem.id!!)
            if(currentItem.endTime == null){
                bundle.putString("EndTime","一直有效")
            }else {
                var endTime =
                    android.icu.text.SimpleDateFormat("yyyy-MM-dd").format(currentItem.endTime)
                bundle.putString("EndTime",endTime)
            }
            val intent = Intent(context, AddMemorandumActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)

            false
        }
        holder.itemView.setOnLongClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("确定删除?")
            builder.setTitle("提示")
            builder.setPositiveButton(
                "确定"
            ) { dialog, which ->
                currentItem.id?.let { it1 -> memorandumViewModel.deleteMemorandum(it1) }
//            exampleList -= exampleList[position]
                notifyItemRemoved(position)

            }
            builder.setNegativeButton(
                "取消"
            ) { dialog, which ->
            }
            builder.create().show()
            false
        }


    }

}
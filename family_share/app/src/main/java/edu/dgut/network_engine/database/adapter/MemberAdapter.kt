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
import kotlinx.android.synthetic.main.member_item.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZoneOffset


//data class MemberItem(val imageResuorce: Int, val text1: String, val text2: String,val text3: String,val text4: String)

class MemberAdapter(
    private var context: Context,
    private var exampleList: List<UserWithAccountList>,
    private var userViewModel: UserViewModel
) :

    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.image_view
        val textView1: TextView = itemView.text_view_1
        val textView2: TextView = itemView.text_view_2
        val textView3: TextView = itemView.text_view_3
        val textView4: TextView = itemView.text_view_4
    }

    fun setAllList(exampleList: List<UserWithAccountList>) {
        this.exampleList = exampleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)

//        val view =LayoutInflater.from(parent.context).inflate(R.layout.wallet_fragment,parent,false)
//        val viewHolder = MemberViewHolder(view)
//
//        viewHolder.itemView.setOnClickListener{
//            val intent = Intent(parent.context,memberDetailActivity::class.java)
//            parent.context.startActivity(intent)
//        }

        return MemberViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val currentItem = exampleList[position]
        if (currentItem.accountList != null && currentItem.accountList!!.isNotEmpty()) {
            holder.imageView.setImageResource(R.mipmap.ic_launcher)
            holder.textView1.text = currentItem.user?.userName
//            var time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            time.timeZone =
//            holder.textView2.text
//                .format(currentItem.accountList!![0].createTime)
            holder.textView2.text =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentItem.accountList!![0].createTime + 8 * 60 * 60 * 1000)
                    .toString()
            holder.textView3.text = currentItem.accountList!![0].price.toString()
            holder.textView4.text = currentItem.accountList!![0].price.toString()
        } else {
            holder.imageView.setImageResource(R.mipmap.ic_launcher)
            holder.textView1.text = currentItem.user?.userName
            holder.textView2.text = "lastCostTime"
            holder.textView3.text = "Cost"
            holder.textView4.text = "Income"
        }
        holder.itemView.setOnClickListener {
            var temp = currentItem.user?.userName

            val bundle = Bundle()
            bundle.putLong("userId", currentItem.user!!.userId)
            val intent = Intent(context, memberDetailActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)

            Log.v("点击了", temp.toString())
            false
        }
        holder.itemView.setOnLongClickListener {
            var temp = currentItem.user?.userName

            userViewModel.deleteUser(exampleList[position].user!!.userId)

//            exampleList -= exampleList[position]

            notifyItemRemoved(position)

            Log.v("长按了", temp.toString())
            false
        }


    }

}
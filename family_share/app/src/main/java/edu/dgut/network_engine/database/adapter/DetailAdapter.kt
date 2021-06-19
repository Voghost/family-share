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
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.AddAccountActivity
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.memberDetailActivity
import edu.dgut.network_engine.view_model.AccountViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import edu.dgut.network_engine.view_model.WalletViewModel
import kotlinx.android.synthetic.main.detail_item.view.*
import java.text.SimpleDateFormat


//data class MemberItem(val imageResuorce: Int, val text1: String, val text2: String,val text3: String,val text4: String)

class DetailAdapter(
    private var context: Context,
    private var exampleList: UserWithAccountList,
    private var userViewModel: UserViewModel,
//    private var walletViewModel: WalletViewModel
    private var accountViewModel: AccountViewModel
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
            bundle.putLong("accountId", currentItem!!.accountId!!)
            bundle.putDouble("price", currentItem.price!!.toDouble())
            bundle.putString("introduce", currentItem.reason)
            currentItem.createTime?.let { it1 -> bundle.putLong("createTime", it1) }
            val intent = Intent(context, AddAccountActivity::class.java)
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
                currentItem!!.accountId?.let { it1 -> accountViewModel.delete(it1) }
//                currentItem!!.accountId?.let { it1 -> walletViewModel.deleteUser(it1) }
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
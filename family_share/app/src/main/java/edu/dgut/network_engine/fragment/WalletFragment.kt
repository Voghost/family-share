package edu.dgut.network_engine.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.adapter.MemberAdapter
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.UserViewModel
import edu.dgut.network_engine.view_model.WalletViewModel
import kotlinx.coroutines.async


class WalletFragment : Fragment() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    private lateinit var viewModel: WalletViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
//    private lateinit var memberlist: List<MemberItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    fun test(view : View){
        Log.v("你添加了","一名用户")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // TODO: Use the ViewModel
        recyclerView = requireView().findViewById(R.id.recycler_view)
//        var allList = userViewModel.getAllUserList()
//            .observe(viewLifecycleOwner, { userList: List<User> ->
//                memberlist = getMemberName(userList)
//                recyclerView.adapter = MemberAdapter(memberlist)
//                recyclerView.layoutManager = LinearLayoutManager(this.activity)
//                println(userList.toString())
//
//
//            })
        val allList =
            userViewModel.getAllUserWithUserList()?.observe(viewLifecycleOwner, { userList: List<UserWithAccountList> ->
                    recyclerView.adapter = MemberAdapter(userList,userViewModel)
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                })
//            println(allList)
//            memberlist = getMemberName(allList!!)
//            recyclerView.adapter = MemberAdapter(memberlist)
//            recyclerView.layoutManager = LinearLayoutManager(activity)
    }
//        userViewModel.getAllUserList().observe(viewLifecycleOwner,
//            { user -> //                Log.v("aaaa", user?.get(0)?.userName!!)
//                memberlist = getMemberName(user as List<User>)
//                recyclerView = requireView().findViewById(R.id.recycler_view)
//                recyclerView.adapter = MemberAdapter(memberlist)
//                recyclerView.layoutManager = LinearLayoutManager(activity)
//            })
//        val memberlist = generateDummyList(3)
//        Log.v("aaaaa",memberlist.toString())

//        recyclerView.adapter = MemberAdapter(memberlist)
//        recyclerView.layoutManager = LinearLayoutManager(this.activity)
}

//private fun getMemberName(allList: UserWithAccountList): List<MemberItem> {
//    val list = ArrayList<MemberItem>()
//
//    val drawable = R.mipmap.ic_launcher
//    var item = MemberItem(drawable, allList.user?.userName.toString(),"LastCostTime","Cost","Income")
//    println(allList)
//    if(allList.accountList != null && allList.accountList!!.isNotEmpty()){
//        item = MemberItem(
//            drawable, allList.user?.userName.toString(), "LastCostTime",
//            allList.accountList!![0].price.toString(), "Income")
//    }
//
//    list += item
//
//    return list
//}


package edu.dgut.network_engine.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.adapter.MemberAdapter
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.AccountViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import edu.dgut.network_engine.view_model.WalletViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import java.lang.Exception
import java.lang.Thread.sleep
import java.util.jar.Manifest


class WalletFragment : Fragment() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    private lateinit var viewModel: WalletViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshView:SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.wallet_fragment, container, false)
        refreshView=root.findViewById(R.id.refresh)


        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        recyclerView = requireView().findViewById(R.id.recycler_view)
        val allList =
            userViewModel.getAllUserWithUserList()
                ?.observe(viewLifecycleOwner, { userList: List<UserWithAccountList> ->
                    recyclerView.adapter =
                        MemberAdapter(this.requireContext(), userList, userViewModel)
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                })
        refreshView.setOnRefreshListener {

            // ?????????????????????
            lifecycleScope.launch {
                userViewModel.synFamily()
                accountViewModel.syn()

            }
            recyclerView.adapter?.notifyDataSetChanged()
            Toast.makeText(this.context, "????????????",Toast.LENGTH_LONG).show()
            refreshView.isRefreshing=false
        }

    }

}




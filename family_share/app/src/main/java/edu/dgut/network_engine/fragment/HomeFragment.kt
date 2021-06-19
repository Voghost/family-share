package edu.dgut.network_engine.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.adapter.ListViewAdapter
import edu.dgut.network_engine.database.adapter.ListViewEmptyAdapter
import edu.dgut.network_engine.database.adapter.MemorandumAdapter
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.databinding.ListviewEmptyItemBinding
import edu.dgut.network_engine.view_model.AccountViewModel
import edu.dgut.network_engine.view_model.MemorandumViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.tdo.TokenTdo
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    //      private lateinit var viewModel: HomeViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var memorandumViewModel: MemorandumViewModel
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var buttonTest: Button
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.home_fragment, container, false)

        listView = root.findViewById(R.id.home_listview)


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        memorandumViewModel = ViewModelProvider(this).get(MemorandumViewModel::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        listView = requireView().findViewById(R.id.home_listview)
        memorandumViewModel.getLiveData()
            ?.observe(viewLifecycleOwner, { memorandumList: List<Memorandum> ->
                if(memorandumList == null){
                    Log.v("执行了","空判断")
                    listView.adapter = ListViewEmptyAdapter(this.requireContext(),memorandumList,Color.WHITE)
                }else{
//                    var templist = arrayListOf<Memorandum>()
//                    for(i in 0..3){
//                        Log.v("测试",memorandumList[i].toString())
//                        if(memorandumList[i] != null){
//                            templist.add(memorandumList[i])
//                        }
//                    }
                    listView.adapter = ListViewAdapter(this.requireContext(),memorandumList, Color.WHITE)
                }
            })

        accountViewModel.getAll()
            ?.observe(viewLifecycleOwner, { accountList: List<Account> ->
                var total = 0.0
                for(current in accountList){
                    if(current.price!!.signum() == 1){
                        total += current.price!!.toDouble()
                    }
                }
                familyTotal.text = total.toString()
            })
        buttonTest = requireView().findViewById(R.id.btTest)
        var res: BaseResponse<TokenTdo>? = null
        var act = this.activity
        buttonTest.setOnClickListener {
            var memorandum: Memorandum = Memorandum()

            memorandumViewModel.deleteMemorandum(1)
        }
    }

}
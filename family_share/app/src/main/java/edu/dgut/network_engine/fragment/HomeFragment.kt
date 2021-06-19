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
                    var templist = arrayListOf<Memorandum>()
                    for(i in memorandumList.indices){
                        templist.add(memorandumList[memorandumList.size -1 - i])
                        Log.v("测试",i.toString())
                        if(i > 3){
                            break;
                        }
                    }
                    listView.adapter = ListViewAdapter(this.requireContext(),templist, Color.WHITE)
                }
            })

        accountViewModel.getAll()
            ?.observe(viewLifecycleOwner, { accountList: List<Account> ->
                var totalCost = 0.0
                var totalIncome = 0.0
                for(current in accountList){
                    if(current.price!!.signum() == 1){
                        totalCost += current.price!!.toDouble()
                    }else{
                        totalIncome += current.price!!.abs().toDouble()
                    }
                }
                var cost = Math.round(totalCost*100)/100
                var income = Math.round(totalIncome*100)/100
                if(totalCost/10000000 > 0){
                    familyTotalCost.text = (cost/10000000).toInt().toString() + "千" + (cost-(cost/10000000)*10000000).toInt().toString() + "百万"
                }else{
                    familyTotalCost.text = cost.toString()
                }
                if(totalIncome/10000000 > 0){
                    familyTotalIncome.text = (income/10000000).toInt().toString() + "千" + (income-(income/10000000)*10000000).toInt().toString() + "百万"
                }else{
                    familyTotalIncome.text = income.toString()
                }
            })

    }

}
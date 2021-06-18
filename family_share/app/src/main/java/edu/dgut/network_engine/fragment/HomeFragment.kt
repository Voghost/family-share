package edu.dgut.network_engine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.view_model.MemorandumViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.tdo.TokenTdo
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    //      private lateinit var viewModel: HomeViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var memorandumViewModel: MemorandumViewModel
    private lateinit var buttonTest: Button
    private lateinit var listView: View

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

        /*
         buttonTest = requireView().findViewById(R.id.btTest)
         buttonTest.setOnClickListener {
            // 测试一: 获取所有用户列表 测试 done
            var allList = userViewModel.getAllUserList()
                .observe(viewLifecycleOwner, { userList: List<User> ->
                    println(userList.toString())
                })
            // 测试二: 后台使用线程， 前台同步  done
            lifecycleScope.async {
                println(userViewModel.getAllCount())
                buttonTest.text = userViewModel.getAllCount().toString()
            }

        }
        */
        buttonTest = requireView().findViewById(R.id.btTest)
        var res: BaseResponse<TokenTdo>? = null
        var act = this.activity
        buttonTest.setOnClickListener {
            var memorandum: Memorandum = Memorandum()

            memorandumViewModel.deleteMemorandum(1)
        }
    }

}
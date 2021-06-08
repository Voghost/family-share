package edu.dgut.network_engine.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import edu.dgut.network_engine.view_model.HomeViewModel
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.view_model.UserViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var buttonTest: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        buttonTest = requireView().findViewById(R.id.btTest)
        buttonTest.setOnClickListener {
            var allList = userViewModel.getAllUserList()
                .observe(viewLifecycleOwner, { userList: List<User> ->
                    println(userList.toString())
                })

        }


    }

}
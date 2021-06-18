package edu.dgut.network_engine.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import edu.dgut.network_engine.view_model.MemorandumViewModel
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.adapter.MemorandumAdapter
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.memorandum_fragment.*
import kotlinx.coroutines.async

class MemorandumFragment : Fragment() {

    companion object {
        fun newInstance() = MemorandumFragment()
    }

    private lateinit var viewModel: MemorandumViewModel
    private lateinit var addMemorandum: ImageView
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.memorandum_fragment, container, false)
        addMemorandum = root.findViewById(R.id.btn_add)
        addMemorandum.setOnClickListener {
            var intent = Intent("android.intent.action.AddMemorandumActivity")
            startActivity(intent)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemorandumViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        var ctx = this.requireContext()
        viewModel.getLiveData()
            ?.observe(viewLifecycleOwner, { memorandumList: List<Memorandum> ->
                memorandum_recycle_view.adapter =
                    MemorandumAdapter(
                        ctx,
                        memorandumList,
                        viewModel,
                    )
                memorandum_recycle_view.layoutManager = LinearLayoutManager(activity)
            })

    }

}
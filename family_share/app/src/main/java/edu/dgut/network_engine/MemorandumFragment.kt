package edu.dgut.network_engine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MemorandumFragment : Fragment() {

    companion object {
        fun newInstance() = MemorandumFragment()
    }

    private lateinit var viewModel: MemorandumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.memorandum_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemorandumViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
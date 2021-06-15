package edu.dgut.network_engine.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import edu.dgut.network_engine.view_model.PersonViewModel
import edu.dgut.network_engine.R
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.member_item.*

class PersonFragment : Fragment() {

    companion object {
        fun newInstance() = PersonFragment()
    }

    private lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root= inflater.inflate(R.layout.person_fragment, container, false)
        var imageViewA:ImageView =root.findViewById(R.id.h_back)
        var imageViewB:ImageView=root.findViewById(R.id.h_head)
        var url="https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg"
        Glide.with(this.activity).load(R.drawable.text2).skipMemoryCache(false).bitmapTransform(BlurTransformation(context,25),CenterCrop(context)).into(imageViewA)
        //设置圆形图像
        Glide.with(this.activity).load(R.drawable.text2).skipMemoryCache(false).bitmapTransform(CropCircleTransformation(context)).into(imageViewB)
        var relativeLayout:RelativeLayout=root.findViewById(R.id.about)
        relativeLayout.setOnClickListener{
            var intent=Intent("android.intent.action.AddMemorandumActivity")
            startActivity(intent)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        // TODO: Use the ViewModel


    }

}
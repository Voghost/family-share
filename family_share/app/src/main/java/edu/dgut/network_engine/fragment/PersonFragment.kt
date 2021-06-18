package edu.dgut.network_engine.fragment

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import edu.dgut.network_engine.DensityUtil
import edu.dgut.network_engine.MyApplication
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
        //设置背景图像
        Glide.with(this.activity).load(R.drawable.text2).skipMemoryCache(false).bitmapTransform(BlurTransformation(context,25),CenterCrop(context)).into(imageViewA)
        //设置头像图像
        Glide.with(this.activity).load(R.drawable.text2).skipMemoryCache(false).bitmapTransform(CropCircleTransformation(context)).into(imageViewB)
       //关于选项
        var about:RelativeLayout=root.findViewById(R.id.about)
        about.setOnClickListener{
            var intent=Intent("android.intent.action.AboutActivity")
            startActivity(intent)
        }
        //加入家庭选项
        var addMember:RelativeLayout=root.findViewById(R.id.add_member)
        addMember.setOnClickListener{
            var intent= Intent("android.intent.action.AddMemberByCodeActivity")
            startActivity(intent)
        }
        //修改个人信息
        var modifyInfo:RelativeLayout=root.findViewById(R.id.modify_info)
        modifyInfo.setOnClickListener{
            var intent= Intent("android.intent.action.ModifyInfoActivity")
            startActivity(intent)
        }
        //登出
        var logout:RelativeLayout=root.findViewById(R.id.logout)
        logout.setOnClickListener{
            showBottomDialog()
        }
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

    }
    private fun showBottomDialog(){
        //使用Dialog,设置style
        val dialog: Dialog? = context?.let { Dialog(it,R.style.DialogTheme) }
        //设置布局
        var view:View=View.inflate(context,R.layout.dialog_custom_layout,null)
        dialog!!.setContentView(view)
        var window: Window? =dialog!!.window
        //设置弹出位置
        window!!.setGravity(Gravity.BOTTOM)
        //设置弹出动画
        window!!.setWindowAnimations(R.style.main_menu_animStyle)
        //设置对话框大小
        var params:ViewGroup.MarginLayoutParams  = view.layoutParams as ViewGroup.MarginLayoutParams
        params.width = resources.displayMetrics.widthPixels - DensityUtil.dip2pxRatio(this.context, 16f)
        params.bottomMargin = DensityUtil.dip2pxRatio(this.context, 8f)
        view.layoutParams = params

        dialog.show()
        //监听确定退出按钮
        dialog.findViewById<TextView>(R.id.tv_logout).setOnClickListener{
            //处理登出
            val sharedPreferences: SharedPreferences =
                MyApplication.getContext()!!.getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            dialog.dismiss()
            var intent=Intent("android.intent.LoginAccountActivity")
            startActivity(intent)
            requireActivity().finish()

        }
        //监听取消按钮
        dialog.findViewById<TextView>(R.id.tv_cancel).setOnClickListener { dialog.dismiss() }

    }

}
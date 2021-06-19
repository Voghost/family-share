package edu.dgut.network_engine.fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Path
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import edu.dgut.network_engine.DensityUtil
import edu.dgut.network_engine.MainActivity
import edu.dgut.network_engine.MyApplication
import edu.dgut.network_engine.view_model.PersonViewModel
import edu.dgut.network_engine.R
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.view_model.UserViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.member_item.*
import kotlinx.android.synthetic.main.person_fragment.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.net.URI

class PersonFragment : Fragment() {

    companion object {
        fun newInstance() = PersonFragment()
    }

    private lateinit var viewModel: PersonViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.person_fragment, container, false)
        var imageViewA: ImageView = root.findViewById(R.id.h_back)
        var imageViewB: ImageView = root.findViewById(R.id.h_head)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        var act = this.activity
        lifecycleScope.async {
            var user = userViewModel.getMe()
            if (user?.username != null && user!!.avatarUrl != null) {
                var url = user.avatarUrl
                Glide.with(act).load(url).skipMemoryCache(false)
                    .bitmapTransform(BlurTransformation(context, 25), CenterCrop(context))
                    .into(imageViewA)
                Glide.with(act).load(url).skipMemoryCache(false)
                    .bitmapTransform(CropCircleTransformation(context)).into(imageViewB)
                var usernameTextView = requireView().findViewById<TextView>(R.id.user_name)
                var userValTextView = requireView().findViewById<TextView>(R.id.user_val)
                usernameTextView.text = user.username
                userValTextView.text = user.phone
            } else {
                //设置背景图像
                Glide.with(act).load(R.drawable.text2).skipMemoryCache(false)
                    .bitmapTransform(BlurTransformation(context, 25), CenterCrop(context))
                    .into(imageViewA)
                //设置头像图像
                Glide.with(act).load(R.drawable.text2).skipMemoryCache(false)
                    .bitmapTransform(CropCircleTransformation(context)).into(imageViewB)
            }
        }


        var url: String? = "https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg"
        //设置背景图像
        Glide.with(this.activity).load(R.drawable.text2).skipMemoryCache(false)
            .bitmapTransform(BlurTransformation(context, 25), CenterCrop(context)).into(imageViewA)
        //设置头像图像
        Glide.with(this.activity).load(R.drawable.text2).skipMemoryCache(false)
            .bitmapTransform(CropCircleTransformation(context)).into(imageViewB)
        //关于选项
        var about: RelativeLayout = root.findViewById(R.id.about)
        about.setOnClickListener {
            var intent = Intent("android.intent.action.AboutActivity")
            startActivity(intent)
        }
        //加入家庭选项
        var addMember: RelativeLayout = root.findViewById(R.id.add_member)
        addMember.setOnClickListener {
            var intent = Intent("android.intent.action.AddMemberByCodeActivity")
            startActivity(intent)
        }
        //修改个人信息
        var modifyInfo: RelativeLayout = root.findViewById(R.id.modify_info)
        modifyInfo.setOnClickListener {
            var intent = Intent("android.intent.action.ModifyInfoActivity")
            startActivity(intent)
        }
        //修改密码
        var modifyPassword: RelativeLayout = root.findViewById(R.id.modify_password)
        modifyPassword.setOnClickListener {
            var intent = Intent("android.intent.action.ModifyPasswordActivity")
            startActivity(intent)
        }
        //退出家庭
        var exitFamily:RelativeLayout=root.findViewById(R.id.exit_family)
        exitFamily.setOnClickListener {
            var ctx=this.context
            lifecycleScope.async {
              var me=userViewModel.getMe()
                if(me!!.userId!=me!!.familyCode) {
                    var builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setMessage("确定退出家庭?")
                    builder.setTitle("提示")
                    builder.setPositiveButton(
                        "确定"
                    ) { dialog, which ->
                        lifecycleScope.launch {
                            userViewModel.quitFamily()
                        }
                    }
                    builder.setNegativeButton(
                        "取消"
                    ) { dialog, which ->
                    }
                    builder.create().show()
                }
                else{
                    Toast.makeText(ctx,"你没有家",Toast.LENGTH_LONG).show()
                }
            }
        }
        //登出
        var logout: RelativeLayout = root.findViewById(R.id.logout)
        logout.setOnClickListener {
            showBottomDialog()
        }
        //更换头像
        var headPicture: ImageView = root.findViewById(R.id.h_head)
        headPicture.setOnClickListener {
            showImageBottomDialog()

        }
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

    }

    //退出登录底部dialog
    private fun showBottomDialog() {
        //使用Dialog,设置style
        val dialog: Dialog? = context?.let { Dialog(it, R.style.DialogTheme) }
        //设置布局
        var view: View = View.inflate(context, R.layout.dialog_custom_layout, null)
        dialog!!.setContentView(view)
        var window: Window? = dialog!!.window
        //设置弹出位置
        window!!.setGravity(Gravity.BOTTOM)
        //设置弹出动画
        window!!.setWindowAnimations(R.style.main_menu_animStyle)
        //设置对话框大小
        var params: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels - DensityUtil.dip2pxRatio(this.context, 16f)
        params.bottomMargin = DensityUtil.dip2pxRatio(this.context, 8f)
        view.layoutParams = params
        dialog.show()
        //监听确定退出按钮
        dialog.findViewById<TextView>(R.id.tv_logout).setOnClickListener {
            //处理登出
            val sharedPreferences: SharedPreferences =
                MyApplication.getContext()!!.getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            userViewModel.logout()
            dialog.dismiss()
            var intent = Intent("android.intent.LoginAccountActivity")
            startActivity(intent)
            requireActivity().finish()

        }
        //监听取消按钮
        dialog.findViewById<TextView>(R.id.tv_cancel).setOnClickListener { dialog.dismiss() }
    }

    //选择头像底部Dialog
    private fun showImageBottomDialog() {
        //使用Dialog,设置style
        val dialog: Dialog? = context?.let { Dialog(it, R.style.DialogTheme) }
        //设置布局
        var view: View = View.inflate(context, R.layout.dialog_headpicture_layout, null)
        dialog!!.setContentView(view)
        var window: Window? = dialog!!.window
        //设置弹出位置
        window!!.setGravity(Gravity.BOTTOM)
        //设置弹出动画
        window!!.setWindowAnimations(R.style.main_menu_animStyle)
        //设置对话框大小
        var params: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels - DensityUtil.dip2pxRatio(this.context, 16f)
        params.bottomMargin = DensityUtil.dip2pxRatio(this.context, 8f)
        view.layoutParams = params
        dialog.show()

        dialog.findViewById<TextView>(R.id.tv_album).setOnClickListener {
            //后续操作
            var intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, 100)
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.tv_remove).setOnClickListener { dialog.dismiss() }
    }

    //对图片进行裁剪
    private fun cutImage(uri: Uri) {
        if (uri == null) {
            Log.i("alarm", "the uri is not exist")
        }
        var intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop,", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", 4000)
        intent.putExtra("outputY", 4000)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, 101)
    }

    private fun setImageToView(data: Intent) {
        var extras: Bundle? = data.extras
        if (extras != null) {
            var imageViewA: ImageView = requireActivity().findViewById(R.id.h_back)
            var imageViewB: ImageView = requireActivity().findViewById(R.id.h_head)
            var mBitmap = extras.getParcelable<Bitmap>("data")
            var result = bmpToByteArray(mBitmap!!)
            //上传至服务器代码在此写
            lifecycleScope.launch {
                userViewModel.upload(mBitmap)
            }

            //设置背景图像
            Glide.with(this.activity).load(result).skipMemoryCache(false)
                .bitmapTransform(BlurTransformation(context, 25), CenterCrop(context))
                .into(imageViewA)
            //设置头像图像
            Glide.with(this.activity).load(result).skipMemoryCache(false)
                .bitmapTransform(CropCircleTransformation(context)).into(imageViewB)


        }
    }

    //Bitmap转二进制
    private fun bmpToByteArray(bmp: Bitmap): ByteArray {
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output)//设置图片格式压缩相关
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                100 -> {
                    if (data != null) {
                        data.data?.let { cutImage(it) }
                    }
                }
                101 -> {
                    if (data != null) {
                        setImageToView(data)
                    }
                }
            }
        }
    }


}
package edu.dgut.network_engine

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.CaptureActivity
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.coroutines.launch

class AddMemberByCodeActivity : AppCompatActivity() {
    private lateinit var code: ImageView
    private lateinit var bitmap: Bitmap
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member_by_code)
        title = "加入家庭"
        //生成二维码
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        code = findViewById(R.id.code)
        var qrCode: QRcode = QRcode()

        val sharedPreferences: SharedPreferences =
            MyApplication.getContext()!!.getSharedPreferences("data", Context.MODE_PRIVATE)
        var token = sharedPreferences.getString("token", "")

        if (!token.isNullOrEmpty()) {
            bitmap = qrCode.qrcode(token)
            code.setImageBitmap(bitmap)
            //监听
            code.setOnClickListener {
                val intent = Intent(this@AddMemberByCodeActivity, CaptureActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                lifecycleScope.launch {
                    userViewModel.joinFamily(result.contents)
                }
//                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
            }

        } else {
            super.onActivityResult(requestCode, requestCode, data)
        }
    }

}


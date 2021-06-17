package edu.dgut.network_engine

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.CaptureActivity

class AddMemberByCodeActivity : AppCompatActivity() {
    private lateinit var code:ImageView
    private lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member_by_code)
        title="加入家庭"
        //生成二维码
        code=findViewById(R.id.code)
        var qrCode:QRcode=QRcode()

        bitmap=qrCode.qrcode("11111111112222222223333333334444444445555555566666666777777778888888899999999900000000011111111222222233333333444444455")
        code.setImageBitmap(bitmap)
        //监听
        code.setOnClickListener {
            val intent = Intent(this@AddMemberByCodeActivity, CaptureActivity::class.java)
            startActivityForResult(intent,REQUEST_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result:IntentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (result!=null)
        {
            if(result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
            }

        }else
        {
            super.onActivityResult(requestCode,requestCode,data)
        }
    }

}


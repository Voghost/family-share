package edu.dgut.network_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        title="关于"
        var versionElement:Element= Element()
        versionElement.title = "Version 1.0"

        var aboutPage: View =AboutPage(this).isRTL(false)
            .setImage(R.drawable.about)
            .setDescription("加群探讨二刺螈")
            .addWebsite("https://blog.ghovos.com/2021/05/19/andorid-family-share/")
            .addItem(versionElement).create()
        setContentView(aboutPage)
    }
}
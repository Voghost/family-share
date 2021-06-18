package edu.dgut.network_engine.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.dgut.network_engine.R
import edu.dgut.network_engine.view_model.ShareViewModel


class ShareFragment : Fragment() {

    companion object {
        fun newInstance() = ShareFragment()
    }

    private lateinit var viewModel: ShareViewModel
    private lateinit var webView: WebView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.share_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShareViewModel::class.java)
        webView = requireView().findViewById(R.id.webView)
        webView.webViewClient = object : WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }


        }
        webView.settings.javaScriptEnabled = true //设置WebView属性,运行执行js脚本
        webView.settings.domStorageEnabled = true

        webView.loadUrl("https://www.baidu.com") //调用loadUrl方法为WebView加入链接



        // TODO: Use the ViewModel
    }

}
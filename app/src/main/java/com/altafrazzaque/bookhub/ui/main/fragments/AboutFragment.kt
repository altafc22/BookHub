package com.altafrazzaque.bookhub.ui.main.fragments

import android.R
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.altafrazzaque.bookhub.base.BaseFragment
import com.altafrazzaque.bookhub.databinding.FragmentAboutBinding
import com.altafrazzaque.bookhub.utilities.WebParam
import timber.log.Timber


class AboutFragment : BaseFragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        Timber.i("onCreate view")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("VIew Created")

        loadWebview()
    }

    override fun setObservers() {

    }

    private fun loadWebview(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (requireActivity().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
        }

        binding.myWebView.settings.javaScriptEnabled = true
        binding.myWebView.addJavascriptInterface(JavaScriptInterface(), WebParam.JAVASCRIPT_OBJ)
        binding.myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (url == WebParam.BASE_URL) {
                    injectJavaScriptFunction()
                }
            }
        }
        binding.myWebView.loadUrl(WebParam.BASE_URL)
    }

    private fun injectJavaScriptFunction() {
        binding.myWebView.loadUrl("javascript: " +
                "window.androidObj.sendToApp = function(message) { " +
                WebParam.JAVASCRIPT_OBJ + ".textFromWeb(message) }")
    }


    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun textFromWeb(fromWeb: String) {

            Timber.i("Text from web: $fromWeb")
        }
    }

}
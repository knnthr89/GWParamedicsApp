package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guelphwellingtonparamedicsapp.R
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import com.guelphwellingtonparamedicsapp.databinding.FragmentWebViewBinding
import com.guelphwellingtonparamedicsapp.utils.Utils

class WebViewFragment : Fragment(), View.OnClickListener {

    private var url : String = ""
    private var back : Boolean = true
    private lateinit var fragmentWebViewBinding : FragmentWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
             url = it.getString("url").toString()
             back = it.getBoolean("back")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        fragmentWebViewBinding = DataBindingUtil.inflate(LayoutInflater.from(inflater.context), R.layout.fragment_web_view, container, false)
        fragmentWebViewBinding.webView.setInitialScale(1);
        fragmentWebViewBinding.webView.settings.javaScriptEnabled = true
        fragmentWebViewBinding.webView.settings.loadWithOverviewMode = true
        fragmentWebViewBinding.webView.settings.useWideViewPort = true
        fragmentWebViewBinding.webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        fragmentWebViewBinding.webView.isScrollbarFadingEnabled = false
        fragmentWebViewBinding.webView.loadUrl(url)

        fragmentWebViewBinding.back.setOnClickListener(this)

        if(!back){
            fragmentWebViewBinding.contactHeader.visibility = View.GONE
        }

        return fragmentWebViewBinding.root
    }

    override fun onClick(v: View?) {
        when(v){
            fragmentWebViewBinding.back -> Utils.backButton(fragmentManager = requireFragmentManager(), application = requireActivity().application)
        }
    }
}
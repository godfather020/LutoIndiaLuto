package com.example.lottry.ui.activity.help

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.lottry.R
import com.example.lottry.baseclass.fragment.Base_Fragment
import com.example.lottry.databinding.FragmentHelpBinding

import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.ui.activity.main.MainViewModel

import com.example.lottry.utils.Constant
import com.softs.meetupfellow.components.activity.CustomAppActivityCompatViewImpl


class Fragment_help : CustomAppActivityCompatViewImpl(), View.OnClickListener {



    lateinit var binding: FragmentHelpBinding
    lateinit var bundle: Bundle
    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView
    lateinit var notify_txt: TextView
    lateinit var wallet_txt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@Fragment_help, R.layout.fragment_help)
        init()

    }

    private fun init() {
        toolbar = binding.toolbarLayout.findViewById(R.id.toolbar)
        toolbarTitle = binding.toolbarLayout.findViewById(R.id.txt_toolbar_title)
        notify_txt = binding.toolbarLayout.findViewById(R.id.notify_txt)
        wallet_txt = binding.toolbarLayout.findViewById(R.id.wallet_txt)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("")
        toolbar.navigationIcon!!.setTint(resources.getColor(R.color.white))
        toolbar.setNavigationOnClickListener(View.OnClickListener {
         onBackPressed()
        })

       var value= intent.getIntExtra(Constant.IntentDataKeys.KEY,0)
        if (value==1){
            toolbarTitle.setText(resources.getString(R.string.help));
            notify_txt.visibility = View.GONE
            wallet_txt.visibility = View.GONE
        }else if (value==2){
            toolbarTitle.setText(resources.getString(R.string.private_policy));
            notify_txt.visibility = View.GONE
            wallet_txt.visibility = View.GONE
        }else {
            toolbarTitle.setText(resources.getString(R.string.Term_and_condition));
            notify_txt.visibility = View.GONE
            wallet_txt.visibility = View.GONE
        }
            binding.webView.webViewClient = WebViewClient()
            // this will load the url of the website
            binding.webView.loadUrl("https://www.impetrosys.com/")
            // this will enable the javascript settings
            binding.webView.settings.javaScriptEnabled = true
            // if you want to enable zoom feature
            binding.webView.settings.setSupportZoom(true)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
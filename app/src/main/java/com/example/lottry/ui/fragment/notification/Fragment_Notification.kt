package com.example.lottry.ui.fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.Row
import com.example.lottry.databinding.FragmentHomeBinding
import com.example.lottry.databinding.FragmentMyTicketBinding
import com.example.lottry.databinding.FragmentNotificationBinding
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.ui.fragment.buy_ticket.Adapter_Buy_Ticket
import com.example.lottry.ui.fragment.tran_history.Tran_History_Adapter

open class Fragment_Notification :Fragment() {

    lateinit var binding :FragmentNotificationBinding
    lateinit var activity: MainActivity
    lateinit var adapterNotification: Adapter_Notification
    lateinit var manager: RecyclerView.LayoutManager
    lateinit var viewModel: Fragment_Notification_viewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity=getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_notification,container,false)
        viewModel=ViewModelProvider(requireActivity()).get(Fragment_Notification_viewModel::class.java)
        val view: View =binding.root
        init()
        return view

    }
    fun init(){

        //activity.setTitleName(resources.getString(R.string.my_ticket))
        manager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        viewModel!!.getNotiList(activity, binding!!)
            .observe(requireActivity(), androidx.lifecycle.Observer{
                if (it != null) {

                    binding!!.progessBar.visibility = View.GONE
                    adapterNotification =
                        (it.getData()!!.notifications!!.rows as MutableList<Row>)?.let { it1 ->
                            Adapter_Notification(activity,
                                it1, object :
                                    Tran_History_Adapter.ClickListener {

                                    override fun tran_detail(Row: Row?, position: Int) {

                                    }
                                })
                        }
                    binding.notificationRv.adapter=adapterNotification
                    binding.notificationRv.layoutManager=manager
                }
            })
    }
}
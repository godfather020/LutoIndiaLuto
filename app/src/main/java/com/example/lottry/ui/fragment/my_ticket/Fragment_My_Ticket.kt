package com.example.lottry.ui.fragment.my_ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.R
import com.example.lottry.baseclass.fragment.Base_Fragment
import com.example.lottry.data.remote.retrofit.response.DailyTickets
import com.example.lottry.data.remote.retrofit.response.Response_Ticket_List
import com.example.lottry.databinding.FragmentHomeBinding
import com.example.lottry.databinding.FragmentMyTicketBinding
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.ui.fragment.buy_ticket.Adapter_Buy_Ticket
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import javax.inject.Inject

open class Fragment_My_Ticket :Base_Fragment() {

    lateinit var binding :FragmentMyTicketBinding
    lateinit var mainActivity:MainActivity
    lateinit var adapterMyTicketDaily: Adapter_My_Ticket
    lateinit var adapterMyTicketMonthly: Adapter_My_Ticket
    lateinit var adapterMyTicketOccationally: Adapter_My_Ticket
    lateinit var managerDaily: RecyclerView.LayoutManager
    lateinit var managerMonthly: RecyclerView.LayoutManager
    lateinit var managerOccationally: RecyclerView.LayoutManager
    var ticketListDaily:DailyTickets = DailyTickets()
    var ticketListMonthly:DailyTickets = DailyTickets()
    var ticketListOccationally:DailyTickets = DailyTickets()
    lateinit var viewModel: Fragment_My_Ticket_viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity=(activity as MainActivity)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_ticket,container,false)
        viewModel=ViewModelProvider(requireActivity()).get(Fragment_My_Ticket_viewModel::class.java)
        val view: View =binding.root
        init()
        return view

    }
    fun init(){

        setInstance(requireActivity())

//        ticketList=ArrayList()
//        ticketList.add(Response_Ticket_List("1",false).toString())
//        ticketList.add(Response_Ticket_List(2,"00000000002","01/11/21","7:30 AM","100",false).toString())
//        ticketList.add(Response_Ticket_List(3,"00000000003","01/11/21","7:30 AM","100",false).toString())

        mainActivity.setTitleName(resources.getString(R.string.my_ticket))
        managerDaily= LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL,false)
        managerMonthly= LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL,false)
        managerOccationally= LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL,false)

        viewModel.get_TicketList(mainActivity,binding).observe(mainActivity, Observer {

            if(it!=null){



                ticketListDaily=it!!.getData()!!.dailyTickets!!
                ticketListMonthly=it!!.getData()!!.monthlyTickets!!
                ticketListOccationally=it!!.getData()!!.occasionallyTickets!!

                if(ticketListDaily.count!! >0){
                    adapterMyTicketDaily=Adapter_My_Ticket(mainActivity,ticketListDaily)
                    binding.myTicketRvLatestDraw.adapter=adapterMyTicketDaily
                    binding.myTicketRvLatestDraw.layoutManager=managerDaily
                }
                if(ticketListMonthly.count!!>0){

                    adapterMyTicketMonthly=Adapter_My_Ticket(mainActivity,ticketListMonthly)
                    binding.myTicketRvMonthlyDraw.adapter=adapterMyTicketMonthly
                    binding.myTicketRvMonthlyDraw.layoutManager=managerMonthly

                }
                if(ticketListOccationally.count!!>0){

                    adapterMyTicketOccationally=Adapter_My_Ticket(mainActivity,ticketListOccationally)
                    binding.myTicketRvOccationDraw.adapter=adapterMyTicketOccationally
                    binding.myTicketRvOccationDraw.layoutManager=managerOccationally

                }

            }
            else{


            }


        })



    }
}
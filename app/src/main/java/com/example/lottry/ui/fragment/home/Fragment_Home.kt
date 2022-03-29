package com.example.lottry.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lottry.R
import com.example.lottry.baseclass.fragment.Base_Fragment
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.data.remote.retrofit.response.HomeTempResponse
import com.example.lottry.databinding.FragmentHomeBinding
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.ui.fragment.buy_ticket.Fragment_Buy_Ticket
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class Fragment_Home : Base_Fragment() ,View.OnClickListener{

    lateinit var notify_txt: TextView
    lateinit var wallet_txt: TextView
    lateinit var binding :FragmentHomeBinding
    lateinit var mypopupWindow:PopupWindow
    lateinit var viewModel: Fragment_Home_viewModel
    lateinit var adapter:Adapter_Home_Fragment
    lateinit var manager:LinearLayoutManager
    lateinit var managerHome:LinearLayoutManager
    var bundleLatest: Bundle= Bundle()
    var bundleMontly: Bundle= Bundle()
    var bundleOccationally: Bundle= Bundle()
    lateinit var adapterBuyLottry: Adapter_Home_Lottry

    @Inject
    lateinit var apis: Apis

    var mainActivity=MainActivity()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity=(activity as MainActivity)
        component.inject(this@Fragment_Home)
        setInstance(requireActivity())
        viewModel=ViewModelProvider(requireActivity()).get(Fragment_Home_viewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        val view: View =binding.root
        init()
        return view

    }



    fun init(){

        notify_txt = mainActivity.findViewById(R.id.notify_txt)
        wallet_txt = mainActivity.findViewById(R.id.wallet_txt)
        notify_txt.visibility = View.VISIBLE
        wallet_txt.visibility = View.VISIBLE

        mainActivity.setTitleName("")
        mainActivity.show_user_detail()
        mainActivity.hide_ticket_amt()
//        loadFragment(mainActivity.supportFragmentManager,
//       Fragment_Buy_Ticket(),
//       R.id.main_activity_main_container,
//    false,
//       "Buy Ticket",
//       null)

        manager= LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL,false)
        managerHome= LinearLayoutManager(mainActivity)
        viewModel.get_TopWinners(mainActivity,binding).observe(mainActivity, Observer {

            if(it!=null){
//                binding.progessBar.visibility=
//                    View.GONE

                adapter= Adapter_Home_Fragment(mainActivity,it!!.getData()!!.winners!!)

                binding.dashboardRvWinners.adapter=adapter
                binding.dashboardRvWinners.layoutManager=manager



            }else{

                showToast(mainActivity.resources.getString(R.string.you_have_no_data))
//                binding.progessBar.visibility=
//                    View.GONE
            }

        })
        viewModel.get_LotteryDetail(mainActivity,binding).observe(mainActivity, Observer {

            if(it!=null){

                if(it.getSuccess() == true){

                    binding.progessBar.visibility=
                        View.GONE
                    Log.e("Home_Fragment",it.toString())

                    if(it.getData()!!.lotteries!!.count!!<3){
                        binding.dashboardTxtOccasionDraw.visibility=View.GONE
                        binding.dashboardFlOcasiondraw.visibility=View.GONE
                    }


                    var type=""
                    var  homeResList: ArrayList<HomeTempResponse> =ArrayList()
                    for(i in 0 until it.getData()!!.lotteries!!.rows!!.size){
                      var homeResponse= HomeTempResponse()
                       if (type.isEmpty()){
                           type= it.getData()!!.lotteries!!.rows?.get(i)!!.ticketType.toString()
                           homeResponse.header=it.getData()!!.lotteries!!.rows?.get(i)!!.ticketType
                           homeResponse.rows=it.getData()!!.lotteries!!.rows?.get(i)
                           homeResList.add(homeResponse)
                       }else{
                        if (type==it.getData()!!.lotteries!!.rows?.get(i)!!.ticketType.toString()){
                            homeResponse.header=""
                            homeResponse.rows=it.getData()!!.lotteries!!.rows?.get(i)
                            homeResList.add(homeResponse)
                        }else{
                            type= it.getData()!!.lotteries!!.rows?.get(i)!!.ticketType.toString()
                            homeResponse.header=it.getData()!!.lotteries!!.rows?.get(i)!!.ticketType
                            homeResponse.rows=it.getData()!!.lotteries!!.rows?.get(i)
                            homeResList.add(homeResponse)
                        }
                       }



//                        if(it.getData()!!.lotteries!!.rows?.get(i)!!.ticketType!!.endsWith(resources.getString(R.string.daily),true)){
//                            binding.dashboardTxtLatestDrawLotteryHeading.text= it.getData()!!.lotteries!!.rows?.get(i)!!.name
//                            binding.dashboardTxtLatestDrawLotteryDate.text= it.getData()!!.lotteries!!.rows?.get(i)!!.endTime
//                            binding.dashboardTxtLatestDrawJackpotPrice.text= it.getData()!!.lotteries!!.rows?.get(i)!!.jackpotAmount
//
//                            bundleLatest.putSerializable("latestDraw",it.getData()!!.lotteries!!.rows?.get(i)!!)
//
//                        }else if(it.getData()!!.lotteries!!.rows?.get(i)!!.ticketType!!.endsWith(resources.getString(R.string.monthly),true)){
//                            binding.dashboardTxtMonthlyDrawLotteryHeading.text= it.getData()!!.lotteries!!.rows?.get(i)!!.name
//                            binding.dashboardTxtMonthlyDrawLotteryDate.text= it.getData()!!.lotteries!!.rows?.get(i)!!.endTime
//                            binding.dashboardTxtMonthlyDrawJackpotPrice.text= it.getData()!!.lotteries!!.rows?.get(i)!!.jackpotAmount
//
//                            bundleMontly.putSerializable("latestDraw",it.getData()!!.lotteries!!.rows?.get(i)!!)
//
//                        }else if(it.getData()!!.lotteries!!.rows?.get(i)!!.ticketType!!.endsWith(resources.getString(R.string.occasionally),true)){
//                            binding.dashboardTxtOccationDrawLotteryHeading.text= it.getData()!!.lotteries!!.rows?.get(i)!!.name
//                            binding.dashboardTxtOccationDrawLotteryDate.text= it.getData()!!.lotteries!!.rows?.get(i)!!.endTime
//                            binding.dashboardTxtOccationDrawJackpotPrice.text= it.getData()!!.lotteries!!.rows?.get(i)!!.jackpotAmount
//
//                            bundleOccationally.putSerializable("latestDraw",it.getData()!!.lotteries!!.rows?.get(i)!!)
//                        }
                    }

                    print("arraysize:-  ${homeResList.size}")
                    type=""
                    adapterBuyLottry=
                        Adapter_Home_Lottry(mainActivity,homeResList,object : Adapter_Home_Lottry.ClickListner{
                            override fun getTicketList(lottry: HomeTempResponse) {
//                            TODO("Not yet implemented")
                                bundleLatest.putSerializable("latestDraw",lottry.rows)
                                loadFragment(mainActivity.supportFragmentManager,Fragment_Buy_Ticket(),R.id.main_activity_main_container,false,"Ticket List",bundleLatest)
                            }
                        })
                    binding.recyclerview.adapter=adapterBuyLottry
                    binding.recyclerview.layoutManager=managerHome
                }
            }else{

                showToast(mainActivity.resources.getString(R.string.you_have_no_data))
                binding.progessBar.visibility=
                    View.GONE
            }
        })
        binding.dashboardBtnLatestDrawTicketBuy.setOnClickListener(this@Fragment_Home)
        binding.dashboardBtnMonthlyDrawTicketBuy.setOnClickListener(this@Fragment_Home)
        binding.dashboardBtnOccationDrawTicketBuy.setOnClickListener(this@Fragment_Home)
        viewModel.get_walletBalance(mainActivity,binding)

    }

    fun setPopUpWindow() {

        lateinit var inflater: LayoutInflater
        lateinit var view: View


        inflater= mainActivity.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        view = inflater.inflate(R.layout.wallet_popup_menu, null);

//        Start = (RelativeLayout) view . findViewById (R.id.start_btn);
//        Pause = (RelativeLayout) view . findViewById (R.id.pause_btn);
//        Stop = (RelativeLayout) view . findViewById (R.id.stop_btn);

        mypopupWindow = PopupWindow(view, 300, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){

           R.id.dashboard_btn_latest_draw_ticket_buy ->{

               loadFragment(mainActivity.supportFragmentManager,Fragment_Buy_Ticket(),R.id.main_activity_main_container,false,"Ticket List",bundleLatest)
           }
            R.id.dashboard_btn_monthly_draw_ticket_buy ->{

               loadFragment(mainActivity.supportFragmentManager,Fragment_Buy_Ticket(),R.id.main_activity_main_container,false,"Ticket List",bundleMontly)
           }
            R.id.dashboard_btn_occation_draw_ticket_buy ->{

               loadFragment(mainActivity.supportFragmentManager,Fragment_Buy_Ticket(),R.id.main_activity_main_container,false,"Ticket List",bundleOccationally)
           }


        }
    }


    override fun onResume() {
        super.onResume()

        init()

    }

}
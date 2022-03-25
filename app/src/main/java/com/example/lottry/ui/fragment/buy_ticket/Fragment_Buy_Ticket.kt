package com.example.lottry.ui.fragment.buy_ticket

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.Response_Ticket_List
import com.example.lottry.databinding.FragmentBuyTicketBinding
import com.example.lottry.databinding.FragmentHomeBinding
import com.example.lottry.ui.activity.main.MainActivity

import android.widget.TextView

import android.graphics.drawable.ColorDrawable

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.os.Handler
import android.os.Looper
import android.provider.Settings.Global.getString
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.animation.content.Content
import com.example.lottry.baseclass.fragment.Base_Fragment
import com.example.lottry.data.remote.retrofit.request.Request_buyTicket
import com.example.lottry.data.remote.retrofit.response.Row
import com.example.lottry.ui.fragment.home.Fragment_Home
import com.example.lottry.utils.Constant
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


open class Fragment_Buy_Ticket :Base_Fragment() ,View.OnClickListener{

    lateinit var notify_txt: TextView
    lateinit var wallet_txt: TextView
    lateinit var binding :FragmentBuyTicketBinding
    lateinit var mainActivity:MainActivity
    lateinit var viewModel:Fragment_Buy_Ticket_viewModel
    lateinit var adapterBuyTicket: Adapter_Buy_Ticket
    lateinit var manager:RecyclerView.LayoutManager
    var ticketList:ArrayList<Response_Ticket_List> = ArrayList()
    var  buyTicketList: ArrayList<String> =ArrayList()
    lateinit var  bundle: Bundle
     var row: Row=Row()
    lateinit var dialog:Dialog
    lateinit var progressBar: ProgressBar
    lateinit var sharedPreferences: SharedPreferencesUtil;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity=(activity as MainActivity)
        sharedPreferences=SharedPreferencesUtil(mainActivity)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_buy_ticket,container,false)
        setInstance(requireActivity())
        setHasOptionsMenu(true)
        val view: View =binding.root
        manager=LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL,false)
        notify_txt = mainActivity.findViewById(R.id.notify_txt)
        wallet_txt = mainActivity.findViewById(R.id.wallet_txt)
        init()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.ticket_list_option_menu,menu)
        menu.getItem(0).isVisible=false
        menu.getItem(1).isVisible=false

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.option_refresh->{
                init()
            }
        }

        return true
    }

    override fun onResume() {
        mainActivity.setTitleName(resources.getString(R.string.buy_ticket))
        super.onResume()
    }
    fun init(){

        mainActivity.setTitleName(resources.getString(R.string.buy_ticket))
        mainActivity.hide_user_detail()

        notify_txt.visibility = View.GONE
        wallet_txt.visibility = View.GONE

        binding.buyTicketBtnTicket.setOnClickListener(this)
        bundle=requireArguments()

        if(bundle!=null){

            row= bundle.getSerializable("latestDraw") as Row

        }
        buyTicketList.clear()
        showTotlaAmt()
//        if(row.id!!.equals(1))
        binding.buyTicketTxtDraw.text=row.ticketType?.capitalize()+" Draw"
//        else if(row.id!!.equals(2))
//            binding.buyTicketTxtDraw.text=mainActivity.resources.getString(R.string.monthly_draw)
//        else
//            binding.buyTicketTxtDraw.text=mainActivity.resources.getString(R.string.occasion_draw)
        viewModel=ViewModelProvider(requireActivity()).get(Fragment_Buy_Ticket_viewModel::class.java)



        viewModel.get_TicketList(mainActivity,binding).observe(mainActivity, Observer {

            if(it!=null){

                if(it.getSuccess()==true){
                    binding.progessBar.visibility=View.GONE
                    ticketList= it.getData()!!.ticketList as ArrayList<Response_Ticket_List>
                    adapterBuyTicket=Adapter_Buy_Ticket(mainActivity,row,ticketList,object : Adapter_Buy_Ticket.ClickListner{

                        override fun getTicketList(buyTicketList: ArrayList<String>) {
//                            TODO("Not yet implemented")
                            getBuyTicketList(buyTicketList)
                        }
                    })
                    binding.buyTicketRv.adapter=adapterBuyTicket
                    binding.buyTicketRv.layoutManager=manager

                }
            }else{

                binding.progessBar.visibility=View.GONE
                showToast(mainActivity.resources.getString(R.string.you_have_no_data))
            }

        })

//        ticketList=ArrayList()
//        ticketList.add(Response_Ticket_List(1,"0000000001","01/11/21","7:30 AM","100",false))
//        ticketList.add(Response_Ticket_List(2,"0000000002","01/11/21","7:30 AM","100",false))
//        ticketList.add(Response_Ticket_List(3,"0000000003","01/11/21","7:30 AM","100",false))



    }

    open fun showDialog() {
        dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_payment)
        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        dialog.findViewById<TextView>(R.id.dialog_payment_txt_jackpot_amt).text=row.jackpotAmount
        dialog.findViewById<TextView>(R.id.dialog_payment_txt_price).text=""+buyTicketList.size*row.ticketPrice!!.toInt()
        dialog.findViewById<TextView>(R.id.dialog_payment_txt_ticket_count).text=""+buyTicketList.size
        progressBar=dialog.findViewById<ProgressBar>(R.id.progess_bar)


        dialog.findViewById<TextView>(R.id.dialog_payment_btn_pay).setOnClickListener(this)
//        dialog.findViewById(R.id.txt_file_path).text = msg
       /* val dialogBtn_cancel: Button = dialog.findViewById(R.id.btn_cancel) as Button
        dialogBtn_cancel.setOnClickListener(View.OnClickListener { //                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
            dialog.dismiss()
        })
        val dialogBtn_okay: Button = dialog.findViewById(R.id.btn_okay) as Button
        dialogBtn_okay.setOnClickListener(View.OnClickListener { //                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
            dialog.cancel()
        })*/
        dialog.show()
    }

    override fun onClick(p0: View?) {
//        TODO("Not yet implemented")
        when(p0!!.id){

            R.id.buy_ticket_btn_ticket ->{
                if (buyTicketList.size<=0){
                    showResponseDialog(resources.getString(R.string.select_ticker))
                    //Toast.makeText(context,resources.getString(R.string.select_ticker), Toast.LENGTH_LONG).show()
                    return
                }
                showDialog()
            }

            R.id.dialog_payment_btn_pay->{

                  buyTicket()

            }
        }
    }

    fun getBuyTicketList(buyTicketList: ArrayList<String>) {
//        TODO("Not yet implemented")

        this.buyTicketList.clear()
        this.buyTicketList.addAll(buyTicketList)
        showTotlaAmt()

    }
    fun showTotlaAmt(){

        mainActivity.show_ticket_amt(buyTicketList.size*row.ticketPrice!!.toInt())

    }



    fun buyTicket(){
        var ticketPrice=buyTicketList.size*row.ticketPrice!!.toInt();
        var  totalwalletBalance=sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.WALLET_BALANCE)!!.toInt()

        if (totalwalletBalance<ticketPrice){
            showDialogInsufficientFund();
            dialog.dismiss()
            return
        }
        progressBar.visibility=View.VISIBLE
val requestBuyticket=Request_buyTicket()

        requestBuyticket.lotteryId=row.id.toString()
        requestBuyticket.ticketList=buyTicketList
        requestBuyticket.ticketPrice=ticketPrice.toString()

        viewModel.buyTicket(mainActivity,requestBuyticket).observe(mainActivity, Observer {

            if(it!=null){
                dialog.dismiss()
                progressBar.visibility=View.GONE
                val context = getContext() ?: return@Observer // early return using Elvis operator
                context.getString(R.string.buy_ticket_success)

                showResponseDialog(requireContext().getString(R.string.buy_ticket_success))
                //Toast.makeText(context, requireContext().getString(R.string.buy_ticket_success) ,Toast.LENGTH_LONG).show()
                Handler(Looper.myLooper()!!).postDelayed({
                    mainActivity.backToHomeScreen();
                },2000)

               // init()
            }else{
                mainActivity.backToHomeScreen();
                dialog.dismiss()
                progressBar.visibility=View.GONE
            }
        })


    }

    fun showDialogInsufficientFund() {

        var dialog = Dialog(mainActivity);
        dialog.setCancelable(false);

        var view = mainActivity.layoutInflater.inflate(R.layout.dialog_insufficiant_fund, null)

        dialog.setContentView(view);
        if (dialog.getWindow() != null) {
            dialog.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        var btnSubmit = view.findViewById<Button>(R.id.dialog_amt_btn_add)
        var btnCancel = view.findViewById<Button>(R.id.dialog_amt_btn_cancel)

        btnCancel.setOnClickListener(View.OnClickListener { view ->

            dialog.dismiss()

        })

        btnSubmit.setOnClickListener(View.OnClickListener { view ->
            dialog.dismiss()
            mainActivity.addBalanceFragment();
        })

        dialog.show();
    }

    fun showResponseDialog(type: String){

        var dialog = Dialog(mainActivity);
        dialog.setCancelable(false);

        var view = mainActivity.layoutInflater.inflate(R.layout.toast_dialog, null)

        dialog.setContentView(view);
        if (dialog.getWindow() != null) {
            dialog.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        var btnOk = view.findViewById<Button>(R.id.dialog_response_ok)
        var response_txt = view.findViewById<TextView>(R.id.response_txt)

        response_txt.setText(type)

        btnOk.setOnClickListener(View.OnClickListener { view ->

            dialog.dismiss()
        })
        dialog.show();
    }

}
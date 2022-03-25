package com.example.lottry.ui.fragment.home

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.BR
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.HomeTempResponse
import com.example.lottry.data.remote.retrofit.response.Response_Ticket_List
import com.example.lottry.data.remote.retrofit.response.Row


open class Adapter_Home_Lottry() : RecyclerView.Adapter<Adapter_Home_Lottry.MyViewHoder>() {


lateinit var activity:AppCompatActivity
 //var row:Row= Row()
 var  lottry: ArrayList<HomeTempResponse> =ArrayList()
    lateinit var clickListner:ClickListner



    constructor(activity:AppCompatActivity, ticketList:ArrayList<HomeTempResponse>,clickListner: ClickListner) : this() {
    this.activity=activity
        this.lottry=ticketList
        this.clickListner=clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
//        TODO("Not yet implemented")
        var view= LayoutInflater.from(activity).inflate(R.layout.home_item_view,parent,false)
        return MyViewHoder(view)
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
//        TODO("Not yet implemented")

        if (lottry.get(position).header!!.isNotEmpty()){
            holder.dashboard_txt_latest_draw.text= lottry.get(position).header!!.capitalize()+" Draw"
            holder.dashboard_txt_latest_draw.visibility=View.VISIBLE
        }else{
            holder.dashboard_txt_latest_draw.visibility=View.GONE
        }
        holder.dashboard_txt_latest_draw_jackpot_price.text=lottry.get(position).rows!!.jackpotAmount
        holder.dashboard_txt_latest_draw_lottery_heading.text=lottry.get(position).rows!!.name
        holder.dashboard_txt_latest_draw_lottery_sub_heading.text=lottry.get(position).rows!!.openTime
        holder.dashboard_txt_latest_draw_lottery_date.text=lottry.get(position).rows!!.openTime
        holder.dashboard_txt_latest_draw_ticket_count.text=lottry.get(position).rows!!.ticketPrice
//        holder.binding.setVariable(BR.ticket,ticketList[position])
//        holder.binding.executePendingBindings()


//        holder.txt_ticketNo.text=ticketList.get(position)!!.id.toString()
//        holder.txt_ticketPrice.text=row.ticketPrice.toString()
//        holder.txt_ticketJackpotPrice.text=row.jackpotAmount.toString()
//        holder.txt_ticketDate.text=row.endTime.toString()
//        Log.e("response",row.ticketPrice.toString())
//

        holder.dashboard_btn_latest_draw_ticket_buy.setOnClickListener(View.OnClickListener {
            clickListner.getTicketList(lottry.get(position))
        })

    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return lottry.size
    }

    class MyViewHoder : RecyclerView.ViewHolder {

        lateinit var dashboard_txt_latest_draw:TextView
        lateinit var dashboard_txt_latest_draw_jackpot_price:TextView
        lateinit var dashboard_txt_latest_draw_lottery_heading:TextView
        lateinit var dashboard_txt_latest_draw_lottery_sub_heading:TextView
        lateinit var dashboard_txt_latest_draw_lottery_date:TextView
        lateinit var dashboard_txt_latest_draw_ticket_count:TextView
        lateinit var dashboard_btn_latest_draw_ticket_buy:AppCompatButton


        constructor(itemView: View) : super(itemView) {
            dashboard_txt_latest_draw=itemView.findViewById(R.id.dashboard_txt_latest_draw)
            dashboard_txt_latest_draw_jackpot_price=itemView.findViewById(R.id.dashboard_txt_latest_draw_jackpot_price)
            dashboard_txt_latest_draw_lottery_heading=itemView.findViewById(R.id.dashboard_txt_latest_draw_lottery_heading)
            dashboard_txt_latest_draw_lottery_sub_heading=itemView.findViewById(R.id.dashboard_txt_latest_draw_lottery_sub_heading)
            dashboard_txt_latest_draw_lottery_date=itemView.findViewById(R.id.dashboard_txt_latest_draw_lottery_date)
            dashboard_txt_latest_draw_ticket_count=itemView.findViewById(R.id.dashboard_txt_latest_draw_ticket_count)
            dashboard_btn_latest_draw_ticket_buy=itemView.findViewById(R.id.dashboard_btn_latest_draw_ticket_buy)
        }


    }

    public interface ClickListner{

        public fun getTicketList(buyTicketList : HomeTempResponse)

    }
}
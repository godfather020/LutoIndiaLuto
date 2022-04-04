package com.example.lottry.ui.fragment.my_ticket

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.parser.IntegerParser
import com.example.lottry.BR
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.DailyTickets
import com.example.lottry.data.remote.retrofit.response.Response_Ticket_List
import com.example.lottry.data.remote.retrofit.response.Row
import kotlinx.coroutines.newFixedThreadPoolContext
import java.time.LocalTime
import java.util.*
import javax.annotation.Resource
import kotlin.collections.ArrayList


open class Adapter_My_Ticket() : RecyclerView.Adapter<Adapter_My_Ticket.MyViewHoder>() {


lateinit var activity:AppCompatActivity
 var row:Row= Row()
 var  ticketList: ArrayList<DailyTickets> =ArrayList()
 var  buyTicketList: ArrayList<String> =ArrayList()
    lateinit var clickListner:ClickListner
    lateinit var dailyTickets:DailyTickets




    constructor(activity:AppCompatActivity, dailyTickets: DailyTickets) : this() {
    this.activity=activity
        this.dailyTickets=dailyTickets
        this.row=row


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
//        TODO("Not yet implemented")
        var view= LayoutInflater.from(activity).inflate(R.layout.rv_ticket_list,parent,false)
        return MyViewHoder(view)
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
//        TODO("Not yet implemented")

//        holder.binding.setVariable(BR.ticket,ticketList[position])
//        holder.binding.executePendingBindings()

//        val selectedTicket: Response_Ticket_List=ticketList.get(position)
        holder.item.visibility = View.VISIBLE

       val row= dailyTickets.rows!![position]

        holder.cb_selectTicket.visibility=View.GONE

        holder.txt_ticketNo.text=row.ticketNumber.toString()
        holder.txt_ticketPrice.text=row.ticketPrice.toString()
        holder.txt_ticketJackpotPrice.text=row.lottery!!.jackpotAmount.toString()
        holder.txt_ticketDate.text=row.lottery!!.openTime.toString()
        Log.e("response",row.ticketPrice.toString())

        val hours = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now().hour
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val minutes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now().minute
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        /*var currentTime = hours

        var createdTime = row.createdAt.toString()

        Log.d("cratedTime", createdTime)

        createdTime = createdTime.replaceBefore("T", "")

        Log.d("cratedTimeTrim", createdTime)

        createdTime = createdTime.replaceAfter(":","")
        createdTime = createdTime.replace("T", "")
        createdTime = createdTime.replace(":", "")

        Log.d("cratedTimeTrim", createdTime)

        var createdTimeInt = Integer.parseInt(createdTime)

        var openTime = row.lottery!!.openTimeDesc?.toString()
        openTime = openTime?.replaceAfter(":", "")
        openTime = openTime?.replace(":", "")

        var openTimeInt = Integer.parseInt(openTime.toString())
        Log.d("openTime", openTimeInt.toString())

        if (createdTimeInt < openTimeInt && currentTime > openTimeInt && row.lottery!!.id == 5){

            holder.item.visibility = View.GONE

        }

        else if (createdTimeInt < openTimeInt && row.lottery!!.id == 6){

            holder.item.visibility = View.GONE

        }

        else if (createdTimeInt < openTimeInt && row.lottery!!.id == 7){

            holder.item.visibility = View.GONE

        }

        else if (createdTimeInt < openTimeInt && row.lottery!!.id == 8){

            holder.item.visibility = View.GONE

        }

        else{

            holder.item.visibility = View.VISIBLE
        }*/

       /* holder.item.setOnClickListener(View.OnClickListener {

            *//*if(!holder.cb_selectTicket.isChecked){



                holder.cb_selectTicket.isChecked =true
                ticketList.get(position).selected =true

                selectedTicket.selected=true

//                notifyDataSetChanged()


                buyTicketList.add(ticketList.get(position).id)
                clickListner.getTicketList(buyTicketList)


                //check list
                if(buyTicketList.size>0) {

                    for (i in 0 until buyTicketList.size) {

                        Log.e("ticket", buyTicketList.get(i)+"+++"+ticketList.get(position).selected.toString())

                    }
                }



            }
            else{


                holder.cb_selectTicket.isChecked =false
                ticketList.get(position).selected =false
                selectedTicket.selected=false
                if(buyTicketList.size>0){
                    for (i in 0 until  buyTicketList.size){

                        if(buyTicketList.get(i).equals(ticketList.get(position).id)){

                            buyTicketList.remove(buyTicketList.get(i))
                            break
                        }
                    }
                }
                clickListner.getTicketList(buyTicketList)

                //check list
//                if(buyTicketList.size>0) {
//                    for (i in 0 until buyTicketList.size) {
//
//                        Log.e("ticket", buyTicketList.get(i))
//
//                    }
//                }

            }*//*

            if(!holder.cb_selectTicket.isChecked){


                holder.cb_selectTicket.isChecked =true
                getList(holder.cb_selectTicket.isChecked,selectedTicket)





         }
         else{


             holder.cb_selectTicket.isChecked =false
                getList(holder.cb_selectTicket.isChecked,selectedTicket)

         }

        })


        holder.cb_selectTicket.setOnClickListener(View.OnClickListener {

            *//*if(!holder.cb_selectTicket.isChecked){



                holder.cb_selectTicket.isChecked =true
                ticketList.get(position).selected =true

                selectedTicket.selected=true

//                notifyDataSetChanged()


                buyTicketList.add(ticketList.get(position).id)
                clickListner.getTicketList(buyTicketList)


                //check list
                if(buyTicketList.size>0) {

                    for (i in 0 until buyTicketList.size) {

                        Log.e("ticket", buyTicketList.get(i)+"+++"+ticketList.get(position).selected.toString())

                    }
                }



            }
            else{


                holder.cb_selectTicket.isChecked =false
                ticketList.get(position).selected =false
                selectedTicket.selected=false
                if(buyTicketList.size>0){
                    for (i in 0 until  buyTicketList.size){

                        if(buyTicketList.get(i).equals(ticketList.get(position).id)){

                            buyTicketList.remove(buyTicketList.get(i))
                            break
                        }
                    }
                }
                clickListner.getTicketList(buyTicketList)

                //check list
//                if(buyTicketList.size>0) {
//                    for (i in 0 until buyTicketList.size) {
//
//                        Log.e("ticket", buyTicketList.get(i))
//
//                    }
//                }

            }*//*

            if(holder.cb_selectTicket.isChecked){


                holder.cb_selectTicket.isChecked =true
                getList(holder.cb_selectTicket.isChecked,selectedTicket)





         }
         else{


             holder.cb_selectTicket.isChecked =false
                getList(holder.cb_selectTicket.isChecked,selectedTicket)

         }

        })*/




    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return dailyTickets.rows!!.size
    }

    class MyViewHoder : RecyclerView.ViewHolder {

        lateinit var binding:ViewDataBinding
        lateinit var txt_ticketNo:TextView
        lateinit var txt_ticketPrice:TextView
        lateinit var txt_ticketDate:TextView
        lateinit var txt_ticketJackpotPrice:TextView
        lateinit var cb_selectTicket:CheckBox
        lateinit var item:ConstraintLayout


        constructor(itemView: View) : super(itemView) {

            binding= DataBindingUtil.bind(itemView)!!
            txt_ticketNo=itemView.findViewById(R.id.ticket_list_txt_lottery_no)
            txt_ticketPrice=itemView.findViewById(R.id.ticket_list_txt_ticket_price)
            txt_ticketJackpotPrice=itemView.findViewById(R.id.ticket_list_draw_price_money)
            txt_ticketDate=itemView.findViewById(R.id.ticket_list_txt_date)
            cb_selectTicket=itemView.findViewById(R.id.ticket_list_img_check_box)
            item=itemView.findViewById(R.id.rv_ticket_list_parent)

        }


    }

    public fun getList(checked:Boolean,ticketList: Response_Ticket_List){

        if(checked){



//            holder.cb_selectTicket.isChecked =true
            ticketList.selected =true

//            selectedTicket.selected=true

//                notifyDataSetChanged()


            buyTicketList.add(ticketList.id)
            clickListner.getTicketList(buyTicketList)


            //check list
            if(buyTicketList.size>0) {

                for (i in 0 until buyTicketList.size) {

                    Log.e("ticket", buyTicketList.get(i)+"+++"+ticketList.selected.toString())

                }
            }



        }
        else{


//            holder.cb_selectTicket.isChecked =false
            ticketList.selected =false
//            selectedTicket.selected=false
            if(buyTicketList.size>0){
                for (i in 0 until  buyTicketList.size){

                    if(buyTicketList.get(i).equals(ticketList.id)){

                        buyTicketList.remove(buyTicketList.get(i))
                        break
                    }
                }
            }
            clickListner.getTicketList(buyTicketList)

            //check list
                if(buyTicketList.size>0) {
                    for (i in 0 until buyTicketList.size) {

                        Log.e("ticket", buyTicketList.get(i))

                    }
                }

        }

    }
    public interface ClickListner{

        public fun getTicketList(buyTicketList : ArrayList<String>)

    }
}
package com.example.lottry.ui.fragment.buy_ticket

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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.BR
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.Response_Ticket_List
import com.example.lottry.data.remote.retrofit.response.Row


open class Adapter_Buy_Ticket() : RecyclerView.Adapter<Adapter_Buy_Ticket.MyViewHoder>() {


lateinit var activity:AppCompatActivity
lateinit var buyActivity: Fragment_Buy_Ticket
 var row:Row= Row()
 var  ticketList: ArrayList<Response_Ticket_List> =ArrayList()
 var  buyTicketList: ArrayList<String> =ArrayList()
    lateinit var clickListner:ClickListner



    constructor(activity:AppCompatActivity, row: Row, ticketList:ArrayList<Response_Ticket_List>,clickListner: ClickListner) : this() {
    this.activity=activity
        this.ticketList=ticketList
        this.row= row
        this.clickListner=clickListner



    }
    constructor(activity:AppCompatActivity, ticketList:ArrayList<Response_Ticket_List> ) : this() {
    this.activity=activity
        this.ticketList=ticketList
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

        val selectedTicket: Response_Ticket_List=ticketList.get(position)


        if(ticketList.get(position).selected){

            holder.cb_selectTicket.isChecked=true

        }else {
            holder.cb_selectTicket.isChecked = false
        }

        holder.txt_ticketNo.text=ticketList.get(position)!!.id.toString()
        holder.txt_ticketPrice.text=row.ticketPrice.toString()
        holder.txt_ticketJackpotPrice.text=row.jackpotAmount.toString()
        holder.txt_ticketDate.text=row.openTime.toString()
        Log.e("response",row.ticketPrice.toString())


        holder.item.setOnClickListener(View.OnClickListener {

            /*if(!holder.cb_selectTicket.isChecked){



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

            }*/

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

            /*if(!holder.cb_selectTicket.isChecked){



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

            }*/

            if(holder.cb_selectTicket.isChecked){


                holder.cb_selectTicket.isChecked =true
                getList(holder.cb_selectTicket.isChecked,selectedTicket)





         }
         else{


             holder.cb_selectTicket.isChecked =false
                getList(holder.cb_selectTicket.isChecked,selectedTicket)

         }

        })




    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return ticketList.size
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
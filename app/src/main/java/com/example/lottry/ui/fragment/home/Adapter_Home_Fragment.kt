package com.example.lottry.ui.fragment.home

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.BR
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.Response_Ticket_List
import com.example.lottry.data.remote.retrofit.response.Row
import com.example.lottry.data.remote.retrofit.response.Winners


open class Adapter_Home_Fragment() : RecyclerView.Adapter<Adapter_Home_Fragment.MyViewHoder>() {


lateinit var activity:AppCompatActivity
 var row:Row= Row()
 var  ticketList: ArrayList<Response_Ticket_List> =ArrayList()
 var  buyTicketList: ArrayList<String> =ArrayList()
    lateinit var clickListner:ClickListner
    lateinit var winners:Winners




    constructor(activity:AppCompatActivity, winners: Winners ) : this() {
    this.activity=activity
        this.winners=winners
        this.row=row


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
//        TODO("Not yet implemented")
        var view= LayoutInflater.from(activity).inflate(R.layout.rv_top_winner,parent,false)
        return MyViewHoder(view)
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
//        TODO("Not yet implemented")

//        holder.binding.setVariable(BR.ticket,ticketList[position])
//        holder.binding.executePendingBindings()

        if (winners.rows!!.get(position).fakeUser == null){

            val user = winners.rows!!.get(position).user
            val ticket=winners.rows!!.get(position).ticket

            var phoneNum = user!!.phoneNumber.toString()
            phoneNum = phoneNum.get(0).toString()+phoneNum.get(1).toString()+"xxxxxx"+phoneNum.get(8).toString()+phoneNum.get(9).toString()
            Log.d("phonenum", phoneNum)
            //holder.txt_winningPrice.text=winners.rows!!.get(position)..toString()
            holder.txt_userName.text=phoneNum

            if(user.profilePic!=null){

            }

            Log.e("response", row.ticketPrice.toString())
        }
        else{

            val rnds1 = (6..9).random()
            val rnds2 = (6..9).random()
            val rnds3 = (0..9).random()
            val rnds4 = (0..9).random()

            val user = winners.rows!!.get(position).fakeUser

            holder.txt_userName.text=rnds1.toString()+rnds2.toString()+"xxxxxx"+rnds3.toString()+rnds4.toString()
        }

    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return winners.rows!!.size
    }

    class MyViewHoder : RecyclerView.ViewHolder {

//        lateinit var binding:ViewDataBinding
        //lateinit var txt_winningPrice:TextView
        lateinit var txt_userName:TextView
        lateinit var img_user:ImageView



        constructor(itemView: View) : super(itemView) {

//            binding= DataBindingUtil.bind(itemView)!!
            txt_userName=itemView.findViewById(R.id.rv_top_winner_txt_username)
            //txt_winningPrice=itemView.findViewById(R.id.rv_top_winner_txt_winning_price)
            img_user=itemView.findViewById(R.id.rv_top_winner_img_user)
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
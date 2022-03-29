package com.example.lottry.ui.fragment.notification

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.Row
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.ui.fragment.tran_history.Tran_History_Adapter
import com.softs.meetupfellow.components.activity.CustomAppActivity
import java.util.ArrayList


open class Adapter_Notification() : RecyclerView.Adapter<Adapter_Notification.MyViewHoder>() {

    lateinit var activity:CustomAppActivity

    var list_Notification: MutableList<Row> = ArrayList<Row>()

    var clickListener: Tran_History_Adapter.ClickListener? = null

    constructor(
        activity: CustomAppActivity,
        list_Notification: MutableList<Row>,
        clickListener: Tran_History_Adapter.ClickListener?
    ) : this() {
        this.activity = activity
        this.list_Notification = list_Notification

        this.clickListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
//        TODO("Not yet implemented")
        var view= LayoutInflater.from(activity).inflate(R.layout.rv_noti_list,parent,false)
        return MyViewHoder(view)
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
//        TODO("Not yet implemented")

        holder.txt_header.text=list_Notification.get(position).description
//        holder.txt_name.text=list_Notification.get(position).title
        holder.txt_name.visibility=View.GONE
        holder.txt_date.visibility=View.VISIBLE
        holder.txt_price.visibility=View.GONE

        var createdAt = list_Notification.get(position).createdAt?.replace("T"," ")
        if (createdAt != null) {
            createdAt = createdAt.replaceAfter(".","")
            createdAt = createdAt.replace(".","")
        }
        holder.txt_date.text = createdAt
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return list_Notification.size
    }

    class MyViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var txt_header=itemView.findViewById<TextView>(R.id.noti_txt_heading)
        var txt_name=itemView.findViewById<TextView>(R.id.noti_txt_name)
        var txt_date=itemView.findViewById<TextView>(R.id.noti_txt_date)
        var txt_price=itemView.findViewById<TextView>(R.id.noti_txt_price_amt)


    }
}
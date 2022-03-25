package com.example.lottry.ui.fragment.tran_history

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.Row
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Tran_History_Adapter : RecyclerView.Adapter<Tran_History_Adapter.MyViewHolder?> {
    var context: Context? = null
    var clicked = false
    var list_Website: MutableList<Row>? = null
    var ticketList: MutableList<Row>? = null
    var ticketListType: MutableList<Row> = ArrayList<Row>()
    var ticketListTitle: List<Row> = ArrayList<Row>()
    var position1 = 0
    var clickListener: ClickListener? = null

    constructor() {}
    constructor(
        context: Context?,
        list_Website: MutableList<Row>?,
        clickListener: ClickListener?
    ) {
        this.context = context
        ticketList = list_Website
        this.list_Website = ArrayList<Row>()
        this.list_Website!!.addAll(list_Website!!)
        this.clickListener = clickListener
    }

    constructor(context: Context?, list_Website: MutableList<Row>?) {
        this.context = context
        ticketList = list_Website
        this.list_Website = list_Website
    }

    override fun getItemViewType(position: Int): Int {
        position1 = position
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.rv_trans_history_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(ticketList!!.get(position).status.equals(context!!.resources.getString(R.string.deducted))){

            holder.txtStatus.setTextColor(context!!.resources.getColor(R.color.red))
        }

        else if(ticketList!!.get(position).status.equals(context!!.resources.getString(R.string.pending))){

            holder.txtStatus.setTextColor(context!!.resources.getColor(R.color.yellow))
        }

        holder.txtHeader.text= ticketList!!.get(position).remark
        holder.txtStatus.text= ticketList!!.get(position).status
        holder.txtAmt.text= "₹ "+ticketList!!.get(position).amount.toString()
        holder.txtDate.text= ticketList!!.get(position).type

        holder.cl_parent.setOnClickListener(View.OnClickListener {
            clickListener!!.tran_detail(
                ticketList!![holder.getAdapterPosition()], holder.getAdapterPosition()
            )
        })
    }

    override fun getItemCount(): Int {

        return ticketList!!.size
    }



    // Filter Class
    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        ticketList!!.clear()
        if (charText.length == 0) {
            ticketList!!.addAll(list_Website!!)
        } else {
            for (wp in list_Website!!) {
//                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    ticketList!!.add(wp)
//                }
//                 else if(wp.getType().toLowerCase(Locale.getDefault()).contains(charText)){
//                    ticketList!!.add(wp);
//                }else {
//
//                }
            }
        }
        notifyDataSetChanged()
    }

    fun filterDateRange(startDate: Date?, endDate: Date?, type: String?) {
        ticketList!!.clear()
        if (startDate == null || startDate.equals("")) {
            if (type != null && !type.isEmpty()) {
                for (wp in list_Website!!) {
//                    if (wp.getTitle().toLowerCase(Locale.getDefault())
//                            .contains(type.toLowerCase())
//                    ) {
//                        ticketList!!.add(wp)
//                    }
                }
            } else {
                ticketList!!.addAll(list_Website!!)
            }
            notifyDataSetChanged()
        } else {
            try {
                for (wp in list_Website!!) {
//                    val sdf1 = SimpleDateFormat("dd MMM yy")
//                    val sdf = SimpleDateFormat("dd MMM yy hh:mm")
//                    val d = sdf.parse(wp.getNotificationDate())
//                    val date = sdf1.format(d)
//                    val strDate = sdf1.parse(date)
//                    Log.d("date", "Date===  $d DateStr=== $strDate")
//                    if (startDate == strDate || endDate == strDate) {
//                        ticketList!!.add(wp)
//                    } else if (startDate.before(strDate) && endDate.after(strDate)) {
//                        ticketList!!.add(wp)
//                    } else {
//                    }
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            if (type != null && !type.isEmpty()) {
                ticketListType.clear()
                for (wp in ticketList!!) {
//                    if (wp.getTitle().toLowerCase(Locale.getDefault())
//                            .contains(type.toLowerCase())
//                    ) {
//                        ticketListType.add(wp)
//                    }
                }
                ticketList!!.clear()
                ticketList!!.addAll(ticketListType)
            }
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /* ImageView img;
        LinearLayout ll;*/

//        var viewDataBinding: ViewDataBinding
        var cl_parent: ConstraintLayout
        var txtStatus: TextView
        var txtAmt: TextView
        var txtHeader: TextView
        var txtDate: TextView


        init {
            /* img=itemView.findViewById(R.id.img_btn_option);
            ll=itemView.findViewById(R.id.ll_img);*/
            //            viewDataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)

            cl_parent = itemView.findViewById(R.id.cl_parent)
            txtStatus = itemView.findViewById<TextView>(R.id.txt_tran_amt_status)
            txtAmt = itemView.findViewById<TextView>(R.id.txt_tran_amt)
            txtHeader = itemView.findViewById<TextView>(R.id.txt_tran_header)
            txtDate = itemView.findViewById<TextView>(R.id.txt_tran_date)
        }
    }

    interface ClickListener {
        fun tran_detail(Row: Row?, position: Int)
    }


}
package com.example.lottry.ui.fragment.tran_history

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lottry.R
import com.example.lottry.data.remote.retrofit.response.Row
import com.example.lottry.data.remote.retrofit.response.Transactionhistory
import com.example.lottry.databinding.TranHistoryFragmentBinding
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.ui.fragment.home.Fragment_Home_viewModel
import java.text.SimpleDateFormat
import java.util.*

class Tran_History_Fragment : Fragment(), View.OnClickListener {
    var binding: TranHistoryFragmentBinding? = null
    lateinit var activity: MainActivity
    var adapter: Tran_History_Adapter? = null
    var viewModel: Trans_History_Fragment_viewModel? = null
    var manager: RecyclerView.LayoutManager? = null
    var list: MutableList<Row> = ArrayList<Row>()
    var filterType: String? = null
    var fromDate: Date? = null
    var toDate: Date? = null
    var showFilterLayout = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.tran_history_fragment, container, false)
        activity = getActivity() as MainActivity

        viewModel = ViewModelProvider(requireActivity()).get(Trans_History_Fragment_viewModel::class.java)
        
        val view = binding!!.root
       
        initView()
        return view
    }

   

    fun initView() {
        manager = LinearLayoutManager(context)
        viewModel!!.getTransList(activity, binding!!)
            .observe(requireActivity(), androidx.lifecycle.Observer{ 
                if (it != null) {
                    it.getData()!!.transactions!!.rows?.let { it1 -> list.addAll(it1) }
                    binding!!.progessBar.visibility = View.GONE
                    adapter =
                        Tran_History_Adapter(context,
                            it.getData()!!.transactions!!.rows as MutableList<Row>?, object :
                            Tran_History_Adapter.ClickListener {

                                override fun tran_detail(Row: Row?, position: Int) {

                                }
                            })
                    binding!!.rvTranHistory.layoutManager = manager
                    binding!!.rvTranHistory.adapter = adapter
                }
            })
        binding!!.rvTranHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                 //   binding!!.fbBtnFilter.visibility = View.GONE
                } else if (dy < 0) {


                  //  binding!!.fbBtnFilter.visibility = View.VISIBLE
                }
            }
        })


//        binding.edtFromDate.setOnClickListener(this);
        binding!!.edtFromDate.setOnClickListener(this)
        binding!!.edtToDate.setOnClickListener(this)
        binding!!.btnFilter.setOnClickListener(this)
        binding!!.btnFilterCancel.setOnClickListener(this)
        binding!!.fbBtnFilter.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
//        activity.toolbar_title.setText(this@Tran_History_Fragment.tag)
        //        activity.showBottomNavigation();
//        activity.setBottomBarTab(3);
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.edt_from_date -> {}

//                utils.showDatePicker(activity, object : DatePickerListener() {
//                fun getDate(date: Date?) {
//                    setDate(date, binding!!.edtFromDate)
//                    fromDate = date
//                }
//            })
            R.id.edt_to_date -> {}

//            utils.showDatePicker(activity, object : DatePickerListener() {
//                fun getDate(date: Date?) {
//                    setDate(date, binding!!.edtToDate)
//                    toDate = date
//                }
//            })
            R.id.btn_filter -> {

                filterList()
            }

            R.id.btn_filter_cancel -> {
                adapter!!.filterDateRange(null, null, null)
                setFilter()
            }
            R.id.fb_btn_filter -> if (!showFilterLayout) {
                showFilterLayout = true
                setFilter()
                binding!!.clFilter.visibility = View.VISIBLE
            } else {
                showFilterLayout = false
                binding!!.clFilter.visibility = View.GONE
            }
        }
    }

    fun setFilter() {
        binding!!.edtToDate.text.clear()
        binding!!.edtFromDate.text.clear()
//        val plantsList: List<String> = ArrayList(
//            Arrays.asList(
//                *activity.getResources().getStringArray(R.array.drop_down_filter_array)
//            )
//        )
//        utils.setSpinnerAdapter(
//            activity,
//            binding!!.spinnerFilterType,
//            plantsList,
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View,
//                    position: Int,
//                    id: Long
//                ) {
//                    filterType = if (position > 0) {
//                        plantsList[position]
//                    } else {
//                        null
//                    }
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {}
//            })
    }

//    fun setDate(date: Date?, editText: EditText) {
//        val myFormat = "dd MMM yy" //Change as you need
//        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
//        editText.setText(sdf.format(date))
//    }

    fun filterList() {
        if (fromDate != null && !fromDate.toString()
                .isEmpty() && toDate != null && !toDate.toString().isEmpty()
        ) {
            if (!toDate!!.before(fromDate)) {
                adapter!!.filterDateRange(fromDate, toDate, filterType)
            } else {
//                utils.showToast(activity, "To date should not be before from From date ")
            }
        } else {
            if (filterType != null && !filterType!!.isEmpty()) {
                adapter!!.filterDateRange(fromDate, toDate, filterType)
            } else {
//                utils.showToast(activity, "Please select date range or type ")
            }
        }
    }
}
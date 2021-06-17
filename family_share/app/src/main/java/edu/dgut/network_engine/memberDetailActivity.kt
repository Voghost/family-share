package edu.dgut.network_engine

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import edu.dgut.network_engine.database.adapter.DetailAdapter
import edu.dgut.network_engine.database.adapter.EmptyAdapter
import edu.dgut.network_engine.database.entity.DateWithAccount
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.UserViewModel
import edu.dgut.network_engine.view_model.WalletViewModel
import kotlinx.android.synthetic.main.activity_detail_member.*
import java.text.SimpleDateFormat
import java.util.*


class memberDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userViewModel: UserViewModel
    private lateinit var walletViewModel: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_member)
        title = "个人账单详情"

        var bundle = this.intent.extras
        var userid = bundle?.get("userId")
        textView13.text = "你已成功跳转到账单详情,当前界面用户Id:" + bundle?.get("userId").toString()
        textView14.text = ""

     /*   val datelist1 = mutableListOf<Long>()
        var currentdate1 = System.currentTimeMillis()
        for(i in 0..29){
            datelist1.add(currentdate1)
            currentdate1 -= 1000 * 60 * 60 * 24
        }

        val datelist = mutableListOf<String>()
        var currentdate = System.currentTimeMillis()
        for(i in 0..29){
            datelist.add(SimpleDateFormat("yy-MM-dd").format(currentdate))
            currentdate -= 1000 * 60 * 60 * 24
        }

        val dateList2 = mutableListOf<DateWithAccount>()
        for(i in 0..29){
            var dateWithAccount = DateWithAccount(
                datelist[i],0.0,0.0
            )
            dateList2.add(dateWithAccount)
        }

        val dataPoint = Array<DataPoint>(dateList2.size) {
            DataPoint(0.0, 0.0)
        }

    userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getUserWithAccountListByUserId(bundle?.get("userId") as Long)
            ?.observe(this, { userList: UserWithAccountList ->
                for(j in 0..userList.accountList!!.size-1){
                    var temp = SimpleDateFormat("yy-MM-dd").format(userList.accountList!![j].createTime)
                    for (k in 0..dateList2.size-1){
                        if(temp == dateList2[k].date){
                            if(userList.accountList!![j].price!!.signum() == 1){
                                dateList2[k].cost = dateList2[k].cost?.plus(userList.accountList!![j].price!!.toDouble())
                            }else{
                                dateList2[k].income = dateList2[k].income?.plus(userList.accountList!![j].price!!.abs().toDouble())
                            }
                            break
                        }
                    }
                }
                val dataPoint = Array<DataPoint>(3) {
                    DataPoint(0.0, 0.0)
                }

                var count = 1000.0
                for (d in 0..2) {
                    val date: Date =SimpleDateFormat("yyyy-MM-dd").parse(dateList2[d].date)
                    dataPoint[d] = DataPoint(date,count)
                    count += 1000
                    Log.d("dataPoint is",dataPoint[d].toString())
                }
                val series = LineGraphSeries<DataPoint>(dataPoint)

                val graph = findViewById<GraphView>(R.id.graph)
                graph.removeAllSeries()
                graph.addSeries(series)
            })*/

        //开关控制曲线图
        var switchA:Switch = findViewById(R.id.switch1)
        var switchB:Switch = findViewById(R.id.switch2)
        var switchC:Switch = findViewById(R.id.switch3)
        var switchD:Switch = findViewById(R.id.switch4)
        var graph: GraphView =findViewById(R.id.graph)
        //最近一周收入测试数据
        var seriesA: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
        seriesA.appendData(DataPoint(1.0, 1.0), false, 3)
        seriesA.appendData(DataPoint(2.0, 2.0), false, 3)
        seriesA.appendData(DataPoint(3.0, 3.0), false, 3)
        graph.addSeries(seriesA)
        //
        switchA.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchB.isChecked=false
                switchC.isChecked=false
                switchD.isChecked=false
                graph.addSeries(seriesA)
            } else {
                graph.removeAllSeries()

            }
        }
        //最近一周支出测试数据
        var seriesC:LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
        seriesC.appendData(DataPoint(1.0, 1.0), false, 3)
        seriesC.appendData(DataPoint(2.0, 1.0), false, 3)
        seriesC.appendData(DataPoint(3.0, 3.0), false, 3)
        //
        switchC.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchA.isChecked=false
                switchB.isChecked=false
                switchD.isChecked=false
                graph.addSeries(seriesC)
            } else {
                graph.removeAllSeries()

            }
        }

        //最近一月测试数据
        var seriesB:LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
        seriesB.appendData(DataPoint(1.0, 6.0), false, 3)
        seriesB.appendData(DataPoint(2.0, 3.0), false, 3)
        seriesB.appendData(DataPoint(3.0, 1.0), false, 3)
        //
        switchB.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchA.isChecked=false
                switchC.isChecked=false
                switchD.isChecked=false
                graph.addSeries(seriesB)
            } else {
                graph.removeAllSeries()

            }
        }
        var seriesD:LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
        seriesD.appendData(DataPoint(1.0, 6.0), false, 3)
        seriesD.appendData(DataPoint(2.0, 3.0), false, 3)
        seriesD.appendData(DataPoint(3.0, 1.0), false, 3)
        //
        switchD.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchA.isChecked=false
                switchB.isChecked=false
                switchC.isChecked=false
                graph.addSeries(seriesD)
            } else {
                graph.removeAllSeries()
            }
        }
        recyclerView = findViewById(R.id.recyclerView)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        val temp =
            userViewModel.getUserWithAccountListByUserId(bundle?.get("userId") as Long)
                ?.observe(this, { userList: UserWithAccountList ->
                    if(userList.accountList!!.isEmpty()){
                        Log.v("测试","此用户无账单")
                        textView13.text = "此用户当前无任何账单"
                        recyclerView.adapter = EmptyAdapter(userList,userViewModel)
                    }else{
                        var cost = 0.0
                        var income = 0.0
                        for(i in 0..userList.accountList!!.size-1){
                            if(userList.accountList!![i].price!!.signum() == 1){
                                cost += userList.accountList!![i].price!!.toDouble()
                            }else{
                                income += userList.accountList!![i].price!!.abs().toDouble()
                            }
                        }
                        textView13.text = "支出:" + cost.toString()
                        textView14.text = "收入:" + income.toString()
                        recyclerView.adapter = DetailAdapter(this,userList, userViewModel,walletViewModel)
                    }
                    recyclerView.layoutManager = LinearLayoutManager(this)
                })

        button4.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("userId", userid.toString().toLong())
            var intent = Intent("android.intent.action.AddAccountActivity")
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}
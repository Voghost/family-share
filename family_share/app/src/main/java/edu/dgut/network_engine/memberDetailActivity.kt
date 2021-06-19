package edu.dgut.network_engine

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.database.adapter.DetailAdapter
import edu.dgut.network_engine.database.adapter.EmptyAdapter
import edu.dgut.network_engine.database.entity.DateWithAccount
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.AccountViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_detail_member.*
import java.text.SimpleDateFormat
import java.util.*


class memberDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userViewModel: UserViewModel

    //    private lateinit var walletViewModel: WalletViewModel
    private lateinit var accountViewModel: AccountViewModel


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_member)
        title = "个人账单详情"

        var bundle = this.intent.extras
        var userid = bundle?.get("userId")
        textView13.text = "你已成功跳转到账单详情,当前界面用户Id:" + bundle?.get("userId").toString()
        textView14.text = ""

        val datelist1 = mutableListOf<Long>()
        var currentdate1 = System.currentTimeMillis()
        for (i in 0..29) {
            datelist1.add(currentdate1)
            currentdate1 -= 1000 * 60 * 60 * 24
        }

        val datelist = mutableListOf<String>()
        var currentdate = System.currentTimeMillis()
        for (i in 0..29) {
            datelist.add(SimpleDateFormat("yy-MM-dd").format(currentdate))
            currentdate -= 1000 * 60 * 60 * 24
        }

        val dateList2 = mutableListOf<DateWithAccount>()
        for (i in 0..29) {
            var dateWithAccount = DateWithAccount(
                datelist[i], 0.0, 0.0
            )
            dateList2.add(dateWithAccount)
        }
        for (i in 0..29) {
            Log.v("日期", dateList2[i].date.toString())
            Log.v("收入", dateList2[i].income.toString())
            Log.v("支出", dateList2[i].cost.toString())
        }

        //开关控制曲线图
        var switchA: Switch = findViewById(R.id.switch1)
        var switchB: Switch = findViewById(R.id.switch2)
        var switchC: Switch = findViewById(R.id.switch3)
        var switchD: Switch = findViewById(R.id.switch4)
        var graph: GraphView = findViewById(R.id.graph)


        graph.removeAllSeries()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getUserWithAccountListByUserId(bundle?.get("userId") as Long)
            ?.observe(this, { userList: UserWithAccountList ->
                for (i in 0..29) {
                    dateList2[i].cost = 0.0
                    dateList2[i].income = 0.0
                }
                for (j in 0..userList.accountList!!.size - 1) {
                    var temp =
                        SimpleDateFormat("yy-MM-dd").format(userList.accountList!![j].createTime)
                    for (k in 0..dateList2.size - 1) {
                        if (temp == dateList2[k].date) {
                            if (userList.accountList!![j].price!!.signum() == 1) {
                                dateList2[k].cost =
                                    dateList2[k].cost?.plus(userList.accountList!![j].price!!.toDouble())
                            } else {
                                dateList2[k].income = dateList2[k].income?.plus(
                                    userList.accountList!![j].price!!.abs().toDouble()
                                )
                            }
                            break
                        }
                    }
                }
                var seriesA: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
                for (i in 0..6) {
                    seriesA.appendData(
                        DataPoint((i + 1).toDouble(), dateList2[i].income!!),
                        false,
                        7
                    )
                }
                graph.addSeries(seriesA)
                graph.viewport.isScalable = true

                graph.title = "单位:元"
                graph.gridLabelRenderer.horizontalAxisTitle = "最近第几天"

                graph.viewport.setMinX(1.0)
                graph.viewport.setMaxX(7.0)
                graph.gridLabelRenderer.numHorizontalLabels = 7
                graph.viewport.isXAxisBoundsManual = false
            })
        switchA.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchB.isChecked = false
                switchC.isChecked = false
                switchD.isChecked = false
                graph.removeAllSeries()
                var seriesA: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
                for (i in 0..6) {
                    seriesA.appendData(
                        DataPoint((i + 1).toDouble(), dateList2[i].income!!),
                        false,
                        7
                    )
                }
                graph.addSeries(seriesA)
                graph.viewport.isScalable = true

                graph.title = "单位:元"
                graph.gridLabelRenderer.horizontalAxisTitle = "最近第几天"

                graph.viewport.setMinX(1.0)
                graph.viewport.setMaxX(7.0)
                graph.gridLabelRenderer.numHorizontalLabels = 7
                graph.viewport.isXAxisBoundsManual = false
            } else {
                graph.removeAllSeries()

            }
        }
        switchC.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchA.isChecked = false
                switchB.isChecked = false
                switchD.isChecked = false
                graph.removeAllSeries()
                var seriesC: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
                for (i in 0..6) {
                    seriesC.appendData(DataPoint((i + 1).toDouble(), dateList2[i].cost!!), false, 7)
                }
                graph.addSeries(seriesC)
                graph.viewport.isScalable = true

                graph.title = "单位:元"
                graph.gridLabelRenderer.horizontalAxisTitle = "最近第几天"

                graph.viewport.setMinX(1.0)
                graph.viewport.setMaxX(7.0)
                graph.gridLabelRenderer.numHorizontalLabels = 7
                graph.viewport.isXAxisBoundsManual = false
            } else {
                graph.removeAllSeries()

            }
        }

        switchB.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchA.isChecked = false
                switchC.isChecked = false
                switchD.isChecked = false
                graph.removeAllSeries()
                var seriesB: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
                for (i in 0..29) {
                    seriesB.appendData(
                        DataPoint((i + 1).toDouble(), dateList2[i].income!!),
                        false,
                        30
                    )
                }
                graph.addSeries(seriesB)
                graph.viewport.isScalable = true

                graph.title = "单位:元"
                graph.gridLabelRenderer.horizontalAxisTitle = "最近第几天"

                graph.viewport.setMinX(1.0)
                graph.viewport.setMaxX(30.0)
                graph.gridLabelRenderer.numHorizontalLabels = 30
                graph.viewport.isXAxisBoundsManual = false
            } else {
                graph.removeAllSeries()

            }
        }

        switchD.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchA.isChecked = false
                switchB.isChecked = false
                switchC.isChecked = false
                graph.removeAllSeries()
                var seriesD: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
                for (i in 0..29) {
                    seriesD.appendData(
                        DataPoint((i + 1).toDouble(), dateList2[i].cost!!),
                        false,
                        30
                    )
                }
                graph.addSeries(seriesD)
                graph.viewport.isScalable = true

                graph.title = "单位:元"
                graph.gridLabelRenderer.horizontalAxisTitle = "最近第几天"

                graph.viewport.setMinX(1.0)
                graph.viewport.setMaxX(30.0)
                graph.gridLabelRenderer.numHorizontalLabels = 30
                graph.viewport.isXAxisBoundsManual = false
            } else {
                graph.removeAllSeries()
            }
        }
        recyclerView = findViewById(R.id.recyclerView)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//        walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        val temp =
            userViewModel.getUserWithAccountListByUserId(bundle?.get("userId") as Long)
                ?.observe(this, { userList: UserWithAccountList ->
                    if (userList.accountList!!.isEmpty()) {
                        Log.v("测试", "此用户无账单")
                        textView13.text = "此用户当前无任何账单"
                        recyclerView.adapter = EmptyAdapter(userList, userViewModel)
                    } else {
                        var cost = 0.0
                        var income = 0.0
                        for (i in 0..userList.accountList!!.size - 1) {
                            if (userList.accountList!![i].price!!.signum() == 1) {
                                cost += userList.accountList!![i].price!!.toDouble()
                            } else {
                                income += userList.accountList!![i].price!!.abs().toDouble()
                            }
                        }
                        textView13.text = "支出:" + cost.toString()
                        textView14.text = "收入:" + income.toString()
                        recyclerView.adapter =
                            DetailAdapter(this, userList, userViewModel, accountViewModel)
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
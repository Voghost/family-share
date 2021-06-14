package edu.dgut.network_engine

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.database.adapter.DetailAdapter
import edu.dgut.network_engine.database.adapter.EmptyAdapter
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_detail_member.*



class memberDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_member)
        title = "个人账单详情"

        var bundle = this.intent.extras
        textView13.text = "你已成功跳转到账单详情,当前界面用户Id:" + bundle?.get("userId").toString()
        textView14.text = ""


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
        recyclerView = findViewById(R.id.recyclerView)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
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
                        recyclerView.adapter = DetailAdapter(userList, userViewModel)
                    }
                    recyclerView.layoutManager = LinearLayoutManager(this)
                })

        button4.setOnClickListener {
            var intent = Intent("android.intent.action.AddAccountActivity")
            startActivity(intent)
        }

            }
        }
    }
}
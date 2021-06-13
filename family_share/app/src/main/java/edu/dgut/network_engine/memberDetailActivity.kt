package edu.dgut.network_engine

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_detail_member.*



class memberDetailActivity : AppCompatActivity() {


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_member)
        title = "这是一个家庭成员个人账单详情"

        var bundle = this.intent.extras
        textView2.text = "你已成功跳转到账单详情,当前界面用户Id:" + bundle?.get("userId").toString()

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
    }

}
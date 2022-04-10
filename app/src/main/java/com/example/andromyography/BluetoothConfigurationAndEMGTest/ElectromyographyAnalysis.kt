package com.example.andromyography.BluetoothConfigurationAndEMGTest

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.graphics.Color
import android.nfc.Tag
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.andromyography.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.electromyography_analysis_layout.*
import java.io.IOException
import java.util.*

class ElectromyographyAnalysis : AppCompatActivity(), OnChartValueSelectedListener {

    companion object{
        val TAG = "EMGSensor"
        val APP_NAME = "EMGSensor"

        var m_myUUID : UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        var m_bluetoothSocket : BluetoothSocket? = null
        lateinit var m_progress : ProgressDialog
        lateinit var m_bluetoothAdapter : BluetoothAdapter
        var m_isConnected : Boolean = false
        lateinit var m_address : String

        var xVal : Int = 0
        var yVal : Int = 0
    }
    lateinit var emgChart :LineChart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.electromyography_analysis_layout)

        title = "Electromyography Analysis"

        m_address = intent.getStringExtra(SelectDeviceActivity.EXTRA_ADDRESS).toString()

        ConnectToDevice(this).execute()

        //add this new
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)


        emgChart = findViewById(R.id.emg_lineChart)
        emgChart.setOnChartValueSelectedListener(this)

        // enable description text
        emgChart.description.isEnabled = true

        // enable touch gestures
        emgChart.setTouchEnabled(true)

        // enable scaling and dragging

        // enable scaling and dragging
        emgChart.isDragEnabled = true
        emgChart.setScaleEnabled(true)
        emgChart.setDrawGridBackground(false)

        // if disabled, scaling can be done on x- and y-axis separately
        emgChart.setPinchZoom(true)

        // set an alternative background color
        emgChart.setBackgroundColor(Color.LTGRAY)

        val data = LineData()
        data.setValueTextColor(Color.WHITE)

        //add empty data

        // add empty data
        emgChart.data = data

        // get the legend (only possible after setting data)

        // get the legend (only possible after setting data)
        val l: Legend = emgChart.legend

        // modify the legend ...

        // modify the legend ...
        l.form = LegendForm.LINE
        //l.typeface =
        l.textColor = Color.WHITE

        val xl: XAxis = emgChart.xAxis
        //xl.typeface = tfLight
        xl.textColor = Color.WHITE
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true

        val leftAxis: YAxis = emgChart.getAxisLeft()
        //leftAxis.typeface = tfLight
        leftAxis.textColor = Color.WHITE
        leftAxis.axisMaximum = 100f
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)

        val rightAxis: YAxis = emgChart.getAxisRight()
        rightAxis.isEnabled = false

        btn_startTest.setOnClickListener {
            //sendCommand("1")
            feedMultiple()
        }
        btn_stopTest.setOnClickListener { sendCommand("0") }
        btn_stop_save_test.setOnClickListener { disconnect() }
    }
    private fun addEntry() {
        val data: LineData = emgChart.data
        if (data != null) {
            var set = data.getDataSetByIndex(0)
            // set.addEntry(...); // can be called as well
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(Entry(set.entryCount.toFloat(), (Math.random() * 40).toFloat() + 30f), 0)
            data.notifyDataChanged()

            // let the chart know it's data has changed
            emgChart.notifyDataSetChanged()

            // limit the number of visible entries
            emgChart.setVisibleXRangeMaximum(120f)
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            emgChart.moveViewToX(data.entryCount.toFloat())

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "Dynamic Data")
        set.axisDependency = AxisDependency.LEFT
        set.color = ColorTemplate.getHoloBlue()
        set.setCircleColor(Color.WHITE)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = ColorTemplate.getHoloBlue()
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }

    private var thread: Thread? = null

    private fun feedMultiple() {
        if (thread != null)
            thread!!.interrupt()

        val runnable = Runnable { addEntry() }
        thread = Thread {
            for (i in 0..999) {

                // Don't generate garbage runnable inside the loop.
                runOnUiThread(runnable)
                try {
                    Thread.sleep(25)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread!!.start()
    }

    /*private fun createDataSet() : LineDataSet {

        var mDataSet = LineDataSet(null , "Data vals")

        mDataSet.cubicIntensity = 0.2f
        mDataSet.axisDependency = YAxis.AxisDependency.LEFT
        mDataSet.color = ColorTemplate.getHoloBlue()
        mDataSet.setCircleColors(ColorTemplate.getHoloBlue())
        mDataSet.lineWidth = 2f
        mDataSet.circleSize = 4f
        mDataSet.fillAlpha = 65
        mDataSet.fillColor = ColorTemplate.getHoloBlue()
        mDataSet.highLightColor = Color.rgb(244, 117, 177)
        mDataSet.valueTextColor = Color.WHITE
        mDataSet.valueTextSize = 10f

        return mDataSet
    }*/

    /*private fun mAddEntry(){
        Log.d(TAG , "yVal : $yVal")
        var mData = mChart.data

        if (mData !=null){
            var mDataSet = mData.getDataSetByIndex(0)

            if (mDataSet == null){
                mDataSet = createDataSet()
                mData.addDataSet(mDataSet)
            }

            var mEntry : Entry = Entry(xVal.toFloat() , yVal.toFloat())
            xVal++

            mData.addEntry(mEntry , 0)
            mChart.notifyDataSetChanged()
            mChart.setVisibleXRangeMaximum(6f)
            mChart.moveViewToX(xVal.toFloat())
        }
    }*/

    /*private fun receiveData(){

        val buffer = ByteArray(1024)
        var bytes : Int
        Log.d(TAG , "Inside ReceiveData")

        while (true){
            if (m_bluetoothSocket != null){
                try {
                    bytes = m_bluetoothSocket!!.inputStream.read(buffer)
                    val incomingMessage = String(buffer , 0 , bytes)
                    Log.d(TAG , "InputStream : $incomingMessage")
                    yVal = incomingMessage.toInt()
                    Handler(Looper.getMainLooper()).post {
                        mAddEntry()
                    }

                }catch (ex : IOException){
                    Log.d(TAG , "Write : Error reading InputStream." + ex.message)
                    break

                }
            }
        }
    }*/

    private fun sendCommand(input : String){
            if (m_bluetoothSocket != null) {
                try {
                    m_bluetoothSocket!!.outputStream.write(input.toByteArray())
                }catch (e : IOException){
                    e.printStackTrace()
                }
            }
    }

    private fun disconnect(){
        if (m_bluetoothSocket != null){
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
        finish()
    }

    private class ConnectToDevice(c : Context) : AsyncTask<Void, Void, String>(){

        private var connectSuccess : Boolean = true
        private val context : Context

        init {
            this.context = c

        }


        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context , "Connecting..." ,"Please wait" )
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected){
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device : BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            }catch (e : IOException){
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess){
                Log.i("data" , "Couldn't Connect")
            }else{
                m_isConnected = true
            }
            m_progress.dismiss()

        }

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("Entry selected", e.toString())
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }

    override fun onPause() {
        super.onPause()
        if (thread != null) {
            thread!!.interrupt()
        }
    }
}
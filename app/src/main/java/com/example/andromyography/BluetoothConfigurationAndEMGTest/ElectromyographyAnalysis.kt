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
import android.util.Log
import com.example.andromyography.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.electromyography_analysis_layout.*
import java.io.IOException
import java.util.*

class ElectromyographyAnalysis : AppCompatActivity() {

    companion object{
        val TAG = "EMGSensor"
        val APP_NAME = "EMGSensor"

        var m_myUUID : UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        var m_bluetoothSocket : BluetoothSocket? = null
        lateinit var m_progress : ProgressDialog
        lateinit var m_bluetoothAdapter : BluetoothAdapter
        var m_isConnected : Boolean = false
        lateinit var m_address : String

        lateinit var mChart :LineChart
        var xVal : Int = 0
        var yVal : Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.electromyography_analysis_layout)

        title = "Electromyography Analysis"
        
        m_address = intent.getStringExtra(SelectDeviceActivity.EXTRA_ADDRESS).toString()

        ConnectToDevice(this).execute()

        mChart = findViewById<LineChart>(R.id.emg_lineChart)

        btn_startTest.setOnClickListener {
            sendCommand("1")
            receiveData()
        }
        btn_stopTest.setOnClickListener { sendCommand("0") }
        btn_stop_save_test.setOnClickListener { disconnect() }
    }

    private fun createDataSet() : LineDataSet {

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
    }

    private fun mAddEntry(){
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
    }

    private fun receiveData(){

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
                    mAddEntry()

                }catch (ex : IOException){
                    Log.d(TAG , "Write : Error reading InputStream." + ex.message)
                    break

                }
            }
        }
    }

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
}
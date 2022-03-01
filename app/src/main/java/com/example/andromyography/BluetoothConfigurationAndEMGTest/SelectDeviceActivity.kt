package com.example.andromyography.BluetoothConfigurationAndEMGTest

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.andromyography.R
import kotlinx.android.synthetic.main.select_device_activity_layout.*

class SelectDeviceActivity : AppCompatActivity() {

    //Bluetooth Adapter
    private var m_bluetoothAdapter : BluetoothAdapter? = null
    private lateinit var m_pairedDevices : Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    companion object{
        val EXTRA_ADDRESS : String = "Device_address"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_device_activity_layout)

        title = "Choose Bluetooth Device"

        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        //label of bluetooth changed when bluetooth is on or off
        if(m_bluetoothAdapter!!.isEnabled){
            bluetooth_label.setImageResource(R.drawable.bluetooth_on)
        }else{
            bluetooth_label.setImageResource(R.drawable.bluetooth_off)
        }

        if(m_bluetoothAdapter == null){
            Toast.makeText(this , "This Device doesn't Support Bluetooth" , Toast.LENGTH_SHORT).show()
            return
        }

        btn_refresh_bluetooth.setOnClickListener { pairedDeviceList() }

        btn_turn_on_bluetooth.setOnClickListener {
            if (m_bluetoothAdapter!!.isEnabled){
                Toast.makeText(this , "Bluetooth is Already on" , Toast.LENGTH_SHORT).show()

            }else{
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBluetoothIntent , REQUEST_ENABLE_BLUETOOTH)
            }
        }

        btn_turn_off_bluetooth.setOnClickListener {
            if (!m_bluetoothAdapter!!.isEnabled){
                Toast.makeText(this , "Bluetooth is Already off" , Toast.LENGTH_SHORT).show()

            }else{
                m_bluetoothAdapter!!.disable()
                bluetooth_label.setImageResource(R.drawable.bluetooth_off)
                Toast.makeText(this , "Bluetooth Turned off" , Toast.LENGTH_SHORT).show()

            }
        }


    }

    private fun pairedDeviceList(){
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if (!m_pairedDevices.isEmpty()){
            for (device:BluetoothDevice in m_pairedDevices){
                list.add(device)
                Log.i("device" , ""+device)
            }

        } else {
            Toast.makeText(this , "No Paired Devices Found" , Toast.LENGTH_SHORT).show()
        }

        val adapter = ArrayAdapter(this , android.R.layout.simple_list_item_1 , list)
        select_device_list.adapter = adapter
        select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device : BluetoothDevice = list[position]
            val address : String = device.address

            val intent = Intent(this , ElectromyographyAnalysis::class.java)
            intent.putExtra(EXTRA_ADDRESS , address)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH){
            if (resultCode == Activity.RESULT_OK){
                if (m_bluetoothAdapter!!.isEnabled){
                    bluetooth_label.setImageResource(R.drawable.bluetooth_on)
                    Toast.makeText(this , "Bluetooth has been Enabled" , Toast.LENGTH_SHORT).show()
                } else {
                    //user denied to turn on bluetooth from confirmation dialog
                    bluetooth_label.setImageResource(R.drawable.bluetooth_off)
                    Toast.makeText(this , "Bluetooth has been Disabled" , Toast.LENGTH_SHORT).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this , "Bluetooth Enabling has been Canceled" , Toast.LENGTH_SHORT).show()

            }
        }
    }
}




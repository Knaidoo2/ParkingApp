package com.example.finalyearproject_parking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var street: EditText
    private lateinit var city: EditText
    private lateinit var postcode: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button

    private lateinit var sqLite: SQLite
    private lateinit var recyclerView: RecyclerView
    private var adapter: ParkingAdapter? = null
    private var std: ParkingModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLite = SQLite(this)

        btnAdd.setOnClickListener { addParking() }
        btnView.setOnClickListener { getParking() }
        adapter?.setOnClickItem { Toast.makeText(this, it.street, Toast.LENGTH_SHORT).show()

            street.setText(it.street)
            city.setText(it.city)
            postcode.setText(it.postcode)
        }
    }

    private fun getParking() {
      val stdList = sqLite.getAllParking()
      Log.e("pppp", "${stdList.size}")

        adapter?.addItems(stdList)
    }




    private fun addParking() {
        val street = street.text.toString()
        val city = city.text.toString()
        val postcode = postcode.text.toString()

        if(street.isEmpty() || city.isEmpty() || postcode.isEmpty()) {
            Toast.makeText( this,"Please enter required fields", Toast.LENGTH_SHORT).show()
        }else {
            val std = ParkingModel(street = street, city = city, postcode = postcode)
            val status = sqLite.insertParking(std)

            if (status > -1) {
                Toast.makeText(this, "Parking Added successfully...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getParking()

            }else {
                    Toast.makeText(this,"Record not saved...", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun clearEditText(){
        street.setText("")
        city.setText("")
        postcode.setText("")
        street.requestFocus()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ParkingAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        street = findViewById(R.id.street)
        city = findViewById(R.id.city)
        postcode = findViewById(R.id.postcode)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        recyclerView = findViewById(R.id.recyclerview)
    }


}
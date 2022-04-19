package com.example.finalyearproject_parking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ParkingAdapter : RecyclerView.Adapter<ParkingAdapter.ParkingViewHolder>() {
    private var stdList: ArrayList<ParkingModel> = ArrayList()
    private var onClickItem: ((ParkingModel) -> Unit?)? = null

    fun addItems(items: ArrayList<ParkingModel>) {
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (ParkingModel) -> Unit) {
        this.onClickItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ParkingViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.parking_items_std,parent,false)
    )
    override fun onBindViewHolder(holder: ParkingViewHolder, position: Int) {
       val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
       return stdList.size
    }


    class ParkingViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var street = view.findViewById<TextView>(R.id.tvStreet)
        private var city = view.findViewById<TextView>(R.id.tvCity)
        private var postcode = view.findViewById<TextView>(R.id.tvPostcode)
        private var btnRead = view.findViewById<Button>(R.id.btnRead)

        fun bindView(std:ParkingModel){
            id.text = std.id.toString()
            street.text = std.street
            city.text = std.city
            postcode.text = std.postcode


        }
    }
}
package com.example.travels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travels.models.Travels
import com.squareup.picasso.Picasso


class TravelAdapter(var travelList: ArrayList<Travels>, val context: Context) : RecyclerView.Adapter<TravelAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.travel_list_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(travelList[position], context)
    }

    override fun getItemCount(): Int {
        return travelList.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(b: Travels, context: Context){
            val url = "http://192.168.0.10:8080/img/travels-"
            val txt_brand: TextView = itemView.findViewById(R.id.textViewBrand)
            val txt_model: TextView = itemView.findViewById(R.id.textViewModel)
            val img: ImageView = itemView.findViewById(R.id.imageViewBicycle)

            txt_brand.text = b.name
            txt_model.text = b.descripcion

            val imageUrl = url + b.id.toString() + ".jpg"
            Picasso.with(context).load(imageUrl).into(img);

            itemView.setOnClickListener {
                val intent = Intent(context, TravelDetailActivity::class.java)
                intent.putExtra("travelId", b.id)
                intent.putExtra("state", "Showing")
                Log.v("hola caracola antes", b.id.toString())
                context.startActivity(intent)
            }
        }
    }
}
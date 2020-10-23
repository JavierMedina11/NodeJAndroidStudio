package com.example.travels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travels.models.Travels
import com.example.travels.service.TravelServiceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TravelListActivity : AppCompatActivity() {
    private lateinit var travels: ArrayList<Travels>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TravelAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_list)

        travels = ArrayList<Travels>()

        viewManager = LinearLayoutManager(this)

        viewAdapter = TravelAdapter(travels, this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBicycles)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager

        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter

        getAllTravels()

        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            val intent = Intent(this, TravelDetailActivity::class.java)
            intent.putExtra("state", "Adding")
            startActivity(intent)
        }
    }

    private fun getAllTravels() {

        val travelServiceImpl = TravelServiceImpl()
        travelServiceImpl.getAll(this) { response ->
            run {
                if (response != null) {
                    viewAdapter.travelList = response
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }

}


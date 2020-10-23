package com.example.travels.service

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.travels.models.Travels
import org.json.JSONObject

class TravelServiceImpl : ITravelService {
    override fun getAll(context: Context, completionHandler: (response: ArrayList<Travels>?) -> Unit) {
        val path = TravelSingleton.getInstance(context).baseUrl + "/api/travels"
        val arrayRequest = JsonArrayRequest(Request.Method.GET, path, null,
                { response ->
                    var travels: ArrayList<Travels> = ArrayList()
                    for (i in 0 until response.length()) {
                        val travel = response.getJSONObject(i)
                        val id = travel.getInt("id")
                        val model = travel.getString("name")
                        val brand = travel.getString("descripcion")
                        travels.add(Travels(id, brand, model))
                    }
                    completionHandler(travels)
                },
                { error ->
                    completionHandler(ArrayList<Travels>())
                })
        TravelSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getById(context: Context, travelId: Int, completionHandler: (response: Travels?) -> Unit) {
        val path = TravelSingleton.getInstance(context).baseUrl + "/api/travels/" + travelId
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
                { response ->
                    if(response == null) completionHandler(null)

                    val id = response.getInt("id")
                    val name = response.getString("name")
                    val descripcion = response.getString("descripcion")

                    val travel = Travels(id,name,descripcion)
                    completionHandler(travel)
                },
                { error ->
                    completionHandler(null)
                })
        TravelSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun deleteById(context: Context, travelId: Int, completionHandler: () -> Unit) {
        val path = TravelSingleton.getInstance(context).baseUrl + "/api/travels/" + travelId
        val objectRequest = JsonObjectRequest(Request.Method.DELETE, path, null,
                { response ->
                    Log.v("Hola caracola", "se borró")
                    completionHandler()
                },
                { error ->
                    Log.v("Hola caracola", "dió error")
                    completionHandler()
                })
        TravelSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun updateTravel(context: Context, travel: Travels, completionHandler: () -> Unit) {
        val path = TravelSingleton.getInstance(context).baseUrl + "/api/travels/" + travel.id
        val travelJson: JSONObject = JSONObject()
        travelJson.put("id", travel.id.toString())
        travelJson.put("name", travel.name)
        travelJson.put("descripcion", travel.descripcion)

        val objectRequest = JsonObjectRequest(Request.Method.PUT, path, travelJson,
                { response ->
                    completionHandler()
                },
                { error ->
                    completionHandler()
                })
        TravelSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun createTravel(context: Context, travel: Travels, completionHandler: () -> Unit) {
        val path = TravelSingleton.getInstance(context).baseUrl + "/api/travels"
        val travelJson: JSONObject = JSONObject()
        travelJson.put("id", travel.id.toString())
        travelJson.put("name", travel.name)
        travelJson.put("descripcion", travel.descripcion)

        val objectRequest = JsonObjectRequest(Request.Method.POST, path, travelJson,
                { response -> completionHandler() },
                { error -> completionHandler() })
        TravelSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }
}
package com.example.travels.service

import android.content.Context
import com.example.travels.models.Travels

interface ITravelService {
    fun getAll(context: Context, completionHandler: (response: ArrayList<Travels>?) -> Unit)

    fun getById(context: Context, travelId: Int, completionHandler: (response: Travels?) -> Unit)

    fun deleteById(context: Context, travelId: Int, completionHandler: () -> Unit)

    fun updateTravel(context: Context, travel: Travels, completionHandler: () -> Unit)

    fun createTravel(context: Context, travel: Travels, completionHandler: () -> Unit)
}
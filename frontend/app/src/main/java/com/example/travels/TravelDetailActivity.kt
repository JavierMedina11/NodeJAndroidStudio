package com.example.travels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.travels.models.Travels
import com.example.travels.service.TravelServiceImpl
import com.example.travels.service.TravelSingleton
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class TravelDetailActivity : AppCompatActivity() {
    private lateinit var state: String
    private lateinit var textInputEditTextBrand: EditText
    private lateinit var textInputEditTextModel: EditText
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        state = this.intent.getStringExtra("state").toString()

        val travelId = this.intent.getIntExtra("travelId", 1)

        textInputEditTextBrand = findViewById(R.id.TextInputEditTextBrand)
        textInputEditTextModel = findViewById(R.id.TextInputEditTextModel)

        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            deleteTravel(travelId)
        }

        if(state == "Showing") getTravel(travelId)

        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            when(state){
                "Showing" -> {
                    changeButtonsToEditing()
                }
                "Editing" -> {
                    val travel = Travels(travelId, textInputEditTextBrand.text.toString(), textInputEditTextModel.text.toString())
                    updateTavel(travel)
                }
                "Adding" -> {
                    val travel = Travels(travelId, textInputEditTextBrand.text.toString(), textInputEditTextModel.text.toString())
                    createTravel(travel)
                }
            }
        }

        if(state == "Adding") changeButtonsToAdding()
    }

    private fun updateTavel(travel: Travels) {
        val travelServiceImpl = TravelServiceImpl()
        travelServiceImpl.updateTravel(this, travel) { ->
            run {
                changeButtonsToShowing(travel.id)
                val intent = Intent(this, TravelListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createTravel(travel: Travels) {
        val travelServiceImpl = TravelServiceImpl()
        travelServiceImpl.createTravel(this, travel) { ->
            run {
                changeButtonsToShowing(travel.id)
                val intent = Intent(this, TravelListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeButtonsToAdding() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Add Travel")
        textInputEditTextBrand.isEnabled = true
        textInputEditTextModel.isEnabled = true
        state = "Adding"
    }

    private fun changeButtonsToShowing(travelId: Int){
        buttonDelete.visibility = View.VISIBLE
        buttonDelete.isEnabled = true
        buttonEdit.setText("Edit Travel")
        textInputEditTextBrand.isEnabled = false
        textInputEditTextModel.isEnabled = false
        state = "Showing"
    }

    private fun changeButtonsToEditing() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Apply changes")
        textInputEditTextBrand.isEnabled = true
        textInputEditTextModel.isEnabled = true
        state = "Editing"
    }

    private fun getTravel(travelId: Int) {
        val travelServiceImpl = TravelServiceImpl()
        travelServiceImpl.getById(this, travelId) { response ->
            run {

                val txt_brand: TextInputEditText = findViewById(R.id.TextInputEditTextBrand)
                val txt_model: TextInputEditText = findViewById(R.id.TextInputEditTextModel)
                val img: ImageView = findViewById(R.id.imageViewBicycleDetail)

                txt_brand.setText(response?.name ?: "")
                txt_model.setText(response?.descripcion ?: "")

                val url = TravelSingleton.getInstance(this).baseUrl + "/img/travels-"
                val imageUrl = url + (response?.id.toString() ?: "0" ) + ".jpg"
                Picasso.with(this).load(imageUrl).into(img);
            }
        }
    }

    private fun deleteTravel(travelId: Int) {
        val travelServiceImpl = TravelServiceImpl()
        travelServiceImpl.deleteById(this, travelId) { ->
            run {
                val intent = Intent(this, TravelListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
package com.example.mtgdeckbuilder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val cardRepository = CardRepository()
    val cards = MutableLiveData<CardsResult>()
    val deck = MutableLiveData<Deck>()
    val error = MutableLiveData<String>()

    fun getCards(name: String?,color: String?, type: String?) {
        cardRepository.getCards(name, color, type).enqueue(object : Callback<CardsResult> {
            override fun onResponse(call: Call<CardsResult>, response: Response<CardsResult>) {
                if (response.isSuccessful) {
                    cards.value = response.body()
                } else error.value = "An error occurred: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<CardsResult>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}
package com.example.mtgdeckbuilder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CardActivityViewModel(application: Application): AndroidViewModel(application) {
    val card = MutableLiveData<Card>()
}
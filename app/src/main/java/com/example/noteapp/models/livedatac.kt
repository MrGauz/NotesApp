package com.example.noteapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class livedatac() : MutableLiveData<MutableList<String>>(){

     fun setValue(value: String) {
        var list = getValue()
        list?.add(value)
        super.setValue(list)
    }
}

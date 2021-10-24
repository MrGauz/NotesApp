package com.example.noteapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NameViewModel : ViewModel() {

    // Create a LiveData with a String
    /*val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }*/
    var currentName1: livedatac = livedatac()

    // Rest of the ViewModel...

    public fun getUsers():MutableLiveData<MutableList<String>> {
        return currentName1
    }

    public fun loadUsers(string: String) {
        currentName1.setValue(string)
    }
}

package com.example.noteapp.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.NotesLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotesViewModel : ViewModel() {
    private val notes = NotesLiveData()
    private var reference: DatabaseReference

    init {
        // Initialize LiveData with empty list
        notes.value = mutableListOf()
        // Connect to database and create a reference to "notes" node
        reference = Firebase
            .database("https://kotlin-praktikum-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("notes")
    }

    fun addNote(note: Note) {
        notes.addNote(note)
    }

    fun updateNote(note: Note) {
        notes.updateNote(note)
    }

    fun deleteNote(note: Note) {
        notes.deleteNote(note)
    }

    fun getNotes(): NotesLiveData {
        return notes.getNotes()

    }
}
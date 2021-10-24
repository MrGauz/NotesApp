package com.example.noteapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.models.Note

class NotesViewModel : ViewModel() {
    private val notesList = mutableListOf<Note>()
    private val notes = MutableLiveData<List<Note>>()

    init {
        notes.value = notesList
    }

    fun addNote(note: Note) {
        notesList.add(note)
        notes.value = notesList
    }

    fun getNotes() = notes as LiveData<List<Note>>
}
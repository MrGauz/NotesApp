package com.example.noteapp.models

import androidx.lifecycle.ViewModel
import com.example.noteapp.NotesLiveData

class NotesViewModel : ViewModel() {
    private val notes = NotesLiveData()

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
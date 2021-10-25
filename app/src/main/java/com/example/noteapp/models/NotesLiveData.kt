package com.example.noteapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.models.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class NotesLiveData : MutableLiveData<MutableList<Note>>() {
    private var reference: DatabaseReference

    init {
        // Initialize LiveData with empty list
        // Connect to database and create a reference to "notes" node
        reference = Firebase
            .database("https://kotlin-praktikum-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("notes")
    }

    fun addNote(note: Note) {
        // Get a new unique key from database
        val uid: String? = reference.push().key
        if (uid != null) {
            // Write new value to database under path /notes/$uid
            reference.child(uid).setValue(note)
            // We don't need to handle data or UI changes here,
            // because once data is changes onDataChange() from getNotes() will be called
        }
    }

    fun updateNote(note: Note) {
        if (note.uid != null) {
            // Update note under path /notes/$uid
            reference.child(note.uid!!).updateChildren(note.toMap())
        }
    }

    fun deleteNote(note: Note) {
        if (note.uid != null) {
            // Delete note under path /notes/$uid
            reference.child(note.uid!!).removeValue()
        }
    }

    fun getNotes(): NotesLiveData {
        // Called once when RecyclerView is initialized, after that all changes are handled
        // automatically by ValueEventListener
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Populate a list of notes from database
                var tmpNotesList = mutableListOf<Note>()
                snapshot.children.forEach {
                    if (it != null) {
                        val note = it.getValue<Note>()!!
                            .also { note -> note.uid = it.key.toString() }
                        tmpNotesList.add(note)
                    }
                }

                // Update data in LiveData
                value = tmpNotesList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message)
            }
        })

        return this
    }

}
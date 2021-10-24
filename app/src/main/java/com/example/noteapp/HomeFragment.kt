package com.example.noteapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.noteapp.models.NameViewModel
import com.example.noteapp.models.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    //private lateinit var dbReference: DatabaseReference
    private val notes: NameViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val writeButton: Button = view.findViewById(R.id.writeButton)
        val nameObserver = Observer<List<String>> {// NewList -> Toast.makeText(context, NewList[0], Toast.LENGTH_SHORT).show()
        }
        var editText : EditText= view.findViewById<EditText>(R.id.edittext)
        notes.currentName1.observe(viewLifecycleOwner, nameObserver)
        writeButton.setOnClickListener {
            var anotherName = editText.text.toString()
            notes.loadUsers(anotherName)
            for(string in notes.getUsers().value!!){
            //val iterator =notes.getUsers().value?.iterator()
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()}
                        /*dbReference = Firebase
                .database("https://kotlin-praktikum-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference

            // Write data
            dbReference.child("notes").setValue(listOf(Note("title1", "sample text"), Note("title2", "multiline\ntext")))

            // Read data once
            dbReference.child("notes").get().addOnSuccessListener {
                Toast.makeText(context, "Got value ${it.value.toString()}", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context, "Error getting data", Toast.LENGTH_SHORT).show()
            }*/

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val notes = dataSnapshot.getValue<List<Note>>()
                    if (notes != null) {
                        for (note:Note in notes) {
                            Toast.makeText(context, "Title: ${note.title}\nText: ${note.text}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            //dbReference.child("notes").addValueEventListener(postListener)
        }
        return view
    }
}
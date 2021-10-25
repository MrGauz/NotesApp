package com.example.noteapp.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Note(var title: String? = null, var text: String? = null) {
    @Exclude
    var uid: String? = null // Unique note's key from database

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "text" to text
        )
    }
}
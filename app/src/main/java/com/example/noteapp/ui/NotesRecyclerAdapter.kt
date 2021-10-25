package com.example.noteapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.models.Note

class NotesRecyclerAdapter(private val notes: List<Note>, private val context: Context) :
// Default constructor takes a list of notes and context ^
    RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>() {

    // Defines UI element from note_preview.xml that are accessed during binding
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.note_title)
        val textTextView: TextView = view.findViewById(R.id.note_text)
        val deleteNoteImageButton: ImageButton = view.findViewById(R.id.delete_note_button)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.note_preview, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from notes list at this position and replace the
        // contents of the view with that element
        val note = notes[position]
        // Assign title
        viewHolder.titleTextView.text = note.title
        // Assign text without new lines
        viewHolder.textTextView.text = note.text?.replace("\n", " | ") ?: ""
        // Add onClick listener to delete button
        viewHolder.deleteNoteImageButton.setOnClickListener {
            if (context is NotesActivity) {
                context.onDeleteNoteClick(note)
            }
        }
        // Add onClick listener for editing note
        viewHolder.itemView.setOnClickListener {
            if (context is NotesActivity) {
                context.onEditNoteCLick(note)
            }
        }
    }

    // Return the size of notes list (invoked by the layout manager)
    override fun getItemCount() = notes.size
}
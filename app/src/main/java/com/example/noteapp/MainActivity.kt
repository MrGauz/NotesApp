package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.databinding.NoteEditDialogBinding
import com.example.noteapp.databinding.NotePreviewBinding
import com.example.noteapp.models.Note
import com.google.firebase.database.DatabaseReference
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private lateinit var dbReference: DatabaseReference
    private lateinit var binding: ActivityMainBinding
    private val notes: MutableList<Note> = mutableListOf(
        Note("title 1", "sample text"),
        Note("title 2", "multiline\ntext"),
        Note("title 3", "just a reminder that sun is gonna explode one day"),
        Note("title 4", "just a reminder that sun is gonna explode one day"),
        Note("title 5", "just a reminder that sun is gonna explode one day"),
        Note("title 6", "just a reminder that sun is gonna explode one day"),
        Note("title 7", "just a reminder that sun is gonna explode one day"),
        Note("title 8", "just a reminder that sun is gonna explode one day"),
        Note("title 9", "just a reminder that sun is gonna explode one day"),
        Note("title 10", "just a reminder that sun is gonna explode one day")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add onClick listener for creating a note
        binding.addNoteButton.setOnClickListener {
            displayEditNoteDialog()
        }

        // TODO: get data from Firebase

        // Display a list of notes
        for (note: Note in notes) {
            val notePreviewBinding: NotePreviewBinding = NotePreviewBinding.inflate(layoutInflater)
            notePreviewBinding.noteTitle.text = note.title
            notePreviewBinding.noteText.text = note.text.subSequence(0, min(150, note.text.length))
            notePreviewBinding.root.setOnClickListener {
                displayEditNoteDialog(note)
            }
            binding.notesList.addView(notePreviewBinding.root)
        }
    }

    private fun displayEditNoteDialog(note: Note? = null) {
        val dialogBinding = NoteEditDialogBinding.inflate(layoutInflater)
        if (note != null) {
            dialogBinding.dialogEditNoteTitle.setText(note.title)
            dialogBinding.dialogEditNoteText.setText(note.text)
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.edit_dialog_save) { _, _ ->
                var title = dialogBinding.dialogEditNoteTitle.text.toString()
                if (title.isEmpty()) {
                    title = resources.getString(R.string.edit_dialog_empty_title_text)
                }

                // TODO: update val in DB
                val editedNote = Note(
                    title,
                    dialogBinding.dialogEditNoteText.text.toString()
                )

                notes.add(editedNote)
                // TODO: update notes list UI
            }
            .setNegativeButton(R.string.edit_dialog_cancel, null)
            .create()

        dialog.show()
    }
}
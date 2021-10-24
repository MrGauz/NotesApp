package com.example.noteapp

import NotesRecyclerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.databinding.NoteEditDialogBinding
import com.example.noteapp.models.Note

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var viewManager = LinearLayoutManager(this)
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialise notes list
        notesRecycler = binding.notesList
        val factory = NotesViewModelFactory()
        notesViewModel = ViewModelProviders.of(this, factory).get(NotesViewModel::class.java)
        initialiseAdapter()

        // Add onClick listener for creating a note
        binding.addNoteButton.setOnClickListener {
            displayEditNoteDialog()
        }
    }

    private fun initialiseAdapter() {
        notesRecycler.layoutManager = viewManager
        notesViewModel.getNotes().observe(this, Observer { notes ->
            notesRecycler.adapter = NotesRecyclerAdapter(notes)
        })
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
                val editedNote = Note(
                    title,
                    dialogBinding.dialogEditNoteText.text.toString()
                )

                notesViewModel.addNote(editedNote)
                notesRecycler.adapter?.notifyDataSetChanged()
            }
            .setNegativeButton(R.string.edit_dialog_cancel, null)
            .create()

        dialog.show()
    }
}
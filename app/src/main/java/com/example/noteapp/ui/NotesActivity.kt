package com.example.noteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.models.NotesViewModel
import com.example.noteapp.models.NotesViewModelFactory
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityNotesBinding
import com.example.noteapp.databinding.NoteEditDialogBinding
import com.example.noteapp.models.Note

class NotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesBinding
    private var viewManager = LinearLayoutManager(this)
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        // Disable dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Initialize layout binding
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel with notes list
        val factory = NotesViewModelFactory()
        notesViewModel = ViewModelProviders.of(this, factory).get(NotesViewModel::class.java)

        // Initialize binding of data changes to the UI list of notes
        notesRecycler = binding.notesList
        notesRecycler.layoutManager = viewManager
        // Loads notes from database with getNotes()
        // At this point there is an onChange listener added to watch for data changes
        // and update the UI when they happen
        notesViewModel.getNotes().observe(this, { notes ->
            notesRecycler.adapter = NotesRecyclerAdapter(notes, this)
        })

        // Add onClick listener for creating a note
        binding.addNoteButton.setOnClickListener {
            onEditNoteCLick()
        }
    }

    fun onEditNoteCLick(note: Note? = null) {
        val dialogBinding = NoteEditDialogBinding.inflate(layoutInflater)
        val isNew = note == null

        // Set text in dialog if editing note
        if (!isNew) {
            dialogBinding.dialogEditNoteTitle.setText(note?.title)
            dialogBinding.dialogEditNoteText.setText(note?.text)
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.edit_dialog_save) { _, _ ->
                // Compose note title
                var title = dialogBinding.dialogEditNoteTitle.text.toString().trim()
                if (title.isEmpty()) {
                    title = resources.getString(R.string.edit_dialog_empty_title_text)
                }

                // Compose new note object
                val editedNote = Note(
                    title,
                    dialogBinding.dialogEditNoteText.text.toString().trim()
                ).also { editedNote -> editedNote.uid = note?.uid }

                // Save changes
                if (isNew) {
                    notesViewModel.addNote(editedNote)
                } else {
                    notesViewModel.updateNote(editedNote)
                }
            }
            .setNegativeButton(R.string.edit_dialog_cancel, null)
            .create()

        dialog.show()
    }

    fun onDeleteNoteClick(note: Note) {
        notesViewModel.deleteNote(note)
    }
}
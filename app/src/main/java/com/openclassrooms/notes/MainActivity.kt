package com.openclassrooms.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.openclassrooms.notes.databinding.ActivityMainBinding
import com.openclassrooms.notes.widget.NoteItemDecoration
import com.openclassrooms.notes.widget.viewmodel.NoteViewModel
import com.openclassrooms.notes.widget.NotesAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.openclassrooms.notes.model.data.Note

/**
 * The main activity for the app.
 */
class MainActivity : AppCompatActivity() {

    /**
     * The binding for the main layout.
     */
    private lateinit var binding: ActivityMainBinding
    private val notesAdapter = NotesAdapter(emptyList())
    private val viewModel: NoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initFABButton()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateNotes(it.listNotes)
                }
            }
        }
    }

    /**
     * Updates the notes from the Adapter.
     */
    private fun updateNotes(noteList: List<Note>) {
        notesAdapter.updateNotes(noteList)
    }

    /**
     * Initializes the FAB button.
     */
    private fun initFABButton() {
        binding.btnAdd.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            val editTitle = dialogView.findViewById<EditText>(R.id.title)
            val editBody = dialogView.findViewById<EditText>(R.id.message)

            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create()
            dialog.show()

            val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            okButton.isEnabled = false //

            fun updateButtonState() {
                okButton.isEnabled = editTitle.text.isNotBlank() && editBody.text.isNotBlank()
            }

            editTitle.addTextChangedListener { updateButtonState() }
            editBody.addTextChangedListener { updateButtonState() }

            okButton.setOnClickListener {
                val title = editTitle.text.toString()
                val body = editBody.text.toString()

                if (title.isBlank() || body.isBlank()) {
                    Toast.makeText(this, "The title or the message is empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val note = (Note(title, body))
                    viewModel.addNote(note)
                    Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }

        }
    }

    /**
     * Initializes the RecyclerView.
     */
    private fun initRecyclerView() {
        with(binding.recycler) {
            addItemDecoration(
                NoteItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.default_margin),
                    resources.getInteger(R.integer.span_count)
                )
            )
            adapter = notesAdapter
        }
    }
}

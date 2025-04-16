package com.openclassrooms.notes

import android.annotation.SuppressLint
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

        lifecycleScope.launch {
            initRecyclerView()
            initFABButton()
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // collects flow
                viewModel.uiState.collect { notesList ->
                    notesAdapter.updateNotes(notesList)
                    }
            }
        }
    }

    /**
     * Initializes the FAB button.
     */

    @SuppressLint("SuspiciousIndentation")
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

            dialog.show()

            val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            okButton.isEnabled = false // désactivé au début

            fun updateButtonState() {
                okButton.isEnabled = editTitle.text.isNotBlank() && editBody.text.isNotBlank()
            }

            // Mise à jour du bouton dès que l’utilisateur tape quelque chose
            editTitle.addTextChangedListener { updateButtonState() }
            editBody.addTextChangedListener { updateButtonState() }

            // Action quand on clique sur OK
            okButton.setOnClickListener {
                val title = editTitle.text.toString()
                val body = editBody.text.toString()

                if (title.isBlank() || body.isBlank()) {
                    Toast.makeText(this, "The title or the message is empty", Toast.LENGTH_SHORT).show()
                } else {
                    // Création de la note et appel du ViewModel
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

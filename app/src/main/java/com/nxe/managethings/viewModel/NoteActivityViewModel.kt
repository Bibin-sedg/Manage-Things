package com.nxe.managethings.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nxe.managethings.model.Note
import com.nxe.managethings.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteActivityViewModel(private val repository: NoteRepository): ViewModel() {

    fun saveNote(newNote: Note) =viewModelScope.launch(Dispatchers.IO) {
        repository.addNote(newNote)
    }
    fun updateNote(existingNote : Note) =viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(existingNote)
    }
    fun deleteNote(existingNote: Note) =viewModelScope.launch(Dispatchers.IO) {
        repository.deletNote(existingNote)
    }

    fun searchNote(query: String): LiveData<List<Note>>
    {
        return repository.searchNote(query)
    }
    fun getAllNotes(): LiveData<List<Note>> = repository.getNote()

}
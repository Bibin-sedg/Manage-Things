package com.nxe.managethings.repository

import com.nxe.managethings.db.NoteDatabase
import com.nxe.managethings.model.Note

class NoteRepository(private val db: NoteDatabase) {

    fun getNote() = db.getNoteDao().getAllNote();

    fun searchNote(query: String) = db.getNoteDao().searchNotes(query)

    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    suspend fun deletNote(note: Note) = db.getNoteDao().deleteNote(note)



}
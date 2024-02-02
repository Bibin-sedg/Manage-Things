package com.nxe.managethings.data.repository

import com.nxe.managethings.data.db.NoteDatabase
import com.nxe.managethings.data.model.Note


class NoteRepository(private val db: NoteDatabase) {

    fun getNote() = db.getNoteDao().getAllNote();

    fun searchNote(query: String) = db.getNoteDao().searchNotes(query)

    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    suspend fun deletNote(note: Note) = db.getNoteDao().deleteNote(note)



}
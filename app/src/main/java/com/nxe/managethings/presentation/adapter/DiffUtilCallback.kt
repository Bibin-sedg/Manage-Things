package com.nxe.managethings.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nxe.managethings.data.model.Note

class DiffUtilCallback:DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id ==newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id==newItem.id
    }
}
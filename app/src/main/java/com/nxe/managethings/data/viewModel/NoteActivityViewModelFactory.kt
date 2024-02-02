package com.nxe.managethings.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nxe.managethings.data.repository.NoteRepository

class NoteActivityViewModelFactory(private val repository: NoteRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteActivityViewModel(repository) as T
    }

}
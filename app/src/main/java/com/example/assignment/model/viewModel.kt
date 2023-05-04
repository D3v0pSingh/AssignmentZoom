package com.example.assignment.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment.database.MainDb
import com.example.assignment.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class viewModel(application: Application) : AndroidViewModel(application) {

    private val repository : Repository

    val allData : LiveData<List<Repos>>

    init {
        val dao = MainDb.getdb(application).getDao()
        repository = Repository(dao)
        allData = repository.allRepos
    }

    fun delete(repos: Repos){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(repos)
        }
    }

    fun insert(repos: Repos){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(repos)
        }
    }

}
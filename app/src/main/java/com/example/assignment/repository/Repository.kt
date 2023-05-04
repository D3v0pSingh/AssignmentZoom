package com.example.assignment.repository

import androidx.lifecycle.LiveData
import com.example.assignment.database.RepoDao
import com.example.assignment.model.Repos

class Repository ( private val dao:RepoDao) {

    val allRepos: LiveData<List<Repos>> = dao.allRepos()

    suspend fun insert(repos: Repos){
        dao.insert(repos)
    }

    suspend fun delete(repos: Repos){
        dao.delete(repos)
    }

}
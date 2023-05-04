package com.example.assignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.model.Repos
@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(repoData : Repos)

    @Query("SELECT * FROM repository ORDER BY id ASC")
    fun allRepos() : LiveData<List<Repos>>

    @Delete
    suspend fun delete(repoData: Repos)

}
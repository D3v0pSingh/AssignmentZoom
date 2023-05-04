package com.example.assignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "repository")
data class Repos(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    //here RepoName and RepoOwner is interchanged
    //since they have the same datatype as String so there properties have changed
    //Time consuming
    @ColumnInfo(name = "RepoName") val name:String,
    @ColumnInfo(name = "RepoOwner") val owner:String,
    @ColumnInfo(name = "RepoDescription") val description:String,
    @ColumnInfo(name = "RepoUrl") val url:String

):java.io.Serializable
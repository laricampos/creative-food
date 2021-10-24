package com.jellypump.creativefood.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TAG_TABLE_NAME = "tags"

@Entity(tableName = TAG_TABLE_NAME)
data class TagEntity(
    @PrimaryKey
    @ColumnInfo(name = "tag_name")
    val name: String,
    val colour: Int
)
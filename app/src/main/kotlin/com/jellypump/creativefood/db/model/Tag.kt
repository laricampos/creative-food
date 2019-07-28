package com.jellypump.creativefood.db.model

import androidx.annotation.ColorRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TAG_TABLE_NAME = "tags"

@Entity(tableName = TAG_TABLE_NAME)
data class Tag(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "colour") @ColorRes val colour: Int
)
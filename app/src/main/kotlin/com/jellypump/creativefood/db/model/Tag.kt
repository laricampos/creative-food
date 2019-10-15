package com.jellypump.creativefood.db.model

import androidx.annotation.ColorRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TAG_TABLE_NAME = "tags"

@Entity(tableName = TAG_TABLE_NAME)
data class Tag(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "colour") @ColorRes val colour: Int
) {

    constructor(name: String, colour: Int) : this(0, name, colour)
}
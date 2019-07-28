package com.jellypump.creativefood.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jellypump.creativefood.db.model.TAG_TABLE_NAME
import com.jellypump.creativefood.db.model.Tag
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TagDao {

    @Insert
    fun insert(tag: Tag): Completable

    @Query("SELECT * FROM $TAG_TABLE_NAME")
    fun getAll(): Single<List<Tag>>

    @Query("SELECT * FROM $TAG_TABLE_NAME WHERE name =(:name)")
    fun getByName(name: String): Single<Tag>
}

package com.jellypump.creativefood.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jellypump.creativefood.db.entity.TAG_TABLE_NAME
import com.jellypump.creativefood.db.entity.TagEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TagDao {

    @Insert
    fun insert(tag: TagEntity): Completable

    @Query("SELECT * FROM $TAG_TABLE_NAME")
    fun getAll(): Flowable<List<TagEntity>>

    @Query("SELECT * FROM $TAG_TABLE_NAME WHERE tag_name =(:name)")
    fun getByName(name: String): Single<TagEntity>
}

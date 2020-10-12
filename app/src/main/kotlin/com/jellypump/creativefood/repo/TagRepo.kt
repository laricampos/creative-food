package com.jellypump.creativefood.repo

import com.jellypump.creativefood.db.dao.TagDao
import com.jellypump.creativefood.db.entity.TagEntity
import com.jellypump.creativefood.db.mapper.toModel
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.model.Tag
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagRepo @Inject constructor(
    private val tagDao: TagDao
) {

    val allTags: Flowable<List<Tag>>
        get() = tagDao.getAll().map { tagList ->
            tagList.map { it.toModel() }
        }

    fun add(tag: TagEntity): Completable = tagDao.insert(tag)

}
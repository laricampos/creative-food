package com.jellypump.creativefood.repo

import com.jellypump.creativefood.db.dao.TagDao
import com.jellypump.creativefood.db.model.Tag
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagRepo @Inject constructor(
    private val tagDao: TagDao
) {

    val allTags: Single<List<Tag>>
        get() = tagDao.getAll()

    fun add(tag: Tag): Completable = tagDao.insert(tag)

}
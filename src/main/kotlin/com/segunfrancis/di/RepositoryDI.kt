package com.segunfrancis.di

import com.segunfrancis.repository.EmojiPhrasesRepository
import com.segunfrancis.repository.Repository

object RepositoryDI {

    private var repository: Repository? = null

    fun provideRepository(): Repository {
        if (repository == null) {
            repository = EmojiPhrasesRepository()
        }
        return repository!!
    }
}
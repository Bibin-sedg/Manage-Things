package com.nxe.managethings.presentation.di

import com.nxe.managethings.data.db.TaskCategoryDao
import com.nxe.managethings.domain.TaskCategoryRepository
import com.nxe.managethings.data.repository.TaskCategoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskCategoryRepository(taskCategoryDao: TaskCategoryDao) : TaskCategoryRepository {
        return TaskCategoryRepositoryImpl(taskCategoryDao)
    }
}
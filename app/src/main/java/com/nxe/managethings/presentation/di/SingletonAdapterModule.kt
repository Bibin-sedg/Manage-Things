package com.nxe.managethings.presentation.di

import com.nxe.managethings.presentation.adapter.CategoryAdapter
import com.nxe.managethings.presentation.adapter.TasksAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonAdapterModule {
    @Provides
    @Singleton
    @Named("home_fragment")
    fun provideTaskAdapterToHomeFragment() = TasksAdapter()

    @Provides
    @Singleton
    @Named("completed_task_fragment")
    fun provideTaskAdapterToCompletedTasksFragment() = TasksAdapter()

    @Provides
    @Singleton
    fun provideCategoryAdapter() = CategoryAdapter()

}
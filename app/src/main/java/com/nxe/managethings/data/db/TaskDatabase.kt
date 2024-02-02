package com.nxe.managethings.data.db

import androidx.room.*
import com.nxe.managethings.data.model.CategoryInfo
import com.nxe.managethings.data.model.TaskInfo

@Database(entities = [TaskInfo::class, CategoryInfo::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskCategoryDao() : TaskCategoryDao
}
package com.nestorss.pptns.dal

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TareaEntity::class], version=2)
abstract class TareasDatabase: RoomDatabase(){
    abstract fun tareaDao(): TareasDao
}
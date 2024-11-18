package com.example.contactosjpc.dal

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactoEnt::class], version = 1)
abstract class ContactosDB: RoomDatabase() {
    abstract fun contactoDao(): ContactoDao
}
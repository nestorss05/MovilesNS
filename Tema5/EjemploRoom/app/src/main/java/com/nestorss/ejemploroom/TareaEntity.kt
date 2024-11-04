package com.nestorss.ejemploroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class TareaEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0,
    var name:String="",
    var isDone:Boolean=false
)

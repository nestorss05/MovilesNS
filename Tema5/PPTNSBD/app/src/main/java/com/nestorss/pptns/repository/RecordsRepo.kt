package com.nestorss.pptns.repository

import com.nestorss.pptns.dal.TareaEntity
import com.nestorss.pptns.dal.TareasDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecordsRepo(private val tareasDao: TareasDao) {
    val todasLasTareas: Flow<List<TareaEntity>> = flow {
        emit(tareasDao.getTodo())
    }

    suspend fun insertarTarea(tarea: TareaEntity): Long {
        return tareasDao.insertar(tarea)
    }

    suspend fun actualizarTarea(tarea: TareaEntity) {
        tareasDao.actualizar(tarea)
    }
}
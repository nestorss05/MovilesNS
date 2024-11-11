package com.nestorss.pptns.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nestorss.pptns.dal.TareaEntity
import com.nestorss.pptns.dal.TareasDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TareasVM(private val repository: TareasDao) : ViewModel() {
    private val _todasLasTareas = MutableStateFlow<List<TareaEntity>>(emptyList())
    val todasLasTareas: StateFlow<List<TareaEntity>> get() = _todasLasTareas.asStateFlow()

    init {
        todasLasTareas()
    }

    private fun todasLasTareas() {
        viewModelScope.launch {
            _todasLasTareas.value = repository.getTodo()
        }
    }

    fun borrarTodasLasTareas() {
        viewModelScope.launch {
            repository.borrarTodo()
        }
    }

}
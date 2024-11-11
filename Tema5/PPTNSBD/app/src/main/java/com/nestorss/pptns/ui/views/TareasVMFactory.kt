package com.nestorss.pptns.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nestorss.pptns.dal.TareasDao

class TareasViewModelFactory(private val tareasDao: TareasDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TareasVM::class.java)) {
            return TareasVM(tareasDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

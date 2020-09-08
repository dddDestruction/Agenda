package cl.apicolm.myapplication.ui.tareas

import android.app.Application
import androidx.lifecycle.*
import cl.apicolm.myapplication.model.AgendaRepository
import cl.apicolm.myapplication.model.entidades.TareaEntidad

class TareasViewModel(application: Application) : AndroidViewModel(application)  {

    val repository = AgendaRepository(application, viewModelScope)

    fun loadTareas(climaId:Int): LiveData<List<TareaEntidad>>{
        return repository.loadTareas(climaId)
    }
}
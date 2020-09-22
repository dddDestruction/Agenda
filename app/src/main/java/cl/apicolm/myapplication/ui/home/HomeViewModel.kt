package cl.apicolm.myapplication.ui.home

import android.app.Application
import androidx.lifecycle.*
import cl.apicolm.myapplication.model.AgendaRepository
import cl.apicolm.myapplication.model.entidades.TareaEntidad

class HomeViewModel (application: Application) : AndroidViewModel(application)  {

    val repository = AgendaRepository(application, viewModelScope)

    fun loadAllTareas(): LiveData<List<TareaEntidad>>{
        return repository.loadAllTareas()
    }

    fun actualizarTareas(lista: List<TareaEntidad>){
        repository.actualizarTareas(lista)
    }
}
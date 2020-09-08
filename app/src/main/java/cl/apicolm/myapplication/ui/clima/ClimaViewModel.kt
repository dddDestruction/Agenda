package cl.apicolm.myapplication.ui.clima

import android.app.Application
import androidx.lifecycle.*
import cl.apicolm.myapplication.model.AgendaRepository
import cl.apicolm.myapplication.model.entidades.TareaEntidad

class ClimaViewModel(application: Application) : AndroidViewModel(application)  {

    val repository = AgendaRepository(application, viewModelScope)
    val climas = repository.climas

    fun load(){
        repository.loadData()
    }
}
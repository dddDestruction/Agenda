package cl.apicolm.myapplication.ui.clima

import android.app.Activity
import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import cl.apicolm.myapplication.model.AgendaRepository
import cl.apicolm.myapplication.model.entidades.TareaEntidad

class ClimaViewModel(application: Application) : AndroidViewModel(application)  {

    val repository = AgendaRepository(application, viewModelScope)
    val climas = repository.climas

    fun localizacion(activity: Activity):MutableLiveData<Location>{
        return repository.obtenerLocalizacion(activity)
    }
    fun load(localizacion:Location){
        repository.loadData(localizacion)
    }
}
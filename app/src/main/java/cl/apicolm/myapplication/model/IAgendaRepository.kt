package cl.apicolm.myapplication.model

import android.app.Activity
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima

interface IAgendaRepository {
    fun loadData(location: Location)
    fun insetarTarea(tarea: List<TareaEntidad>)
    fun loadTareas(climaId:Int): LiveData<List<TareaEntidad>>
    fun loadAllTareas(): LiveData<List<TareaEntidad>>
    fun actualizarTareas(lista:List<TareaEntidad>)
    fun deleteTarea()
    //Métodos de localización
    fun obtenerLocalizacion(activity: Activity):MutableLiveData<Location>
}
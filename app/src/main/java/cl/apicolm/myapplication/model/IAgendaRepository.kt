package cl.apicolm.myapplication.model

import android.app.Activity
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cl.apicolm.myapplication.model.entidades.TareaEntidad

interface IAgendaRepository {
    fun loadData()
    fun insetarTarea(tarea: List<TareaEntidad>)
    fun loadTareas(climaId:Int): LiveData<List<TareaEntidad>>
    fun loadAllTareas(): LiveData<List<TareaEntidad>>
    fun actualizarTareas(lista:List<TareaEntidad>)
    fun deleteTarea()
    //Métodos de localización
    fun obtenerLocalizacion(activity: Activity):MutableLiveData<Location>
}
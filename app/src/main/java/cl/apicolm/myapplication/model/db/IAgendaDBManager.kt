package cl.apicolm.myapplication.model.db

import androidx.lifecycle.LiveData
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad

interface IAgendaDBManager {
    fun getClimas():LiveData<List<ClimaEntidad>>
    fun insertarClimas(climas:List<ClimaEntidad>)
    fun getTareas(climaId:Int):LiveData<List<TareaEntidad>>
    fun insertarTarea(tareaEntidad: TareaEntidad)
    fun deleteTarea(tareaEntidad: TareaEntidad)
}
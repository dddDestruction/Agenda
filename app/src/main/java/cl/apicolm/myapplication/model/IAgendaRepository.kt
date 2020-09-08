package cl.apicolm.myapplication.model

import androidx.lifecycle.LiveData
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima

interface IAgendaRepository {
    fun loadData()
    fun insetarTarea(tarea: TareaEntidad)
    fun loadTareas(climaId:Int): LiveData<List<TareaEntidad>>
    fun deleteTarea(tarea: TareaEntidad)
}
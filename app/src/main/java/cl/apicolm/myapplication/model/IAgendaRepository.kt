package cl.apicolm.myapplication.model

import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.Tarea

interface IAgendaRepository {
    fun loadData()
    fun insetarTarea(tarea: Tarea)
    fun loadTareas()
}
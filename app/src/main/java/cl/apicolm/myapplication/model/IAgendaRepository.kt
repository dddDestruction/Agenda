package cl.apicolm.myapplication.model

import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima

interface IAgendaRepository {
    fun loadData()
    fun insetarTarea(tarea: TareaEntidad)
    fun loadTareas(climaId:Int)
    fun deleteTarea(tarea: TareaEntidad)
    //Transforma el resultado de la Api en una lista de ClimaEntidad
    fun mapperApiClima(clima: Clima): List<ClimaEntidad>
    //Transforma de grados Kelvin a grados Celsius
    fun enCelsius(kelvin:Double):Double
}
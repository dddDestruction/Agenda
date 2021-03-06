package cl.apicolm.myapplication.model

import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima

interface IRepoUtil {
    //Transforma el resultado de la Api en una lista de ClimaEntidad
    fun mapperApiClima(clima: Clima): List<ClimaEntidad>
    //Transforma de grados Kelvin a grados Celsius
    fun enCelsius(kelvin:Double):Double
    //Retorna la diferencia entre una fecha en String y fecha actual
    fun diff(fecha:String):Long
    //Retorna 0 si la tarea expiró o el nuevo índice en caso contrario
    fun vigencia(tarea: TareaEntidad, diff:Long):Long
    //Limpia las tareas
    fun tareasCleaner(listaTareas:List<TareaEntidad>, diff:Long):MutableList<TareaEntidad>
}
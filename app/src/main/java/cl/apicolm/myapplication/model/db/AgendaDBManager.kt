package cl.apicolm.myapplication.model.db

import android.content.Context
import androidx.lifecycle.LiveData
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad

class AgendaDBManager(val context: Context):IAgendaDBManager {

    val agendaDao = AgendaDB.getDatabase(context).dao()

    override fun getClimas(): LiveData<List<ClimaEntidad>> {
        return agendaDao.getAllClimas()
    }

    override fun insertarClimas(climas: List<ClimaEntidad>) {
        agendaDao.insertClima(climas)
    }

    override fun getTareas(climaId: Int):LiveData<List<TareaEntidad>> {
        return agendaDao.getAllTareas(climaId)
    }

    override fun insertarTarea(tareaEntidad: TareaEntidad) {
        agendaDao.insertTarea(tareaEntidad)
    }

    override fun deleteTarea(tareaEntidad: TareaEntidad) {
        TODO("Not yet implemented")
    }
}
package cl.apicolm.myapplication.model.db

import android.content.Context
import androidx.lifecycle.LiveData
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AgendaDBManager(val context: Context, val scope:CoroutineScope):IAgendaDBManager {

    val agendaDao = AgendaDB.getDatabase(context).dao()

    override fun getClimas(): LiveData<List<ClimaEntidad>> {
        return agendaDao.getAllClimas()
    }

    override fun insertarClimas(climas: List<ClimaEntidad>) = scope.launch {
        agendaDao.insertClima(climas)
    }

    override fun getTareas(climaId: Int):LiveData<List<TareaEntidad>> {
        return agendaDao.getAllTareas(climaId)
    }

    override fun insertarTarea(tareaEntidad: TareaEntidad) = scope.launch  {
        agendaDao.insertTarea(tareaEntidad)
    }

    override fun deleteTarea(tareaEntidad: TareaEntidad) = scope.launch  {
        TODO("Not yet implemented")
    }
}
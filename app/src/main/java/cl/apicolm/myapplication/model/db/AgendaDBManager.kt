package cl.apicolm.myapplication.model.db

import android.content.Context
import androidx.lifecycle.LiveData
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad

class AgendaDBManager(val context: Context):IAgendaDBManager {

    val superDao = AgendaDB.getDatabase(context).dao()

    override fun getClimas(): LiveData<List<ClimaEntidad>> {
        return superDao.getAllClimas()
    }

    override fun insertarClimas(climas: List<ClimaEntidad>) {
        superDao.insertClima(climas)
    }

    override fun getTareas(climaId: Int):LiveData<List<TareaEntidad>> {
        TODO("Not yet implemented")
    }

    override fun insertarTarea(tareaEntidad: TareaEntidad) {
        TODO("Not yet implemented")
    }

    override fun deleteTarea(tareaEntidad: TareaEntidad) {
        TODO("Not yet implemented")
    }
}
package cl.apicolm.myapplication.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
//import cl.apicolm.myapplication.model.entidades.ClimaTarea
import cl.apicolm.myapplication.model.entidades.TareaEntidad

@Dao
interface AgendaDao {
    //Inserta los valores de clima
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClima(climas: List<ClimaEntidad>)

    //Obtiene todos los valores de clima desde Room DB
    @Query("SELECT * FROM climas")
    fun getAllClimas(): LiveData<List<ClimaEntidad>>

    //Obtiene todos los valores de tareas de un dia desde Room DB
    @Query("SELECT * FROM tareas WHERE climaId = :id")
    fun getTareas(id:Int): LiveData<List<TareaEntidad>>

    //Obtiene todos los valores de tareas de un dia desde Room DB
    @Query("SELECT * FROM tareas")
    fun getAllTareas(): LiveData<List<TareaEntidad>>

    //Inserta las tareas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTarea(tarea: List<TareaEntidad>)


    //Borra el contenido de la tabla tareas
    @Query("Delete FROM tareas")
    suspend fun borrarTarea()

}

package cl.apicolm.myapplication.model.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "climas")
data class ClimaEntidad(
    @PrimaryKey var id: Int,
    var temp: Double,
    var min:Double,
    var max:Double,
    var clima:String,
    var descripcionClima:String,
    var iconClima:String,
    var diaSemana:String
)

@Entity(tableName = "tareas")
data class TareaEntidad(
    var climaId:Int,
    var descripcion: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}

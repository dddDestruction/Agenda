package cl.apicolm.myapplication.model.entidades

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ClimaTarea(
    @Embedded val dia: ClimaEntidad,
    @Relation(
                          parentColumn = "id",
                          entityColumn = "climaId"
                      )
    val tareas: List<TareaEntidad>
)

@Entity
data class ClimaEntidad(
    @PrimaryKey var id: Int,
    var temp: Double,
    var min:Double,
    var max:Double,
    var clima:String,
    var descripcionClima:String,
    var iconClima:String
    //@Embedded val tarea: TareaEntidad
)

class TareaEntidad(
    var climaId:Int,
    var descripcion: String,
    var hora:String
)/* {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}
        */
package cl.apicolm.myapplication.model

import android.content.Context
import android.util.Log
import cl.apicolm.myapplication.model.api.RetrofitClient
import cl.apicolm.myapplication.model.db.AgendaDBManager
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima
import cl.apicolm.myapplication.model.sharedPreferences.SharedPrefenrecesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.truncate

class AgendaRepository( val context: Context
    //, scope: CoroutineScope
    ):IAgendaRepository {

    val agendaManager = AgendaDBManager(context
        //, scope
        )
    var climas = agendaManager.getClimas()
    //var tareas = agendaManager.tareas
    var climasApi = mutableListOf<Clima>()

    override fun loadData() {

        val retrofit = RetrofitClient.retrofitInstance()
        val call = retrofit.getClima(0.0,0.0,"hourly,minutely","b2a60a866478dbb03febc32b537c1896")
        call.enqueue(object : Callback<Clima> {
            override fun onResponse(
                call: retrofit2.Call<Clima>,
                response: Response<Clima>
            ) {
                Log.d("AAA", response.body().toString())
                if (response.body() != null){
                    agendaManager.insertarClimas(mapperApiClima(response.body()!!))
                }
                val calendar = Calendar.getInstance()
                Log.d("AAA", "fecha ${SimpleDateFormat("dd/MM/yyyy").format(calendar.time)}")
                if (diff(SharedPrefenrecesManager(context).getDate()) != 0L){
                    SharedPrefenrecesManager(context).addSharedPreferences(
                        SimpleDateFormat("dd/MM/yyyy").format(calendar.time)
                    )
                }
                Log.d("AAA", "fecha desde Shared ${SharedPrefenrecesManager(context).getDate()}")

            }

            override fun onFailure(call: Call<Clima>, t: Throwable) {
                Log.d("AAA", "Error: " + t)
            }
        })
    }

    override fun insetarTarea(tarea: TareaEntidad) {
        TODO("Not yet implemented")
    }

    override fun loadTareas(climaId:Int) {
        TODO("Not yet implemented")
    }

    override fun deleteTarea(tarea: TareaEntidad) {
        TODO("Not yet implemented")
    }

    override fun mapperApiClima(clima: Clima): List<ClimaEntidad> {
        var calendar = Calendar.getInstance()
        var listaClima = mutableListOf<ClimaEntidad>(ClimaEntidad(0,
            enCelsius(clima.current.temp),
            enCelsius(clima.daily[0].temp.min),
            enCelsius(clima.daily[0].temp.max),
            clima.current.weather.get(0).main,
            clima.current.weather.get(0).description,
            clima.current.weather.get(0).icon,
            "Hoy"
        ))
        var i = 1
        for (ele in clima.daily){
            if (ele != clima.daily[0]){
                listaClima.add(
                    ClimaEntidad(
                        i,
                        enCelsius(ele.temp.day),
                        enCelsius(ele.temp.min),
                        enCelsius(ele.temp.max),
                        ele.weather.get(0).main,
                        ele.weather.get(0).description,
                        ele.weather.get(0).icon,
                        calendar.diaSemana(i)
                    )
                )
                i++
            }
        }
        Log.d("AAA", "lista: ${listaClima.toString()}")
        return listaClima
    }

    override fun enCelsius(kelvin: Double): Double {
        return truncate(kelvin-272.15)
    }

    override fun diff(fecha: String): Long {

        val milisAdias = 24 * 60 * 60 * 1000
        val valores = fecha.split("/")
        val dia = Calendar.getInstance()

        Log.d("AAA", "dia diff AgendaRepository ${dia.time}")
        try {
            dia.set(valores[2].toInt(), valores[1].toInt(), valores[0].toInt())
        }catch (e:Exception){
            Log.d("AAA", "Error diff AgendaRepository ${e.message}")
        }
        val hoy = Calendar.getInstance().timeInMillis/(milisAdias)-dia.timeInMillis/(milisAdias)

        return hoy
    }

    override fun vigencia(tarea: TareaEntidad, diff: Long): Long {
        return when(tarea.climaId - diff >= 0){
            true -> tarea.climaId - diff
            else -> 0
        }
    }

    override fun tareasCleaner(listaTareas:List<TareaEntidad>):MutableList<TareaEntidad> {
        val fecha = diff(SharedPrefenrecesManager(context).getDate())
        val nuevaLista = mutableListOf<TareaEntidad>()
        for (tarea in listaTareas){
            var vigencia = vigencia(tarea, fecha).toInt()
            if (vigencia != 0){
                tarea.climaId = vigencia
                nuevaLista.add(tarea)
            }
        }
        return nuevaLista
    }

}

fun Calendar.diaSemana(contador: Int):String{
    var dia = fixDia(get(Calendar.DAY_OF_WEEK) + contador)
    return when (dia){
        Calendar.MONDAY -> "Lunes"
        Calendar.TUESDAY -> "Martes"
        Calendar.WEDNESDAY -> "Miercoles"
        Calendar.THURSDAY -> "Jueves"
        Calendar.FRIDAY -> "Viernes"
        Calendar.SATURDAY -> "Sábado"
        else ->"Domingo"
    }
}
fun Calendar.fixDia(valor:Int):Int{
    return when (valor > 7){
        true -> valor - 7
        false -> valor
    }
}
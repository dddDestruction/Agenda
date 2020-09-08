package cl.apicolm.myapplication.model

import android.util.Log
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima
import cl.apicolm.myapplication.model.sharedPreferences.SharedPrefenrecesManager
import java.util.*
import kotlin.math.truncate

class RepoUtil {

    fun mapperApiClima(clima: Clima): List<ClimaEntidad> {
        var calendar = Calendar.getInstance()
        var listaClima = mutableListOf<ClimaEntidad>(
            ClimaEntidad(0,
                enCelsius(clima.current.temp),
                enCelsius(clima.daily[0].temp.min),
                enCelsius(clima.daily[0].temp.max),
                clima.current.weather.get(0).main,
                clima.current.weather.get(0).description,
                clima.current.weather.get(0).icon,
                "Hoy"
            )
        )
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

     fun enCelsius(kelvin: Double): Double {
        return truncate(kelvin-272.15)
    }

     fun diff(fecha: String): Long {

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

     fun vigencia(tarea: TareaEntidad, diff: Long): Long {
        return when(tarea.climaId - diff >= 0){
            true -> tarea.climaId - diff
            else -> 0
        }
    }
}
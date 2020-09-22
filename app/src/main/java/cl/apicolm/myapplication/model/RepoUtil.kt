package cl.apicolm.myapplication.model

import android.util.Log
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima
import cl.apicolm.myapplication.model.sharedPreferences.SharedPrefenrecesManager
import java.util.*
import kotlin.math.truncate

class RepoUtil():IRepoUtil {

    override fun mapperApiClima(clima: Clima): List<ClimaEntidad> {
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

     override fun enCelsius(kelvin: Double): Double {
        return truncate(kelvin-272.15)
    }

     override fun diff(fecha: String): Long {

        val milisAdias = 24 * 60 * 60 * 1000
        val valores = fecha.split("/")
        val dia = Calendar.getInstance()

        Log.d("AAA", "dia diff RepoUtil ${dia.time}, mes ${Calendar.SEPTEMBER}")
        try {
            dia.set(valores[2].toInt(), valores[1].toInt()-1, valores[0].toInt())
        }catch (e:Exception){
            Log.d("AAA", "Error diff RepoUtil ${e.message}")
        }
        val hoy = Calendar.getInstance().timeInMillis/(milisAdias)-dia.timeInMillis/(milisAdias)
         Log.d("AAA", "return diff RepoUtil $hoy, calendar ${Calendar.getInstance().timeInMillis/(milisAdias)}, dia guardado ${dia.time}")
        return hoy
    }

     override fun vigencia(tarea: TareaEntidad, diff: Long): Long {
        return when(tarea.climaId - diff >= 0L){
            true -> tarea.climaId - diff
            else -> -1L
        }
    }

    override fun tareasCleaner(listaTareas:List<TareaEntidad>, diff:Long):MutableList<TareaEntidad> {
        val nuevaLista = mutableListOf<TareaEntidad>()
        for (tarea in listaTareas){
            var vigencia = vigencia(tarea, diff).toInt()
            if (vigencia != -1){
                tarea.climaId = vigencia
                nuevaLista.add(tarea)
            }
        }
        return nuevaLista
    }
}
package cl.apicolm.myapplication.model

import android.content.Context
import android.util.Log
import cl.apicolm.myapplication.model.api.RetrofitClient
import cl.apicolm.myapplication.model.db.AgendaDBManager
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        var listaClima = mutableListOf<ClimaEntidad>(ClimaEntidad(0,
            enCelsius(clima.current.temp),
            enCelsius(clima.daily[0].temp.min),
            enCelsius(clima.daily[0].temp.max),
            clima.current.weather.get(0).main,
            clima.current.weather.get(0).description,
            clima.current.weather.get(0).icon
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
                        ele.weather.get(0).icon
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

}
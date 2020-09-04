package cl.apicolm.myapplication.model

import android.util.Log
import cl.apicolm.myapplication.model.api.RetrofitClient
import cl.apicolm.myapplication.model.entidades.Tarea
import cl.apicolm.myapplication.model.pojo.Clima
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgendaRepository(
    //context: Context, scope: CoroutineScope
    ):IAgendaRepository {

    //val agendaManager = AgendaDBManager(context, scope)
    //var climas = agendaManager.climas
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
                //agendaManager.insert(climasApi)

            }

            override fun onFailure(call: Call<Clima>, t: Throwable) {
                Log.d("AAA", "Error: " + t)
            }
        })
    }

    override fun insetarTarea(tarea: Tarea) {
        TODO("Not yet implemented")
    }

    override fun loadTareas() {
        TODO("Not yet implemented")
    }

}
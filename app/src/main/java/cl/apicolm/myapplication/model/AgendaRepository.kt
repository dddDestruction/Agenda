package cl.apicolm.myapplication.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import cl.apicolm.myapplication.model.api.RetrofitClient
import cl.apicolm.myapplication.model.db.AgendaDBManager
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima
import cl.apicolm.myapplication.model.sharedPreferences.SharedPrefenrecesManager
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.truncate

class AgendaRepository( val context: Context, scope: CoroutineScope):IAgendaRepository {

    val agendaManager = AgendaDBManager(context, scope)
    var climas = agendaManager.getClimas()
    var climasApi = mutableListOf<Clima>()
    val repoUtil = RepoUtil()

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
                    agendaManager.insertarClimas(repoUtil.mapperApiClima(response.body()!!))
                }
                val calendar = Calendar.getInstance()
                Log.d("AAA", "fecha ${SimpleDateFormat("dd/MM/yyyy").format(calendar.time)}")
                if (repoUtil.diff(SharedPrefenrecesManager(context).getDate()) != 0L){
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
        agendaManager.insertarTarea(tarea)
    }

    override fun loadTareas(climaId:Int):LiveData<List<TareaEntidad>> {
        Log.d("AAA", "loadTareas ${agendaManager.getTareas(climaId).value}")
        return agendaManager.getTareas(climaId)
    }

    override fun deleteTarea(tarea: TareaEntidad) {
        TODO("Not yet implemented")
    }

}
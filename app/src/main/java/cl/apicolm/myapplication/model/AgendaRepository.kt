package cl.apicolm.myapplication.model

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cl.apicolm.myapplication.BuildConfig
import cl.apicolm.myapplication.model.api.RetrofitClient
import cl.apicolm.myapplication.model.db.AgendaDBManager
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.pojo.Clima
import cl.apicolm.myapplication.model.sharedPreferences.SharedPrefenrecesManager
import cl.apicolm.myapplication.util.Location
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AgendaRepository( val context: Context, scope: CoroutineScope):IAgendaRepository {

    val agendaManager = AgendaDBManager(context, scope)
    var climas = agendaManager.getClimas()
    private val calendar = Calendar.getInstance()
    private val sharedPrefenrecesManager = SharedPrefenrecesManager(context)
    val repoUtil = RepoUtil()

    override fun loadData() {

        val retrofit = RetrofitClient.retrofitInstance()
        var location = sharedPrefenrecesManager.getCoords()
        Log.d("AAA", "valores a llamada ${location.latitude}, ${location.longitude}")
        val call = retrofit.getClima(location.latitude,location.longitude,"hourly,minutely", BuildConfig.API_KEY)
        call.enqueue(object : Callback<Clima> {
            override fun onResponse(
                call: retrofit2.Call<Clima>,
                response: Response<Clima>
            ) {
                Log.d("AAA", response.body().toString())
                if (response.body() != null){
                    agendaManager.insertarClimas(repoUtil.mapperApiClima(response.body()!!))
                }
            }

            override fun onFailure(call: Call<Clima>, t: Throwable) {
                Log.d("AAA", "Error: " + t)
            }
        })
    }

    override fun insetarTarea(tarea: List<TareaEntidad>) {
        agendaManager.insertarTarea(tarea)
    }

    override fun loadTareas(climaId:Int):LiveData<List<TareaEntidad>> {
        Log.d("AAA", "loadTareas ${agendaManager.getTareas(climaId).value}")
        return agendaManager.getTareas(climaId)
    }

    override fun loadAllTareas(): LiveData<List<TareaEntidad>> {
        return  agendaManager.getAllTareas()
    }

    override fun actualizarTareas(lista:List<TareaEntidad>) {
        deleteTarea()
        Log.d("AAA", "fecha ${SimpleDateFormat("dd/MM/yyyy").format(calendar.time)}")
        var diff = repoUtil.diff(sharedPrefenrecesManager.getDate())
        sharedPrefenrecesManager.addSharedPreferences(
            SimpleDateFormat("dd/MM/yyyy").format(calendar.time))
        Log.d("AAA", "fecha desde Shared ${sharedPrefenrecesManager.getDate()}")
        agendaManager.insertarTarea(repoUtil.tareasCleaner(lista, diff))
    }


    override fun deleteTarea() {
        agendaManager.deleteTarea()
    }

    //códigos de localización
    override fun obtenerLocalizacion(activity: Activity): MutableLiveData<android.location.Location> {
        val coord = MutableLiveData<android.location.Location>()
            Location(activity).localizacion().observeForever {
            sharedPrefenrecesManager.addCoords(it)
            coord.value = it
        }
        return coord
    }
}
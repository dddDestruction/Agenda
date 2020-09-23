package cl.apicolm.myapplication.model.sharedPreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.Location

class SharedPrefenrecesManager(context: Context):ISharedPreferencesManager {

    val sharedPref: SharedPreferences = context.getSharedPreferences("this", Context.MODE_PRIVATE)
    private val FECHA_LABEL = "fecha"
    private val LATITUD_LABEL = "lat"
    private val LONGITUD_LABEL = "long"
    override fun addSharedPreferences(fechaHoy: String) {
        val editor =  sharedPref.edit()
        editor.putString(FECHA_LABEL, fechaHoy).apply()
    }

    override fun getDate(): String {
        return sharedPref.getString(FECHA_LABEL, "01/01/1990")!!
    }

    override fun addCoords(location: Location) {
        val editor =  sharedPref.edit()
        editor.putFloat(LATITUD_LABEL, location.latitude.toFloat())
        editor.putFloat(LONGITUD_LABEL, location.longitude.toFloat()).apply()
    }

    override fun getCoords(): Location {
        val coord = Location("gps")
        coord.latitude = sharedPref.getFloat(LATITUD_LABEL, 0.0f).toDouble()
        coord.longitude = sharedPref.getFloat(LONGITUD_LABEL, 0.0f).toDouble()
        return coord
    }


}
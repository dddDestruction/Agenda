package cl.apicolm.myapplication.model.sharedPreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SharedPrefenrecesManager(context: Context):ISharedPreferencesManager {

    val sharedPref: SharedPreferences = context.getSharedPreferences("this", Context.MODE_PRIVATE)

    override fun addSharedPreferences(fechaHoy: String) {
        val editor =  sharedPref.edit()
        editor.putString("fecha", fechaHoy).apply()
    }

    override fun getDate(): String {
        return sharedPref.getString("fecha", "01/01/1990")!!
    }
}
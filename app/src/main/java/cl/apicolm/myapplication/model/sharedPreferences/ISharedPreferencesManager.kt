package cl.apicolm.myapplication.model.sharedPreferences

import android.location.Location

interface ISharedPreferencesManager {
    //Agregar fecha a SharedPreferences
    fun addSharedPreferences(fechaHoy:String)
    //Obtener fecha de SharedPreferences
    fun getDate():String

    //Ingresar localizacion
    fun addCoords(location: Location)
    //Obtener localizacion
    fun getCoords(): Location
}
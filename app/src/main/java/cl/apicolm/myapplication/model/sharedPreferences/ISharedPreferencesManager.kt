package cl.apicolm.myapplication.model.sharedPreferences

interface ISharedPreferencesManager {
    //Agregar fecha a SharedPreferences
    fun addSharedPreferences(fechaHoy:String)
    //Obtener fecha de SharedPreferences
    fun getDate():String
}
package cl.apicolm.myapplication.util

import android.location.Location
import androidx.lifecycle.MutableLiveData

interface ILocation {
    fun localizacion(): MutableLiveData<Location>
    fun pedirPermiso()
    fun crearLocationRequest()
    fun crearLocationCallback()
    fun llamarGPS()
    fun terminarLlamadaGPS()
}
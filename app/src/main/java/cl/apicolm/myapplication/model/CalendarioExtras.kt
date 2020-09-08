package cl.apicolm.myapplication.model

import java.util.*

//Devuelve un String correspondiente al dia de la semana en base a un entero
fun Calendar.diaSemana(contador: Int):String{
    var dia = fixDia(get(Calendar.DAY_OF_WEEK) + contador)
    return transformarDia(dia)
}
//Convierte un valor numérico de la clase Calendar en un String que representa un dia de semana
fun Calendar.transformarDia (dia:Int):String{
    return when (dia){
        Calendar.MONDAY -> "Lunes"
        Calendar.TUESDAY -> "Martes"
        Calendar.WEDNESDAY -> "Miércoles"
        Calendar.THURSDAY -> "Jueves"
        Calendar.FRIDAY -> "Viernes"
        Calendar.SATURDAY -> "Sábado"
        else ->"Domingo"
    }
}
//Ajusta los valores para que siempre sean un n+umero del 1 al 7
fun Calendar.fixDia(valor:Int):Int{
    return when (valor > 7){
        true -> valor - 7
        false -> valor
    }
}
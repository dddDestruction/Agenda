package cl.apicolm.myapplication.model.pojo

data class Clima(val current:Current, val daily: List<Daily>)

data class Current(val temp:Double, val weather:List<Weather>)

data class Daily(val temp:Temp, val weather: List<Weather>)

data class Weather(val main:String, val description:String, val icon:String)

data class Temp(val day:Double, val min:Double, val max:Double)
package cl.apicolm.myapplication.model.api

import cl.apicolm.myapplication.model.pojo.Clima
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList

interface Api {
    @GET("onecall")
    fun getClima(@Query("lat") lat:Double,
                 @Query("lon")lon:Double ,
                 @Query("lon")exclude:String ,
                 @Query("appid") key:String): Call<Clima>
    /*
    @DELETE("/posts/{postId}")
    fun deletePost(@Path("postId") postId: Int?): Call<Void>

     */
}
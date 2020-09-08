package cl.apicolm.myapplication

import cl.apicolm.myapplication.model.fixDia
import cl.apicolm.myapplication.model.transformarDia
import org.junit.Assert
import org.junit.Test
import java.util.*

class CalendarioExtrasTest {
    val calendario = Calendar.getInstance()
    @Test
    fun fixDia_HappyCase() {
        //Este test busca ajustar un valor numérico menor a 15 a uno del 1 al 7
        //En este caso se introduce 10 y se espera 3
        Assert.assertEquals(3, calendario.fixDia(10))
    }
    @Test
    fun transformarDia_Domingo() {
        //Este test busca demostrar el funcionamiento del método transformarDia
        //el cual devuelve el dia de la semana correspondiente a cada número del 1 al 7
        Assert.assertEquals("Domingo", calendario.transformarDia(1))
    }
    @Test
    fun transformarDia_Lunes() {
        Assert.assertEquals("Lunes", calendario.transformarDia(2))
    }
    @Test
    fun transformarDia_Martes() {
        Assert.assertEquals("Martes", calendario.transformarDia(3))
    }
    @Test
    fun transformarDia_Miercoles() {
        Assert.assertEquals("Miércoles", calendario.transformarDia(4))
    }
    @Test
    fun transformarDia_Jueves() {
        Assert.assertEquals("Jueves", calendario.transformarDia(5))
    }
    @Test
    fun transformarDia_Viernes() {
        Assert.assertEquals("Viernes", calendario.transformarDia(6))
    }
    @Test
    fun transformarDia_Sabado() {
        Assert.assertEquals("Sábado", calendario.transformarDia(7))
    }

    @Test
    fun transformarDia_DomingoDefault() {
        //Si el valor no es un valor de 1 a 7, se devuelve Domingo por defecto
        Assert.assertEquals("Domingo", calendario.transformarDia(10))
    }
}
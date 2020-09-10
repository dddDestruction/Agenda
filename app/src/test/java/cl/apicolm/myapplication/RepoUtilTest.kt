package cl.apicolm.myapplication

import cl.apicolm.myapplication.model.RepoUtil
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import org.junit.Assert
import org.junit.Test

private const val fecha1 = "05/09/2020"
class RepoUtilTest {
    val repoUtil = RepoUtil()
    @Test
    fun enCelsius_HappyCase() {
        Assert.assertEquals(20.0, repoUtil.enCelsius(293.0), 0.1)
    }

    @Test
    fun vigencia_NoEsVigenteId1() {
        val tareaEntidad = TareaEntidad(1, "tarea")
        Assert.assertEquals(-1L, repoUtil.vigencia(tareaEntidad, 3L))
    }

    @Test
    fun vigencia_NoEsVigenteId2() {
        val tareaEntidad = TareaEntidad(2, "tarea")
        Assert.assertEquals(-1L, repoUtil.vigencia(tareaEntidad, 3L))
    }

    @Test
    fun vigencia_EsVigente() {
        val tareaEntidad = TareaEntidad(5, "tarea")
        Assert.assertEquals(2L, repoUtil.vigencia(tareaEntidad, 3L))
    }

    @Test
    fun tareasCleaner_HappyCaseFiltro() {
        val tarea1 = TareaEntidad(1, "tarea")
        val tarea2 = TareaEntidad(2, "tarea")
        val tarea3 = TareaEntidad(5, "tarea")
        val listaInicial = listOf<TareaEntidad>(
            tarea1,
            tarea2,
            tarea3
        )
        val listaFinal = mutableListOf<TareaEntidad>(
            tarea3
        )
        Assert.assertEquals(listaFinal, repoUtil.tareasCleaner(listaInicial, 3L))
    }

    @Test
    fun tareasCleaner_HappyCaseCambioClimaId() {
        val tarea1 = TareaEntidad(1, "tarea")
        val tarea2 = TareaEntidad(2, "tarea")
        val tarea3 = TareaEntidad(5, "tarea")
        val listaInicial = listOf<TareaEntidad>(
            tarea1,
            tarea2,
            tarea3
        )
        //Se espera que in climaId de TareaEntidad cambie de 5 a 2
        Assert.assertEquals(2, repoUtil.tareasCleaner(listaInicial, 3L).get(0).climaId)
    }
}
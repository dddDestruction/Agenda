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
    fun vigencia_NoEsVigente() {
        val tareaEntidad = TareaEntidad(0, "tarea", "hora1")
        Assert.assertEquals(0L, repoUtil.vigencia(tareaEntidad, 2L))
    }

    @Test
    fun vigencia_EsVigente() {
        val tareaEntidad = TareaEntidad(5, "tarea", "hora1")
        Assert.assertEquals(3L, repoUtil.vigencia(tareaEntidad, 2L))
    }
}
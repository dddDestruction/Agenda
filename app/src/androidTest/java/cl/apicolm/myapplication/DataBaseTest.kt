package cl.apicolm.myapplication

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.apicolm.myapplication.model.db.AgendaDB
import cl.apicolm.myapplication.model.db.AgendaDao
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import kotlinx.coroutines.runBlocking
import org.junit.After
import com.google.common.truth.Truth.assertThat


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DataBaseTest {
    private lateinit var agendaDao: AgendaDao
    private lateinit var db: AgendaDB
    val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    var instantTastkExectorTest = InstantTaskExecutorRule()
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertThat("cl.apicolm.myapplication").isEqualTo(appContext.packageName)
    }

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            context, AgendaDB::class.java).build()
        agendaDao = db.dao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertarYobtenerClimas()= runBlocking {
        val clima1 = ClimaEntidad(1,
            28.1,
            13.1,
            32.0,
            "sunny",
            "sunny",
            "10d",
            "Lunes"
        )
        val listaClimas = listOf<ClimaEntidad>(
            clima1
        )
        agendaDao.insertClima(listaClimas)

        agendaDao.getAllClimas().observeForever {
            listaRes ->
            assertThat(listaClimas).isEqualTo(listaRes)
        }

    }

    @Test
    @Throws(Exception::class)
    fun insertarYobtenerTareas() = runBlocking {
        val tarea1 =listOf( TareaEntidad(1,
            "tarea1"
        ))

        agendaDao.insertTarea(tarea1)
        agendaDao.getAllTareas().observeForever {
            listaRes->
            assertThat(listaRes).isNotNull()
            assertThat(listaRes).hasSize(1)
            assertThat(listaRes).isEqualTo(tarea1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertarYobtenerTareasConId() = runBlocking {
        val tarea1 = TareaEntidad(1, "tarea1")
        val tarea2 = TareaEntidad(2, "tarea2")
        val lista =listOf( tarea1,tarea2)

        agendaDao.insertTarea(lista)
        agendaDao.getTareas(2).observeForever {
                listaRes->
            assertThat(listaRes).isNotNull()
            assertThat(listaRes).hasSize(1)
            assertThat(listaRes[0]).isEqualTo(tarea2)
        }
    }

    @Test
    @Throws(Exception::class)
    fun borrarTareas() = runBlocking {
        val tarea1 =listOf( TareaEntidad(1,
            "tarea1"
        ))

        agendaDao.insertTarea(tarea1)
        agendaDao.borrarTarea()
        agendaDao.getAllTareas().observeForever {
                listaRes->
            assertThat(listaRes).isNotNull()
            assertThat(listaRes).hasSize(0)
        }

    }
}

@Throws(InterruptedException::class)
fun <T> LiveData<T>.getValueBlocking(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    Handler(Looper.getMainLooper()).post {
        observeForever(innerObserver)
    }
    latch.await(2, TimeUnit.SECONDS)
    return value
}
package cl.apicolm.myapplication

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
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
    val latch = CountDownLatch(1)
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("cl.apicolm.myapplication", appContext.packageName)
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

        var listaRes = agendaDao.getAllClimas().getValueBlocking()
        latch.await(10, TimeUnit.SECONDS)
        assertEquals(listaClimas, listaRes)
    }

    @Test
    @Throws(Exception::class)
    fun insertarYobtenerTareas() = runBlocking {
        val tarea1 = TareaEntidad(1,
            "tarea1",
            "19:00"
        )

        agendaDao.insertTarea(tarea1)
        var listaRes = agendaDao.getAllTareas(1).getValueBlocking()
        assertNotNull(listaRes)
        //assertEquals(tarea1, listaRes[0])

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
package cl.apicolm.myapplication.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.apicolm.myapplication.model.entidades.ClimaEntidad

@Database(entities = arrayOf(ClimaEntidad::class), version = 1)
abstract class AgendaDB  : RoomDatabase() {

    abstract fun dao(): AgendaDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AgendaDB? = null

        fun getDatabase(context: Context): AgendaDB {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AgendaDB::class.java,
                    "database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
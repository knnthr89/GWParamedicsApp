package com.guelphwellingtonparamedicsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.guelphwellingtonparamedicsapp.daos.UserDao
import com.guelphwellingtonparamedicsapp.entities.User
import org.jetbrains.annotations.NotNull
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        var context: Context? = null

        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase.javaClass) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java, "paramedics_database"
                        )
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback: Callback = object : RoomDatabase.Callback() {
            override fun onCreate(@NotNull db: SupportSQLiteDatabase) {
                super.onCreate(db)
                databaseWriteExecutor.execute {
                    /*var dao : UserDao = INSTANCE!!.userDao()

                    var user = User(1, "este es mi token")
                    dao.insert(user)*/
                }
            }
        }
    }
}
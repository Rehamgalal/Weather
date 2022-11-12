package com.scan.data.database.general

import android.content.Context
import androidx.room.*
import com.scan.data.database.converters.Converters
import com.scan.data.database.dao.WeatherDao
import com.scan.data.models.WeatherResponse
import org.koin.core.component.KoinComponent

@Database(entities = [WeatherResponse::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object : KoinComponent {

        private val TAG = this::class.simpleName

        const val DB_NAME = "weatherApp"


        @Volatile
        private var instance: AppDataBase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            val builder = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                DB_NAME
            )
            return builder.build()
        }

    }
}
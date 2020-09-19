package pl.chemik.ubiapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.chemik.ubiapp.database.dao.BoxDao
import pl.chemik.ubiapp.database.dao.ItemDao
import pl.chemik.ubiapp.database.dao.LocationDao
import pl.chemik.ubiapp.database.dao.PictureDao
import pl.chemik.ubiapp.database.entities.Box
import pl.chemik.ubiapp.database.entities.Item
import pl.chemik.ubiapp.database.entities.Location
import pl.chemik.ubiapp.database.entities.Picture

@Database(
    entities = [Item::class, Box::class, Location::class, Picture::class],
    version = 1
)
abstract class UbiDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao;
    abstract fun boxDao(): BoxDao;
    abstract fun locationDao(): LocationDao;
    abstract fun pictureDao(): PictureDao;

    companion object {
        private val DATABASE_NAME = "db.db";
        private var INSTANCE: UbiDatabase? = null;

        fun getDatabase(context: Context): UbiDatabase {
            val temporaryInstance = INSTANCE
            if(temporaryInstance != null){
                return temporaryInstance
            }

            synchronized(UbiDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UbiDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
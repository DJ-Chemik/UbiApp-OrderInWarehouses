package pl.chemik.ubiapp.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import pl.chemik.ubiapp.database.entities.Location

@Dao
interface LocationDao {
    @Insert
    fun create(item: Location)

    @Update
    fun update(item: Location)

    @Delete
    fun delete(item: Location)

    @Query("SELECT * FROM location")
    fun getAll(): List<Location>;

    @Query("SELECT * FROM location WHERE id = :id")
    fun getOneById(id: Int): Location;
}
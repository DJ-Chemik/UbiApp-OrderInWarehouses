package pl.chemik.ubiapp.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import pl.chemik.ubiapp.database.entities.Location

@Dao
interface LocationDao {
//    @Insert
//    fun create(item: Location): Single<Integer>;

    @Update
    fun update(item: Location): Completable;

    @Delete
    fun delete(item: Location): Completable;

    @Query("SELECT * FROM location")
    fun getAll(): Maybe<Location>;
}
package pl.chemik.ubiapp.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import pl.chemik.ubiapp.database.entities.Picture

@Dao
interface PictureDao {
//    @Insert
//    fun create(item: Picture): Single<Int>;

    @Update
    fun update(item: Picture): Completable;

    @Delete
    fun delete(item: Picture): Completable;

    @Query("SELECT * FROM picture")
    fun getAll(): Maybe<Picture>;
}
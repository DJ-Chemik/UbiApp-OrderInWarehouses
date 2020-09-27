package pl.chemik.ubiapp.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import pl.chemik.ubiapp.database.entities.Box

@Dao
interface BoxDao {
//    @Insert
//    fun create(item: Box): Single<Int>;

    @Update
    fun update(item: Box): Completable;

    @Delete
    fun delete(item: Box): Completable;

    @Query("SELECT * FROM box")
    fun getAll(): Maybe<Box>;
}
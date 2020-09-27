package pl.chemik.ubiapp.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import pl.chemik.ubiapp.database.entities.Picture

@Dao
interface PictureDao {
    @Insert
    fun create(item: Picture)

    @Update
    fun update(item: Picture)

    @Delete
    fun delete(item: Picture)

    @Query("SELECT * FROM picture")
    fun getAll(): Array<Picture>;
}
package pl.chemik.ubiapp.database.dao

import androidx.room.*
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
    fun getAll(): List<Picture>;

    @Query("SELECT * FROM picture WHERE id = :id")
    fun getOneById(id: Int): Picture;
}
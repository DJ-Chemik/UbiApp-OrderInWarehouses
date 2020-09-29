package pl.chemik.ubiapp.database.dao

import androidx.room.*
import pl.chemik.ubiapp.database.entities.Box
import pl.chemik.ubiapp.database.entities.Location

@Dao
interface BoxDao {
    @Insert
    fun create(item: Box)

    @Update
    fun update(item: Box)

    @Delete
    fun delete(item: Box)

    @Query("SELECT * FROM box")
    fun getAll(): List<Box>;

    @Query("SELECT * FROM box WHERE id = :id")
    fun getOneById(id: Int): Box;
}
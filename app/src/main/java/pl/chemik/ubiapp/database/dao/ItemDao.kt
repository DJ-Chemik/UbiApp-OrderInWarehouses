package pl.chemik.ubiapp.database.dao

import androidx.room.*
import pl.chemik.ubiapp.database.entities.Item

@Dao
interface ItemDao {

    @Insert
    fun create(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("SELECT * FROM item")
    fun getAll(): List<Item>;

    @Query("SELECT * FROM item WHERE id = :id")
    fun getOneById(id: Int): Item;
}
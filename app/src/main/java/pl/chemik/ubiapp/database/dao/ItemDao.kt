package pl.chemik.ubiapp.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import pl.chemik.ubiapp.database.entities.Item
import io.reactivex.Single

@Dao
interface ItemDao {

    @Insert
    fun create(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("SELECT * FROM item")
    fun getAll(): Maybe<Item>;
}
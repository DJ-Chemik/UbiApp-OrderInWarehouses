package pl.chemik.ubiapp.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import pl.chemik.ubiapp.database.entities.Item
import io.reactivex.Single

@Dao
interface ItemDao {

    @Insert
    fun create(item: Item): Single<Int>;

    @Update
    fun update(item: Item): Completable;

    @Delete
    fun delete(item: Item): Completable;

    @Query("SELECT * FROM item")
    fun getAll(): Maybe<Item>;
}
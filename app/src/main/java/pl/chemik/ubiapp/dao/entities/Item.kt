package pl.chemik.ubiapp.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["name"])]
)
data class Item (
    @ColumnInfo(name = "name")
    var name: String,

//    @ColumnInfo(name = "pictures")
//    var pictures: ByteArray?,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "ean_upc_code")
    var eanUpcCode: String?,

    @ColumnInfo(name = "categories")
    var categories: String?,

    @ColumnInfo(name = "box_id")
    var boxId: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
}
package pl.chemik.ubiapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Picture (
    @ColumnInfo(name = "content", typeAffinity = ColumnInfo.BLOB)
    var content: ByteArray,

    @ColumnInfo(name = "box_id")
    var box_id: Int,

    @ColumnInfo(name = "box_id")
    var item_id: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
}
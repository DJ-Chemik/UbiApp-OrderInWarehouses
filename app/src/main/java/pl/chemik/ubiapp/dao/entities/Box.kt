package pl.chemik.ubiapp.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["name"], unique = true)]
)
data class Box (
    @ColumnInfo(name = "name")
    var name: String,

//    @ColumnInfo(name = "picture")
//    var picture: ???

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "qr_code")
    var qrCode: String?,

    @ColumnInfo(name = "location_id")
    var locationId: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
}
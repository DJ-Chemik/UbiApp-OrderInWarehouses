package pl.chemik.ubiapp.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location (
    @ColumnInfo(name = "name")
    var name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
}
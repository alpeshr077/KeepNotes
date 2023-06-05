package Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")

data class NoteEntity(

    @ColumnInfo(name = "title") var title: Entity.NoteEntity,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "date") var date: String,
){
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}

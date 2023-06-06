package com.alpesh1.keepnotes

import Adapter.NotesAdapter
import Database.RoomDB
import Entity.NoteEntity
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alpesh1.keepnotes.databinding.ActivityMainBinding
import com.alpesh1.keepnotes.databinding.AddDialogBinding
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var db: RoomDB
    lateinit var adapter: NotesAdapter

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RoomDB.init(this)

        initView()
    }

    private fun initView() {
        binding.add.setOnClickListener {
            addNoteDialog()
        }

        adapter = NotesAdapter{
            var isPin = false
            if (it.pin){
                isPin = false
            }else{
                isPin = true
            }
            var data = NoteEntity(it.title,it.text,it.date,isPin)
            data.id = it.id
            db.note().updateNote(data)
            adapter.update(filterNote(db.note().getNotes()))
        }

        adapter.setNotes(filterNote(db.note().getNotes()))
        binding.noteList.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter = adapter
    }

    fun filterNote(list: List<NoteEntity>): ArrayList<NoteEntity> {
        var newList = ArrayList<NoteEntity>()
        for (l in list){
            if (l.pin){
                newList.add(l)
            }
        }
        for (l in list){
            if (!l.pin){
                newList.add(l)
            }
        }
        return newList
    }

    private fun addNoteDialog() {

        var dialog = Dialog(this)
        var bind = AddDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.btnSubmit.setOnClickListener {
            var title = bind.edtTitle.text.toString()
            var text = bind.edtText.text.toString()

            var format = SimpleDateFormat("DD/MM/YYYY hh:mm:ss a")
            var current = format.format(Date())

            var data = NoteEntity(title, text, current,false)
            db.note().addNote(data)

            bind.edtTitle.setText("")
            bind.edtText.setText("")

            adapter.update(filterNote(db.note().getNotes()))
            dialog.dismiss()
        }

        dialog.show()
    }
}
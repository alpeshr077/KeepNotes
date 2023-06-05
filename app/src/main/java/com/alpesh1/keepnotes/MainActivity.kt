package com.alpesh1.keepnotes

import Adapter.NotesAdapter
import Database.RoomDB
import Entity.NoteEntity
import android.app.Dialog
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alpesh1.keepnotes.databinding.ActivityMainBinding
import com.alpesh1.keepnotes.databinding.AddDialogBinding
import com.alpesh1.keepnotes.databinding.UpdateDialogBinding
import java.text.SimpleDateFormat
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



        adapter = NotesAdapter() {
            updateDialog(it)

        }
        initView()
    }

    private fun initView() {
        binding.add.setOnClickListener {
            addNoteDialog()
        }

        adapter = NotesAdapter(db.note().getNotes())
        binding.noteList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter = adapter
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

            var data = NoteEntity(title, text, current)
            db.note().addNote(data)

            bind.edtTitle.setText("")
            bind.edtText.setText("")

            adapter.update(db.note().getNotes())
            dialog.dismiss()
        }

        dialog.show()
    }

    fun updateDialog(noteEntity: NoteEntity) {
        var dialog = Dialog(this)
        var bind1 = UpdateDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind1.root)

        bind1.edtTitle.setText(noteEntity.title.toString())
        bind1.edtText.setText(noteEntity.text.toString())

        bind1.btnSubmit.setOnClickListener {
            var title = bind1.edtTitle.text.toString()
            var text = bind1.edtTitle.text.toString()

            var modal = NoteEntity(noteEntity, title, text)


        }
    }
}
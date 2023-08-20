package com.alpesh1.keepnotes

import Adapter.NotesAdapter
import Database.RoomDB
import android.app.Dialog
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alpesh1.keepnotes.databinding.ActivityMainBinding
import com.alpesh1.keepnotes.databinding.AddDialogBinding
import com.alpesh1.keepnotes.databinding.UpdateDialogBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import Entity.NoteEntity as NoteEntity1

class MainActivity : AppCompatActivity() {

    lateinit var db: RoomDB
    lateinit var adapter: NotesAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RoomDB.init(this)
        db.note().getNotes()

        initView()
    }

    private fun initView() {
        binding.add.setOnClickListener {
            addNoteDialog()
        }

        adapter = NotesAdapter({
            var isPin = false
            isPin = !it.pin

            var data = NoteEntity1(it.title, it.text, it.date, isPin)
            data.id = it.id
            db.note().updateNote(data)
            adapter.update(filterNote(db.note().getNotes()))
        }, {

            updateDialog(it)

        }) {

            db.note().deleteNote(it)
            adapter.update(filterNote(db.note().getNotes()))

        }

        adapter.setNotes(filterNote(db.note().getNotes()))
        binding.noteList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter = adapter

//        var searchView = findViewById<SearchView>(R.id.search)
//
//        searchView.clearFocus()
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//
//                searchView.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//
//                adapter.update(filterNote(db.note().getNotes()))
//
//                return false
//            }
//
//        })

    }

    fun filterNote(list: List<NoteEntity1>): ArrayList<NoteEntity1> {
        var newList = ArrayList<NoteEntity1>()
        for (l in list) {
            if (l.pin) {
                newList.add(l)
            }
        }
        for (l in list) {
            if (!l.pin) {
                newList.add(l)
            }
        }
        return newList
    }

    private fun addNoteDialog() {

        var dialog = Dialog(this)
        var bind = AddDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.edtDate.setText(
            SimpleDateFormat(
                "d/MM/yyyy",
                Locale.getDefault()
            ).format(Date())
        )

        bind.btnSubmit.setOnClickListener {

            var title = bind.edtTitle.text.toString()
            var text = bind.edtText.text.toString()
            var date = bind.edtDate.text.toString()

            if (title.isEmpty()) {
                Toast.makeText(this, "Please insert title", Toast.LENGTH_SHORT).show()
            } else if (text.isEmpty()) {
                Toast.makeText(this, "Please insert text", Toast.LENGTH_SHORT).show()
            } else {

                var data = NoteEntity1(title, text, date, false)
                db.note().addNote(data)

                bind.edtTitle.setText("")
                bind.edtText.setText("")
                bind.edtDate.setText("")

                adapter.update(filterNote(db.note().getNotes()))
                dialog.dismiss()
            }

        }
        dialog.show()
    }

    private fun updateDialog(noteEntity: NoteEntity1) {

        var dialog = Dialog(this)
        var bind1 = UpdateDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind1.root)

        bind1.edtTitle.setText(noteEntity.title)
        bind1.edtText.setText(noteEntity.text)

        bind1.btnSubmit1.setOnClickListener {

            var title = bind1.edtTitle.text.toString()
            var text = bind1.edtText.text.toString()

            var format = SimpleDateFormat("DD/MM/YYYY hh:mm:ss a")
            var current = format.format(Date())

            var data1 = NoteEntity1(title, text, current, false)
            db.note().updateNote(data1)

            adapter.update(filterNote(db.note().getNotes()))
            dialog.dismiss()

            bind1.edtTitle.setText("")
            bind1.edtText.setText("")

        }

        dialog.show()
    }

}
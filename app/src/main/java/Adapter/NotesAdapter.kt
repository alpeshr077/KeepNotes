package Adapter

import Entity.NoteEntity
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.drawerlayout.R
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alpesh1.keepnotes.databinding.NoteItemBinding

class NotesAdapter(notes: List<NoteEntity>,update :(NoteEntity) ->Unit,delete :(NoteEntity) ->Unit) : Adapter<NotesAdapter.NotesHolder>() {

    var update = update
    var delete = delete

    var notes = notes

    lateinit var context : Context

    class NotesHolder(itemView: NoteItemBinding) : ViewHolder(itemView.root) {

        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {

        context = parent.context

        var binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder:NotesHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.apply {
            txtTite.isSelected = true
            notes.get(position).apply {
                txtTite.text = title
                txtText.text = text
            }
        }

        holder.itemView.setOnLongClickListener(object : OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                var popupMenu = PopupMenu(context,holder.itemView)
                popupMenu.menuInflater.inflate(com.alpesh1.keepnotes.R.menu.update_menu,popupMenu.menu)

              popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                  override fun onMenuItemClick(p0: MenuItem?): Boolean {

                      if (p0?.itemId == com.alpesh1.keepnotes.R.id.update){
                          update.invoke(notes.get(position))
                      }

                     return true
                  }

              })
                return true
            }

        })

    }

    fun update(notes: List<NoteEntity>) {
        this.notes = notes
        notifyDataSetChanged()
    }
}
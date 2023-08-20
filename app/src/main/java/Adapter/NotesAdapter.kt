package Adapter

import Entity.NoteEntity
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alpesh1.keepnotes.R
import com.alpesh1.keepnotes.databinding.NoteItemBinding

class NotesAdapter(updatePin: (NoteEntity) -> Unit, update: (NoteEntity) -> Unit, delete: (NoteEntity) -> Unit) : Adapter<NotesAdapter.NotesHolder>() {

    var updatePin = updatePin

    var update = update
    var delete = delete

    var notes = ArrayList<NoteEntity>()

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
                txtDate.text = date

                if (pin){
                    imgPin.setImageResource(com.alpesh1.keepnotes.R.drawable.pin)
                }else{
                    imgPin.setImageResource(com.alpesh1.keepnotes.R.drawable.unpin)
                }

                imgPin.setOnClickListener {
                    updatePin.invoke(notes.get(position))
                }
            }
        }


        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {

                var popupMenu = PopupMenu(context, holder.itemView)
                popupMenu.menuInflater.inflate(com.alpesh1.keepnotes.R.menu.update_menu,popupMenu.menu)

                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {


                        if (p0?.itemId == R.id.edit){
                            update.invoke(notes.get(position))
                        }

                        if (p0?.itemId == R.id.delete){
                            delete.invoke(notes.get(position))
                        }
                        return true
                    }

                })

                popupMenu.show()

                return false
            }

        })

    }

    fun update(notes: List<NoteEntity>) {
        this.notes = notes as ArrayList<NoteEntity>
        notifyDataSetChanged()
    }

    fun setNotes(notes: List<NoteEntity>) {
        this.notes = notes as ArrayList<NoteEntity>
    }
}
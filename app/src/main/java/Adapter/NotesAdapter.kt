package Adapter

import Entity.NoteEntity
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alpesh1.keepnotes.databinding.NoteItemBinding

class NotesAdapter(updatePin: (NoteEntity) -> Unit) : Adapter<NotesAdapter.NotesHolder>() {

    var updatePin = updatePin
//    lateinit var notes : List<NoteEntity>

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

    }

    fun update(notes: List<NoteEntity>) {
        this.notes = notes as ArrayList<NoteEntity>
        notifyDataSetChanged()
    }

    fun setNotes(notes: List<NoteEntity>) {
        this.notes = notes as ArrayList<NoteEntity>
    }
}
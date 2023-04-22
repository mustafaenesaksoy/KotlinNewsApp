package com.enesaksoy.kotlinnewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enesaksoy.kotlinnewsapp.databinding.RecyclerRowBinding
import com.enesaksoy.kotlinnewsapp.roomdb.NewModel
import com.enesaksoy.kotlinnewsapp.view.ListFragmentDirections

class ListNewAdapter : RecyclerView.Adapter<ListNewAdapter.ListHolder>() {
    class ListHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    val diffUtil =object:  DiffUtil.ItemCallback<NewModel>(){
        override fun areItemsTheSame(oldItem: NewModel, newItem: NewModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewModel, newItem: NewModel): Boolean {
            return newItem == oldItem
        }
    }
    private val recyclerlist = AsyncListDiffer(this,diffUtil)

    var modelList : List<NewModel>
    get() = recyclerlist.currentList
    set(value) = recyclerlist.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListHolder(binding)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val selectedNew = modelList.get(position)
        holder.binding.newTitle.text = selectedNew.title
        holder.binding.newAuthor.text = selectedNew.author
         holder.itemView.setOnClickListener {
         val action = ListFragmentDirections.actionListFragmentToDetailsFragment(selectedNew.id)
         Navigation.findNavController(it).navigate(action)
         }
}
}
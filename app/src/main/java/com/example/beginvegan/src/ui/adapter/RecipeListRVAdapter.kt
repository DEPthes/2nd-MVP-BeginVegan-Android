package com.example.beginvegan.src.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beginvegan.databinding.ItemRecipeBinding

class RecipeListRVAdapter(private val recipeNames: ArrayList<String>): RecyclerView.Adapter<RecipeListRVAdapter.RecycleViewHolder>() {
    private var listener: OnItemClickListener? = null

    inner class RecycleViewHolder(private val binding: ItemRecipeBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(recipeName:String){
            binding.tvRecipeName.text = recipeName
        }
    }

    //
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecycleViewHolder(binding)
    }

    override fun getItemCount(): Int = recipeNames.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val item = recipeNames[position]
        holder.bind(item)

        if(position != RecyclerView.NO_POSITION){
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, recipeNames[position], position)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
    interface OnItemClickListener {
        fun onItemClick(v: View, data: String, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}
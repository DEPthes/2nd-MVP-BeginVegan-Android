package com.example.beginvegan.src.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beginvegan.databinding.ItemVeganmapRestaurantBinding

class VeganMapBottomSheetRVAdapter(private val dataList: ArrayList<String>): RecyclerView.Adapter<VeganMapBottomSheetRVAdapter.DataViewHolder>(){
    private var listener: OnItemClickListener? = null

    inner class DataViewHolder(private val binding: ItemVeganmapRestaurantBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(){
            // bind
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val binding = ItemVeganmapRestaurantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: DataViewHolder,
        position: Int
    ) {
        holder.bind()
    }
    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    interface OnItemClickListener {
        fun onItemClick(v: View, data: String, position: Int)
        fun onLongClick(v: View, data: String, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}
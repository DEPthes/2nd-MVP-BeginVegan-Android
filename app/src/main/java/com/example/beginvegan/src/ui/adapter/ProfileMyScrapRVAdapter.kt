package com.example.beginvegan.src.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beginvegan.R
import com.example.beginvegan.databinding.ItemProfileMyreviewBinding
import com.example.beginvegan.databinding.ItemProfileMyscrapBinding

class ProfileMyScrapRVAdapter(private val scrapList: ArrayList<String>): RecyclerView.Adapter<ProfileMyScrapRVAdapter.RecycleViewHolder>() {
    private var listener: OnItemClickListener? = null

    //sample img
    val resImg = listOf(
        R.drawable.test_home_res1,
        R.drawable.test_home_res2,
        R.drawable.test_home_re3,
        R.drawable.test_res2,
        R.drawable.test_res,
    )

    inner class RecycleViewHolder(private val binding: ItemProfileMyscrapBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(restaurantName:String, time:String, address:String, position: Int){
            Log.d("TEST","bind")
            binding.ivRestaurantImg.setImageResource(resImg[position % resImg.size ])
            binding.tvRestaurantName.text = restaurantName
            binding.tvRestaurantTime.text = time
            binding.tvRestaurantAddress.text = address
            binding.ibDeleteScrap.setOnClickListener {
                //
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val binding = ItemProfileMyscrapBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecycleViewHolder(binding)
    }

    override fun getItemCount(): Int = scrapList.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val item = scrapList[position]
        holder.bind("식당 이름","time","address", position)

        Log.d("TEST","onBindViewHolder")
        if(position != RecyclerView.NO_POSITION){
            Log.d("TAG","Click")
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, scrapList[position], position)
                Log.d("TAG","ClickListener")
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
        Log.d("TAG","setOnItemClickListener")
        this.listener = listener
    }
}
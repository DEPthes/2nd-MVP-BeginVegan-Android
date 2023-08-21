package com.example.beginvegan.src.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.beginvegan.R
import com.example.beginvegan.databinding.ItemHomeRecommendRestaurantBinding
import com.example.beginvegan.src.data.model.restaurant.NearRestaurant

class HomeRecommendRestRVAdapter(private val context: Context,private val recommendRestList: ArrayList<NearRestaurant>):
    RecyclerView.Adapter<HomeRecommendRestRVAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ItemHomeRecommendRestaurantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecyclerViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = recommendRestList[position % recommendRestList.size]
        holder.bind(item)
    }

    override fun getItemCount() = Int.MAX_VALUE

    class RecyclerViewHolder(private val context: Context, private val binding: ItemHomeRecommendRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data:NearRestaurant) {
            binding.tvRestaurantName.text = data.name
            if(!data.imageUrl.isNullOrEmpty()){
                Glide.with(context).load(data.imageUrl).transform(CenterCrop(),RoundedCorners(8)).into(binding.ivRestaurantImg)
            }else{
                Glide.with(context).load(R.drawable.test_home_res1).transform(CenterCrop(),RoundedCorners(8)).into(binding.ivRestaurantImg)
            }
        }
    }
}
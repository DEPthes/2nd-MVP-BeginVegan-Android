package com.example.beginvegan.util

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.beginvegan.R
import com.example.beginvegan.databinding.DialogHomeMagazineDetailBinding
import com.example.beginvegan.src.data.model.magazine.MagazineDetail
import okhttp3.internal.applyConnectionSpec

class HomeMagazineDetailDialog(context: Context, private val data:MagazineDetail): Dialog(context) {

    private val binding: DialogHomeMagazineDetailBinding = DialogHomeMagazineDetailBinding.inflate(
        LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //데이터 연결
        binding.tvMagazineTitle.text = data.title
        val contents = data.magazineContents
        for(i:Int in 0..contents.size){
            if(data.id==1&&i==0){
                createImageView(contents[i].content)
            }else{
                createTextView(contents[i].content)
            }
        }

        //dialog 크기
//        val layoutParams = WindowManager.LayoutParams()
//        val dialogView = findViewById<View>(R.id.fl_magazine_dialog)
//        layoutParams.copyFrom(window!!.attributes)
//        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
//        window!!.attributes = layoutParams
    }

    private fun createTextView(text:String){
        val newTextView = TextView(context)
        newTextView.text = text
        newTextView.textSize = R.dimen.text_14.toFloat()
        newTextView.setTextColor(ContextCompat.getColor(context,R.color.color_text_32))
        newTextView.gravity = Gravity.CENTER
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        newTextView.layoutParams = params
        binding.llMagazineContents.addView(newTextView)
    }

    private fun createImageView(url:String){
        val newImgView = ImageView(context)
        Glide.with(context).load(url).into(newImgView)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        newImgView.layoutParams = params
        binding.llMagazineContents.addView(newImgView)
    }
}
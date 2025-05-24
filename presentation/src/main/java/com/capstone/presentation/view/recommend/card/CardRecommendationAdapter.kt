package com.capstone.presentation.view.recommend.card

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.domain.model.recommend.card.CardRecommendations
import com.capstone.presentation.R

class CardRecommendationAdapter(
    private val items: List<CardRecommendations>
) : RecyclerView.Adapter<CardRecommendationAdapter.RecommendationViewHolder>() {

    inner class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.iv_recommend_card_image)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvReason: TextView = itemView.findViewById(R.id.tvReason)
        val tvDetailUrl: TextView = itemView.findViewById(R.id.tvDetailUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommend_card_result, parent, false)
        return RecommendationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val item = items[position]

        holder.tvDescription.text = item.description
        holder.tvReason.text = item.reason

        holder.tvDetailUrl.apply {
            text = "상세 보기"
            setTextColor(context.getColor(R.color.blue)) // 링크 색상 지정
            paint.isUnderlineText = true
            setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.detailUrl))
                context.startActivity(browserIntent)
            }
        }

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_logo) // 이미지 로딩 실패 시 기본 이미지
            .into(holder.ivImage)
    }

    override fun getItemCount(): Int = items.size
}

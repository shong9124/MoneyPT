package com.capstone.presentation.view.recommend.card

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.domain.model.recommend.card.CardRecommendations
import com.capstone.presentation.databinding.ItemRecommendCardResultBinding

class RecommendationCardDetailAdapter(
    private val items: List<CardRecommendations>
) : RecyclerView.Adapter<RecommendationCardDetailAdapter.RecommendationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendCardResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class RecommendationViewHolder(
        private val binding: ItemRecommendCardResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CardRecommendations) = with(binding) {
            tvDescription.text = item.description
            tvReason.text = item.reason

            tvDetailUrl.apply {
                text = "상세 보기"
                paint.isUnderlineText = true
                setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.detailUrl))
                    context.startActivity(intent)
                }
            }

            Glide.with(root.context)
                .load(item.imageUrl)
                .placeholder(com.capstone.presentation.R.drawable.ic_logo)
                .into(ivRecommendCardImage)
        }
    }
}

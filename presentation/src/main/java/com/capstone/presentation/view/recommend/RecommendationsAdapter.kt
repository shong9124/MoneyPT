package com.capstone.presentation.view.recommend

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstone.domain.model.recommend.Recommendations
import com.capstone.presentation.R
import com.capstone.presentation.databinding.ItemRecommendItemResultBinding
import android.view.LayoutInflater
import com.capstone.util.LoggerUtil

class RecommendationAdapter(
    private val items: List<Recommendations>
) : RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    inner class RecommendationViewHolder(val binding: ItemRecommendItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recommendations) {
            LoggerUtil.d("Binding item: ${item.description}")

            binding.tvDescription.text = item.description
            binding.tvReason.text = item.reason

            binding.tvDetailUrl.apply {
                text = item.detailUrl
                paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
                setTextColor(ContextCompat.getColor(context, R.color.blue)) // blue 컬러 리소스 추가
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.detailUrl))
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecommendItemResultBinding.inflate(inflater, parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

package com.capstone.presentation.view.recommend.bank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.domain.model.recommend.bank.Recommendations
import com.capstone.domain.model.recommend.bank.ResponseRecommendation
import com.capstone.presentation.databinding.ItemRecommendItemResultBinding

class RecommendationBankDetailAdapter(
    responseList: List<ResponseRecommendation>
) : RecyclerView.Adapter<RecommendationBankDetailAdapter.RecommendationViewHolder>() {

    private val flattenedRecommendations: List<Recommendations> = responseList.flatMap { it.content.recommendations }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(flattenedRecommendations[position])
    }

    override fun getItemCount(): Int = flattenedRecommendations.size

    inner class RecommendationViewHolder(
        private val binding: ItemRecommendItemResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Recommendations) = with(binding) {
            tvDescription.text = item.description
            tvReason.text = item.reason

            // 링크 텍스트 스타일 및 클릭 가능하도록 설정
            tvDetailUrl.apply {
                text = item.detailUrl
                paint.isUnderlineText = true
                setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
                setOnClickListener {
                    // 외부 브라우저로 열기
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                        data = android.net.Uri.parse(item.detailUrl)
                    }
                    it.context.startActivity(intent)
                }
            }
        }
    }
}

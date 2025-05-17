package com.capstone.presentation.view.signIn

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.domain.model.FinancialType
import com.capstone.domain.model.UserSurveyResult
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentQuestionBinding
import com.capstone.presentation.util.UiState
import com.capstone.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionFragment : BaseFragment<FragmentQuestionBinding>() {

    private val viewModel: PropensityViewModel by viewModels()

    private val questionList = listOf(
        Question(
            questionText = "1. 이사를 가야하는 상황, 전월세나 매매에 들어가는 금액이 비슷하다면?",
            options = listOf(
                "전세로 살면서 돈은 최대한 안전하게 들고 있는 게 낫지. 유동성이 최고야",
                "집은 어차피 자산이 아니라 불편하니까, 인테리어 잘 된 월세 집에서 자유롭게 살래요",
                "매매로 가죠. 장기적으로 집값 오를 수 있으니 투자 가치로 봐야죠",
                "전세로 들어가고 남은 돈은 부동산 외 투자처에도 분산하죠.",
                "거주 안정성, 유동성, 투자 전망까지 모두 따져서 분석 후 결정할래요."
            )
        ),
        Question(
            questionText = "2. 복권에 당첨되어서 갑자기 150만원이 생긴다면?",
            options = listOf(
                "이럴 땐 무조건 비상금 통장에 넣어야지. 혹시 몰라서 말이야",
                "드디어! 갖고 싶던 명품 소품 바로 사러 간다~!",
                "이건 운 좋게 생긴 투자 기회지. ETF나 코인에 바로 들어간다",
                "반은 여행 가서 힐링하고, 반은 굴려봐야지. 놀면서 버는 게 최고!",
                "지금 내 상황에서 제일 필요한 게 뭘까? 그걸 먼저 생각해볼래."
            )
        ),
        Question(
            questionText = "3. 집으로 정체 모를 세금 고지서가 날아왔습니다. 분명히 내야 할 세금은 다 냈는데 말이죠.",
            options = listOf(
                "뭐야 이거... 이중으로 낸 거 아닌지 일단 꼼꼼히 확인부터 해야지.",
                "귀찮아도 그냥 내버리고 잊는 게 속 편하지 뭐…",
                "혹시 돌려받을 방법이 있지 않을까? 절세 팁 찾아봐야지",
                "일단 납부는 하되, 왜 그런지 따져보는 건 해야지",
                "우선 내고, 공공기관에 전화해서 상황부터 파악해보자."
            )
        ),
        Question(
            questionText = "4. 갑자기 핸드폰이 고장났어요. 어떤 핸드폰을 사고싶으세요?",
            options = listOf(
                "20~40만원 선으로 중고폰 찾아봐야겠다. 급하다고 돈 쓸 순 없지",
                "이참에 올해나온 플래그십으로 업그레이드할래. 기분전환도 되고!",
                "1~2년 된 아이폰 중고로 사서 쓰다가 나중에 당근으로 팔면 되니까.",
                "기능은 꼭 필요한 것만, 감성도 챙길 수 있는 모델로 적당한 중고나 보급형 중에서 골라야지.",
                "중고폰부터 최신 플래그십까지 전부 비교해서 내 재정 상태에 맞는 최적의 선택을 할래."
            )
        ),
        Question(
            questionText = "5. 1년간 저축해오던 적금이 만기입니다. 이자가 너무 적어 실망스러운 당신, 가장 먼저 무슨 생각을 했을까요?",
            options = listOf(
                "그래도 그냥 다시 묻어두는 게 마음이 편하긴 해.",
                "이럴 줄 알았으면 그냥 쓰는 건데, 괜히 아꼈네~",
                "이젠 좀 굴려야겠어. ETF나 펀드에 넣자",
                "이번엔 반은 안정형, 반은 수익형으로 나눠서 투자해보자.",
                "내 목표에 맞춰서 저축이든 투자인지 전략 다시 짜야겠네"
            )
        ),
        Question(
            questionText = "6. 이번 휴가때 해외여행을 가려는데 계획했던 예산보다 오버될것 같아요. 어떻게 해야할까요?",
            options = listOf(
                "여행은 다음 기회로 미루고 그 예산은 고스란히 저축할래",
                "이 기회 놓치면 평생 못 가잖아. 일단 가고 보자! 부족한건 친구한테 빌리지 뭐",
                "이번 여행은 내 재충전이자 경험 투자야. 필요하면 적금 일부 깨도 괜찮아",
                "필수 일정만 챙기고, 나머진 저가 항공과 게스트하우스로 여행비 조정할래.",
                "꼭 지금 가야 하는 걸까? 여행을 가서 내가 얻을 수 있는게 뭘까? 전체 일정을 다시 보고, 다음 휴가 때 가는 게 나을지도 모르겠어."
            )
        ),
        Question(
            questionText = "7. 보험설계사인 고모에게 보험을 추천 받았습니다. 보험료도 커피 한 잔 값이고 보장 내용도 원하던 상품입니다. 어떻게 할까요?",
            options = listOf(
                "고모가 추천해도 바로 가입하긴 좀 그래. 고모~ 나중에 가입할게, 좀 더 고민해볼게!",
                "커피 한 잔 값이라며? 거절하면 고모도 서운할것 같아. 그냥 가입할래~",
                "비슷한 조건 상품이 더 좋은 게 있는지 비교해서 가성비 따져볼래.",
                "고모 마음도 소중하고 상품도 괜찮으니, 내가 원하는 특약이 포함되면 가입할래",
                "내가 지금 보험이 진짜 필요한지부터 다시 보고, 고모가 추천해준 게 최선인지 전체적으로 따져볼래."
            )
        ),
        Question(
            questionText = "8. 다이소에서 필요한 물품을 사러 들어갔는데 갑자기 사고싶어진 물건이 눈에 보입니다. 어떻게 할까요?",
            options = listOf(
                "필요한 것만 사야지. 장바구니에서 뺄래",
                "예쁘고 싸면 고민할 필요 없지~ 바로 장바구니!",
                "이걸 왜 사고 싶은지 스스로에게 물어봐야지.",
                "이번 달 지출 여유 있는지 확인하고, 괜찮으면 사도 되겠지.",
                "다른 소비랑 비교해보면서 우선순위 정해볼래."
            )
        ),
        Question(
            questionText = "9. SNS 광고에서 본 신상, 완전히 내 취향이에요. 내 마음에 가장 먼저 떠오른 생각은…",
            options = listOf(
                "음... 멋지긴 한데 나한테 진짜 필요한 물건은 아니지.",
                "이건 진짜 내 거야. 놓치면 평생 후회할지도 몰라!!",
                "이거 리셀가도 높을까? 브랜드 가치 검색해봐야지.",
                "세일할 때까지 기다리자. 쿠폰이나 이벤트도 확인!",
                "지금 내 재정 상태랑 계획 먼저 보고 결정하자."
            )
        ),
        Question(
            questionText = "10. 짝사랑하는 여/남사친이 있는데, 고백할까 말까 고민되네..",
            options = listOf(
                "내 감정 들키면 어색해질 수 있으니까... 그냥 조용히 좋아하는 걸로 만족할래.",
                "고백은 타이밍이야. 지금 아니면 후회할지도 몰라! 해보자!",
                "그 사람 성향이랑 현재 상황 분석 좀 해봐야겠어. 승산 있는 타이밍에 가야지.",
                "너무 직접적이진 않게, 밥 한 번 먹자고 자연스럽게 떠보자.",
                "내 감정도 중요하지만, 그 사람 상황도 보면서 감정 정리하고 한 번 더 생각해볼래."
            )
        )
    )

    // 유형별 점수 저장 맵
    private val scoreMap = mutableMapOf(
        FinancialType.CONSERVATIVE to 0.0,
        FinancialType.CONSUMPTIVE to 0.0,
        FinancialType.INVESTMENT to 0.0,
        FinancialType.FUSION to 0.0,
        FinancialType.BALANCED to 0.0
    )

    // 각 질문별 가중치 리스트
    private val questionWeights = listOf(
        2.0, 1.33, 1.1, 1.05, 2.1, 2.15, 1.0, 1.15, 1.1, 1.25
    )

    private var currentQuestionIndex = 0
    private var selectedOptionIndex: Int? = null
    private val userAnswers = mutableListOf<Int>()


    override fun initView() {

        setupOptionClickListeners()
        showQuestion(currentQuestionIndex)

        binding.btnToSignComplete.setOnClickListener {
            if (selectedOptionIndex == null) {
                showToast("선지를 선택해주세요")
                return@setOnClickListener
            }

            // 선택한 유형에 맞게 점수 누적
            val selectedType = when (selectedOptionIndex) {
                0 -> FinancialType.CONSERVATIVE
                1 -> FinancialType.CONSUMPTIVE
                2 -> FinancialType.INVESTMENT
                3 -> FinancialType.FUSION
                4 -> FinancialType.BALANCED
                else -> null
            }

            selectedType?.let {
                val weight = questionWeights[currentQuestionIndex]
                scoreMap[it] = scoreMap.getOrDefault(it, 0.0) + weight
            }

            userAnswers.add(selectedOptionIndex!!)

            LoggerUtil.d("$userAnswers")

            if (currentQuestionIndex < questionList.size - 1) {
                currentQuestionIndex++
                showQuestion(currentQuestionIndex)
            } else {
                // 최종 점수 문자열 생성
                val resultString = buildResultString()
                showToast("모든 질문을 완료했습니다.\n결과:\n$resultString")

                LoggerUtil.d(resultString)

                // TODO: postSurveyResult(resultString) 함수 추가 시 호출
                viewModel.sendQuestionResult(UserSurveyResult(resultString))
            }
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.propensityState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    showToast("금융 성향 분석에 성공했습니다.")
                    val route = NavigationRoutes.RecommendFinancailItem
                    moveToNext(route)
                }
                is UiState.Error -> {
                    showToast("금융 성향 분석에 실패했습니다.")
                }
            }
        }
    }

    private fun showQuestion(index: Int) {
        val question = questionList[index]
        binding.tvQuestion.text = question.questionText

        val optionViews = listOf(
            binding.tvOption1, binding.tvOption2,
            binding.tvOption3, binding.tvOption4,
            binding.tvOption5
        )

        for (i in optionViews.indices) {
            optionViews[i].text = question.options[i]
            optionViews[i].isSelected = false
        }

        selectedOptionIndex = null
    }

    private fun setupOptionClickListeners() {
        val optionViews = listOf(
            binding.tvOption1, binding.tvOption2,
            binding.tvOption3, binding.tvOption4,
            binding.tvOption5
        )

        optionViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                optionViews.forEach { it.isSelected = false }
                textView.isSelected = true
                selectedOptionIndex = index
            }
        }
    }

    private fun buildResultString(): String {
        return scoreMap.entries.joinToString(",\n") {
            val label = when (it.key) {
                FinancialType.BALANCED -> "균형형"
                FinancialType.INVESTMENT -> "공격형"
                FinancialType.CONSERVATIVE -> "저축형"
                FinancialType.CONSUMPTIVE -> "소비형"
                FinancialType.FUSION -> "융합형"
            }
            "$label: ${it.value}"
        }
    }

    private fun moveToNext(route: NavigationRoutes) {
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRoute(route)
            )
        }
    }

}
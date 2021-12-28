package com.chus.clua.breakingbad.presentation.features.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chus.clua.breakingbad.R
import com.chus.clua.breakingbad.databinding.ActivityCharacterDetailBinding
import com.chus.clua.breakingbad.presentation.base.BaseActivity
import com.chus.clua.breakingbad.presentation.extensions.hide
import com.chus.clua.breakingbad.presentation.extensions.onAnimationEndExt
import com.chus.clua.breakingbad.presentation.extensions.show
import com.chus.clua.breakingbad.presentation.models.CharacterUiModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailActivity : BaseActivity() {

    private var characterID: Long = -1
    private val detailViewModel: CharacterDetailViewModel by viewModel()
    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportPostponeEnterTransition()

        initListeners()
        observeViewModel()
        characterID = intent.getLongExtra(CHARACTER_ID, -1L)
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.onEvent(DetailEvent.Load(characterID))
    }

    private fun observeViewModel() {
        detailViewModel.viewState.observe(this, Observer(::render))
    }

    private fun render(viewState: DetailViewState) = when (viewState) {
        DetailViewState.Loading -> showLoading()
        is DetailViewState.OnLoadCharacter -> onLoad(viewState)
        is DetailViewState.OnError -> onError(viewState.message)
        is DetailViewState.OnUpdated -> onUpdated(viewState.isFavorite)
    }

    private fun onLoad(viewState: DetailViewState.OnLoadCharacter) {
        hideLoading()
        bindDetail(viewState.character)
    }

    private fun onError(message: String?) {
        hideLoading()
        showToast(message)
    }

    private fun onUpdated(isFavorite: Boolean) {
        if (isFavorite) startFavoriteAnimation()
        bindFabIcon(isFavorite)
    }

    private fun showLoading() = binding.progress.show()

    private fun hideLoading() = binding.progress.hide()

    private fun bindDetail(characterUiModel: CharacterUiModel?) {
        bindNickName(characterUiModel?.nickname)
        bindPortrayedName(characterUiModel?.portrayed)
        bindAvatar(characterUiModel?.img)
        bindName(characterUiModel?.name)
        bindAges(characterUiModel?.ages)
        bindStatus(characterUiModel?.status)
        bindOccupations(characterUiModel?.occupation)
        bindBreakingBadAppearance(characterUiModel?.appearance)
        bindBetterCallSaulAppearance(characterUiModel?.betterCallSaulAppearance)
        bindFabIcon(characterUiModel?.isFavorite)
    }

    private fun bindNickName(nickName: String?) {
        binding.contentDetail.detailHeaderContent.nickName.text = nickName
    }

    private fun bindPortrayedName(portrayedName: String?) {
        binding.contentDetail.detailHeaderContent.portrayedName.text = portrayedName
    }

    private fun bindAvatar(avatar: String?) =
        Glide.with(this)
            .load(avatar)
            .apply(RequestOptions.circleCropTransform())
            .error(R.drawable.ic_heisenberg)
            .into(binding.contentDetail.detailHeaderContent.characterImage).apply {
                supportStartPostponedEnterTransition()
            }

    private fun bindName(name: String?) {
        binding.contentDetail.detailDataContent.charName.text = name
    }

    private fun bindAges(ages: String?) {
        binding.contentDetail.detailDataContent.charAges.text = ages
    }

    private fun bindStatus(status: String?) {
        binding.contentDetail.detailDataContent.charStatus.text = status
    }

    private fun bindOccupations(occupations: String?) {
        binding.contentDetail.detailDataContent.charOccupations.text = occupations
    }

    private fun bindBreakingBadAppearance(appearance: String?) {
        if (!appearance.isNullOrEmpty()) {
            binding.contentDetail.detailDataContent.charBreakingAppearance.show()
            binding.contentDetail.detailDataContent.charBreakingAppearanceLabel.show()
            binding.contentDetail.detailDataContent.charBreakingAppearance.text = appearance
        }
    }

    private fun bindBetterCallSaulAppearance(appearance: String?) {
        if (!appearance.isNullOrEmpty()) {
            binding.contentDetail.detailDataContent.charBetterAppearance.show()
            binding.contentDetail.detailDataContent.charBetterAppearanceLabel.show()
            binding.contentDetail.detailDataContent.charBetterAppearance.text = appearance
        }
    }

    private fun bindFabIcon(favorite: Boolean?) = when (favorite) {
        true -> binding.fab.setImageResource(R.drawable.ic_favorites_filled)
        else -> binding.fab.setImageResource(R.drawable.ic_favorites)
    }

    private fun startFavoriteAnimation() {
        binding.contentDetail.detailHeaderContent.favoriteAnimation.show()
        binding.contentDetail.detailHeaderContent.favoriteAnimation.playAnimation()
        binding.contentDetail.detailHeaderContent.favoriteAnimation onAnimationEndExt { binding.contentDetail.detailHeaderContent.favoriteAnimation.hide() }
    }

    private fun initListeners() {
        binding.contentDetail.detailHeaderContent.backButton.setOnClickListener { onBackPressed() }
        binding.fab.setOnClickListener {
            detailViewModel.onEvent(DetailEvent.ToggleFavorite(characterID))
        }
    }

    companion object {
        private const val CHARACTER_ID = "character_id"
        fun start(context: Activity?, characterID: Long?, imageView: AppCompatImageView) {
            if (context == null || characterID == null) return
            Intent(context, CharacterDetailActivity::class.java).apply {
                putExtra(CHARACTER_ID, characterID)
            }.run {
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context, imageView, ViewCompat.getTransitionName(imageView)!!
                ).let { context.startActivity(this, it.toBundle()) }
            }
        }
    }
}
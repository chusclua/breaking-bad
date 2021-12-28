package com.chus.clua.breakingbad.presentation.features.charactersLists

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chus.clua.breakingbad.R
import com.chus.clua.breakingbad.databinding.ActivityMainBinding
import com.chus.clua.breakingbad.presentation.base.BaseActivity
import com.chus.clua.breakingbad.presentation.extensions.hide
import com.chus.clua.breakingbad.presentation.extensions.show
import com.chus.clua.breakingbad.presentation.features.charactersLists.fragments.BetterCallSaulCharactersFragment
import com.chus.clua.breakingbad.presentation.features.charactersLists.fragments.BreakingBadCharactersFragment
import com.chus.clua.breakingbad.presentation.features.charactersLists.fragments.FavoriteCharactersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val bbFragment: BreakingBadCharactersFragment by lazy { BreakingBadCharactersFragment.newInstance() }
    private val bcsFragment: BetterCallSaulCharactersFragment by lazy { BetterCallSaulCharactersFragment.newInstance() }
    private val fFragment: FavoriteCharactersFragment by lazy { FavoriteCharactersFragment.newInstance() }

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        initBottomNavigation()
        observeViewModel()
    }

    private fun observeViewModel() {
        mainViewModel.viewState.observe(this, Observer(::render))
    }

    private fun render(viewState: MainViewState) = when (viewState) {
        MainViewState.Loading -> showLoading()
        is MainViewState.OnLoadBetterCallSaulCharacters,
        is MainViewState.OnLoadBreakingCharacters,
        is MainViewState.OnLoadFavoritesCharacters -> hideLoading()
        is MainViewState.OnError -> {
            hideLoading()
            showToast(viewState.message)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.breaking_bad_list -> {
                setMainTitle(R.string.breaking_bad_title)
                replaceFragment(bbFragment)
            }
            R.id.better_call_saul_list -> {
                setMainTitle(R.string.better_call_saul_title)
                replaceFragment(bcsFragment)
            }
            R.id.favorites_list -> {
                setMainTitle(R.string.favorites)
                replaceFragment(fFragment)
            }
        }
        return true
    }

    private fun showLoading() = binding.progress.show()

    private fun hideLoading() = binding.progress.hide()

    private fun setMainTitle(@StringRes resTitle: Int) {
        title = getString(resTitle)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(FRAGMENT_ID, binding.bottomNavigation.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { bundle ->
            binding.bottomNavigation.selectedItemId = bundle.getInt(FRAGMENT_ID)
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.apply {
            isSaveEnabled = false
            setOnNavigationItemSelectedListener(this@MainActivity)
            selectedItemId = R.id.breaking_bad_list
        }
    }

    companion object {
        private const val FRAGMENT_ID = "fragment_id"
        fun start(context: Activity) {
            Intent(context, MainActivity::class.java).run {
                context.startActivity(this)
                context.onBackPressed()
            }
        }
    }

}
package com.chus.clua.breakingbad.presentation.features.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import com.chus.clua.breakingbad.databinding.ActivitySplashBinding
import com.chus.clua.breakingbad.presentation.base.BaseActivity
import com.chus.clua.breakingbad.presentation.features.charactersLists.MainActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()

    }

    private fun startAnimation() {
        binding.splashView.animate()
            .alpha(1f)
            .scaleXBy(1f)
            .scaleYBy(1f)
            .setDuration(3000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    MainActivity.start(this@SplashActivity)
                }
            })
    }
}

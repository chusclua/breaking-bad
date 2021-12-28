package com.chus.clua.breakingbad.presentation.extensions

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView


infix fun LottieAnimationView.onAnimationEndExt(block: () -> Unit) {
    this.addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
            block()
        }

        override fun onAnimationCancel(p0: Animator?) {

        }

        override fun onAnimationStart(p0: Animator?) {

        }

    })
}
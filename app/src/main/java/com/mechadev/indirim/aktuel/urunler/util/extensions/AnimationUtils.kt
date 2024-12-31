package com.mechadev.indirim.aktuel.urunler.util.extensions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.widget.TextView

fun TextView.changeTextWithAnimation(newText: String) {
    val fadeOut = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
    fadeOut.setDuration(500)
    fadeOut.startDelay = 1000

    fadeOut.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {}
        override fun onAnimationEnd(animator: Animator) {
            this@changeTextWithAnimation.text = newText
            val fadeIn = ObjectAnimator.ofFloat(this@changeTextWithAnimation, "alpha", 0f, 1f)
            fadeIn.setDuration(200)
            fadeIn.start()
        }
        override fun onAnimationCancel(animator: Animator) {}
        override fun onAnimationRepeat(animator: Animator) {}
    })
    fadeOut.start()
}

fun TextView.changeTextWithoutAnimation(newText: String) {
    val fadeOut = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
    fadeOut.setDuration(100)

    fadeOut.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {}
        override fun onAnimationEnd(animator: Animator) {
            this@changeTextWithoutAnimation.text = newText
            val fadeIn = ObjectAnimator.ofFloat(this@changeTextWithoutAnimation, "alpha", 0f, 1f)
            fadeIn.setDuration(200)
            fadeIn.start()
        }
        override fun onAnimationCancel(animator: Animator) {}
        override fun onAnimationRepeat(animator: Animator) {}
    })
    fadeOut.start()
}

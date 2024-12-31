package com.mechadev.indirim.aktuel.urunler.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import android.widget.RelativeLayout
import kotlin.math.max


class RevealAnimation(private val mView: View, intent: Intent, private val mActivity: Activity) {
    private var revealX = 0
    private var revealY = 0
    private var startRevealX = 0
    private var startRevealY = 0

    init {
        if (intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) && intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            mView.visibility = View.INVISIBLE

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)

            startRevealX = intent.getIntExtra(START_REVEAL_X, 0)
            startRevealY = intent.getIntExtra(START_REVEAL_Y, 0)

            if (startRevealX == 0) {
                startRevealX = revealX
            }

            if (startRevealY == 0) {
                startRevealY = revealY
            }

            val viewTreeObserver = mView.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        revealActivity(revealX, revealY)
                        mView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        } else {
            mView.visibility = View.VISIBLE
        }
    }

    fun revealActivity(x: Int, y: Int) {
        val finalRadius = (max(mView.width.toDouble(), mView.height.toDouble()) * 1.1).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(mView, x, y, 0f, finalRadius)
        circularReveal.setDuration(300)
        circularReveal.interpolator = AccelerateInterpolator()
        mView.visibility = View.VISIBLE
        circularReveal.start()
    }

    fun unRevealActivity() {
        val finalRadius = (max(mView.width.toDouble(), mView.height.toDouble()) * 1.1).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            mView, startRevealX, startRevealY, finalRadius, 0f
        )

        circularReveal.setDuration(300)
        circularReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mView.visibility = View.INVISIBLE
                mActivity.finish()
                mActivity.overridePendingTransition(0, 0)
            }
        })

        circularReveal.start()
    }

    fun startColorAnimation(view: View, startColor: Int, endColor: Int, duration: Int) {
        val anim = ValueAnimator()
        anim.setIntValues(startColor, endColor)
        anim.setEvaluator(ArgbEvaluator())
        anim.addUpdateListener { valueAnimator: ValueAnimator ->
            view.setBackgroundColor(
                (valueAnimator.animatedValue as Int)
            )
        }
        anim.setDuration(duration.toLong())
        anim.start()
    }

    companion object {
        const val EXTRA_CIRCULAR_REVEAL_X: String = "EXTRA_CIRCULAR_REVEAL_X"
        const val EXTRA_CIRCULAR_REVEAL_Y: String = "EXTRA_CIRCULAR_REVEAL_Y"
        const val START_REVEAL_X: String = "START_REVEAL_X"
        const val START_REVEAL_Y: String = "START_REVEAL_Y"
    }
}

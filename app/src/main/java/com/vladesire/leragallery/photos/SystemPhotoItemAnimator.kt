package com.vladesire.leragallery.photos

import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo

class SystemPhotoItemAnimator : DefaultItemAnimator() {
    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo
    ): Boolean {

        val holder = newHolder as PhotoViewHolder

        if (preLayoutInfo is SystemPhotoItemHolderInfo) {
            if (preLayoutInfo.saved) {
                // TODO: reverse animation
            } else {
                // TODO: Start animation

                holder.binding.image.animation = RotateAnimation(0f, 45f).apply {
                    setAnimationListener(object : Animation.AnimationListener{
                        override fun onAnimationStart(p0: Animation?) {}
                        override fun onAnimationRepeat(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            Log.d("Animation", "Animation Ended")
                            holder.binding.image.rotation = 45f
                            dispatchAnimationFinished(holder)
                        }
                    })
                    duration = 3000
                    start()
                }
            }

            return true
        }

        return super.animateChange(oldHolder, newHolder, preLayoutInfo, postLayoutInfo)
    }

    // Cross-fade animation is not going to be used
    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder) = true
    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder, payloads: MutableList<Any>) = true

    class SystemPhotoItemHolderInfo(val saved: Boolean) : ItemHolderInfo()

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {

        if (changeFlags == FLAG_CHANGED) {

            for (payload in payloads) {
                if (payload as? Int == SAVED_ANIMATION) {
                    // TODO: check if animation in progress

                    return SystemPhotoItemHolderInfo(false)
                }
            }

        }

        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    companion object {
        const val SAVED_ANIMATION = 1
    }
}

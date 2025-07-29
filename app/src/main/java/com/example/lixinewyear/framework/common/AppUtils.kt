package com.example.lixinewyear.framework.common

import android.content.Context

class AppUtils {

    companion object {
        fun dpToPx(context: Context, dp: Float): Float {
            val density = context.resources.displayMetrics.density
            return dp * density
        }
    }

}
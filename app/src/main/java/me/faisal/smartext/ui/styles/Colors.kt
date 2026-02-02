package me.faisal.smartext.ui.styles

import android.graphics.Color

val me.faisal.smartext.domain.ProcessingState.color: Int
    get() = when (this) {
        me.faisal.smartext.domain.ProcessingState.Pending -> Color.parseColor("#FFBB86FC")
        me.faisal.smartext.domain.ProcessingState.Processed -> Color.parseColor("#FF6200EE")
        me.faisal.smartext.domain.ProcessingState.Sent -> Color.parseColor("#FF3700B3")
        me.faisal.smartext.domain.ProcessingState.Delivered -> Color.parseColor("#FF03DAC5")
        me.faisal.smartext.domain.ProcessingState.Failed -> Color.parseColor("#FF018786")
    }
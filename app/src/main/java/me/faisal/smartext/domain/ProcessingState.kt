package me.faisal.smartext.domain

enum class ProcessingState {
    Pending,
    Processed,
    Sent,
    Delivered,
    Failed
}
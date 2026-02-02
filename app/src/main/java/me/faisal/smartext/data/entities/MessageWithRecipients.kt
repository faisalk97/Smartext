package me.faisal.smartext.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class MessageWithRecipients(
    @Embedded val message: Message,
    @Relation(
        parentColumn = "id",
        entityColumn = "messageId",
    )
    val recipients: List<MessageRecipient>,
    @Relation(
        parentColumn = "id",
        entityColumn = "messageId",
    )
    val states: List<MessageState> = emptyList(),
    @ColumnInfo(name = "rowid")
    val rowId: Long = 0,
) {
    val state: me.faisal.smartext.domain.ProcessingState
        get() = when {
            recipients.any { it.state == me.faisal.smartext.domain.ProcessingState.Pending } -> me.faisal.smartext.domain.ProcessingState.Pending
            recipients.any { it.state == me.faisal.smartext.domain.ProcessingState.Processed } -> me.faisal.smartext.domain.ProcessingState.Processed

            recipients.all { it.state == me.faisal.smartext.domain.ProcessingState.Failed } -> me.faisal.smartext.domain.ProcessingState.Failed
            recipients.all { it.state == me.faisal.smartext.domain.ProcessingState.Delivered } -> me.faisal.smartext.domain.ProcessingState.Delivered
            else -> me.faisal.smartext.domain.ProcessingState.Sent
        }
}

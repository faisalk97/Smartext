package me.faisal.smartext.modules.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import com.google.gson.GsonBuilder
import me.faisal.smartext.data.dao.MessagesDao
import me.faisal.smartext.data.entities.Message
import me.faisal.smartext.data.entities.MessageRecipient
import me.faisal.smartext.data.entities.MessageType
import me.faisal.smartext.data.entities.MessageWithRecipients
import me.faisal.smartext.data.entities.MessagesTotals
import me.faisal.smartext.domain.MessageContent
import me.faisal.smartext.domain.ProcessingState
import me.faisal.smartext.modules.messages.data.SendParams
import me.faisal.smartext.modules.messages.data.SendRequest
import me.faisal.smartext.modules.messages.data.StoredSendRequest
import java.util.Date

class MessagesRepository(private val dao: MessagesDao) {
    private val gson = GsonBuilder().serializeNulls().create()

    fun selectLast(limit: Int) = dao.selectLast(limit).distinctUntilChanged()

    val messagesTotals: LiveData<MessagesTotals> = dao.getMessagesStats().distinctUntilChanged()

    fun get(id: String): StoredSendRequest {
        return dao.get(id)?.toRequest()
            ?: throw IllegalArgumentException("Message with id $id not found")
    }

    fun enqueue(request: SendRequest) {
        val message = MessageWithRecipients(
            Message(
                id = request.message.id,
                type = when (request.message.content) {
                    is MessageContent.Text -> MessageType.Text
                    is MessageContent.Data -> MessageType.Data
                },
                content = gson.toJson(request.message.content),
                withDeliveryReport = request.params.withDeliveryReport,
                simNumber = request.params.simNumber,
                validUntil = request.params.validUntil,
                isEncrypted = request.message.isEncrypted,
                skipPhoneValidation = request.params.skipPhoneValidation,
                priority = request.params.priority ?: Message.PRIORITY_DEFAULT,
                source = request.source,

                createdAt = request.message.createdAt.time,
            ),
            request.message.phoneNumbers.map {
                MessageRecipient(
                    request.message.id,
                    it,
                    ProcessingState.Pending
                )
            },
        )

        dao.insert(message)
    }

    fun getPending(order: MessagesSettings.ProcessingOrder): StoredSendRequest? {
        while (true) {
            val message = when (order) {
                MessagesSettings.ProcessingOrder.LIFO -> dao.getPendingLifo()
                MessagesSettings.ProcessingOrder.FIFO -> dao.getPendingFifo()
            } ?: return null

            if (message.state != ProcessingState.Pending) {
                // if for some reason stored state is not in sync with recipients state
                dao.updateMessageState(message.message.id, message.state)
                continue
            }

            return message.toRequest()
        }
    }

    private fun MessageWithRecipients.toRequest(): StoredSendRequest {
        val message = this

        return StoredSendRequest(
            id = message.rowId,
            state = message.state,
            recipients = this.recipients,
            message.message.source,
            me.faisal.smartext.modules.messages.data.Message(
                id = message.message.id,
                content = when (message.message.type) {
                    MessageType.Text -> gson.fromJson(
                        message.message.content,
                        MessageContent.Text::class.java
                    )

                    MessageType.Data -> gson.fromJson(
                        message.message.content,
                        MessageContent.Data::class.java
                    )
                },
                phoneNumbers = message.recipients.filter { it.state == ProcessingState.Pending }
                    .map { it.phoneNumber },
                isEncrypted = message.message.isEncrypted,
                createdAt = Date(message.message.createdAt),
            ),
            SendParams(
                withDeliveryReport = message.message.withDeliveryReport,
                skipPhoneValidation = message.message.skipPhoneValidation,
                simNumber = message.message.simNumber,
                validUntil = message.message.validUntil,
                priority = message.message.priority
            ),
        )
    }
}
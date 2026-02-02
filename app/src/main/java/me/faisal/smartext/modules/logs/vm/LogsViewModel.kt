package me.faisal.smartext.modules.logs.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.faisal.smartext.modules.logs.db.LogEntry
import me.faisal.smartext.modules.logs.repositories.LogsRepository

class LogsViewModel(
    logs: LogsRepository
) : ViewModel() {
    val lastEntries: LiveData<List<LogEntry>> = logs.lastEntries
}
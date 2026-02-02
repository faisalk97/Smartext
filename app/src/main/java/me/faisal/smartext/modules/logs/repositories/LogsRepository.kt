package me.faisal.smartext.modules.logs.repositories

import androidx.lifecycle.distinctUntilChanged
import me.faisal.smartext.modules.logs.db.LogEntriesDao

class LogsRepository(
    private val dao: LogEntriesDao
) {
    val lastEntries = dao.selectLast().distinctUntilChanged()
}
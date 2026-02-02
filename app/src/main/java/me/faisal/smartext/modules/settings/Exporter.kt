package me.faisal.smartext.modules.settings

interface Exporter {
    fun export(): Map<String, *>
}
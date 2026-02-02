package me.faisal.smartext.modules.settings

interface Importer {
    fun import(data: Map<String, *>): Boolean
}
package me.faisal.smartext.providers

interface IPProvider {
    suspend fun getIP(): String?
}
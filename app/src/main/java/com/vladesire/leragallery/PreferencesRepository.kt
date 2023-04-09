package com.vladesire.leragallery

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PreferencesRepository private constructor(
    private val dataStore: DataStore<Preferences>
){
    val name: Flow<String> = dataStore.data.map {
        it[PREF_USER_NAME] ?: ""
    }.distinctUntilChanged()

    suspend fun setName(name: String) {
        dataStore.edit {
            it[PREF_USER_NAME] = name
        }
    }

    companion object {
        private val PREF_USER_NAME = stringPreferencesKey("name")

        private var INSTANCE: PreferencesRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile("settings")
                }
                INSTANCE = PreferencesRepository(dataStore)
            }
        }
        fun get(): PreferencesRepository {
            return INSTANCE ?: throw IllegalStateException(
                "PreferencesRepository must be initialized"
            )
        }
    }
}
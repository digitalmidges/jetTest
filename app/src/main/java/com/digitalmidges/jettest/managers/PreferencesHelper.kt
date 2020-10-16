package com.digitalmidges.jettest.managers

import android.content.Context
import android.content.SharedPreferences
import com.digitalmidges.jettest.BaseApplication
import com.digitalmidges.jettest.BuildConfig

object PreferencesHelper {


    //=============================================================================//
    //=============================================================================//
    //=============================================================================//


    private const val SHARED_PREFS = BuildConfig.APPLICATION_ID + " Prefs"


    private var sharedPreferences: SharedPreferences = BaseApplication.instance
        .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    private fun save(key: String, value: Any?) {
        val editor = sharedPreferences.edit()
        when {
            value is Boolean -> editor.putBoolean(key, (value as Boolean?)!!)
            value is Int -> editor.putInt(key, (value as Int?)!!)
            value is Float -> editor.putFloat(key, (value as Float?)!!)
            value is Long -> editor.putLong(key, (value as Long?)!!)
            value is String -> editor.putString(key, value as String?)
            value is Enum<*> -> editor.putString(key, value.toString())
            value != null -> throw RuntimeException("Attempting to save non-supported preference")
        }
        editor.apply()
    }


    @Suppress("UNCHECKED_CAST")
    private  operator fun <T> get(key: String): T {
        return sharedPreferences.all[key] as T
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun <T> get(key: String, defValue: T): T {
        val returnValue = sharedPreferences.all[key] as T
        return returnValue ?: defValue
    }

    private fun has(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    private fun delete(key: String) {
        if (sharedPreferences.contains(key)) {
            getEditor().remove(key).commit()
        }
    }

    private fun getEditor(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    //=============================================================================//
    fun deleteSharedPreferences() {
        val sharedPreferences = BaseApplication.instance.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .clear()
            .apply()
    }
    //=============================================================================//






    //=============================================================================//
    //=============================================================================//
    //=============================================================================//

}
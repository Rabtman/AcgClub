package com.rabtman.common.integration

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import java.util.*

class ManifestParser(context: Application) {
    companion object {
        private const val MODULE_VALUE = "ConfigModule"

        private fun parseModule(className: String): ConfigModule {
            val clazz: Class<*> = try {
                Class.forName(className)
            } catch (e: ClassNotFoundException) {
                throw IllegalArgumentException("Unable to find ConfigModule implementation", e)
            }
            val module: Any = try {
                clazz.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException(
                    "Unable to instantiate ConfigModule implementation for $clazz",
                    e
                )
            } catch (e: IllegalAccessException) {
                throw RuntimeException(
                    "Unable to instantiate ConfigModule implementation for $clazz",
                    e
                )
            }
            if (module !is ConfigModule) {
                throw RuntimeException("Expected instanceof ConfigModule, but found: $module")
            }
            return module
        }
    }

    private val context: Context

    init {
        this.context = context
    }

    fun parse(): List<ConfigModule> {
        val modules: MutableList<ConfigModule> = ArrayList()
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName, PackageManager.GET_META_DATA
            )
            if (appInfo.metaData != null) {
                for (key in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData[key]) {
                        modules.add(parseModule(key))
                    }
                }
            }
        } catch (e: NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse ConfigModule", e)
        }
        return modules
    }
}
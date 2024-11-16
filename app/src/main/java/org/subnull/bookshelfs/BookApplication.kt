package org.subnull.bookshelfs

import android.app.Application
import org.subnull.bookshelfs.data.AppContainer
import org.subnull.bookshelfs.data.DefaultAppContainer

class BookApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
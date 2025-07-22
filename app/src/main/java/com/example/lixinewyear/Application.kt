package com.example.lixinewyear

import com.example.lixinewyear.framework.appModule
import com.example.lixinewyear.framework.common.localehelper.LocaleAwareApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: LocaleAwareApplication() {

    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }



    }

}
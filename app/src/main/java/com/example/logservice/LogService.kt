package com.example.logservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

import com.example.aidl.ILogAIDLInterface
import java.text.SimpleDateFormat
import java.util.*

class LogService : Service() {

    val TAG = LogService::class.java.simpleName

    override fun onBind(intent: Intent?): IBinder {

        return object : ILogAIDLInterface.Stub() {

            override fun log(loglevel: String, message: String) {
                    val head  = Date(System.currentTimeMillis()).formatToServerTimeDefaults()+"====>"
                when (loglevel) {
                        "v" -> {
                            Log.v(TAG, head+message)
                        }
                        "d" -> {
                            Log.d(TAG, head+message)
                        }
                        "i" -> {
                            Log.i(TAG, head+message)
                        }
                        "w" -> {
                            Log.w(TAG, head+message)
                        }
                        else -> Log.e(TAG, head+message)
                    }
            }
        }
    }


}

fun Date.formatToServerTimeDefaults(): String{
    val sdf= SimpleDateFormat("yy.MM.DD.hh.mm.ss", Locale.getDefault())
    return sdf.format(this)
}

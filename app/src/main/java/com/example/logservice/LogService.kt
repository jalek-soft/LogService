package com.example.logservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

import com.example.aidl.ILogAIDLInterface

class LogService : Service() {

    val TAG = LogService::class.java.simpleName

    override fun onBind(intent: Intent?): IBinder {
        return object : ILogAIDLInterface.Stub() {
            override fun log(loglevel: String, message: String) {
                    when (loglevel) {
                        "v" -> {
                            Log.v(TAG, message)
                        }
                        "d" -> {
                            Log.d(TAG, message)
                        }
                        "i" -> {
                            Log.i(TAG, message)
                        }
                        "w" -> {
                            Log.w(TAG, message)
                        }
                        else -> Log.e(TAG, message)
                    }
            }
        }
    }
}
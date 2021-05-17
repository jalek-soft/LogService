package com.example.client_app

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.aidl.ILogAIDLInterface

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
private var logData: ILogAIDLInterface? = null

private val serviceConnection = object : ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        logData = ILogAIDLInterface.Stub.asInterface(service)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        logData = null
    }
}

override fun onStart() {
    super.onStart()
    bindService(createExplicitIntent(), serviceConnection, Context.BIND_AUTO_CREATE)
}

override fun onStop() {
    super.onStop()
    unbindService(serviceConnection)
}
}

private fun Activity.createExplicitIntent(): Intent {
    val intent = Intent("com.example.aidl.REMOTE_CONNECTION")
    val services = packageManager.queryIntentServices(intent, 0)
    if (services.isEmpty()) {
        throw IllegalStateException("Server app not found")
    }
    return Intent(intent).apply {
        val resolveInfo = services[0]
        val packageName = resolveInfo.serviceInfo.packageName
        val className = resolveInfo.serviceInfo.name
        component = ComponentName(packageName, className)
    }
}
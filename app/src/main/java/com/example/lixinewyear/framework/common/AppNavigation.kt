package com.example.lixinewyear.framework.common

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lixinewyear.framework.base.BaseActivity
import com.example.lixinewyear.presentation.InputDataMoney.InputDataMoneyActivity
import com.google.android.material.snackbar.Snackbar

object AppNavigation {

    private fun startActivity(
        context: Context,
        cl: Class<*>,
        bundle: Bundle? = null
    ) {
        val intent = Intent(context, cl)
        bundle?.let {
            intent.putExtras(it)
        }
        context.startActivity(intent, bundle)
    }

    private fun startActivityForResult(
        context: Context,
        cl: Class<*>,
        startForResult: ActivityResultLauncher<Intent>,
        bundle: Bundle? = null,
    ) {
        val intent = Intent(context, cl)
        bundle?.let {
            intent.putExtras(it)
        }
        startForResult.launch(intent)
    }

    fun checkSelfPermission(
        permission: String,
        requestCode: Int,
        activity: BaseActivity<*, *>,
        explainSnack: String? = null,
        onGranted: () -> Unit
    ) {
        checkSelfPermission(permission, requestCode, activity, onGranted) {
            activity.getViewRoot()?.let { viewRoot ->
                Snackbar.make(viewRoot, explainSnack ?: permission, Snackbar.LENGTH_INDEFINITE)
                    .setAction(activity.getString(android.R.string.ok)) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", activity.packageName, null)
                        intent.data = uri
                        activity.startActivity(intent)
                    }.show()
            }
        }
    }

    fun checkSelfPermission(
        permission: String,
        requestCode: Int,
        activity: BaseActivity<*, *>,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        when {
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                onGranted()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                onDenied()
            }

            else -> {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
        }
    }

    fun checkSelfPermission(
        permission: String,
        requestPermissionLauncher: ActivityResultLauncher<String>,
        activity: BaseActivity<*, *>,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        when {
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                onGranted()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                onDenied()
            }

            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }


    fun inputDataMoneyDirection(
        context: Context,
        startForResult: ActivityResultLauncher<Intent>
    ) {
        startActivityForResult(context, InputDataMoneyActivity::class.java, startForResult)
    }

}
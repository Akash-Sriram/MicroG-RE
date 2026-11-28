/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.gcm

import android.content.Context
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.cloudmessaging.internal.ICloudMessagingService
import com.google.android.gms.cloudmessaging.internal.IRegisterCallback
import com.google.android.gms.common.Feature
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.internal.ConnectionInfo
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.android.gms.common.internal.IGmsCallbacks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.microg.gms.BaseService
import org.microg.gms.checkin.LastCheckinInfo
import org.microg.gms.common.GmsService
import org.microg.gms.gcm.GcmConstants.EXTRA_ERROR
import org.microg.gms.gcm.GcmConstants.EXTRA_REGISTRATION_ID

private const val TAG = "GmsCloudMsgSvc"

class CloudMessagingService : BaseService(TAG, GmsService.CLOUDMESSAGING) {

    private val supportedFeatures = arrayOf(
        Feature("cloud_messaging", 1L)
    )

    private lateinit var database: GcmDatabase

    override fun onCreate() {
        super.onCreate()
        database = GcmDatabase(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    override fun handleServiceRequest(
        callback: IGmsCallbacks,
        request: GetServiceRequest,
        service: GmsService
    ) {
        val connectionInfo = ConnectionInfo().apply {
            features = supportedFeatures
        }
        val impl = CloudMessagingServiceImpl(this, database, request.packageName, lifecycleScope)
        callback.onPostInitCompleteWithConnectionInfo(0, impl.asBinder(), connectionInfo)
    }
}

class CloudMessagingServiceImpl(
    private val context: Context,
    private val database: GcmDatabase,
    private val callingPackage: String,
    private val scope: CoroutineScope
) : ICloudMessagingService.Stub() {

    override fun register(
        callback: IRegisterCallback?,
        pkg: String?,
        senderId: String?,
        extras: Bundle?
    ) {
        val targetPkg = pkg?.ifEmpty { null } ?: callingPackage
        Log.d(TAG, "register: pkg=$targetPkg, senderId=$senderId, extras=$extras")
        scope.launch(Dispatchers.IO) {
            try {
                ensureCheckinIsUpToDate(context)
                ensureAppRegistrationAllowed(context, database, targetPkg)
                val request = RegisterRequest()
                    .build(context)
                    .sender(senderId)
                    .checkin(LastCheckinInfo.read(context))
                    .app(targetPkg)
                    .delete(false)
                if (extras != null) {
                    request.extraParams(extras)
                }

                val resultBundle = completeRegisterRequest(context, database, request)
                val token = resultBundle.getString(EXTRA_REGISTRATION_ID)
                if (token != null) {
                    Log.d(TAG, "register success for $targetPkg: token=$token")
                    callback?.onComplete(Status.SUCCESS, token)
                } else {
                    val error = resultBundle.getString(EXTRA_ERROR) ?: "SERVICE_NOT_AVAILABLE"
                    Log.w(TAG, "register failed for $targetPkg: error=$error")
                    callback?.onComplete(Status(CommonStatusCodes.ERROR, error), null)
                }
            } catch (e: Exception) {
                Log.w(TAG, "register exception for $targetPkg", e)
                try {
                    callback?.onComplete(Status(CommonStatusCodes.ERROR, e.message), null)
                } catch (re: RemoteException) {
                    Log.w(TAG, "remote callback error", re)
                }
            }
        }
    }

    override fun unregister(
        callback: IRegisterCallback?,
        pkg: String?,
        senderId: String?,
        extras: Bundle?
    ) {
        val targetPkg = pkg?.ifEmpty { null } ?: callingPackage
        Log.d(TAG, "unregister: pkg=$targetPkg, senderId=$senderId, extras=$extras")
        scope.launch(Dispatchers.IO) {
            try {
                val request = RegisterRequest()
                    .build(context)
                    .sender(senderId)
                    .checkin(LastCheckinInfo.read(context))
                    .app(targetPkg)
                    .delete(true)
                if (extras != null) {
                    request.extraParams(extras)
                }

                val resultBundle = completeRegisterRequest(context, database, request)
                Log.d(TAG, "unregister completed for $targetPkg: $resultBundle")
                callback?.onComplete(Status.SUCCESS, null)
            } catch (e: Exception) {
                Log.w(TAG, "unregister exception for $targetPkg", e)
                try {
                    callback?.onComplete(Status(CommonStatusCodes.ERROR, e.message), null)
                } catch (re: RemoteException) {
                    Log.w(TAG, "remote callback error", re)
                }
            }
        }
    }
}

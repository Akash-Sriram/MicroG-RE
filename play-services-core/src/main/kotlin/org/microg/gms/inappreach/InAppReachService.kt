/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.inappreach

import android.os.IBinder
import android.util.Log
import com.google.android.gms.common.Feature
import com.google.android.gms.common.api.ApiMetadata
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.api.internal.IStatusCallback
import com.google.android.gms.common.internal.ConnectionInfo
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.android.gms.common.internal.IGmsCallbacks
import com.google.android.gms.inappreach.internal.IInAppReachService
import com.google.android.gms.inappreach.internal.IOnAccountDataResponseListener
import com.google.android.gms.inappreach.internal.IOnAccountHealthAlertsListener
import com.google.android.gms.inappreach.internal.IOnAccountMessagesListener
import org.microg.gms.BaseService
import org.microg.gms.common.GmsService

private const val TAG = "GmsInAppReachSvc"

class InAppReachService : BaseService(TAG, GmsService.INAPP_REACH) {

    private val supportedFeatures = arrayOf(
        Feature("account_health_alerts", 1L),
        Feature("account_messages", 1L),
        Feature("account_data_response", 1L),
        Feature("account_data_response_v2", 1L)
    )

    override fun handleServiceRequest(
        callback: IGmsCallbacks,
        request: GetServiceRequest,
        service: GmsService
    ) {
        val connectionInfo = ConnectionInfo().apply {
            features = supportedFeatures
        }
        val impl = InAppReachServiceImpl(request.packageName)
        callback.onPostInitCompleteWithConnectionInfo(0, impl.asBinder(), connectionInfo)
    }
}

class InAppReachServiceImpl(private val callingPackage: String) : IInAppReachService.Stub() {

    override fun registerHealthAlertsListener(
        callback: IStatusCallback?,
        packageName: String?,
        listener: IOnAccountHealthAlertsListener?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "registerHealthAlertsListener for $packageName (calling: $callingPackage)")
        callback?.onResult(Status.SUCCESS)
    }

    override fun checkAccountHealth(
        callback: IStatusCallback?,
        accountName: String?,
        accountType: String?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "checkAccountHealth for $accountName ($accountType)")
        callback?.onResult(Status.SUCCESS)
    }

    override fun registerAccountMessagesListener(
        callback: IStatusCallback?,
        packageName: String?,
        listener: IOnAccountMessagesListener?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "registerAccountMessagesListener for $packageName")
        callback?.onResult(Status.SUCCESS)
    }

    override fun registerDataResponseListener(
        callback: IStatusCallback?,
        packageName: String?,
        listener: IOnAccountDataResponseListener?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "registerDataResponseListener for $packageName")
        callback?.onResult(Status.SUCCESS)
    }

    override fun sendAccountData(
        callback: IStatusCallback?,
        accountName: String?,
        data: ByteArray?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "sendAccountData for $accountName (${data?.size ?: 0} bytes)")
        callback?.onResult(Status.SUCCESS)
    }

    override fun unregisterListener(
        callback: IStatusCallback?,
        packageName: String?,
        listener: IBinder?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "unregisterListener for $packageName")
        callback?.onResult(Status.SUCCESS)
    }
}

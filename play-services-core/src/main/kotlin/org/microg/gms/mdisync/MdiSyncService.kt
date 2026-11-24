// RE specific changes
/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.mdisync

import android.content.Context
import android.util.Log
import com.google.android.gms.common.Feature
import com.google.android.gms.common.api.ApiMetadata
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.internal.ConnectionInfo
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.android.gms.common.internal.IGmsCallbacks
import com.google.android.gms.mdisync.SyncOptions
import com.google.android.gms.mdisync.SyncRequest
import com.google.android.gms.mdisync.SyncResult
import com.google.android.gms.mdisync.internal.IMdiSyncCallbacks
import com.google.android.gms.mdisync.internal.IMdiSyncService
import org.microg.gms.BaseService
import org.microg.gms.common.GmsService
import java.io.File

private const val TAG = "GmsMdiSyncSvc"

private val MDISYNC_FEATURES = arrayOf(
    Feature("mdisync_gis_backend", 1),
    Feature("mdisync_profile_backend", 1)
)

class MdiSyncService : BaseService(TAG, GmsService.MDI_SYNC) {
    override fun handleServiceRequest(callback: IGmsCallbacks, request: GetServiceRequest?, service: GmsService?) {
        val accountName = request?.account?.name.orEmpty()
        Log.d(TAG, "handleServiceRequest: pkg=${request?.packageName}, account=$accountName")
        val connectionInfo = ConnectionInfo().apply { features = MDISYNC_FEATURES }
        callback.onPostInitCompleteWithConnectionInfo(0, MdiSyncServiceImpl(this, accountName).asBinder(), connectionInfo)
    }
}

internal class MdiSyncServiceImpl(
    private val context: Context,
    private val accountName: String
) : IMdiSyncService.Stub() {

    override fun sync(
        callbacks: IMdiSyncCallbacks?,
        request: SyncRequest?,
        options: SyncOptions?,
        metadata: ApiMetadata?
    ) {
        if (callbacks == null) return
        Log.d(TAG, "sync: account='$accountName', type=${request?.type}, tag='${options?.tag}'")

        try {
            val profileFile = if (accountName.isNotEmpty()) {
                File(context.filesDir, "mobstore/${context.packageName}/managed/mdisync/$accountName/profilesync/public/profile_info.pb")
            } else null

            val data = if (profileFile != null && profileFile.exists()) {
                Log.d(TAG, "sync: serving cached profile info (${profileFile.length()} bytes)")
                profileFile.readBytes()
            } else {
                request?.data ?: ByteArray(0)
            }

            callbacks.onSync(Status.SUCCESS, SyncResult(data), metadata)
        } catch (e: Exception) {
            Log.e(TAG, "sync failed", e)
            callbacks.onSync(Status(Status.INTERNAL_ERROR.statusCode, e.message), SyncResult(ByteArray(0)), metadata)
        }
    }
}

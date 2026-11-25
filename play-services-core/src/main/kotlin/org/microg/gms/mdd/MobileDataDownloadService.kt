// RE specific changes
/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.mdd

import android.content.Context
import android.util.Log
import com.google.android.gms.common.Feature
import com.google.android.gms.common.api.ApiMetadata
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.api.internal.IStatusCallback
import com.google.android.gms.common.internal.ConnectionInfo
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.android.gms.common.internal.IGmsCallbacks
import com.google.android.gms.mdd.FileGroupRequest
import com.google.android.gms.mdd.FileGroupResponse
import com.google.android.gms.mdd.FileGroupWithDownloadStatus
import com.google.android.gms.mdd.IMddFileGroupCallback
import com.google.android.gms.mdd.IMddFileGroupWithDownloadStatusCallback
import com.google.android.gms.mdd.IMobileDataDownloadService
import com.google.android.gms.mdd.OverrideFileGroupRequest
import org.microg.gms.BaseService
import org.microg.gms.common.GmsService
import org.microg.gms.common.PackageUtils

private const val TAG = "GmsMddSvc"

private val MDD_FEATURES = arrayOf(
    Feature("mdd_download_right_now", 1),
    Feature("mdd_delayed_download", 1),
)

class MobileDataDownloadService : BaseService(TAG, GmsService.MOBILE_DATA_DOWNLOAD) {
    override fun handleServiceRequest(callback: IGmsCallbacks, request: GetServiceRequest?, service: GmsService?) {
        val packageName = PackageUtils.getAndCheckCallingPackage(this, request?.packageName)
        Log.d(TAG, "handleServiceRequest: pkg=$packageName, requestPkg=${request?.packageName}")
        val connectionInfo = ConnectionInfo().apply { features = MDD_FEATURES }
        callback.onPostInitCompleteWithConnectionInfo(0, MobileDataDownloadServiceImpl(this, packageName).asBinder(), connectionInfo)
    }
}

internal class MobileDataDownloadServiceImpl(
    private val context: Context,
    private val callingPackage: String?
) : IMobileDataDownloadService.Stub() {

    override fun getFileGroup(
        callback: IMddFileGroupCallback,
        request: FileGroupRequest?,
        metadata: ApiMetadata?
    ) {
        val groupName = request?.groupName
        val ownerPackage = request?.ownerPackage ?: callingPackage.orEmpty()
        Log.d(TAG, "getFileGroup: groupName=$groupName, ownerPackage=$ownerPackage, caller=$callingPackage")
        callback.onFileGroup(Status(29500, "Group not found"), null, metadata ?: ApiMetadata.DEFAULT)
    }

    override fun clear(callback: IStatusCallback, metadata: ApiMetadata?) {
        Log.d(TAG, "clear: caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun maintenance(flag: Boolean, callback: IStatusCallback, metadata: ApiMetadata?) {
        Log.d(TAG, "maintenance: flag=$flag, caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun downloadAllPendingGroups(callback: IStatusCallback, metadata: ApiMetadata?) {
        Log.d(TAG, "downloadAllPendingGroups: caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun verifyAllPendingGroups(callback: IStatusCallback, metadata: ApiMetadata?) {
        Log.d(TAG, "verifyAllPendingGroups: caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun overrideFileGroup(
        callback: IStatusCallback,
        request: OverrideFileGroupRequest?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "overrideFileGroup: groupName=${request?.groupName}, caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun cancelAll(callback: IStatusCallback, metadata: ApiMetadata?) {
        Log.d(TAG, "cancelAll: caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun downloadPendingGroup(callback: IStatusCallback, metadata: ApiMetadata?) {
        Log.d(TAG, "downloadPendingGroup: caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun cleanup(callback: IStatusCallback, metadata: ApiMetadata?) {
        Log.d(TAG, "cleanup: caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun getFileGroupWithDownloadStatus(
        callback: IMddFileGroupWithDownloadStatusCallback,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "getFileGroupWithDownloadStatus: caller=$callingPackage")
        callback.onFileGroupWithDownloadStatus(Status.SUCCESS, emptyList(), metadata ?: ApiMetadata.DEFAULT)
    }

    override fun getFileGroupsByFilter(
        callback: IMddFileGroupCallback,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "getFileGroupsByFilter: caller=$callingPackage")
        callback.onFileGroup(Status.SUCCESS, null, metadata ?: ApiMetadata.DEFAULT)
    }

    override fun downloadFileGroup(
        callback: IStatusCallback,
        request: FileGroupRequest?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "downloadFileGroup: groupName=${request?.groupName}, caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }

    override fun scheduleFileGroupDownload(
        callback: IStatusCallback,
        request: FileGroupRequest?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "scheduleFileGroupDownload: groupName=${request?.groupName}, caller=$callingPackage")
        callback.onResult(Status.SUCCESS)
    }
}

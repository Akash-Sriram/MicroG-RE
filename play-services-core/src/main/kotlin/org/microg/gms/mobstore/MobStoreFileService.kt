// RE specific changes
/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.mobstore

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import com.google.android.gms.common.Feature
import com.google.android.gms.common.api.ApiMetadata
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.internal.ConnectionInfo
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.android.gms.common.internal.IGmsCallbacks
import com.google.android.gms.mobstore.DeleteFileRequest
import com.google.android.gms.mobstore.IMobStoreFileCallbacks
import com.google.android.gms.mobstore.IMobStoreFileService
import com.google.android.gms.mobstore.OpenFileDescriptorRequest
import com.google.android.gms.mobstore.OpenFileDescriptorResponse
import com.google.android.gms.mobstore.RenameFileRequest
import org.microg.gms.BaseService
import org.microg.gms.common.GmsService
import java.io.File

private const val TAG = "GmsMobStoreSvc"

private val MOBSTORE_FEATURES = arrayOf(
    Feature("mdd_download_right_now", 1),
    Feature("mdd_delayed_download", 1),
    Feature("mobstore_write_api", 1),
    Feature("mobstore_rename", 1),
    Feature("icing_get_document", 1)
)

class MobStoreFileService : BaseService(TAG, GmsService.MOBSTORE_FILE) {
    override fun handleServiceRequest(callback: IGmsCallbacks, request: GetServiceRequest?, service: GmsService?) {
        Log.d(TAG, "handleServiceRequest: pkg=${request?.packageName}")
        val connectionInfo = ConnectionInfo().apply { features = MOBSTORE_FEATURES }
        callback.onPostInitCompleteWithConnectionInfo(0, MobStoreFileServiceImpl(this).asBinder(), connectionInfo)
    }
}

internal class MobStoreFileServiceImpl(private val context: Context) : IMobStoreFileService.Stub() {

    private fun resolveFile(uri: Uri?): File? {
        if (uri == null) return null
        val authority = uri.authority ?: return null
        val path = uri.path ?: return null
        val cleanPath = path.trimStart('/')
        if (cleanPath.contains("..")) {
            Log.w(TAG, "Directory traversal path rejected: $path")
            return null
        }
        val baseDir = File(context.filesDir, "mobstore/$authority")
        val target = File(baseDir, cleanPath)
        return try {
            if (target.canonicalPath.startsWith(baseDir.canonicalPath)) target else null
        } catch (_: Exception) {
            null
        }
    }

    override fun openFile(
        callbacks: IMobStoreFileCallbacks,
        request: OpenFileDescriptorRequest?,
        metadata: ApiMetadata?
    ) {
        val uri = request?.uri
        Log.d(TAG, "openFile: uri=$uri, mode=${request?.mode}")
        if (uri == null) {
            callbacks.onOpenFile(Status(33500, "Invalid or null URI"), null, metadata)
            return
        }

        val file = resolveFile(uri)
        if (file == null) {
            callbacks.onOpenFile(Status(33500, "Unable to resolve file path for: $uri"), null, metadata)
            return
        }

        try {
            when (request.mode) {
                0 -> {
                    // Mode 0: Read
                    if (!file.exists()) {
                        Log.d(TAG, "openFile: file not found for read: $uri")
                        callbacks.onOpenFile(Status(33500, "File not found: $uri"), null, metadata)
                        return
                    }
                    val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                    Log.d(TAG, "openFile: returning read pfd for $uri")
                    callbacks.onOpenFile(Status.SUCCESS, OpenFileDescriptorResponse(pfd), metadata)
                }
                1 -> {
                    // Mode 1: Write / Create
                    file.parentFile?.mkdirs()
                    val pfd = ParcelFileDescriptor.open(
                        file,
                        ParcelFileDescriptor.MODE_READ_WRITE or ParcelFileDescriptor.MODE_CREATE
                    )
                    Log.d(TAG, "openFile: returning write/create pfd for $uri")
                    callbacks.onOpenFile(Status.SUCCESS, OpenFileDescriptorResponse(pfd), metadata)
                }
                else -> {
                    callbacks.onOpenFile(Status(Status.INTERNAL_ERROR.statusCode, "Unsupported mode: ${request.mode}"), null, metadata)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "openFile failed for $uri", e)
            callbacks.onOpenFile(Status(Status.INTERNAL_ERROR.statusCode, e.message), null, metadata)
        }
    }

    override fun deleteFile(
        callbacks: IMobStoreFileCallbacks,
        request: DeleteFileRequest?,
        metadata: ApiMetadata?
    ) {
        val uri = request?.uri
        Log.d(TAG, "deleteFile: uri=$uri")
        if (uri != null) {
            val file = resolveFile(uri)
            if (file != null && file.exists()) {
                file.delete()
            }
        }
        callbacks.onDeleteFile(Status.SUCCESS, metadata)
    }

    override fun renameFile(
        callbacks: IMobStoreFileCallbacks,
        request: RenameFileRequest?,
        metadata: ApiMetadata?
    ) {
        Log.d(TAG, "renameFile: src=${request?.srcUri}, dst=${request?.dstUri}")
        val src = resolveFile(request?.srcUri)
        val dst = resolveFile(request?.dstUri)
        if (src != null && dst != null && src.exists()) {
            dst.parentFile?.mkdirs()
            src.renameTo(dst)
        }
        callbacks.onRenameFile(Status.SUCCESS, metadata)
    }
}

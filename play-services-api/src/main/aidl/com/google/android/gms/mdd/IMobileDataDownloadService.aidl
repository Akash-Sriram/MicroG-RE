/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdd;

import com.google.android.gms.common.api.ApiMetadata;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.mdd.FileGroupRequest;
import com.google.android.gms.mdd.IMddFileGroupCallback;
import com.google.android.gms.mdd.IMddFileGroupWithDownloadStatusCallback;
import com.google.android.gms.mdd.OverrideFileGroupRequest;

interface IMobileDataDownloadService {
    void getFileGroup(IMddFileGroupCallback callback, in FileGroupRequest request, in ApiMetadata metadata) = 0;
    void clear(IStatusCallback callback, in ApiMetadata metadata) = 1;
    void maintenance(boolean flag, IStatusCallback callback, in ApiMetadata metadata) = 2;
    void downloadAllPendingGroups(IStatusCallback callback, in ApiMetadata metadata) = 3;
    void verifyAllPendingGroups(IStatusCallback callback, in ApiMetadata metadata) = 4;
    void overrideFileGroup(IStatusCallback callback, in OverrideFileGroupRequest request, in ApiMetadata metadata) = 5;
    void cancelAll(IStatusCallback callback, in ApiMetadata metadata) = 6;
    void downloadPendingGroup(IStatusCallback callback, in ApiMetadata metadata) = 7;
    void cleanup(IStatusCallback callback, in ApiMetadata metadata) = 8;
    void getFileGroupWithDownloadStatus(IMddFileGroupWithDownloadStatusCallback callback, in ApiMetadata metadata) = 9;
    void getFileGroupsByFilter(IMddFileGroupCallback callback, in ApiMetadata metadata) = 10;
    void downloadFileGroup(IStatusCallback callback, in FileGroupRequest request, in ApiMetadata metadata) = 11;
    void scheduleFileGroupDownload(IStatusCallback callback, in FileGroupRequest request, in ApiMetadata metadata) = 12;
}

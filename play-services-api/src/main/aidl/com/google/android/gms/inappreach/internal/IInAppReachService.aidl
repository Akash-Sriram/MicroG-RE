/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.inappreach.internal;

import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.ApiMetadata;
import com.google.android.gms.inappreach.internal.IOnAccountHealthAlertsListener;
import com.google.android.gms.inappreach.internal.IOnAccountMessagesListener;
import com.google.android.gms.inappreach.internal.IOnAccountDataResponseListener;

interface IInAppReachService {
    oneway void registerHealthAlertsListener(IStatusCallback callback, String packageName, IOnAccountHealthAlertsListener listener, in ApiMetadata metadata) = 0;
    oneway void checkAccountHealth(IStatusCallback callback, String accountName, String accountType, in ApiMetadata metadata) = 3;
    oneway void registerAccountMessagesListener(IStatusCallback callback, String packageName, IOnAccountMessagesListener listener, in ApiMetadata metadata) = 6;
    oneway void registerDataResponseListener(IStatusCallback callback, String packageName, IOnAccountDataResponseListener listener, in ApiMetadata metadata) = 10;
    oneway void sendAccountData(IStatusCallback callback, String accountName, in byte[] data, in ApiMetadata metadata) = 12;
    oneway void unregisterListener(IStatusCallback callback, String packageName, IBinder listener, in ApiMetadata metadata) = 14;
}

package com.google.android.gms.mdisync.internal;

import com.google.android.gms.mdisync.internal.IMdiSyncCallbacks;
import com.google.android.gms.mdisync.SyncRequest;
import com.google.android.gms.mdisync.SyncOptions;
import com.google.android.gms.common.api.ApiMetadata;

interface IMdiSyncService {
    oneway void sync(IMdiSyncCallbacks callbacks, in SyncRequest request, in SyncOptions options, in ApiMetadata metadata) = 0;
}

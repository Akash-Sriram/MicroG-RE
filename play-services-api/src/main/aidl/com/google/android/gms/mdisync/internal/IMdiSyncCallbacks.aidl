package com.google.android.gms.mdisync.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.mdisync.SyncResult;
import com.google.android.gms.common.api.ApiMetadata;

interface IMdiSyncCallbacks {
    oneway void onSync(in Status status, in SyncResult result, in ApiMetadata metadata) = 0;
}

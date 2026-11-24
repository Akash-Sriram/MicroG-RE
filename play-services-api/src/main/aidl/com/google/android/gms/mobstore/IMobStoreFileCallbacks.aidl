package com.google.android.gms.mobstore;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.mobstore.OpenFileDescriptorResponse;
import com.google.android.gms.common.api.ApiMetadata;

interface IMobStoreFileCallbacks {
    oneway void onOpenFile(in Status status, in OpenFileDescriptorResponse response, in ApiMetadata metadata) = 0;
    oneway void onDeleteFile(in Status status, in ApiMetadata metadata) = 1;
    oneway void onRenameFile(in Status status, in ApiMetadata metadata) = 2;
}

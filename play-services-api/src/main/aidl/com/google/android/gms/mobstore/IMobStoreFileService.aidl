package com.google.android.gms.mobstore;

import com.google.android.gms.mobstore.IMobStoreFileCallbacks;
import com.google.android.gms.mobstore.OpenFileDescriptorRequest;
import com.google.android.gms.mobstore.DeleteFileRequest;
import com.google.android.gms.mobstore.RenameFileRequest;
import com.google.android.gms.common.api.ApiMetadata;

interface IMobStoreFileService {
    oneway void openFile(IMobStoreFileCallbacks callbacks, in OpenFileDescriptorRequest request, in ApiMetadata metadata) = 0;
    oneway void deleteFile(IMobStoreFileCallbacks callbacks, in DeleteFileRequest request, in ApiMetadata metadata) = 1;
    oneway void renameFile(IMobStoreFileCallbacks callbacks, in RenameFileRequest request, in ApiMetadata metadata) = 2;
}

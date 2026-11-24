/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mobstore;

import android.net.Uri;

import org.microg.safeparcel.AutoSafeParcelable;

public class RenameFileRequest extends AutoSafeParcelable {
    @Field(1)
    public Uri srcUri;

    @Field(2)
    public Uri dstUri;

    public RenameFileRequest() {
    }

    public RenameFileRequest(Uri srcUri, Uri dstUri) {
        this.srcUri = srcUri;
        this.dstUri = dstUri;
    }

    public static final Creator<RenameFileRequest> CREATOR = new AutoCreator<>(RenameFileRequest.class);
}

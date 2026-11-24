/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mobstore;

import android.net.Uri;

import org.microg.safeparcel.AutoSafeParcelable;

public class DeleteFileRequest extends AutoSafeParcelable {
    @Field(1)
    public Uri uri;

    public DeleteFileRequest() {
    }

    public DeleteFileRequest(Uri uri) {
        this.uri = uri;
    }

    public static final Creator<DeleteFileRequest> CREATOR = new AutoCreator<>(DeleteFileRequest.class);
}

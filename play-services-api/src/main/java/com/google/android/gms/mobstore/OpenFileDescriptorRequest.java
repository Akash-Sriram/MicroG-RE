/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mobstore;

import android.net.Uri;

import org.microg.safeparcel.AutoSafeParcelable;

public class OpenFileDescriptorRequest extends AutoSafeParcelable {
    @Field(1)
    public Uri uri;

    @Field(2)
    public int mode;

    public OpenFileDescriptorRequest() {
    }

    public OpenFileDescriptorRequest(Uri uri, int mode) {
        this.uri = uri;
        this.mode = mode;
    }

    public static final Creator<OpenFileDescriptorRequest> CREATOR = new AutoCreator<>(OpenFileDescriptorRequest.class);
}

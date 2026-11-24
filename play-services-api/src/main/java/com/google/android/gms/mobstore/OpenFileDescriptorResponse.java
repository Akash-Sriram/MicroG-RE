/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mobstore;

import android.os.ParcelFileDescriptor;

import org.microg.safeparcel.AutoSafeParcelable;

public class OpenFileDescriptorResponse extends AutoSafeParcelable {
    @Field(1)
    public ParcelFileDescriptor pfd;

    public OpenFileDescriptorResponse() {
    }

    public OpenFileDescriptorResponse(ParcelFileDescriptor pfd) {
        this.pfd = pfd;
    }

    public static final Creator<OpenFileDescriptorResponse> CREATOR = new AutoCreator<>(OpenFileDescriptorResponse.class);
}

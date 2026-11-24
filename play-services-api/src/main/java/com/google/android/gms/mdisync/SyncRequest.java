/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdisync;

import org.microg.safeparcel.AutoSafeParcelable;

public class SyncRequest extends AutoSafeParcelable {
    @Field(1)
    public int type;

    @Field(2)
    public byte[] data;

    @Field(3)
    public byte[] options;

    @Field(4)
    public long timestamp;

    public SyncRequest() {
    }

    public SyncRequest(int type, byte[] data, byte[] options, long timestamp) {
        this.type = type;
        this.data = data;
        this.options = options;
        this.timestamp = timestamp;
    }

    public static final Creator<SyncRequest> CREATOR = new AutoCreator<>(SyncRequest.class);
}

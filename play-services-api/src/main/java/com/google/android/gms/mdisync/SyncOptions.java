/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdisync;

import org.microg.safeparcel.AutoSafeParcelable;

public class SyncOptions extends AutoSafeParcelable {
    @Field(1)
    public String tag;

    @Field(2)
    public long timestamp;

    public SyncOptions() {
    }

    public SyncOptions(String tag, long timestamp) {
        this.tag = tag;
        this.timestamp = timestamp;
    }

    public static final Creator<SyncOptions> CREATOR = new AutoCreator<>(SyncOptions.class);
}

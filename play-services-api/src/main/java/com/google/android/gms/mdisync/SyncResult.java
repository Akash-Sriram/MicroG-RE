/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdisync;

import org.microg.safeparcel.AutoSafeParcelable;

public class SyncResult extends AutoSafeParcelable {
    @Field(1)
    public byte[] data;

    public SyncResult() {
    }

    public SyncResult(byte[] data) {
        this.data = data;
    }

    public static final Creator<SyncResult> CREATOR = new AutoCreator<>(SyncResult.class);
}

/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdd;

import org.microg.safeparcel.AutoSafeParcelable;

public class MddFile extends AutoSafeParcelable {
    @Field(1)
    public String fileId;

    @Field(2)
    public String fileUri;

    public MddFile() {
    }

    public MddFile(String fileId, String fileUri) {
        this.fileId = fileId;
        this.fileUri = fileUri;
    }

    public static final Creator<MddFile> CREATOR = new AutoCreator<>(MddFile.class);
}

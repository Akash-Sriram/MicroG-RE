/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdd;

import org.microg.safeparcel.AutoSafeParcelable;

public class FileGroupWithDownloadStatus extends AutoSafeParcelable {
    @Field(1)
    public String groupName;

    @Field(2)
    public String ownerPackage;

    @Field(3)
    public boolean downloaded;

    @Field(4)
    public int version;

    public FileGroupWithDownloadStatus() {
    }

    public FileGroupWithDownloadStatus(String groupName, String ownerPackage, boolean downloaded, int version) {
        this.groupName = groupName;
        this.ownerPackage = ownerPackage;
        this.downloaded = downloaded;
        this.version = version;
    }

    public static final Creator<FileGroupWithDownloadStatus> CREATOR = new AutoCreator<>(FileGroupWithDownloadStatus.class);
}

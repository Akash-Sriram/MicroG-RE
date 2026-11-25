/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdd;

import org.microg.safeparcel.AutoSafeParcelable;

import java.util.List;

public class FileGroupResponse extends AutoSafeParcelable {
    @Field(1)
    public String groupName;

    @Field(value = 2, subClass = MddFile.class)
    public List<MddFile> files;

    @Field(3)
    public String ownerPackage;

    @Field(4)
    public int versionNumber;

    public FileGroupResponse() {
    }

    public FileGroupResponse(String groupName, String ownerPackage, int versionNumber, List<MddFile> files) {
        this.groupName = groupName;
        this.ownerPackage = ownerPackage;
        this.versionNumber = versionNumber;
        this.files = files;
    }

    public static final Creator<FileGroupResponse> CREATOR = new AutoCreator<>(FileGroupResponse.class);
}

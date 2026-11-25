/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdd;

import android.accounts.Account;
import android.os.ParcelFileDescriptor;

import org.microg.safeparcel.AutoSafeParcelable;

public class OverrideFileGroupRequest extends AutoSafeParcelable {
    @Field(1)
    public String groupName;

    @Field(2)
    public String ownerPackage;

    @Field(3)
    public String[] fileIdList;

    @Field(4)
    public String[] fileUriList;

    @Field(5)
    public ParcelFileDescriptor[] parcelFileDescriptors;

    @Field(6)
    public Account account;

    public OverrideFileGroupRequest() {
    }

    public OverrideFileGroupRequest(String groupName, String ownerPackage, String[] fileIdList, String[] fileUriList, ParcelFileDescriptor[] parcelFileDescriptors, Account account) {
        this.groupName = groupName;
        this.ownerPackage = ownerPackage;
        this.fileIdList = fileIdList;
        this.fileUriList = fileUriList;
        this.parcelFileDescriptors = parcelFileDescriptors;
        this.account = account;
    }

    public static final Creator<OverrideFileGroupRequest> CREATOR = new AutoCreator<>(OverrideFileGroupRequest.class);
}

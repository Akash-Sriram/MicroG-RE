/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdd;

import android.accounts.Account;

import org.microg.safeparcel.AutoSafeParcelable;

public class FileGroupRequest extends AutoSafeParcelable {
    @Field(1)
    public String groupName;

    @Field(2)
    public String ownerPackage;

    @Field(3)
    public Account account;

    public FileGroupRequest() {
    }

    public FileGroupRequest(String groupName, String ownerPackage, Account account) {
        this.groupName = groupName;
        this.ownerPackage = ownerPackage;
        this.account = account;
    }

    public static final Creator<FileGroupRequest> CREATOR = new AutoCreator<>(FileGroupRequest.class);
}

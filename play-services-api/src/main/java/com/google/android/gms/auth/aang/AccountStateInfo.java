/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class AccountStateInfo extends AutoSafeParcelable {
    @Field(1)
    public String accountName;

    @Field(2)
    public String accountType;

    @Field(3)
    public String gaiaId;

    public AccountStateInfo() {
    }

    public AccountStateInfo(String accountName, String accountType, String gaiaId) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.gaiaId = gaiaId;
    }

    public static final Creator<AccountStateInfo> CREATOR = new AutoCreator<>(AccountStateInfo.class);
}

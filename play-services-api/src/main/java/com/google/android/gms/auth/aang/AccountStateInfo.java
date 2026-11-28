/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class AccountStateInfo extends AutoSafeParcelable {
    @Field(1)
    public String gaiaId;

    @Field(2)
    public String accountType;

    @Field(3)
    public String accountName;

    public AccountStateInfo() {
    }

    public AccountStateInfo(String gaiaId, String accountType, String accountName) {
        this.gaiaId = gaiaId;
        this.accountType = accountType;
        this.accountName = accountName;
    }

    public static final Creator<AccountStateInfo> CREATOR = new AutoCreator<>(AccountStateInfo.class);
}

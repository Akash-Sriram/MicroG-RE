/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class AccountEventInfo extends AutoSafeParcelable {
    @Field(1)
    public AccountStateInfo account;

    @Field(2)
    public AccountEventDetails details;

    public AccountEventInfo() {
    }

    public AccountEventInfo(AccountStateInfo account, AccountEventDetails details) {
        this.account = account;
        this.details = details;
    }

    public static final Creator<AccountEventInfo> CREATOR = new AutoCreator<>(AccountEventInfo.class);
}

/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class AccountRemoveResult extends AutoSafeParcelable {
    @Field(1)
    public AccountStateInfo account;

    @Field(2)
    public boolean success;

    public AccountRemoveResult() {
    }

    public AccountRemoveResult(AccountStateInfo account, boolean success) {
        this.account = account;
        this.success = success;
    }

    public static final Creator<AccountRemoveResult> CREATOR = new AutoCreator<>(AccountRemoveResult.class);
}

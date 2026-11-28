/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;
import java.util.ArrayList;
import java.util.List;

public class AccountStateResult extends AutoSafeParcelable {
    @Field(1)
    public List<AccountStateInfo> accounts = new ArrayList<>();

    @Field(2)
    public List<AccountEventInfo> events = new ArrayList<>();

    public AccountStateResult() {
    }

    public AccountStateResult(List<AccountStateInfo> accounts, List<AccountEventInfo> events) {
        if (accounts != null) this.accounts = accounts;
        if (events != null) this.events = events;
    }

    public static final Creator<AccountStateResult> CREATOR = new AutoCreator<>(AccountStateResult.class);
}

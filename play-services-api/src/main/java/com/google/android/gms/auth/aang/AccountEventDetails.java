/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class AccountEventDetails extends AutoSafeParcelable {
    @Field(1)
    public boolean flag1;

    @Field(2)
    public boolean flag2;

    public AccountEventDetails() {
    }

    public AccountEventDetails(boolean flag1, boolean flag2) {
        this.flag1 = flag1;
        this.flag2 = flag2;
    }

    public static final Creator<AccountEventDetails> CREATOR = new AutoCreator<>(AccountEventDetails.class);
}

/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class AccountRemoveRequest extends AutoSafeParcelable {
    @Field(1)
    public AccountStateInfo account;

    @Field(2)
    public String str;

    public AccountRemoveRequest() {
    }

    public static final Creator<AccountRemoveRequest> CREATOR = new AutoCreator<>(AccountRemoveRequest.class);
}

/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;
import java.util.ArrayList;
import java.util.List;

public class AccountStateRequest extends AutoSafeParcelable {
    @Field(1)
    public String accountName;

    @Field(2)
    public List<String> scopes = new ArrayList<>();

    @Field(3)
    public List<String> capabilities = new ArrayList<>();

    @Field(4)
    public boolean flag1;

    @Field(5)
    public boolean flag2;

    public AccountStateRequest() {
    }

    public static final Creator<AccountStateRequest> CREATOR = new AutoCreator<>(AccountStateRequest.class);
}

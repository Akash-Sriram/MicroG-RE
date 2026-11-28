/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;
import java.util.ArrayList;
import java.util.List;

public class CheckScopesRequest extends AutoSafeParcelable {
    @Field(1)
    public AccountStateInfo account;

    @Field(2)
    public List<String> scopes = new ArrayList<>();

    public CheckScopesRequest() {
    }

    public CheckScopesRequest(AccountStateInfo account, List<String> scopes) {
        this.account = account;
        if (scopes != null) this.scopes = scopes;
    }

    public static final Creator<CheckScopesRequest> CREATOR = new AutoCreator<>(CheckScopesRequest.class);
}

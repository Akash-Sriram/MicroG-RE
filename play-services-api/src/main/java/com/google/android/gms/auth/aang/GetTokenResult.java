/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class GetTokenResult extends AutoSafeParcelable {
    @Field(1)
    public String token;

    @Field(2)
    public TokenDetails details;

    public GetTokenResult() {
    }

    public GetTokenResult(String token, TokenDetails details) {
        this.token = token;
        this.details = details;
    }

    public static final Creator<GetTokenResult> CREATOR = new AutoCreator<>(GetTokenResult.class);
}

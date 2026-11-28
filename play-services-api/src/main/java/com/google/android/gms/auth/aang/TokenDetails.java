/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;
import java.util.ArrayList;
import java.util.List;

public class TokenDetails extends AutoSafeParcelable {
    @Field(1)
    public Long expiry;

    @Field(2)
    public List<String> grantedScopes = new ArrayList<>();

    public TokenDetails() {
    }

    public TokenDetails(Long expiry, List<String> grantedScopes) {
        this.expiry = expiry;
        if (grantedScopes != null) this.grantedScopes = grantedScopes;
    }

    public static final Creator<TokenDetails> CREATOR = new AutoCreator<>(TokenDetails.class);
}

/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class EmbeddedReauthResult extends AutoSafeParcelable {
    @Field(1)
    public AccountEventDetails details;

    public EmbeddedReauthResult() {
    }

    public EmbeddedReauthResult(AccountEventDetails details) {
        this.details = details;
    }

    public static final Creator<EmbeddedReauthResult> CREATOR = new AutoCreator<>(EmbeddedReauthResult.class);
}

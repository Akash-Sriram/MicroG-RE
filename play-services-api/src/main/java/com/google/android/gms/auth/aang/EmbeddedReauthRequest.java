/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class EmbeddedReauthRequest extends AutoSafeParcelable {
    @Field(1)
    public String accountName;

    public EmbeddedReauthRequest() {
    }

    public static final Creator<EmbeddedReauthRequest> CREATOR = new AutoCreator<>(EmbeddedReauthRequest.class);
}

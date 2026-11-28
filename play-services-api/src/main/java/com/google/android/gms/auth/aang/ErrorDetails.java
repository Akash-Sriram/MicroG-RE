/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import org.microg.safeparcel.AutoSafeParcelable;

public class ErrorDetails extends AutoSafeParcelable {
    @Field(1)
    public boolean field1;

    @Field(2)
    public boolean field2;

    public ErrorDetails() {
    }

    public ErrorDetails(boolean field1, boolean field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public static final Creator<ErrorDetails> CREATOR = new AutoCreator<>(ErrorDetails.class);
}

/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang.internal;

import com.google.android.gms.auth.aang.internal.IGoogleAuthAangCallbacks;
import com.google.android.gms.auth.aang.AccountStateRequest;
import com.google.android.gms.auth.aang.GetTokenRequest;
import com.google.android.gms.auth.aang.CheckScopesRequest;
import com.google.android.gms.auth.aang.EmbeddedReauthRequest;

interface IGoogleAuthAangService {
    oneway void syncAccountState(IGoogleAuthAangCallbacks callback, in AccountStateRequest request) = 0;
    oneway void getToken(IGoogleAuthAangCallbacks callback, in GetTokenRequest request) = 1;
    oneway void commitTransientAccount(IGoogleAuthAangCallbacks callback, String accountName) = 2;
    oneway void checkScopes(IGoogleAuthAangCallbacks callback, in CheckScopesRequest request) = 3;
    oneway void embeddedReauth(IGoogleAuthAangCallbacks callback, in EmbeddedReauthRequest request) = 4;
}

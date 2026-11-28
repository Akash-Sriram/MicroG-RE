/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.auth.aang.AccountStateResult;
import com.google.android.gms.auth.aang.GetTokenResult;
import com.google.android.gms.auth.aang.EmbeddedReauthResult;
import com.google.android.gms.auth.aang.ErrorDetails;

interface IGoogleAuthAangCallbacks {
    oneway void onAccountStateSynced(in Status status, in AccountStateResult result) = 0;
    oneway void onGetTokenResponse(in Status status, in GetTokenResult result) = 1;
    oneway void onIntResult(in Status status, int result) = 2;
    oneway void onEmbeddedReauthCompleted(in Status status, in EmbeddedReauthResult result) = 3;
    oneway void onGetTokenResponseWithError(in Status status, in GetTokenResult result, in ErrorDetails errorDetails) = 6;
}

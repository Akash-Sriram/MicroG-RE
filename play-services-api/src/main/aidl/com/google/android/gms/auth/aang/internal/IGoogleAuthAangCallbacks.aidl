/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.auth.aang.AccountStateResult;
import com.google.android.gms.auth.aang.AccountRemoveResult;
import com.google.android.gms.auth.aang.EmbeddedReauthResult;
import com.google.android.gms.auth.aang.ErrorDetails;

interface IGoogleAuthAangCallbacks {
    oneway void onAccountStateSynced(in Status status, in AccountStateResult result) = 0;
    oneway void onAccountRemoved(in Status status, in AccountRemoveResult result) = 1;
    oneway void onTransientAccountCommitted(in Status status, int result) = 2;
    oneway void onEmbeddedReauthCompleted(in Status status, in EmbeddedReauthResult result) = 3;
    oneway void onResult5(in Status status, in AccountRemoveResult result) = 4;
    oneway void onError(in Status status, in ErrorDetails errorDetails) = 5;
}

/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.cloudmessaging.internal;

import com.google.android.gms.common.api.Status;

interface IRegisterCallback {
    oneway void onComplete(in Status status, String registrationId) = 0;
}

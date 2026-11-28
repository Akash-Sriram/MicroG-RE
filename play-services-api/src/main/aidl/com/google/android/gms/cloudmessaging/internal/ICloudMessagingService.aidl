/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.cloudmessaging.internal;

import com.google.android.gms.cloudmessaging.internal.IRegisterCallback;
import android.os.Bundle;

interface ICloudMessagingService {
    oneway void register(IRegisterCallback callback, String pkg, String senderId, in Bundle extras) = 0;
    oneway void unregister(IRegisterCallback callback, String pkg, String senderId, in Bundle extras) = 1;
}

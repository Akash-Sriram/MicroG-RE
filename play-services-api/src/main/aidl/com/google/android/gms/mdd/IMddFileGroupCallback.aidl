/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.mdd;

import com.google.android.gms.common.api.ApiMetadata;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.mdd.FileGroupResponse;

oneway interface IMddFileGroupCallback {
    void onFileGroup(in Status status, in FileGroupResponse response, in ApiMetadata metadata) = 0;
}

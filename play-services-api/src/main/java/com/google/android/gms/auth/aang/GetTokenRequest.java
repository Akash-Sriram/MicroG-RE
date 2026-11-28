/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.auth.aang;

import android.net.Network;
import org.microg.safeparcel.AutoSafeParcelable;
import java.util.ArrayList;
import java.util.List;

public class GetTokenRequest extends AutoSafeParcelable {
    @Field(1)
    public AccountStateInfo account;

    @Field(2)
    public String gaiaId;

    @Field(3)
    public List<String> oauth2Scopes = new ArrayList<>();

    @Field(4)
    public List<String> webLoginUrls = new ArrayList<>();

    @Field(5)
    public List<String> clientLoginScopes = new ArrayList<>();

    @Field(6)
    public List<String> oauth2TokenIdScopes = new ArrayList<>();

    @Field(7)
    public int delegationType;

    @Field(8)
    public String delegateeUserId;

    @Field(9)
    public boolean handleNotification;

    @Field(10)
    public byte[] clientData;

    @Field(11)
    public String callingPackage;

    @Field(12)
    public boolean suppressProgressScreen;

    @Field(13)
    public Network network;

    @Field(14)
    public boolean useNewExceptions;

    @Field(15)
    public int clientVersion;

    public GetTokenRequest() {
    }

    public static final Creator<GetTokenRequest> CREATOR = new AutoCreator<>(GetTokenRequest.class);
}

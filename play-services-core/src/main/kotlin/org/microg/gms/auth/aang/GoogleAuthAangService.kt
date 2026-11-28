/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.auth.aang

import android.accounts.AccountManager
import android.content.Context
import android.util.Log
import com.google.android.gms.auth.aang.AccountEventDetails
import com.google.android.gms.auth.aang.AccountEventInfo
import com.google.android.gms.auth.aang.AccountRemoveRequest
import com.google.android.gms.auth.aang.AccountRemoveResult
import com.google.android.gms.auth.aang.AccountStateInfo
import com.google.android.gms.auth.aang.AccountStateRequest
import com.google.android.gms.auth.aang.AccountStateResult
import com.google.android.gms.auth.aang.EmbeddedReauthRequest
import com.google.android.gms.auth.aang.EmbeddedReauthResult
import com.google.android.gms.auth.aang.internal.IGoogleAuthAangCallbacks
import com.google.android.gms.auth.aang.internal.IGoogleAuthAangService
import com.google.android.gms.common.Feature
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.internal.ConnectionInfo
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.android.gms.common.internal.IGmsCallbacks
import org.microg.gms.BaseService
import org.microg.gms.auth.AuthConstants
import org.microg.gms.common.GmsService

private const val TAG = "GmsAuthAangSvc"

class GoogleAuthAangService : BaseService(TAG, GmsService.GOOGLE_AUTH_AANG) {

    private val supportedFeatures = arrayOf(
        Feature("google_auth_api", 1L),
        Feature("sync_account_state_api", 1L),
        Feature("embedded_reauth", 1L),
        Feature("remove_account_api", 1L),
        Feature("commit_transient_account_api", 1L)
    )

    override fun handleServiceRequest(
        callback: IGmsCallbacks,
        request: GetServiceRequest,
        service: GmsService
    ) {
        val connectionInfo = ConnectionInfo().apply {
            features = supportedFeatures
        }
        val impl = GoogleAuthAangServiceImpl(this, request.packageName)
        callback.onPostInitCompleteWithConnectionInfo(0, impl.asBinder(), connectionInfo)
    }
}

class GoogleAuthAangServiceImpl(
    private val context: Context,
    private val callingPackage: String
) : IGoogleAuthAangService.Stub() {

    override fun syncAccountState(
        callback: IGoogleAuthAangCallbacks?,
        request: AccountStateRequest?
    ) {
        Log.d(TAG, "syncAccountState called by $callingPackage for account: ${request?.accountName}")
        try {
            val accountManager = AccountManager.get(context)
            val accounts = accountManager.getAccountsByType(AuthConstants.DEFAULT_ACCOUNT_TYPE)
            val accountList = accounts.map { acc ->
                AccountStateInfo(acc.name, acc.type, null)
            }
            val result = AccountStateResult(accountList, emptyList())
            callback?.onAccountStateSynced(Status.SUCCESS, result)
        } catch (e: Exception) {
            Log.w(TAG, "syncAccountState error", e)
            callback?.onAccountStateSynced(Status.SUCCESS, AccountStateResult(emptyList(), emptyList()))
        }
    }

    override fun removeAccount(
        callback: IGoogleAuthAangCallbacks?,
        request: AccountRemoveRequest?
    ) {
        Log.d(TAG, "removeAccount called by $callingPackage")
        callback?.onAccountRemoved(Status.SUCCESS, AccountRemoveResult(request?.account, true))
    }

    override fun commitTransientAccount(
        callback: IGoogleAuthAangCallbacks?,
        accountName: String?
    ) {
        Log.d(TAG, "commitTransientAccount called by $callingPackage for $accountName")
        callback?.onTransientAccountCommitted(Status.SUCCESS, 0)
    }

    override fun embeddedReauth(
        callback: IGoogleAuthAangCallbacks?,
        request: EmbeddedReauthRequest?
    ) {
        Log.d(TAG, "embeddedReauth called by $callingPackage for ${request?.accountName}")
        callback?.onEmbeddedReauthCompleted(Status.SUCCESS, EmbeddedReauthResult(AccountEventDetails(true, false)))
    }
}

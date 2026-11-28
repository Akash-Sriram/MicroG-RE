/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.auth.aang

import android.accounts.AccountManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.aang.AccountEventDetails
import com.google.android.gms.auth.aang.AccountStateInfo
import com.google.android.gms.auth.aang.AccountStateRequest
import com.google.android.gms.auth.aang.AccountStateResult
import com.google.android.gms.auth.aang.CheckScopesRequest
import com.google.android.gms.auth.aang.EmbeddedReauthRequest
import com.google.android.gms.auth.aang.EmbeddedReauthResult
import com.google.android.gms.auth.aang.GetTokenRequest
import com.google.android.gms.auth.aang.GetTokenResult
import com.google.android.gms.auth.aang.TokenDetails
import com.google.android.gms.auth.aang.internal.IGoogleAuthAangCallbacks
import com.google.android.gms.auth.aang.internal.IGoogleAuthAangService
import com.google.android.gms.common.Feature
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.internal.ConnectionInfo
import com.google.android.gms.common.internal.GetServiceRequest
import com.google.android.gms.common.internal.IGmsCallbacks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.microg.gms.BaseService
import org.microg.gms.auth.AuthConstants
import org.microg.gms.auth.AuthManager
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
        val impl = GoogleAuthAangServiceImpl(this, request.packageName, this.lifecycle)
        callback.onPostInitCompleteWithConnectionInfo(0, impl.asBinder(), connectionInfo)
    }
}

class GoogleAuthAangServiceImpl(
    private val context: Context,
    private val callingPackage: String,
    override val lifecycle: Lifecycle
) : IGoogleAuthAangService.Stub(), LifecycleOwner {

    override fun syncAccountState(
        callback: IGoogleAuthAangCallbacks?,
        request: AccountStateRequest?
    ) {
        val requestedType = request?.accountType?.takeIf { it.isNotEmpty() } ?: AuthConstants.DEFAULT_ACCOUNT_TYPE
        Log.d(TAG, "syncAccountState called by $callingPackage for accountType: $requestedType")
        try {
            val accountManager = AccountManager.get(context)
            val accounts = (accountManager.getAccountsByType(requestedType).toList() +
                    accountManager.getAccountsByType(AuthConstants.DEFAULT_ACCOUNT_TYPE).toList())
                .distinctBy { it.name }
            val accountList = accounts.map { acc ->
                val gaiaId = accountManager.getUserData(acc, "GoogleUserId")
                    ?: accountManager.getUserData(acc, "accountId")
                    ?: acc.name
                AccountStateInfo(gaiaId, requestedType, acc.name)
            }
            val result = AccountStateResult(accountList, emptyList())
            callback?.onAccountStateSynced(Status.SUCCESS, result)
        } catch (e: Exception) {
            Log.w(TAG, "syncAccountState error", e)
            callback?.onAccountStateSynced(Status.SUCCESS, AccountStateResult(emptyList(), emptyList()))
        }
    }

    override fun getToken(
        callback: IGoogleAuthAangCallbacks?,
        request: GetTokenRequest?
    ) {
        Log.d(TAG, "getToken called by $callingPackage: account=${request?.account?.accountName}, gaiaId=${request?.gaiaId}, scopes=${request?.oauth2Scopes}")
        lifecycleScope.launchWhenStarted {
            try {
                val accountManager = AccountManager.get(context)
                val allAccounts = (accountManager.getAccountsByType(AuthConstants.DEFAULT_ACCOUNT_TYPE).toList() +
                        accountManager.getAccountsByType("com.google").toList()).distinctBy { it.name }

                val targetAccount = if (request?.account?.accountName?.isNotEmpty() == true) {
                    allAccounts.firstOrNull { it.name.equals(request.account.accountName, ignoreCase = true) }
                } else if (request?.gaiaId?.isNotEmpty() == true) {
                    allAccounts.firstOrNull { acc ->
                        accountManager.getUserData(acc, "GoogleUserId") == request.gaiaId ||
                                accountManager.getUserData(acc, "accountId") == request.gaiaId ||
                                acc.name.equals(request.gaiaId, ignoreCase = true)
                    }
                } else {
                    allAccounts.firstOrNull()
                }

                if (targetAccount == null) {
                    Log.w(TAG, "getToken: no matching account found for request")
                    callback?.onGetTokenResponse(Status.CANCELED, null)
                    return@launchWhenStarted
                }

                val requestedScopes = request?.oauth2Scopes?.takeIf { it.isNotEmpty() }
                    ?: listOf("https://www.googleapis.com/auth/photos.native")
                val scopeString = "oauth2:" + requestedScopes.joinToString(" ").replaceFirst("^oauth2:", "")

                val authResponse = withContext(Dispatchers.IO) {
                    val authManager = AuthManager(context, targetAccount.name, callingPackage, scopeString).apply {
                        accountType = targetAccount.type
                    }
                    authManager.requestAuth(false)
                }

                if (authResponse?.auth != null) {
                    val expiry = if (authResponse.expiry > 0) authResponse.expiry else (System.currentTimeMillis() / 1000 + 3600)
                    val granted = authResponse.grantedScopes?.split(" ")?.filter { it.isNotEmpty() } ?: requestedScopes
                    val details = TokenDetails(expiry, granted)
                    val result = GetTokenResult(authResponse.auth, details)
                    Log.d(TAG, "getToken success for ${targetAccount.name}, expiry=$expiry")
                    callback?.onGetTokenResponse(Status.SUCCESS, result)
                } else {
                    Log.w(TAG, "getToken returned null auth for ${targetAccount.name}")
                    callback?.onGetTokenResponse(Status.INTERNAL_ERROR, null)
                }
            } catch (e: Exception) {
                Log.w(TAG, "getToken error", e)
                callback?.onGetTokenResponse(Status.INTERNAL_ERROR, null)
            }
        }
    }

    override fun commitTransientAccount(
        callback: IGoogleAuthAangCallbacks?,
        accountName: String?
    ) {
        Log.d(TAG, "commitTransientAccount called by $callingPackage for $accountName")
        callback?.onIntResult(Status.SUCCESS, 0)
    }

    override fun checkScopes(
        callback: IGoogleAuthAangCallbacks?,
        request: CheckScopesRequest?
    ) {
        Log.d(TAG, "checkScopes called by $callingPackage for ${request?.account?.accountName}, scopes=${request?.scopes}")
        callback?.onIntResult(Status.SUCCESS, 1)
    }

    override fun embeddedReauth(
        callback: IGoogleAuthAangCallbacks?,
        request: EmbeddedReauthRequest?
    ) {
        Log.d(TAG, "embeddedReauth called by $callingPackage for ${request?.accountName}")
        callback?.onEmbeddedReauthCompleted(Status.SUCCESS, EmbeddedReauthResult(AccountEventDetails(true, false)))
    }
}

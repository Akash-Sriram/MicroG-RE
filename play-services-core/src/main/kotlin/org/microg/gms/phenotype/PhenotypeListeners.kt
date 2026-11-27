// RE specific changes
/*
 * SPDX-FileCopyrightText: 2026 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.phenotype

import android.content.Context
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.google.android.gms.phenotype.internal.IFlagUpdateListener

internal object PhenotypeListeners {
    private data class Entry(val listener: IFlagUpdateListener, val death: IBinder.DeathRecipient)

    private val entries = mutableMapOf<Pair<String, String?>, Entry>()

    fun register(caller: String, processName: String?, listener: IFlagUpdateListener) {
        val key = caller to processName
        lateinit var entry: Entry
        entry = Entry(listener, IBinder.DeathRecipient { remove(key, entry) })
        synchronized(entries) {
            listener.asBinder().linkToDeath(entry.death, 0)
            entries.put(key, entry)?.let { it.listener.asBinder().unlinkToDeath(it.death, 0) }
        }
    }

    private fun remove(key: Pair<String, String?>, entry: Entry) {
        synchronized(entries) {
            if (entries[key] === entry) {
                entries.remove(key)
                entry.listener.asBinder().unlinkToDeath(entry.death, 0)
            }
        }
    }

    fun notifyChanges(context: Context, configPackages: Set<String>) {
        val database = PhenotypeDatabase.getInstance(context)
        val androidPackages = database.getAndroidPackageNames(configPackages)
        val byApplication = configPackages.mapNotNull { name -> androidPackages[name]?.let { it to name } }
            .groupBy({ it.first }, { it.second })
        for ((application, packages) in byApplication) {
            val recipients = synchronized(entries) { entries.filterKeys { it.first == application }.toList() }
            if (recipients.isNotEmpty()) {
                val payload = FlagUpdate(packages.sorted()).encode()
                for ((key, entry) in recipients) {
                    try {
                        entry.listener.onFlagUpdate(payload)
                    } catch (e: RemoteException) {
                        remove(key, entry)
                        Log.w(PHENOTYPE_LOG_TAG, "Flag listener is unavailable")
                    }
                }
            }
            // RE changes start
            try {
                val targetApps = unresolveTargetGooglePackage(application)
                for (pkg in packages) {
                    val updateIntent = android.content.Intent("com.google.android.gms.phenotype.UPDATE")
                        .putExtra("com.google.android.gms.phenotype.PACKAGE_NAME", pkg)
                    for (targetApp in targetApps) {
                        context.sendBroadcast(android.content.Intent(updateIntent).setPackage(targetApp))
                    }
                    val receivers = context.packageManager.queryBroadcastReceivers(updateIntent, 0)
                    for (receiver in receivers) {
                        val rPkg = receiver.activityInfo?.packageName ?: continue
                        if (!targetApps.contains(rPkg) && rPkg != application) continue
                        val cls = receiver.activityInfo?.name ?: continue
                        context.sendBroadcast(android.content.Intent(updateIntent).setClassName(rPkg, cls))
                    }
                }
            } catch (e: Exception) {
                Log.w(PHENOTYPE_LOG_TAG, "Failed to send Phenotype update broadcast to $application", e)
            }
            // RE changes end
        }
    }

    fun notifyPendingChanges(context: Context, androidPackageName: String) {
        notifyChanges(context, PhenotypeDatabase.getInstance(context).getPendingUpdatePackages(androidPackageName))
    }
}

package com.client.kyc_verify.repo

import com.client.kyc_verify.util.NetworkCall
import rx.Observable
import java.util.concurrent.Flow.Subscriber


/**
 * Implementation class of the [Repo] Interface
 */
class RepoImpl(private val networkCall: NetworkCall) : Repo {

    /**
     * Method to fetch details of trending repositories.
     * Based on Internet connectivity it sends data to url
     */

    override fun sendDataUsingNetwork(url: String,data:String): Observable<String?>? {
        return Observable.create { subscriber ->
            try {
                val response = networkCall.makeServiceCall(url)
                subscriber.onNext(response)
                subscriber.onCompleted()
            } catch (e: java.lang.Exception) {
                subscriber.onError(e)
            }
        }
    }
}
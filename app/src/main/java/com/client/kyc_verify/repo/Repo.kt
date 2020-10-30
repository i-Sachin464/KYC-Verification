package com.client.kyc_verify.repo

import rx.Observable

/**
 * Interface specifying methods for fetching details
 */
interface Repo {

    /**
     * method to send details
     * @param sendDataUsingNetwork which sends data to server
     */
    fun sendDataUsingNetwork(url: String, data: String): Observable<String?>?
}
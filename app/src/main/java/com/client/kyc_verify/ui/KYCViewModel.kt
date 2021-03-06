package com.client.kyc_verify.ui

import androidx.lifecycle.ViewModel
import com.client.kyc_verify.util.IdType
import com.client.kyc_verify.util.RegexPatterns
import java.util.regex.Pattern


/**
 * ViewModel class for the [MainActivity]
 */
class KYCViewModel : ViewModel() {

    /**
     * @param: (String)Type, (String)card_text
     *
     * @return (Boolean)
     *
     * This function is for validate id card according to card type selected
     * and returns true if it is in right format
     *
     */
    fun validateIdCard(type: String, card_text: String): Boolean {
        when (type) {
            IdType.AADHAR_CARD.text -> {
                if (card_text.length != 12) {
                    return false
                }
                val regexAadhar = Pattern.compile(RegexPatterns.aadhar_card)
                val matcher = regexAadhar.matcher(card_text)
                return matcher.matches();
            }
            IdType.VOTER_CARD.text -> {
                if (card_text.length != 10) {
                    return false
                }
                val regexAadhar = Pattern.compile(RegexPatterns.voter_id_card)
                val matcher = regexAadhar.matcher(card_text)
                return matcher.matches();
            }
            IdType.PAN_CARD.text -> {
                if (card_text.length != 10) {
                    return false
                }
                val regexPancard = Pattern.compile(RegexPatterns.pan_card)
                val matcher = regexPancard.matcher(card_text)
                return matcher.matches();
            }
            IdType.DRIVING_LICENSE.text -> {
                if (card_text.length != 16) {
                    return false
                }
                val regexDL = Pattern.compile(RegexPatterns.driving_license)
                val matcher = regexDL.matcher(card_text)
                return matcher.matches();
            }
            IdType.PASSPORT.text -> {
                if (card_text.length != 8) {
                    return false
                }
                val regexPassport = Pattern.compile(RegexPatterns.passport)
                val matcher = regexPassport.matcher(card_text)
                return matcher.matches();
            }
        }
        return false
    }

}
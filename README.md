# KYC-Verification

KYC-Verification is an application that validate all ID-Card (i.e. Aadhar card, Pancard, Voterid card, Passport) and send that details to the server.

## Requirement
- Android 5.1 or later (Minimum SDK level 21)
- Android Studio (to compile and use)

## Language Used
- Kotlin

## Features
- Validate various id cards

## Architecture
- MVVM

## Application Flow
When we run this application first it'll start with a **Splash Screen** and it will go to **MainActivity** in which it will ask ID card type and enter the card value to validate. In MainActivity the validate is used to validate if user enter a valid id card or not and send data to the server.


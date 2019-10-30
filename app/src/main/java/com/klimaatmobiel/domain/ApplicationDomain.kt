package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ApplicationDomain(val ApplicationDomainId : Long, val applicationDomainName : String, val applicationDomainDescr : String) : Parcelable {

}
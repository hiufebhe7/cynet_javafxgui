package com.example.demo.net

import com.google.gson.annotations.SerializedName

class OAuth {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("website")
    var website: String? = null
    @SerializedName("redirect_uri")
    var redirect_uri: String? = null
    @SerializedName("client_id")
    var clientId: String? = null
    @SerializedName("client_secret")
    var clientSecret: String? = null
    @SerializedName("vapid_key")
    var vapid_key: String? = null
}

class AccessToken {
    @SerializedName("access_token")
    var accessToken: String? = null
}

class Media {
    lateinit var id: String
    lateinit var type: String
    lateinit var url: String
    @SerializedName("preview_url")
    lateinit var previewUrl: String
    @SerializedName("text_url")
    lateinit var textUrl: String
}

class Account {
    var id: String? = null
    @SerializedName("username")
    var username: String? = null
    @SerializedName("acct")
    var acct: String? = null
    @SerializedName("display_name")
    var displayName: String? = null
        get() {
            return if (field!!.length == 0) {
                username
            } else {
                field
            }
        }
    var locked = false
    var note: String? = null
    var url: String? = null
    var avatar: String? = null
    @SerializedName("avatar_static")
    var avatarStatic: String? = null
    var header: String? = null
    @SerializedName("header_static")
    var headerStatic: String? = null
    @SerializedName("followers_count")
    var followersCount: String? = null
    @SerializedName("following_count")
    var followingCount: String? = null
    @SerializedName("statuses_count")
    var statusesCount: String? = null
    var emojis: Array<Emojis>? = null
    var fields: Array<Fields>? = null
//    override fun hashCode(): Int {
//        return id.hashCode()
//    }

//    override fun equals(other: Any?): Boolean {
//        if (id == null) {
//            return this === other
//        } else if (other !is Account) {
//            return false
//        }
//        return other.id == id
//    }

    //    @Override
//    public String getBody() {
//        return acct;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//    }
//    constructor() {}

//    protected constructor(`in`: Parcel?) {}

    inner class Emojis {
        var shortcode: String? = null
        var url: String? = null
        @SerializedName("static_url")
        var staticUrl: String? = null
        @SerializedName("visible_in_picker")
        var visibleInPicker: String? = null
    }

    inner class Fields {
        var name: String? = null
        var value: String? = null
        @SerializedName("verified_at")
        var verifiedAt: Boolean? = null
    }

//    companion object {
//        val CREATOR = object : Parcelable.Creator<Account?> {
//            override fun createFromParcel(source: Parcel): Account? {
//                return Account(source)
//            }
//
//            override fun newArray(size: Int): Array<Account?> {
//                return arrayOfNulls(size)
//            }
//        }
//    }
}
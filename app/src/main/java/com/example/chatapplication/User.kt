package com.example.chatapplication

import com.google.firebase.auth.FirebaseUser

class User {
    var name: String? = null;
    var email: String? = null;
    var uid: String? = null;

    constructor(){
        this.name = null;
        this.email = null;
        this.uid = null;
    }

    constructor(uid: String?, name: String?, email: String?){
        this.name = name;
        this.email = email;
        this.uid = uid;
    }
}
package com.melo.allan.poc.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.liferay.mobile.push.Push
import com.liferay.mobile.screens.auth.login.LoginListener
import com.liferay.mobile.screens.auth.login.LoginScreenlet
import com.liferay.mobile.screens.context.SessionContext
import com.liferay.mobile.screens.context.User
import com.melo.allan.poc.R
import com.melo.allan.poc.util.PrefsUtil
import org.json.JSONException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginScreenlet = findViewById(R.id.loginScreenlet) as LoginScreenlet

        loginScreenlet.listener = object : LoginListener {
            override fun onLoginSuccess(user: User) {
                registerForPushNotification()

                saveScreenName(user)
                val intent = Intent(loginScreenlet.context, MainActivity::class.java)

                startActivity(intent)
            }

            override fun onLoginFailure(e: Exception) {
                Log.e("ERROR", e.localizedMessage)
            }
        }

        SessionContext.createBasicSession(getString(R.string.default_user), getString(R.string.default_password))
    }

    private fun saveScreenName(user: User) {
        PrefsUtil(this).screenName = user.screenName
    }

    private fun registerForPushNotification() {
        val session = SessionContext.createSessionFromCurrentSession()

        try {
            Push.with(session).withPortalVersion(getString(R.string.liferay_portal_version))
                    .onSuccess { jsonObject ->
                        try {
                            val token = jsonObject.getString("token")
                            Log.e("token", token)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    .onFailure { e -> Log.e("onFailure", e.message) }
                    .register(applicationContext, getString(R.string.push_sender))
        } catch (e: Exception) {
            Log.e("ERROR", e.message)
        }

    }
}

package com.example.spellbook5eapplication.app.Utility

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.nefilim.kjwt.JOSEType
import io.github.nefilim.kjwt.JWSES256Algorithm
//import io.github.nefilim.kjwt.JWT
import io.github.nefilim.kjwt.JWT.Companion.es256
import java.time.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.Overlays.CreateOverlay
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import java.security.interfaces.ECPrivateKey
import org.apache.commons.codec.binary.Base64
import org.json.JSONObject

object JsonTokenManager {

    fun tokenise(json: String, index: String): String {
        val algorithm = Algorithm.none()

        val token = JWT.create()
            .withClaim("index", index)
            .withClaim("json", json)
            .sign(algorithm)

        return token
    }


    fun tokenMyHomebrew(index: String): String{
        val json = LocalDataLoader.getJson(index, LocalDataLoader.DataType.HOMEBREW)
        val token = tokenise(json!!, index)
        return token
    }

    fun saveTokenAsHomebrew(token: String) {
        val parts = token.split(".")
        if (parts.size != 3) {
            println("Not a usual token")
        }

        val decodedToken = JWT.decode(token)

        val index = decodedToken.claims.get("index").toString().removeSurrounding("\"")
        val json = decodedToken.claims.get("json").toString().removeSurrounding("\"")

        println("Found token as index: $index and json: $json")

        LocalDataLoader.saveJson(json, index, LocalDataLoader.DataType.HOMEBREW)
    }


}
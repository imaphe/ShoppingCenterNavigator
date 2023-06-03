package com.example.shoppingcenternavigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.wineBerry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun User(navController: NavController,selectedItem: MutableState<Int>) {
    BackHandler(onBack = {selectedItem.value = 4})
    val auth = Firebase.auth
    val alertDialog = remember { mutableStateOf(value = false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            //Divider(color = Color.Black)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                , verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.arrow_back), contentDescription = "", modifier = Modifier
                    .padding(top = 2.dp, end = 4.dp)
                    .clickable { selectedItem.value = 4 }, tint = Color.White)
                Text(text = "Account Information", style = MaterialTheme.typography.h5, color = wineBerry)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                , verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Email, contentDescription = "", modifier = Modifier.padding(top = 2.dp, end = 4.dp), tint = Color.White)
                Text(text = "E-Mail:", style = MaterialTheme.typography.h5, color = wineBerry)
                Text(text = "${auth.currentUser?.email}")
            }
            Divider(color = Color.White)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                , verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { auth.currentUser?.email?.let { auth.sendPasswordResetEmail(it) } }) {
                    Text("Send Password Reset Email")
                }
            }
            Divider(color = Color.White)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                , verticalAlignment = Alignment.CenterVertically) {

                if (alertDialog.value){
                    AlertDialog(
                        onDismissRequest = { alertDialog.value = false },
                        text = { Text(text = "Çıkış yapmak istediğinize emin misiniz?",
                            color = Color.White, fontSize = 18.sp) },
                        confirmButton = {
                            Text(text = "Hayır",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        alertDialog.value = false
                                    },
                                color = Color.White)},
                        dismissButton = {
                            Text(text = "Evet",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        auth.signOut()
                                        navController.navigate("LoginPage")
                                    },
                                color = Color.White)

                        },
                        backgroundColor = wineBerry
                    )
                }
                Button(onClick = {
                    alertDialog.value = true
                },
                    Modifier.size(200.dp,50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = wineBerry
                    )) {
                    Text(text = "Çıkış Yap", color = Color.White)
                }
            }
        }
    }
}
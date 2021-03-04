/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {

        Column(
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 200.dp).fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var timeInMillisLeft: Long by rememberSaveable { mutableStateOf(0) }
            var seconds: Int by rememberSaveable { mutableStateOf(0) }
            var minutes: Int by rememberSaveable { mutableStateOf(0) }
            var hours: Int by rememberSaveable { mutableStateOf(0) }

            Countdown(hours, minutes, seconds)

            Row(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        seconds--
                    }
                ) {
                    Text("-", fontSize = 40.sp)
                }
                Button(
                    onClick = {
                        seconds++
                    },
                    modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Text("+", fontSize = 40.sp)
                }
            }

            var button by remember { mutableStateOf("Play >") }

            val timeFinal = (hours * 3600 + minutes * 60 + seconds) * 1000

            Button(
                onClick = {
                    button = "Running..."
                    val timer = object : CountDownTimer(timeFinal.toLong(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                            timeInMillisLeft = millisUntilFinished
                            seconds = (timeInMillisLeft / 1000 % 60).toInt()

                            minutes = (timeInMillisLeft / 1000 / 60).toInt()

                            hours = (timeInMillisLeft / 1000 / 3600).toInt()
                        }

                        override fun onFinish() {
                            button = "Finished !"
                        }
                    }
                    timer.start()
                }
            ) {
                Text(button, fontSize = 40.sp)
            }
        }
    }
}

@Composable
fun Countdown(hours: Int, minutes: Int, seconds: Int) {

    Row(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 100.dp)) {
        Text(text = "$hours : ", fontSize = 60.sp)
        Text(text = "$minutes : ", fontSize = 60.sp)
        Text(text = "$seconds", fontSize = 60.sp)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

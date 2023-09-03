package com.vinibarros.endlessmonstermayhem.game.view

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class EndlessMonsterMayhemMainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    private lateinit var endlessMonsterMayhemSurfaceView: EndlessMonsterMayhemSurfaceView
    private var playerName = ""


    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        onBackPressedDispatcher.addCallback(this) {}
        setContent {
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    BackHandler(true) {}
                    StartGameScreen(onGameStart = {
                        navController.navigate("endless_monster_mayhem") {
                            launchSingleTop = true
                        }
                    })
                }
                composable("endless_monster_mayhem") {
                    BackHandler(true) {}
                    endlessMonsterMayhemSurfaceView =
                        EndlessMonsterMayhemSurfaceView(this@EndlessMonsterMayhemMainActivity) { score ->
                            this@EndlessMonsterMayhemMainActivity.runOnUiThread {
                                scope.launch {
                                    try {
                                        db.collection("ranking").add(Score(playerName, score))
                                            .addOnSuccessListener {}.addOnFailureListener {}.await()
                                    } catch (_: Exception) {}
                                    navController.navigate("game_over") {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }
                    EndlessMonsterMayhemContainer()
                }
                composable("game_over") {
                    BackHandler(true) {}
                    GameOverScreen(onGameStart = {
                        navController.navigate("endless_monster_mayhem") {
                            launchSingleTop = true
                        }
                    })
                }
            }
        }
    }

    @Composable
    fun EndlessMonsterMayhemContainer() {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                endlessMonsterMayhemSurfaceView
            }
        )
    }

    @Composable
    fun StartGameScreen(
        onGameStart: () -> Unit
    ) {
        CommonGameScreen(
            buttonLabel = "Start Game",
            showNameInput = true,
            onGameAction = {
                onGameStart.invoke()
            }
        )
    }

    @Composable
    fun GameOverScreen(
        onGameStart: () -> Unit
    ) {
        CommonGameScreen(
            buttonLabel = "Restart Game",
            showNameInput = false,
            onGameAction = { onGameStart.invoke() }
        )
    }

    @Composable
    fun CommonGameScreen(
        buttonLabel: String,
        showNameInput: Boolean,
        onGameAction: (String) -> Unit
    ) {
        var playerName by remember { mutableStateOf(TextFieldValue()) }
        var ranking by remember { mutableStateOf(listOf<Score>()) }

        LaunchedEffect(true) {
            val scoresCollection = db.collection("ranking")
            val query = scoresCollection.orderBy(
                "score",
                com.google.firebase.firestore.Query.Direction.DESCENDING
            ).limit(5)
            val result = query.get().await()
            ranking = result.toObjects(Score::class.java)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showNameInput) {
                this@EndlessMonsterMayhemMainActivity.playerName = playerName.text
                BasicTextField(
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (this@EndlessMonsterMayhemMainActivity.playerName.isNotEmpty()) {
                                this@EndlessMonsterMayhemMainActivity.playerName = playerName.text
                                onGameAction(playerName.text)
                            }
                        }
                    ),
                    value = playerName,
                    onValueChange = {
                        playerName = it
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 64.dp)
                                .fillMaxWidth()
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFAAE9E6),
                                    shape = RoundedCornerShape(size = 16.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                        ) {
                            if (playerName.text.isEmpty()) {
                                Text(
                                    text = "Digite seu nome",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.LightGray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (this@EndlessMonsterMayhemMainActivity.playerName.isNotEmpty()) {
                        onGameAction(playerName.text)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = buttonLabel)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(ranking) { score ->
                    ScoreItem(score)
                }
            }
        }
    }

    @Composable
    fun ScoreItem(score: Score) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = score.playerName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            )
            Text(
                text = score.score.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            )
        }
    }

    data class Score(val playerName: String = "", val score: Int = 0)

    override fun onPause() {
        if (::endlessMonsterMayhemSurfaceView.isInitialized) {
            endlessMonsterMayhemSurfaceView.pause()
        }
        super.onPause()
    }
}
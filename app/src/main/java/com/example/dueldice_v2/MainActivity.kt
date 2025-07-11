// Declare the package name
package com.example.dueldice

// Import Android and Jetpack Compose components used in main activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Main entry point of the app; inherits from ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the UI content using Jetpack Compose
        setContent {
            // Apply the custom app theme
            DuelDiceTheme {
                // Load the game UI
                DiceGameApp()
            }
        }
    }
}

// Add the custom Material theme used throughout the app
@Composable
fun DuelDiceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),   // Use default light color scheme
        content = content   // Pass the inner content to be themed
    )
}

// Helper function to get dice image resource
fun getDiceResource(value: Int): Int? {
    return when (value) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_1
    }
}

// Check if dice images exist
@Composable
fun diceImagesExist(): Boolean {
    val context = LocalContext.current
    return try {
        context.resources.getDrawable(R.drawable.dice_1, context.theme)
        context.resources.getDrawable(R.drawable.dice_2, context.theme)
        context.resources.getDrawable(R.drawable.dice_3, context.theme)
        context.resources.getDrawable(R.drawable.dice_4, context.theme)
        context.resources.getDrawable(R.drawable.dice_5, context.theme)
        context.resources.getDrawable(R.drawable.dice_6, context.theme)
        true
    } catch (e: Exception) {
        false
    }
}

// Dice face using dots
@Composable
fun DiceFaceDots(value: Int) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(4.dp)
    ) {
        when (value) {
            1 -> {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.Center)
                )
            }
            2 -> {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.TopStart)
                        .offset(6.dp, 6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.BottomEnd)
                        .offset((-6).dp, (-6).dp)
                )
            }
            3 -> {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.TopStart)
                        .offset(6.dp, 6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.Center)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.BottomEnd)
                        .offset((-6).dp, (-6).dp)
                )
            }
            4 -> {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.TopStart)
                        .offset(6.dp, 6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.TopEnd)
                        .offset((-6).dp, 6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.BottomStart)
                        .offset(6.dp, (-6).dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.BottomEnd)
                        .offset((-6).dp, (-6).dp)
                )
            }
            5 -> {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.TopStart)
                        .offset(6.dp, 6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.TopEnd)
                        .offset((-6).dp, 6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.Center)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.BottomStart)
                        .offset(6.dp, (-6).dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.BottomEnd)
                        .offset((-6).dp, (-6).dp)
                )
            }
            6 -> {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.TopStart)
                        .offset(6.dp, 6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.TopEnd)
                        .offset((-6).dp, 6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.CenterStart)
                        .offset(6.dp, 0.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.CenterEnd)
                        .offset((-6).dp, 0.dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.BottomStart)
                        .offset(6.dp, (-6).dp)
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.BottomEnd)
                        .offset((-6).dp, (-6).dp)
                )
            }
        }
    }
}

@Composable
fun DiceGameApp() {
    // Use rememberSaveable to preserve state across configuration changes
    var currentScreen by rememberSaveable { mutableStateOf(Screen.MAIN_MENU) }
    var humanWins by rememberSaveable { mutableStateOf(0) }
    var computerWins by rememberSaveable { mutableStateOf(0) }
    var targetScore by rememberSaveable { mutableStateOf(101) }

    when (currentScreen) {
        Screen.MAIN_MENU -> MainMenuScreen(
            onNewGame = {
                currentScreen = Screen.GAME
            },
            onAbout = { currentScreen = Screen.ABOUT },
            onSetTarget = { target -> targetScore = target }
        )
        Screen.ABOUT -> AboutScreen(
            onBack = { currentScreen = Screen.MAIN_MENU }
        )
        Screen.GAME -> {
            // Create a unique key for GameScreen to force recomposition on new game
            key(humanWins + computerWins) {
                GameScreen(
                    onGameEnd = { humanWon ->
                        if (humanWon) humanWins++ else computerWins++
                        currentScreen = Screen.MAIN_MENU
                    },
                    onBackToMenu = { currentScreen = Screen.MAIN_MENU },
                    humanWins = humanWins,
                    computerWins = computerWins,
                    targetScore = targetScore
                )
            }
        }
    }
}

enum class Screen {
    MAIN_MENU, ABOUT, GAME
}

@Composable
fun MainMenuScreen(
    onNewGame: () -> Unit,
    onAbout: () -> Unit,
    onSetTarget: (Int) -> Unit
) {
    var showTargetDialog by remember { mutableStateOf(false) }
    var targetInput by remember { mutableStateOf("101") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.dice_background), // Replace with your image name
            contentDescription = "Dice background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // This will fill the screen and crop if needed
        )

        // Semi-transparent overlay for better text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)) // Adjust alpha for darkness
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Removed the "Duel Dice - V2" title since it's now in the background image

            Button(
                onClick = onNewGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f) // Slight transparency
                )
            ) {
                Text("New Game", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAbout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                )
            ) {
                Text("About", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showTargetDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                )
            ) {
                Text("Set Score", fontSize = 20.sp)
            }
        }
    }

    if (showTargetDialog) {
        Dialog(onDismissRequest = { showTargetDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Set Target Score", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = targetInput,
                        onValueChange = { targetInput = it },
                        label = { Text("Target Score") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Button(
                            onClick = { showTargetDialog = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                val target = targetInput.toIntOrNull()
                                if (target != null && target > 0) {
                                    onSetTarget(target)
                                    showTargetDialog = false
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Set")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Dialog(onDismissRequest = onBack) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "About",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Student ID: w2053013\nName: Sachintha Chamod Piyathunga",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "I confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onBack) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun GameScreen(
    onGameEnd: (Boolean) -> Unit,
    onBackToMenu: () -> Unit,
    humanWins: Int,
    computerWins: Int,
    targetScore: Int
) {
    // Create game manager instance
    val gameManager = remember { GameManager() }
    val coroutineScope = rememberCoroutineScope()

    // Use remember to avoid serialization issues with complex state
    var gameState by remember { mutableStateOf(GameState()) }
    var showWinDialog by remember { mutableStateOf(false) }
    var winMessage by remember { mutableStateOf("") }
    var isHumanWinner by remember { mutableStateOf(true) }

    // Handle computer turn automatically
    LaunchedEffect(gameState.gamePhase) {
        if (gameState.gamePhase == GamePhase.COMPUTER_TURN && !gameState.isRolling) {
            delay(1000) // Brief pause before computer plays
            gameState = gameManager.handleComputerTurn(
                gameState = gameState,
                targetScore = targetScore,
                onGameEnd = { humanWon, message, color ->
                    winMessage = message
                    isHumanWinner = humanWon
                    showWinDialog = true
                },
                onStateUpdate = { newState ->
                    gameState = newState
                }
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image - same as main menu
        Image(
            painter = painterResource(id = R.drawable.game_background),
            contentDescription = "Dice background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Semi-transparent overlay for better readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)) // Slightly darker for game screen
        )

        // Game content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Score display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "P:$humanWins/C:$computerWins",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Changed to white for visibility
                )
                Text(
                    text = "Player: ${gameState.humanScore} | Computer: ${gameState.computerScore}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Changed to white for visibility
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Target: $targetScore | Turn: ${gameState.currentTurn} | Rolls: ${gameState.rollCount}/3",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White // Changed to white for visibility
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Current phase indicator
            Text(
                text = when (gameState.gamePhase) {
                    GamePhase.HUMAN_TURN -> "Your Turn"
                    GamePhase.COMPUTER_TURN -> "Computer's Turn"
                    GamePhase.TIE_BREAKER -> "TIE BREAKER"
                    GamePhase.GAME_OVER -> "Game Over"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = when (gameState.gamePhase) {
                    GamePhase.HUMAN_TURN -> Color.Cyan // Changed for better visibility on dark background
                    GamePhase.COMPUTER_TURN -> Color(0xFFFF6B6B) // Light red
                    GamePhase.TIE_BREAKER -> Color(0xFFFF69B4) // Hot pink
                    GamePhase.GAME_OVER -> Color.LightGray
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dice display - Side by side
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Human dice column
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Dices",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White // Changed to white for visibility
                    )
                    DiceColumn(
                        dice = gameState.humanDice,
                        selectedDice = gameState.selectedDice,
                        onDiceClick = { index ->
                            if (gameState.canSelectDice && gameState.gamePhase == GamePhase.HUMAN_TURN) {
                                gameState = gameState.copy(
                                    selectedDice = gameState.selectedDice.toMutableList().apply {
                                        this[index] = !this[index]
                                    }
                                )
                            }
                        },
                        isRolling = gameState.isRolling && gameState.gamePhase == GamePhase.HUMAN_TURN
                    )
                }

                // Computer dice column
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Computer Dices",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White // Changed to white for visibility
                    )
                    DiceColumn(
                        dice = gameState.computerDice,
                        selectedDice = listOf(false, false, false, false, false),
                        onDiceClick = { },
                        isRolling = gameState.isRolling && gameState.gamePhase == GamePhase.COMPUTER_TURN
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Game controls - only show for human turn
            if (gameState.gamePhase == GamePhase.HUMAN_TURN || gameState.gamePhase == GamePhase.TIE_BREAKER) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                gameState = gameManager.rollHumanDice(gameState)
                            }
                        },
                        enabled = gameState.canThrow && !gameState.isRolling,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                        )
                    ) {
                        Text("Throw", fontSize = 18.sp)
                    }

                    if (gameState.gamePhase != GamePhase.TIE_BREAKER) {
                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                gameState = gameManager.scoreHumanTurn(gameState, targetScore) { humanWon, message, color ->
                                    winMessage = message
                                    isHumanWinner = humanWon
                                    showWinDialog = true
                                }
                            },
                            enabled = gameState.canScore && !gameState.isRolling,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                            )
                        ) {
                            Text("Score", fontSize = 18.sp)
                        }
                    }
                }
            } else if (gameState.gamePhase == GamePhase.COMPUTER_TURN) {
                // Show computer thinking message
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (gameState.isRolling) "Computer is rolling..." else "Computer is thinking...",
                        fontSize = 16.sp,
                        color = Color.LightGray, // Changed for better visibility
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current turn score
            val currentScore = when (gameState.gamePhase) {
                GamePhase.HUMAN_TURN -> gameState.humanDice.sum()
                GamePhase.COMPUTER_TURN -> gameState.computerDice.sum()
                GamePhase.TIE_BREAKER -> gameState.humanDice.sum()
                else -> 0
            }

            Text(
                text = "Current Turn Score: $currentScore",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = Color.White // Changed to white for visibility
            )

            // Game status
            if (gameState.gamePhase == GamePhase.TIE_BREAKER) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "TIE BREAKER - Single roll determines winner!",
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color(0xFFFF6B6B), // Light red for better visibility
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Back to Menu button
            Button(
                onClick = onBackToMenu,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f)
                )
            ) {
                Text("Back to Menu", fontSize = 18.sp)
            }
        }
    }

    if (showWinDialog) {
        Dialog(onDismissRequest = { }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = winMessage,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isHumanWinner) Color.Green else Color.Red
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            showWinDialog = false
                            onGameEnd(isHumanWinner)
                        }
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
fun DiceColumn(
    dice: List<Int>,
    selectedDice: List<Boolean>,
    onDiceClick: (Int) -> Unit,
    isRolling: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dice.forEachIndexed { index, value ->
            DiceImage(
                value = value,
                isSelected = selectedDice[index],
                onClick = { onDiceClick(index) },
                isRolling = isRolling
            )
        }
    }
}

@Composable
fun DiceImage(
    value: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    isRolling: Boolean
) {
    val rotation by animateFloatAsState(
        targetValue = if (isRolling) 360f else 0f,
        animationSpec = if (isRolling) {
            infiniteRepeatable(
                animation = tween(500, easing = LinearEasing), // Same rotation speed as computer
                repeatMode = RepeatMode.Restart
            )
        } else {
            tween(0)
        },
        label = "diceRotation"
    )

    val hasImages = diceImagesExist()

    Box(
        modifier = Modifier
            .size(60.dp)
            .rotate(if (isRolling) rotation else 0f)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Color.Red else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = if (isSelected) Color.Red.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (hasImages) {
            Image(
                painter = painterResource(id = getDiceResource(value)!!),
                contentDescription = "Dice showing $value",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        } else {
            DiceFaceDots(value = value)
        }
    }
}
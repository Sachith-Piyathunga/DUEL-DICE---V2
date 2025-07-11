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

// Helper function to get dice image resource base on the dice values
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

// Function to check if all dice image resources exist
@Composable
fun diceImagesExist(): Boolean {
    val context = LocalContext.current
    return try {
        // Try loading each dice image and if any fail it will throw an exception
        context.resources.getDrawable(R.drawable.dice_1, context.theme)
        context.resources.getDrawable(R.drawable.dice_2, context.theme)
        context.resources.getDrawable(R.drawable.dice_3, context.theme)
        context.resources.getDrawable(R.drawable.dice_4, context.theme)
        context.resources.getDrawable(R.drawable.dice_5, context.theme)
        context.resources.getDrawable(R.drawable.dice_6, context.theme)
        true    // Send true if all resources exist
    } catch (e: Exception) {
        false   // Send false at least one image is missing
    }
}

// Custom composable to visually draw a dice face with black dots based on its value
@Composable
fun DiceFaceDots(value: Int) {
    // Add box to container the dice
    Box(
        modifier = Modifier
            .size(60.dp)    // Fixed size for each dice
            .background(Color.White, RoundedCornerShape(8.dp))  // Add white background with rounded corners
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))    // Add black border around the dice
            .padding(4.dp)  // Add padding inside the dice
    ) {
        // Based on the value (1-6), draw black dots using smaller Box components
        when (value) {
            1 -> {
                // Single center dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .align(Alignment.Center)
                )
            }
            2 -> {
                // Top-left and bottom-right dots
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
                // Diagonal line of 3 dots
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
                // Four corner dots
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
                // Four corner dots and a center dot
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
                // Two columns of three dots
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

// Create a composable function for the main entry point of the game UI
@Composable
fun DiceGameApp() {
    // Keeps track of which screen to show (main menu, about, or game screen)
    var currentScreen by rememberSaveable { mutableStateOf(Screen.MAIN_MENU) }
    // Track number of wins by player and computer
    var humanWins by rememberSaveable { mutableStateOf(0) }
    var computerWins by rememberSaveable { mutableStateOf(0) }
    // Set the defult target score to win the game
    var targetScore by rememberSaveable { mutableStateOf(101) }

    // Display the appropriate screen based on current state
    when (currentScreen) {
        Screen.MAIN_MENU -> MainMenuScreen(
            onNewGame = { currentScreen = Screen.GAME },    // Navigate to the Game screen
            onAbout = { currentScreen = Screen.ABOUT },     // Navigate to theAbout screen
            onSetTarget = { target -> targetScore = target }    // Update the target score
        )
        Screen.ABOUT -> AboutScreen(    // Show the About screen
            onBack = { currentScreen = Screen.MAIN_MENU }   // Navigate back to Main Menu
        )
        Screen.GAME -> {    // Show the Game screen
            // Create a unique key for GameScreen to force recomposition on new game
            key(humanWins + computerWins) {
                GameScreen(
                    onGameEnd = { humanWon ->
                        // Update win counters based on who is won
                        if (humanWon) humanWins++ else computerWins++
                        currentScreen = Screen.MAIN_MENU    // Return back to the main menu after game ends
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

enum class Screen {     // set the enum class to represent which screen is currently active
    MAIN_MENU, ABOUT, GAME
}

// Create a composable function for the game screen
@Composable
fun MainMenuScreen(
    onNewGame: () -> Unit,      // Callback for "New Game" button
    onAbout: () -> Unit,        // Callback for "About" button
    onSetTarget: (Int) -> Unit  // Callback for setting custom target score
) {
    // Whether the "Set Target" dialog is shown
    var showTargetDialog by remember { mutableStateOf(false) }
    // Input field value for target defult score
    var targetInput by remember { mutableStateOf("101") }

    // Set the root container for the screen
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Add the background image for the better experiance
        Image(
            painter = painterResource(id = R.drawable.dice_background), // Replace image with image name
            contentDescription = "Dice background",     // Add description for accessibility
            modifier = Modifier.fillMaxSize(),       // Cover full screen with the image
            contentScale = ContentScale.Crop // This will fill the screen and crop if needed
        )

        // Semi-transparent overlay for better text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)) // Adjust alpha for darkness
        )

        // Add the column for the main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),         // Padding around the content
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center    // Center items vertically
        ) {
            // Set the NEW GAME Button
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

            Spacer(modifier = Modifier.height(16.dp))   // Set the space between buttons

            // Add the ABOUT button
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

            // Add SET SCORE button
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

    // Set the score input dialog
    if (showTargetDialog) {
        Dialog(onDismissRequest = { showTargetDialog = false }) {
            Card(   // Add the dialog card container
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(     // Add he dialog content
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Set Target Score", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    // Add the input field for custom target score
                    OutlinedTextField(
                        value = targetInput,
                        onValueChange = { targetInput = it },   // Update state on input
                        label = { Text("Target Score") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    // Set the dialog buttons
                    Row {
                        Button( // Add the cancel button
                            onClick = { showTargetDialog = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button( // Add the set score button
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

// Composable function for the displaying the "About" screen
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Dialog(onDismissRequest = onBack) { // Displays a modal dialog. Dismisses when user taps outside or presses "Close"
        // Card provides a material surface with rounded corners and padding
        Card(
            modifier = Modifier
                .fillMaxWidth()     // Makes the dialog card span full width of parent
                .padding(16.dp),    // Outer padding from screen edges
            shape = RoundedCornerShape(16.dp)    // Rounded corners for the card
        ) {
            // Content inside the card is arranged vertically
            Column(
                modifier = Modifier.padding(16.dp),     // Set the inner padding for card content
                horizontalAlignment = Alignment.CenterHorizontally  // Center-align all child elements
            ) {
                Text(       // Set the about button title: "About"
                    text = "About",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))   // Set the space between elements
                // Add my details
                Text(
                    text = "Student ID: w2053013\nName: Sachintha Chamod Piyathunga",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
                // Declaration of academic integrity
                Text(
                    text = "I confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
                // Add close button to dismiss the dialog and go back
                Button(onClick = onBack) {
                    Text("Close")
                }
            }
        }
    }
}

// Composable function for the displaying the Game screen
@Composable
fun GameScreen(
    onGameEnd: (Boolean) -> Unit,   // Set the callback to notify whether human won
    onBackToMenu: () -> Unit,       // Set the callback to return to main menu
    humanWins: Int,                 // Set the number of times the player has won
    computerWins: Int,              // Set the number of times the computer has won
    targetScore: Int                // Set the score required to win the game
) {
    // Create a GameManager instance and a coroutine scope for operations
    val gameManager = remember { GameManager() }
    val coroutineScope = rememberCoroutineScope()

    // Set the mutable states to hold current game data and result dialog status
    var gameState by remember { mutableStateOf(GameState()) }
    var showWinDialog by remember { mutableStateOf(false) }
    var winMessage by remember { mutableStateOf("") }
    var isHumanWinner by remember { mutableStateOf(true) }

    // When the game phase changes to COMPUTER_TURN, automatically play the computer's turn
    LaunchedEffect(gameState.gamePhase) {
        if (gameState.gamePhase == GamePhase.COMPUTER_TURN && !gameState.isRolling) {
            delay(1000) // Wait a bit before computer starts playing
            gameState = gameManager.handleComputerTurn(
                gameState = gameState,
                targetScore = targetScore,
                onGameEnd = { humanWon, message, color ->
                    winMessage = message        // Set win/lose message
                    isHumanWinner = humanWon    // Track who won
                    showWinDialog = true        // Trigger win dialog
                },
                onStateUpdate = { newState ->
                    gameState = newState        // Update state in real time
                }
            )
        }
    }
    // Outer box with background
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Set the background image - same as main menu
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

        // Set the main vertical column for game content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Set the win counters for Player and Computer
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
            // Set the target score, current turn, and number of rolls used
            Text(
                text = "Target: $targetScore | Turn: ${gameState.currentTurn} | Rolls: ${gameState.rollCount}/3",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White // Changed to white for visibility
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display the current phase of the game
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

            // Display the player and Computer dices side by side
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Set the column for Player dice
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
                    // Create the dice UI for player
                    DiceColumn(
                        dice = gameState.humanDice,
                        selectedDice = gameState.selectedDice,
                        onDiceClick = { index ->
                            // Allows player to select dice only during his turn
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

                // Set the column for Computer dice
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
                    // Create the dice UI for computer
                    DiceColumn(
                        dice = gameState.computerDice,
                        selectedDice = listOf(false, false, false, false, false),
                        onDiceClick = { },  // Set the no click functionality for computer dice
                        isRolling = gameState.isRolling && gameState.gamePhase == GamePhase.COMPUTER_TURN
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Game controls - only show when it's the player's turn or tie breaker round
            if (gameState.gamePhase == GamePhase.HUMAN_TURN || gameState.gamePhase == GamePhase.TIE_BREAKER) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Create a throw Button - triggers rolling of selected dice
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                gameState = gameManager.rollHumanDice(gameState)    // Launch dice roll
                            }
                        },
                        enabled = gameState.canThrow && !gameState.isRolling,   // Only enable when allowed and not already rolling
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                        )
                    ) {
                        Text("Throw", fontSize = 18.sp)
                    }
                    // If not a tie-breaker, show Score button
                    if (gameState.gamePhase != GamePhase.TIE_BREAKER) {
                        Spacer(modifier = Modifier.width(16.dp))
                        // Set the score Button - end the player's turn and evaluate score
                        Button(
                            onClick = {
                                gameState = gameManager.scoreHumanTurn(gameState, targetScore) { humanWon, message, color ->
                                    winMessage = message        // Show who won and why
                                    isHumanWinner = humanWon    // Track winner
                                    showWinDialog = true        // Open dialog
                                }
                            },
                            enabled = gameState.canScore && !gameState.isRolling,   // Only enable when scoring is allowed
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
                // Display a message when it's the computer's turn
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

            Spacer(modifier = Modifier.height(16.dp))   // Add the spacing before the next section

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
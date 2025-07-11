
// Package declaration
package com.example.dueldice

// Import necessary libraries for UI color, coroutine delays, and random number generations
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random

// Define the phases of the game
enum class GamePhase {
    HUMAN_TURN, COMPUTER_TURN, GAME_OVER, TIE_BREAKER
}

// Represent the complete state of the game
data class GameState(
    val humanDice: List<Int> = listOf(1, 1, 1, 1, 1),   // Dice values for human
    val computerDice: List<Int> = listOf(1, 1, 1, 1, 1),    // Dice values for computer
    val selectedDice: List<Boolean> = listOf(false, false, false, false, false),    // Dice selected by player to keep
    val humanScore: Int = 0,            // Total score of the human player
    val computerScore: Int = 0,         // Total score of the computer
    val currentTurn: Int = 1,           // Current turn number
    val rollCount: Int = 0,             // Number of times dice has been rolled in perticular turn
    val canThrow: Boolean = true,       // Whether the player can throw dice
    val canScore: Boolean = false,      // Whether the player can score this turn
    val canSelectDice: Boolean = false, // Whether the player can select dice
    val isRolling: Boolean = false,     // Whether dice rolling animation is ongoing
    val gamePhase: GamePhase = GamePhase.HUMAN_TURN,    // Current phase of the game
    val isTieBreaker: Boolean = false   // Whether the game is currently in a tie breaker round
)

// Create a class to handles the full logic of the game
class GameManager {

    /*
    COMPUTER STRATEGY IMPLEMENTATION;

    The computer strategy is designed to be efficient and adaptive based on the current game state.
    The strategy considers:
    1. Current score difference between computer and human
    2. Remaining turns needed to reach target
    3. Expected value of current dice vs. potential reroll
    4. Risk assessment based on game position

    Strategy Logic:
    - If computer is significantly behind, take more risks (reroll more often)
    - If computer is ahead, play more conservatively
    - Calculate expected value of keeping current dice vs. rerolling
    - Use probability-based decision making for optimal play

    The strategy implementation uses the following approach:
    1. Always keep dice showing 5 or 6 (high value)
    2. Keep dice showing 4 if ahead significantly or close to target
    3. Keep dice showing 3 only if significantly ahead
    4. Be more aggressive when behind the player - this is random dessition
    5. Play conservatively in other situations
    */

    // This function to handle player's dice roll
    suspend fun rollHumanDice(gameState: GameState): GameState {
        // Set rolling animation (Starting point)
        val newState = gameState.copy(isRolling = true)

        delay(1000) // Animation delay (exactly same as computer)

        // Generate new dice values and the keep selected ones if not the first roll
        val newHumanDice = gameState.humanDice.mapIndexed { index, value ->
            if (gameState.rollCount == 0 || !gameState.selectedDice[index]) {
                Random.nextInt(1, 7)    // Roll the new dice value
            } else {
                value   // Keep the existing die
            }
        }

        // Update game state after rolling - player and computer
        val updatedState = newState.copy(
            humanDice = newHumanDice,
            rollCount = gameState.rollCount + 1,
            selectedDice = listOf(false, false, false, false, false),   // Reset the selection
            canThrow = gameState.rollCount < 2, // Only allow max 3 rolls
            canScore = true,
            canSelectDice = gameState.rollCount < 2,
            isRolling = false
        )

        // If roll limit reached or in tie breaker, skip to computer turn
        return if (updatedState.rollCount >= 3 || gameState.gamePhase == GamePhase.TIE_BREAKER) {
            if (gameState.gamePhase == GamePhase.TIE_BREAKER) {
                // In tie breaker, immediately transition to computer turn
                updatedState.copy(
                    gamePhase = GamePhase.COMPUTER_TURN,
                    canThrow = false,
                    canScore = false,
                    canSelectDice = false
                )
            } else {
                // Normal game flow
                updatedState.copy(
                    gamePhase = GamePhase.COMPUTER_TURN,
                    canThrow = false,
                    canScore = false,
                    canSelectDice = false
                )
            }
        } else {
            updatedState    // Continue human turn
        }
    }

    // Human (player) turn score function (currently just changes game phase)
    fun scoreHumanTurn(
        gameState: GameState,
        targetScore: Int,
        onGameEnd: (Boolean, String, Color) -> Unit
    ): GameState {
        return gameState.copy(
            gamePhase = GamePhase.COMPUTER_TURN,
            canThrow = false,
            canScore = false,
            canSelectDice = false
        )
    }

    // Handles the computer's full turn
    suspend fun handleComputerTurn(
        gameState: GameState,
        targetScore: Int,
        onGameEnd: (Boolean, String, Color) -> Unit,
        onStateUpdate: (GameState) -> Unit // Add callback to update UI state
    ): GameState {
        // Start computer turn with rolling animation
        var currentState = gameState.copy(isRolling = true)
        onStateUpdate(currentState)

        delay(1000) // Computer thinking time

        // Computer plays its turn (up to 3 rolls)
        var computerRolls = 0
        var computerDice = listOf(1, 1, 1, 1, 1)

        // First roll - show rolling animation
        currentState = currentState.copy(isRolling = true)
        onStateUpdate(currentState)

        delay(1000) // Rolling animation time

        computerDice = List(5) { Random.nextInt(1, 7) }
        computerRolls++

        // Update state with result of first roll and stop rolling animation
        currentState = currentState.copy(
            computerDice = computerDice,
            isRolling = false
        )
        onStateUpdate(currentState)

        delay(500) // Brief pause to see the result

        // Computer strategy for additional rolls (only if not in tie breaker)
        if (!gameState.isTieBreaker) {
            while (computerRolls < 3) {
                val shouldReroll = computerShouldReroll(computerDice, currentState.computerScore, currentState.humanScore, targetScore)
                if (!shouldReroll) break

                // Show rolling animation for reroll
                currentState = currentState.copy(isRolling = true)
                onStateUpdate(currentState)

                delay(1000) // Rolling animation time

                val keepDice = computerStrategy(computerDice, currentState.computerScore, currentState.humanScore, targetScore)
                computerDice = computerDice.mapIndexed { index, value ->
                    if (keepDice[index]) value else Random.nextInt(1, 7)
                }
                computerRolls++

                // Update state with new dice and stop rolling animation
                currentState = currentState.copy(
                    computerDice = computerDice,
                    isRolling = false
                )
                onStateUpdate(currentState)

                delay(500) // Brief pause to see the result
            }
        }

        // Calculate total scores
        val humanTurnScore = currentState.humanDice.sum()
        val computerTurnScore = computerDice.sum()
        val newHumanScore = currentState.humanScore + humanTurnScore
        val newComputerScore = currentState.computerScore + computerTurnScore

        currentState = currentState.copy(
            computerDice = computerDice,
            humanScore = newHumanScore,
            computerScore = newComputerScore,
            isRolling = false
        )

        // Check tie breaker logic and end game conditions
        if (currentState.isTieBreaker) {
            when {
                humanTurnScore > computerTurnScore -> {
                    onGameEnd(true, "You Win!", Color.Green)
                    return currentState.copy(gamePhase = GamePhase.GAME_OVER)
                }
                computerTurnScore > humanTurnScore -> {
                    onGameEnd(false, "You Lose!", Color.Red)
                    return currentState.copy(gamePhase = GamePhase.GAME_OVER)
                }
                else -> {
                    // Continue tie breaker
                    return currentState.copy(
                        humanDice = listOf(1, 1, 1, 1, 1),
                        computerDice = listOf(1, 1, 1, 1, 1),
                        rollCount = 0,
                        gamePhase = GamePhase.TIE_BREAKER,
                        canThrow = true,
                        canScore = false,
                        canSelectDice = false
                    )
                }
            }
        }

        // Add the regular game-over conditions
        val humanReachedTarget = newHumanScore >= targetScore
        val computerReachedTarget = newComputerScore >= targetScore

        when {
            humanReachedTarget && computerReachedTarget -> {
                when {
                    newHumanScore > newComputerScore -> {
                        onGameEnd(true, "You Win!", Color.Green)
                        return currentState.copy(gamePhase = GamePhase.GAME_OVER)
                    }
                    newComputerScore > newHumanScore -> {
                        onGameEnd(false, "You Lose!", Color.Red)
                        return currentState.copy(gamePhase = GamePhase.GAME_OVER)
                    }
                    else -> {
                        // Start tie breaker
                        return currentState.copy(
                            humanDice = listOf(1, 1, 1, 1, 1),
                            computerDice = listOf(1, 1, 1, 1, 1),
                            rollCount = 0,
                            gamePhase = GamePhase.TIE_BREAKER,
                            isTieBreaker = true,
                            canThrow = true,
                            canScore = false,
                            canSelectDice = false
                        )
                    }
                }
            }
            humanReachedTarget -> {
                onGameEnd(true, "You Win!", Color.Green)
                return currentState.copy(gamePhase = GamePhase.GAME_OVER)
            }
            computerReachedTarget -> {
                onGameEnd(false, "You Lose!", Color.Red)
                return currentState.copy(gamePhase = GamePhase.GAME_OVER)
            }
            else -> {
                // Continue to next player turn
                return currentState.copy(
                    humanDice = listOf(1, 1, 1, 1, 1),
                    computerDice = listOf(1, 1, 1, 1, 1),
                    rollCount = 0,
                    currentTurn = currentState.currentTurn + 1,
                    gamePhase = GamePhase.HUMAN_TURN,
                    canThrow = true,
                    canScore = false,
                    canSelectDice = false
                )
            }
        }
    }

    // Determines whether computer should reroll based on score difference and current total
    private fun computerShouldReroll(
        currentDice: List<Int>,
        computerScore: Int,
        humanScore: Int,
        targetScore: Int
    ): Boolean {
        val currentSum = currentDice.sum()

        // Don't reroll if current sum is already good
        if (currentSum >= 25) return false

        // More likely to reroll if behind
        val scoreDifference = computerScore - humanScore
        val threshold = when {
            scoreDifference < -20 -> 15 // Very aggressive when far behind
            scoreDifference < 0 -> 18   // Aggressive when behind
            scoreDifference > 20 -> 22  // Conservative when far ahead
            else -> 20                  // Normal play
        }

        return currentSum < threshold
    }

    // Returns a list of which dice should be kept (true) based on computer strategy
    private fun computerStrategy(
        currentDice: List<Int>,
        computerScore: Int,
        humanScore: Int,
        targetScore: Int
    ): List<Boolean> {
        val scoreDifference = computerScore - humanScore

        return currentDice.map { die ->
            val isAheadSignificantly = scoreDifference > 10
            val isBehindSignificantly = scoreDifference < -10
            val isCloseToTarget = computerScore >= targetScore - 30

            when {
                // Always keep 6s and 5s
                die >= 5 -> true
                // Keep 4s if we're ahead or close to target
                die == 4 && (isAheadSignificantly || isCloseToTarget) -> true
                // Keep 3s only if significantly ahead
                die == 3 && isAheadSignificantly -> true
                // Be more aggressive if behind
                die >= 3 && isBehindSignificantly -> Random.nextBoolean()
                // Conservative play otherwise
                else -> false
            }
        }
    }
}
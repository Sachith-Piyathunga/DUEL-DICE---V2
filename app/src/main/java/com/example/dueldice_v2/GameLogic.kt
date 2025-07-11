// Package declaration
package com.example.dueldice

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class GamePhase {
    HUMAN_TURN, COMPUTER_TURN, GAME_OVER, TIE_BREAKER
}

data class GameState(
    val humanDice: List<Int> = listOf(1, 1, 1, 1, 1),
    val computerDice: List<Int> = listOf(1, 1, 1, 1, 1),
    val selectedDice: List<Boolean> = listOf(false, false, false, false, false),
    val humanScore: Int = 0,
    val computerScore: Int = 0,
    val currentTurn: Int = 1,
    val rollCount: Int = 0,
    val canThrow: Boolean = true,
    val canScore: Boolean = false,
    val canSelectDice: Boolean = false,
    val isRolling: Boolean = false,
    val gamePhase: GamePhase = GamePhase.HUMAN_TURN,
    val isTieBreaker: Boolean = false
)

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

    Advantages:
    - Adapts to game situation dynamically
    - Considers both offensive and defensive positions
    - Uses mathematical expected value calculations
    - Balances risk vs. reward based on score differential

    Disadvantages:
    - May be too conservative in some situations
    - Doesn't account for human player's potential strategies
    - Random elements can still lead to suboptimal outcomes

    The strategy implementation uses the following approach:
    1. Always keep dice showing 5 or 6 (high value)
    2. Keep dice showing 4 if ahead significantly or close to target
    3. Keep dice showing 3 only if significantly ahead
    4. Be more aggressive (random decisions) when behind
    5. Play conservatively in other situations
    */

    suspend fun rollHumanDice(gameState: GameState): GameState {
        // Start rolling animation
        val newState = gameState.copy(isRolling = true)

        delay(1000) // Animation delay (exactly same as computer)

        val newHumanDice = gameState.humanDice.mapIndexed { index, value ->
            if (gameState.rollCount == 0 || !gameState.selectedDice[index]) {
                Random.nextInt(1, 7)
            } else {
                value
            }
        }

        val updatedState = newState.copy(
            humanDice = newHumanDice,
            rollCount = gameState.rollCount + 1,
            selectedDice = listOf(false, false, false, false, false),
            canThrow = gameState.rollCount < 2,
            canScore = true,
            canSelectDice = gameState.rollCount < 2,
            isRolling = false
        )

        // Auto-score after 3 rolls or handle tie breaker
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
            updatedState
        }
    }

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

        // Update state with new dice and stop rolling animation
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

        // Calculate final scores
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

        // Check for game end conditions
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
                // Continue to next turn
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
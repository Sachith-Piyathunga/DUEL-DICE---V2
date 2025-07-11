# 🎲 Duel Dice - Android Dice Game

A modern Android dice game built with Jetpack Compose where players compete against an intelligent computer opponent to reach a target score first.

## 📱 Features

### Core Gameplay
- **Player vs Computer**: Strategic dice rolling game with intelligent AI opponent
- **Customizable Target Score**: Set your own winning score (default: 101)
- **Rolling Strategy**: Choose which dice to keep and which to reroll (max 3 rolls per turn)
- **Tie Breaker System**: Sudden death round when both players reach the target simultaneously
- **Win Tracking**: Persistent score tracking across multiple games

### Visual & UI Features
- **Beautiful Background**: Stunning purple dice-themed background throughout the app
- **Smooth Animations**: Synchronized dice rolling animations for both player and computer
- **Modern Material Design**: Clean, intuitive interface with Jetpack Compose
- **Responsive Layout**: Optimized for various Android screen sizes
- **Visual Feedback**: Clear indication of selected dice, game phases, and current scores

### Technical Features
- **State Management**: Robust game state handling with automatic persistence
- **Coroutine Integration**: Smooth asynchronous operations for animations and AI turns
- **Adaptive AI Strategy**: The Computer opponent adjusts its strategy based on the game situation
- **Fallback Graphics**: Custom dot-based dice rendering when images are unavailable

## 🎯 How to Play

### Basic Rules
1. **Objective**: Be the first to reach the target score (default: 101 points)
2. **Turn Structure**: Each turn consists of up to 3 dice rolls
3. **Dice Selection**: After each roll, choose which dice to keep for the next roll
4. **Scoring**: The Sum of all 5 dice is added to your total score
5. **Strategy**: Balance risk vs reward - reroll for higher scores or play it safe

### Game Flow
1. **Player Turn**:
   - Roll all 5 dice initially
   - Select the dice you want to keep (tap to select/deselect)
   - Reroll unselected dice (up to 2 more times)
   - Choose "Score" to end turn and add dice sum to total
  
2. **Computer Turn**:
   - The computer automatically plays with an intelligent strategy
   - Watch dice roll animations and strategic decisions
   - The computer considers the score difference and risk assessment


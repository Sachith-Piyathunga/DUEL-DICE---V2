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

---

<div align="center">
  <img src="https://github.com/Sachith-Piyathunga/DUEL-DICE---V2/blob/ddb7be36d94f00df79e0076473e4793783d4a4be/Image/Main%20Screen.png?raw=true" alt="Home Screen" width="45%"/>
  <img src="https://github.com/Sachith-Piyathunga/DUEL-DICE---V2/blob/f9a7c56e0f7f4dedc79401b003c8c5aaa00b11ff/Image/Game%20Screen.png?raw=true" alt="Game Screen" width="45%"/>
</div>

---
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

3. **Winning Conditions**:
   - First to reach the target score wins
   - If both reach the target in the same round, the highest score wins
   - If tied exactly, a sudden-death tie-breaker round

### Controls
- **Tap Dice**: Select/deselect dice to keep for next roll
- **Throw Button**: Roll unselected dice
- **Score Button**: End turn and add current dice sum to total
- **Back to Menu**: Return to main menu anytime

## 🏗️ Technical Architecture

### Project Structure
```
app/src/main/java/com/example/dueldice/
├── MainActivity.kt          # Main activity and UI components
├── GameLogic.kt            # Game state management and logic
└── res/
    └── drawable/
        ├── dice_background.png    # Main background image
        ├── game_background.png    # Game screen background
        ├── dice_1.png            # Dice face images (optional)
        ├── dice_2.png
        ├── dice_3.png
        ├── dice_4.png
        ├── dice_5.png
        └── dice_6.png
```

### Key Components

#### GameLogic.kt
- **GameState**: Data class holding complete game state
- **GamePhase**: Enum for different game phases (HUMAN_TURN, COMPUTER_TURN, etc.)
- **GameManager**: Core game logic handler with methods:
  - `rollHumanDice()`: Handles player dice rolling
  - `handleComputerTurn()`: Manages computer AI gameplay
  - `computerStrategy()`: AI decision making for dice selection
  - `computerShouldReroll()`: AI risk assessment for rerolling

#### MainActivity.kt
- **DiceGameApp**: Main navigation controller
- **MainMenuScreen**: Welcome screen with background and buttons
- **GameScreen**: Main gameplay interface
- **AboutScreen**: Developer information
- **DiceImage**: Individual dice rendering with animations
- **DiceFaceDots**: Fallback dice rendering using dots

## 🚀 Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API level 21 or higher
- Kotlin 1.7+
- Jetpack Compose BOM 2023.03.00+

### Installation Steps

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd duel-dice
   ```

2. **Open in Android Studio**:
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory

3. **Add Background Images**:
   - Download the provided dice background images
   - Place them in `app/src/main/res/drawable/`:
     - `dice_background.png` (main menu background)
     - `game_background.png` (game screen background)
     - Optional: `dice_1.png` through `dice_6.png` (dice face images)

4. **Build and Run**:
   ```bash
   ./gradlew assembleDebug
   ```
   Or use Android Studio's "Run" button

## 🎨 Customization

### Changing Target Score
- Use "Set Score" button on the main menu
- Enter any positive integer
- Default: 101 points

### Visual Customization
- Replace background images in `res/drawable/`
- Modify colors in theme definitions
- Adjust dice size by changing `.size(60.dp)` values
- Customize animations by modifying `tween()` duration values

### AI Difficulty
Modify computer strategy in `GameLogic.kt`:
- Adjust threshold values in `computerShouldReroll()`
- Change dice keeping logic in `computerStrategy()`
- Modify aggression levels based on score difference

## 🤝 Contributing

### Code Style
- Follow Android Kotlin style guidelines
- Use meaningful variable and function names
- Add comprehensive comments for complex logic
- Maintain consistent indentation 

### Adding Features
1. Fork the repository
2. Create feature branch: `git checkout -b feature-name`
3. Implement changes with tests
4. Submit a pull request with a detailed description

*Built with ❤️ using Jetpack Compose and Kotlin - if you like this repo give me a star*




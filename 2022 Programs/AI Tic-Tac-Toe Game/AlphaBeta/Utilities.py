from math import inf as infinity
from random import choice
import platform
import time
from os import system

from AlphaBeta import *

HUMAN = -1
COMP = +1
board = [
    [0, 0, 0],
    [0, 0, 0],
    [0, 0, 0]
]


def evaluate_score(state):
    #Function to evaluate the score of a given state.
    if winning_player(state, COMP):
        score = +1
    elif winning_player(state, HUMAN):
        score = -1
    else:
        score = 0

    return score


def winning_player(state, player):
    #Function tests if given player wins.
    winning_states = [
        [state[0][0], state[0][1], state[0][2]],
        [state[1][0], state[1][1], state[1][2]],
        [state[2][0], state[2][1], state[2][2]],
        [state[0][0], state[1][0], state[2][0]],
        [state[0][1], state[1][1], state[2][1]],
        [state[0][2], state[1][2], state[2][2]],
        [state[0][0], state[1][1], state[2][2]],
        [state[2][0], state[1][1], state[0][2]],
    ]
    if [player, player, player] in winning_states:
        return True
    else:
        return False


def game_over(state):
    #Function returns the winner of the game (human or computer).
    return winning_player(state, HUMAN) or winning_player(state, COMP)


def empty_cells(state):
    #Function returns a list of the empty cells of the board.
    cells = []

    for x, row in enumerate(state):
        for y, cell in enumerate(row):
            if cell == 0:
                cells.append([x, y])
    return cells


def valid_move(x, y):
    #Function tests if given cell is valid/empty.
    if [x, y] in empty_cells(board):
        return True
    else:
        return False


def set_move(x, y, player):
    #Function updates board for given player if the given space is valid. 
    if valid_move(x, y):
        board[x][y] = player
        return True
    else:
        return False


def clear_console():
    #Clears the console
    os_name = platform.system().lower()
    if 'windows' in os_name:
        system('cls')
    else:
        system('clear')


def draw_game(state, c_choice, h_choice):
    #Print the board/game.
    chars = {-1: h_choice, +1: c_choice, 0: ' '}
    str_line = '---------------'

    print('\n' + str_line)
    for row in state:
        for cell in row:
            symbol = chars[cell]
            print(f'| {symbol} |', end='')
        print('\n' + str_line)


def display_results(state, c_choice, h_choice):
    if winning_player(state, HUMAN):
        clear_console()
        print(f'Human turn [{h_choice}]')
        draw_game(board, c_choice, h_choice)
        print('YOU WIN!')
    elif winning_player(board, COMP):
        clear_console()
        print(f'Computer turn [{c_choice}]')
        draw_game(board, c_choice, h_choice)
        print('YOU LOSE!')
    else:
        clear_console()
        draw_game(board, c_choice, h_choice)
        print('DRAW!')


def ai_turn(c_choice, h_choice):
    #AI logic for computer player.
    #If (depth < 9),then it calls the Alpha-Beta Prunning algorithm.
    #Else it chooses a random coordinate.
    depth = len(empty_cells(board))
    if depth == 0 or game_over(board):
        return

    clear_console()
    print(f'Computer turn [{c_choice}]')
    draw_game(board, c_choice, h_choice)

    if depth == 9:
        x = choice([0, 1, 2])
        y = choice([0, 1, 2])
    else:
        move = MiniMaxAB(board, depth, -infinity, infinity, COMP)
        x, y = move[0], move[1]

    set_move(x, y, COMP)
    time.sleep(1)


def human_turn(c_choice, h_choice):
    #The Human player chooses a valid move.
    depth = len(empty_cells(board))
    if depth == 0 or game_over(board):
        return

    # Dictionary of valid moves
    move = -1
    moves = {
        1: [0, 0], 2: [0, 1], 3: [0, 2],
        4: [1, 0], 5: [1, 1], 6: [1, 2],
        7: [2, 0], 8: [2, 1], 9: [2, 2],
    }

    clear_console()
    print(f'Human turn [{h_choice}]')
    draw_game(board, c_choice, h_choice)

    while move < 1 or move > 9:
        try:
            move = int(input('Use numpad (1..9): '))
            coord = moves[move]
            can_move = set_move(coord[0], coord[1], HUMAN)

            if not can_move:
                print('Location Filled. Try Again.')
                move = -1
        except (EOFError, KeyboardInterrupt):
            print('Game Over')
            exit()
        except (KeyError, ValueError):
            print('Choose a number!')
            
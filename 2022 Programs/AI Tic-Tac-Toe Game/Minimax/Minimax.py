from Utilities import *

"""
An implementation of Minimax AI Algorithm in Tic Tac Toe,
using Python.
This software is available under GPL license.
Author: Clederson Cruz
Year: 2017
License: GNU GENERAL PUBLIC LICENSE (GPL)
"""

HUMAN = -1
COMP = +1
board = [
    [0, 0, 0],
    [0, 0, 0],
    [0, 0, 0],
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


def minimax(state, depth, player):
    #AI function that chooses the best move.
    row, col = -1, -1
    if player == COMP:
        best = [row, col, -infinity]
    else:
        best = [row, col, +infinity]

    if depth == 0 or game_over(state):
        score = evaluate_score(state)
        return [row, col, score]
    for cell in empty_cells(state):
        x, y = cell[0], cell[1]
        state[x][y] = player
        score = minimax(state, depth - 1, -player)
        state[x][y] = 0
        score[0], score[1] = x, y

        if player == COMP:
            if score[2] > best[2]:
                best = score  # max value
        else:
            if score[2] < best[2]:
                best = score  # min value
    return best

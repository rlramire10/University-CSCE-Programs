import argparse
import numpy as np

from forward_checking import *
from AC3 import *
from Utility import *

def flatten(seqs):
    return sum(seqs, [])
    
_R3 = list(range(3))
_CELL = itertools.count().__next__
_BGRID = [[[[_CELL() for x in _R3] for y in _R3] for bx in _R3] for by in _R3]
_BOXES = flatten([list(map(flatten, brow)) for brow in _BGRID])
_ROWS = flatten([list(map(flatten, zip(*brow))) for brow in _BGRID])
_COLS = list(zip(*_ROWS))

_NEIGHBORS = {v: set() for v in flatten(_ROWS)}
for unit in map(set, _BOXES + _ROWS + _COLS):
    for v in unit:
        _NEIGHBORS[v].update(unit - {v})
        
def different_values_constraint(A, a, B, b):
        """A constraint saying two neighboring variables must differ in value."""
        return a != b

class Sudoku(CSP):
    R3 = _R3
    Cell = _CELL
    bgrid = _BGRID
    boxes = _BOXES
    rows = _ROWS
    cols = _COLS
    neighbors = _NEIGHBORS
    
    def __init__(self, grid):
        """Build a Sudoku problem from a string representing the grid:
        the digits 1-9 denote a filled cell, '.' or '0' an empty one;
        other characters are ignored."""
        squares = iter(re.findall(r'\d|\.', grid))
        domains = {var: [ch] if ch in '123456789' else '123456789'
                   for var, ch in zip(flatten(self.rows), squares)}
        for _ in squares:
            raise ValueError("Not a Sudoku grid", grid)  # Too many squares
        CSP.__init__(self, None, domains, self.neighbors, different_values_constraint)

    def display(self, assignment):
        def show_box(box): return [' '.join(map(show_cell, row)) for row in box]

        def show_cell(cell): return str(assignment.get(cell, '0'))

        def abut(lines1, lines2): return list(
            map(' | '.join, list(zip(lines1, lines2)))
            )
            
        print('\n------+-------+------\n'.join(
            '\n'.join(reduce(
                abut, map(show_box, brow))) for brow in self.bgrid))

def main():
    #Read Sudoku From initial_S.txt File
    with open('initial_S.txt') as file:
        input_Sudoku = [[int(digit) for digit in line.split()] for line in file]
    
    #Format Input For Sudoku_solver.py
    array = [0]*81
    counter = 0
    for row in input_Sudoku:
        for elem in row:
            array[counter] = elem
            counter += 1
    sudoku03 = str(array)[1:-1]
    
    #Display Unsolved Sodoku Puzzle
    initial_S = Sudoku(sudoku03)
    print("Unsolved Sudoku Puzzle:")
    initial_S.display(initial_S.infer_assignment())
    
    #Run BT+FC Algorithm
    print ("")
    backtracking_search(initial_S, select_unassigned_variable=mrv, inference=forward_checking) is not None
    print ("Solved Sudoku Puzzle (BT+FC):")
    initial_S.display(initial_S.infer_assignment())
    
    #Run AC3 Algorithm
    print("")
    initial_S = Sudoku(sudoku03)
    AC3(initial_S)
    print("Solved Sudoku Puzzle (AC3):")
    initial_S.display(initial_S.infer_assignment())
    
if __name__ == '__main__':
    main()
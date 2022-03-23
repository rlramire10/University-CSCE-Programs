from Utilities import *

def main():
    #Main function
    clear_console()
    h_choice = ''  # X or O
    c_choice = ''  # X or O
    first = ''  # if human is the first


    #Human chooses symbol to play as (X or O).
    while h_choice != 'O' and h_choice != 'X':
        try:
            print('')
            h_choice = input('Choose X or O\nChosen: ').upper()
        except (EOFError, KeyboardInterrupt):
            print('Application Closed')
            exit()
        except (KeyError, ValueError):
            print('Bad choice. Try Again.')


    #Setting computer's symbol choice
    if h_choice == 'X':
        c_choice = 'O'
    else:
        c_choice = 'X'


    #Chooses starting player
    clear_console()
    while first != 'Y' and first != 'N':
        try:
            first = input('First to start?[y/n]: ').upper()
        except (EOFError, KeyboardInterrupt):
            print('Application Closed')
            exit()
        except (KeyError, ValueError):
            print('Bad choice. Try Again.')


    #Main game loop
    while len(empty_cells(board)) > 0 and not game_over(board):
        if first == 'N':
            ai_turn(c_choice, h_choice)
            first = ''

        human_turn(c_choice, h_choice)
        ai_turn(c_choice, h_choice)


    # Game over message
    display_results(board, c_choice, h_choice)

    exit()


if __name__ == '__main__':
    main()
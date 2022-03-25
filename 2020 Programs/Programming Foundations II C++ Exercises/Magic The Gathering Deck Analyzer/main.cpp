/* 
 * File:   main.cpp
 * Author: Rudy Ramirez
 *
 * Created on January 22, 2020, 8:04 PM
 */

#include <iostream>
#include <cstdlib>
#include <fstream>
#include <string>
#include "Magic.h"
#include "MagicDeck.h"

using namespace std;
/*
 * The goal of this programming project is to create a program that Magic 
 * lovers could use to keep track of the Magic cards in their collection.
 */
int main()
{
    //Declare Deck of Magic Cards
    MagicDeck deck1;
    
    //Reads Information from txt file into the digital deck.
    deck1.ReadDeck();
    
    //Prints all card information inside the Magic Deck.
    deck1.PrintDeck();
    
    //Adds up all the Current Values of the Cards and displays the value.
    deck1.TotalValue();
    
    //Displays the card with the highest Current Value along with the value.
    deck1.MostValuable();
    
    //Displays the card with the highest Quantity Value along with the value.
    deck1.MostNumerous();
 
    return 0;
}
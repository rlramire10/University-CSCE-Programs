/* 
 * File:   Text.cpp
 * Author: Rudy Ramirez
 * 
 * Created on February 22, 2020, 10:06 PM
 */

#include "Text.h"

//Constructor
Text::Text() 
{
    Rank = 0;
    Word = "";
}
//Copy Constructor
Text::Text(const Text& orig)
{
    Rank = orig.Rank;
    Word = orig.Word;
}
//Destructor
Text::~Text(){
}
//Getters
int Text::getRank()
{
    return Rank;
}
string Text::getWord()
{
    return Word;
}
//Setters
void Text::setRank(int rank)
{
    Rank = rank;
}
void Text::setWord(string word)
{
    Word = word;
}
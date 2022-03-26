/* 
 * File:   Text.h
 * Author: Rudy Ramirez
 *
 * Created on February 22, 2020, 10:06 PM
 */

#include <fstream>
#include <string>
#include <locale>
#include <cstdlib>
#include <iostream>
using namespace std;

#ifndef TEXT_H
#define TEXT_H

class Text
{
public:
    Text();
    Text(const Text& orig);
    ~Text();
    int getRank();
    string getWord();
    void setRank(int rank);
    void setWord(string word);
private:
    int Rank;
    string Word;
};

#endif /* TEXT_H */
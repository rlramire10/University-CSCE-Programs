/* 
 * File:   Dictionary.h
 * Author: Rudy Ramirez
 *
 * Created on February 22, 2020, 9:41 PM
 */
#include "Text.h"

#ifndef DICTIONARY_H
#define DICTIONARY_H

const int MAXSIZE = 1000;

class Dictionary
{
public:
    Dictionary();
    Dictionary(const Dictionary& orig);
    ~Dictionary();
    string CheckCase(string original_word);
    void CreateDatabase();
    int BinarySearch(Text data[], string target_word, int low, int high);
    void ReadFile01(string filename);
    void ReadFile02(string filename);
    float CalculateReadLevel();
private:
    Text database[MAXSIZE];
    Text text[MAXSIZE];
    float ReadingLevel;
};
#endif /* DICTIONARY_H */
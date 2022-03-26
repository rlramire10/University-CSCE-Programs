/* 
 * File:   Book.h
 * Author: Rudy Ramirez
 *
 * Created on April 9, 2020, 5:26 PM
 */

#include <fstream>
#include <string>
#include <locale>
#include <vector>
#include <cstdlib>
#include <iostream>
using namespace std;

#ifndef BOOK_H
#define BOOK_H

const int MAXSIZE = 1000000000;

class Book
{
    public:
        Book();
        Book(const Book& orig);
        ~Book();
        
        vector <string> &getWords();
        int getWordCount();
        void setWords(vector <string> &text);
        void setWordCount(int count);
        
        void ReadFile(string filename);
        string CheckCase(string original_word);
        void Print();
        
        void CreateBookCountFile();
    private:
        vector <string> words;
        int wordcount;
        int frequency;
};

#endif /* BOOK_H */


/* 
 * File:   main.cpp
 * Author: Rudy Ramirez
 *
 * Created on February 21, 2020, 1:01 PM
 */

#include "Text.h"
#include "Dictionary.h"

/*
 * The goal of this programming assignment is to gain experience with
 * recursive binary search, reading and processing text files, and object
 * oriented design. Your task is to write a program that reads a document 
 * written in English, and calculates the estimated reading level of the 
 * document based on the rarity of words used. Documents that use more common
 * words will have a lower reading level than documents that use very rare
 * words.
 */

int main()
{
    //Create empty array for main file to use.
    Dictionary book;
    
    string file;
    float reading_level = 0.0;
    
    
    cout << "Welcome to Rudy's Reading Level Evaluator" << endl;
    cout << "Enter name of input file: ";
    cin >> file;
        
    if(((file == "words.txt"))||(file == "rank.txt"))
    {
        book.CreateDatabase();
        book.ReadFile02(file);
    }
    else
    {
        book.CreateDatabase();
        book.ReadFile01(file);
    }
        
    //book.PrintAll();
    reading_level = book.CalculateReadLevel();
        
    cout << "The estimated reading level of " << file << " is: " 
         << reading_level << endl; 
}
/* 
 * File:   main.cpp
 * Author: Rudy Ramirez
 *
 * Created on April 9, 2020, 4:00 PM
 */

#include "Book.h"
#include "HybridSort.h"
//#include "OldHybridSort.h"

/*
 * description here
 */
int main()
{
    string name;
    //Open and create initial string vector
    Book Book;
    
    while((name != "AnneGables.txt") && (name != "Dracula.txt") && (name != "MonteCristo.txt") && (name != "Test.txt"))
    {
        cout << "Enter Book File Name: ";
        cin >> name;
    }
    
    Book.ReadFile(name);
    //Book.Print();
    
    //Call HybridSort
    HybridSort SortedBook;
    SortedBook.Separate(Book.getWords());
    SortedBook.CreateCompleteData();
    
    //Implement Sorted Words Into Previous Book
    Book.setWords(SortedBook.getOriginal());
    
    //Write Information into "book.count"
    Book.CreateBookCountFile();
}
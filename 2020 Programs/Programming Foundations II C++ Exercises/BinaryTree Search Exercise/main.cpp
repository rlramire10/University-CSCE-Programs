/* 
 * File:   main.cpp
 * Author: Rudy Ramirez
 *
 * Created on April 20, 2020, 11:00 AM
 */

#include "tree.h"

/*
 * description here
 */
int main() 
{
    // Initialize Variables
    const int MAXSIZE = 10000;
    int InfoCount = 0;
    
    // Initialize Binary Tree
    BinaryTree info;
    
    //--------------------------------------------------------------------------
    // Open And Insert Information
    ifstream din;
    din.open("last.txt");
    if (din.fail())
        cout << "Error: Could not open last.txt" << endl;

    string name;
    long count;
    float percentage;
    int rank;
    
    // Loop reading book records
    while( !din.eof() && (InfoCount < MAXSIZE) )
    {
        din >> name >> count >> percentage >> rank;
        info.Insert(name, count, percentage, rank);
        InfoCount++;
    }
    // Close input file
    din.close();
    //--------------------------------------------------------------------------
    
    // Loop Asking for Names to Find
    bool Exit = false;
    name = "";
    while (Exit != true)
    {
        cout << "Type in Name to Search Or QUIT: ";
        cin >> name;
        
        // Exiting Conditions
        if(name == "Quit"||name == "QUIT"||name == "quit")
        {
            info.Print();
            Exit = true;
        }
        // Error Correction If Name Wasn't Found
        else if(info.Search(name) == false)
        {
            cout << "Name Not Found" << endl;
        }
    }
}
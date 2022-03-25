/* 
 * File:   main.cpp
 * Author: Rudy Ramirez
 *
 * Created on February 5, 2020, 1:13 PM
 */
#include <iostream>
#include <cstdlib>
#include "UserNode.h"
#include "UserList.h"

using namespace std;

/*
 * The goal of this assignment is to create a linked list that could be used
 * to store information about the users in each group. In particular, your task 
 * is to design, implement, and test a UserNode class that contains information
 * about each user on GroupMe, and a UserList class that contains a linked 
 * list of UserNode representing the current members of a group.
 */
int main()
{
    UserList list;
    int choice = 0;
    string first_name = "";
    string last_name = "";
    string handle = "";
    string phone_number = "";
    int message_count = 0;
    
    //Menu
    while(choice != 5)
    {
        cout << "[          MENU          ]" << endl
             << "Enter number for a command:" << endl
             << "1) Add" << endl
             << "2) Update" << endl
             << "3) Find" << endl
             << "4) Print List" << endl
             << "5) Quit" << endl
             << endl
             << "Choice: ";
        
        cin >> choice;
        cout << endl;
        
        if(choice == 1)
        {
            cout << "[          ADD          ]" << endl;
            cout << "Enter First Name:" << endl;
            cin >> first_name;
            cout << "Enter Last Name:" << endl;
            cin >> last_name;
            cout << "Enter Handle/Username:" << endl;
            cin >> handle;
            cout << "Enter Phone Number:" << endl;
            cin >> phone_number;
            cout << "Enter Message Count:" << endl;
            cin >> message_count;
            
            list.Add(first_name, last_name, handle, phone_number, message_count);
            cout << endl;
        }
        if(choice == 2)
        {
            cout << "[          UPDATE          ]" << endl
                 << "Enter Handle/Username Of Node" << endl
                 << "You Want To Update:" << endl;
            cin >> handle;
            list.Update(handle);
            cout << endl;
        }
        if(choice == 3)
        {
            cout << "[          FIND          ]" << endl;
            list.Find();
            cout << endl;
        }
        if(choice == 4)
        {
            cout << "[          PRINT LIST          ]" << endl;
            list.PrintList();
        }
    }
}


/* 
 * File:   main.cpp
 * Author: Rudy Ramirez
 *
 * Created on March 15, 2020, 1:06 PM
 */
#include <iostream>
#include <cstdlib>
#include "stack.h"
#include "queue.h"

using namespace std;

/*
 * The goal of this programming	assignment is for students to gain experience 
 * with	the implementation and use of stacks and queues. Instead of using a 
 * fixed size array, or	a variable length linked list, you will	be using the
 * C++ vector class to create the “container” to store the stack’s and queue’s 
 * data.
 * 
 * The vector class was	invented specifically to have the advantages of	an array 
 * (data elements can be accessed by index) and	of a linked list (growing or 
 * shrinking to	meet the needs of an application). This	means that you can enjoy
 * the speed of	traditional algorithms for array based stacks and queues, and 
 * still avoid stack-overflow or queue-overflow situations.
 */
int main()
{
    // Variables
    int calls = 0;
    int push_count = 0;
    int pop_count = 0;
    int insert_count = 0;
    int remove_count = 0;
    
    // User Interface
    cout << "Enter number of operations: ";
    cin >> calls;
    
    //Get Start Time 1
    clock_t time1=clock();
    
    // Generate Random Numbers and Probability
    vector <int> rand_num;
    vector <int> chance;
    for (int i = 0; i < calls; ++i)
    {
        // Generate Random Probability from 0 - 99
        chance.push_back(rand() % 99);
        // Generate Random Number to Store from 0 - 10
        rand_num.push_back(rand() % 11); 
    }
    
    // Create Stack Object
    Stack data1;
    for (int i = 0; i < calls; ++i)
    {
        // 49% of calling pop
        if((chance.at(i) < 49) && (!data1.isEmpty()))
        {
            cout << "pop " << data1.pop() << " : ";
            data1.print();
            pop_count++;
        }
        // 51% of calling push
        else
        {
            data1.push(rand_num.at(i));
            cout << "push " << rand_num.at(i) << " : ";
            data1.print();
            push_count++;
        }
    }
    cout << endl;
    cout << "Push Count = " << push_count << endl;
    cout << "Pop Count = " << pop_count << endl;
    cout << "Stack Size: " << data1.getCount() << endl;
    cout << "Stack Contents: ";
    data1.print();
    
    // Get End Time 1
    clock_t time2=clock();
    double run_time1 = (time2-time1)/(double)CLOCKS_PER_SEC;
    cout << "Stack Run time: " << run_time1 << " seconds\n";
    
    cout << endl;
    
    //Get Start time 2
    clock_t time3=clock();
    
    Queue data2;
    for (int i = 0; i < calls; ++i)
    {
        // 49% of calling remove
        if((chance.at(i) < 49) && (!data2.isEmpty()))
        {
            cout << "remove " << data2.remove() << " : ";
            data2.print();
            remove_count++;
        }
        // 51% of calling insert
        else
        {
            data2.insert(rand_num.at(i));
            cout << "insert " << rand_num.at(i) << " : ";
            data2.print();
            insert_count++;
        }
    }
    cout << endl;
    cout << "Insert Count = " << insert_count << endl;
    cout << "Remove Count = " << remove_count << endl;
    cout << "Queue Size: " << data2.getCount() << endl;
    cout << "Queue Contents: ";
    data2.print();
    
    // Get End Time 2
    clock_t time4=clock();
    double run_time2 = (time4-time3)/(double)CLOCKS_PER_SEC;
    cout << "Queue Run time: " << run_time2 << " seconds\n";
}


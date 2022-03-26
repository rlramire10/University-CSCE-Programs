/* 
 * File:   stack.h
 * Author: Rudy Ramirez
 *
 * Created on March 15, 2020, 1:20 PM
 */

#ifndef STACK_H
#define STACK_H

#include <vector>
#include <iostream>
using namespace std;

class Stack
{
    public:
        Stack();
        ~Stack();
        
        void push(int number);
        int pop();
        int top();
    
        bool isFull();
        bool isEmpty();
        int getCount();
        void print();
    private:
        int count;
        vector <int> data;
};

#endif /* STACK_H */


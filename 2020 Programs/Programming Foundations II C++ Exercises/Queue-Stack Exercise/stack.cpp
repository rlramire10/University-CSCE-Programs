/* 
 * File:   stack.cpp
 * Author: Rudy Ramirez
 * 
 * Created on March 15, 2020, 1:20 PM
 */

#include "stack.h"

//Constructor & Destructor
Stack::Stack()
{
    count = 0;
    data;
}
Stack::~Stack()
{
}

//Other Methods
bool Stack::isFull()
{
    return (!data.empty());
}
bool Stack::isEmpty()
{
    if(data.size() < 0)
    {
        data.resize(0);
    }
    return (data.empty());
}
int Stack::getCount()
{
    return count;
}
void Stack::print()
{
    for (int i = 0; i < count; ++i)
    {
        cout << data.at(i) << " ";
    }
    cout << endl;
}

//Push, Pop, & Top Methods
void Stack::push(int number)
{
    data.push_back(number);
    count = data.size();
}
int Stack::pop()
{
    if(!isEmpty())
    {
        int pop = data.at(count-1);
        data.pop_back();
        count = data.size();
        return pop;
    }
}
int Stack::top()
{
    return (data.at(count-1));
}


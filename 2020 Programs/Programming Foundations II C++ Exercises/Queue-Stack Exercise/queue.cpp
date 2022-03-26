/* 
 * File:   queue.cpp
 * Author: Rudy Ramirez
 * 
 * Created on March 15, 2020, 1:16 PM
 */

#include "queue.h"

//Constructor & Destructor
Queue::Queue()
{
    size = 0;
    count = 0;
    front = 0;
    back = 0;
    data;
}
Queue::~Queue()
{
}

//Other Methods
bool Queue::isFull()
{
    return (!data.empty());
}
bool Queue::isEmpty()
{
    if(data.size() < 0)
    {
        data.resize(0);
    }
    return (data.empty());
}
int Queue::getCount()
{
    return count;
}

//Insert & Remove Methods
void Queue::insert(int number)
{
    data.push_back(number);
    //back = back + 1;
    // Update Counters
    count = data.size();
    size = data.size();
}
int Queue::remove()
{
    if(!isEmpty())
    {
        int remove = data.at(front);
        data.erase(data.begin());
        // Update Counters
        count = data.size();
        size = data.size();
        return remove;
    }
}

void Queue::print()
{
    for (int i = 0; i < count; ++i)
    {
        cout << data.at(i) << " ";
    }
    cout << endl;
}
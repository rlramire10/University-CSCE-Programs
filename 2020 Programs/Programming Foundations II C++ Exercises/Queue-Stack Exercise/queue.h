/* 
 * File:   queue.h
 * Author: Rudy Ramirez
 *
 * Created on March 15, 2020, 1:16 PM
 */

#ifndef QUEUE_H
#define QUEUE_H

#include <vector>
#include <iostream>
using namespace std;

class Queue
{
    public:
        Queue();
        ~Queue();
        
        void insert(int number);
        int remove();
    
        bool isFull();
        bool isEmpty();
        int getCount();
        void print();
    private:
        int size;
        int count;
        int front;
        int back;
        vector <int> data;
};

#endif /* QUEUE_H */


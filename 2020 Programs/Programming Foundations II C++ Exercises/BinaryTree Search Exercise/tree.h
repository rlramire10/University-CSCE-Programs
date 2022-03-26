/* 
 * File:   tree.h
 * Author: Rudy Ramirez
 *
 * Created on April 20, 2020, 11:06 AM
 */

#include <iostream>
#include <fstream>
using namespace std;

#ifndef TREE_H
#define TREE_H

//-----------------------------------------------------------
// BinaryTree data node definition
//-----------------------------------------------------------
class Node
{
    public:
        string Name;
        long Count;
        float Percentage;
        int Rank;
        Node *Left;
        Node *Right;
};

//-----------------------------------------------------------
// Define the BinaryTree class interface 
//-----------------------------------------------------------
class BinaryTree
{
 public:
    // Constructor functions
    BinaryTree();
   ~BinaryTree();

    // General binary tree operations
    bool Search(string Name);
    bool Insert(string Name, long Count, float Percentage, int Rank);
    bool Delete(string Name, long Count, float Percentage, int Rank);
    void Print();

 private:
    // Helper functions
    void DestroyHelper(Node * Tree);
    bool SearchHelper(string Name, Node * Tree);
    bool InsertHelper(string Name, long Count, float Percentage, int Rank, Node * &Tree);
    bool DeleteNode(Node * &Tree);
    bool DeleteHelper(string Name, long Count, float Percentage, int Rank, Node * &Tree);
    void PrintHelper(Node * Tree);

    // Tree pointer
    Node *Root;
};

#endif /* TREE_H */
/* 
 * File:   tree.cpp
 * Author: Rudy Ramirez
 * 
 * Created on April 20, 2020, 11:06 AM
 */

#include "tree.h"
#include <stdlib.h>

//-----------------------------------------------------------
// Constructor function.
//-----------------------------------------------------------
BinaryTree::BinaryTree()
{
    Root = NULL;
}

//-----------------------------------------------------------
// Destructor function helper function.
//-----------------------------------------------------------
void BinaryTree::DestroyHelper(Node * Tree)
{
    // Delete node and it's children
    if (Tree != NULL)
    {
        DestroyHelper(Tree->Left);
        DestroyHelper(Tree->Right);
        delete Tree;
    }
}

//-----------------------------------------------------------
// Destructor function.
//-----------------------------------------------------------
BinaryTree::~BinaryTree()
{
    // Call tree destroy function
    DestroyHelper(Root);
}

//-----------------------------------------------------------
// Search helper function.
//-----------------------------------------------------------
bool BinaryTree::SearchHelper(string Name, Node * Tree)
{
    // Data value not found 
    if (Tree == NULL)
        return false;

    // Data value found 
    else if (Tree->Name == Name)
    {
        cout << "Name: " << Tree->Name << endl;
        cout << "Count: " << Tree->Count << endl;
        cout << "Percentage: " << Tree->Percentage << endl;
        cout << "Rank: " << Tree->Rank << endl;
        return true;
    }

    // Recursively search for data value
    else if (Tree->Name > Name)
        return (SearchHelper(Name, Tree->Left));
    else 
        return (SearchHelper(Name, Tree->Right));
}

//-----------------------------------------------------------
// Search for data in the binary tree.
//-----------------------------------------------------------
bool BinaryTree::Search(string Name)
{
    // Call tree searching function
    return (SearchHelper(Name, Root));
}

//-----------------------------------------------------------
// Insert helper function.
//-----------------------------------------------------------
bool BinaryTree::InsertHelper(string Name, long Count, float Percentage, int Rank, Node * &Tree)
{
    // Insert data into the tree
    if (Tree == NULL)
    {
        Tree = new Node;
        Tree->Name = Name;
        Tree->Count = Count;
        Tree->Percentage = Percentage;
        Tree->Rank = Rank;
        Tree->Left = NULL;
        Tree->Right = NULL;
        return true;
    }

    // Recursively search for insertion position
    else if (Tree->Name > Name)
        return (InsertHelper(Name, Count, Percentage, Rank, Tree->Left));
    else 
        return (InsertHelper(Name, Count, Percentage, Rank, Tree->Right));
}

//-----------------------------------------------------------
// Insert data into the binary tree.
//-----------------------------------------------------------
bool BinaryTree::Insert(string Name, long Count, float Percentage, int Rank)
{
    // Call tree insertion function
    return (InsertHelper(Name, Count, Percentage, Rank, Root));
}

//-----------------------------------------------------------
// Delete a single node from the binary tree.
//-----------------------------------------------------------
bool BinaryTree::DeleteNode(Node * &Tree)
{
    Node *Temp = Tree;

    // Node has 0 children
    if ((Tree->Left == NULL) && (Tree->Right == NULL))
        Tree = NULL;

    // Node has 1 child
    else if (Tree->Left == NULL)
        Tree = Tree->Right;
    else if (Tree->Right == NULL)
        Tree = Tree->Left;


    // Node has 2 children
    else
    {
        // Find leftmost node in right subtree
        Node *Parent = Tree;
        Temp = Tree->Right;
        while (Temp->Left != NULL)
        {
           Parent = Temp;
           Temp = Parent->Left;
        }

        // Replace deleted data with leftmost value
        if (Parent == Tree)
           Parent->Right = Temp->Right;
        else
           Parent->Left = Temp->Right;
        Tree->Name = Temp->Name;
    }

    // Delete node
    delete Temp;
    return true;
}

//-----------------------------------------------------------
// Delete helper function.
//-----------------------------------------------------------
bool BinaryTree::DeleteHelper(string Name, long Count, float Percentage, int Rank, Node * &Tree)
{
    // Data value not found
    if (Tree == NULL)
        return false;

    // Data value found and deleted
    else if (Tree->Name == Name)
        return (DeleteNode(Tree));

    // Recursively search for node to delete
    else if (Tree->Name > Name)
        return (DeleteHelper(Name, Count, Percentage, Rank, Tree->Left));
    else 
        return (DeleteHelper(Name, Count, Percentage, Rank, Tree->Right));
}

//-----------------------------------------------------------
// Delete data from the binary tree.
//-----------------------------------------------------------
bool BinaryTree::Delete(string Name, long Count, float Percentage, int Rank)
{
    // Call tree deletion function
    return (DeleteHelper(Name, Count, Percentage, Rank, Root));
}

//-----------------------------------------------------------
// Print helper function.
//-----------------------------------------------------------
void BinaryTree::PrintHelper(Node * Tree)
{
    // Check terminating condition
    if (Tree != NULL)
    {
        // Print left subtree
        PrintHelper(Tree->Left);

        // Print node value
        cout << Tree->Name << ", ";
        cout << Tree->Count << ", ";
        cout << Tree->Percentage << ", ";
        cout << Tree->Rank << endl;

        // Print right subtree
        PrintHelper(Tree->Right);
    }
}

//-----------------------------------------------------------
// Print all records in the binary tree.
//-----------------------------------------------------------
void BinaryTree::Print()
{
    // Call tree printing function
    PrintHelper(Root);
    cout << endl;
}
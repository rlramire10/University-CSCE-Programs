/* 
 * File:   UserList.cpp
 * Author: Rudy Ramirez
 * 
 * Created on February 7, 2020, 12:51 PM
 */

#include "UserList.h"

//Constructor
UserList::UserList()
{
    Head = NULL;
}
//Copy Constructor
UserList::UserList(const UserList& orig)
{
    // Create first node
   UserNode *copy = new UserNode();
   Head = copy;

   // Walk list to copy nodes
   UserNode *temp = orig.Head;
   while (temp != NULL)
   {
      copy->setNext(new UserNode());
      copy = copy->getNext();
      copy->setFirstName(temp->getFirstName());
      copy->setLastName(temp->getLastName());
      copy->setHandle(temp->getHandle());
      copy->setPhoneNumber(temp->getPhoneNumber());
      copy->setMessageCount(temp->getMessageCount());
      copy->setNext(NULL);
      temp = temp->getNext();
   }

   // Tidy first node
   copy = Head;
   Head = copy->getNext();
   delete copy;
}
//Destructor
UserList::~UserList() 
{
   // Walk list to delete nodes
   while (Head != NULL)
   {
      UserNode *temp = Head;
      Head = Head->getNext();
      delete temp;
   }
}
//Other Methods
//----------------------------------------------
void UserList::Add(string first_name, string last_name, string handle, string phone_number, int message_count)
{
   // Find tail node
   UserNode *temp = Head;
   while ((temp != NULL) && (temp->getNext() != NULL))
      temp = temp->getNext();

   // Insert new node
   if (temp != NULL)
      temp->setNext(new UserNode(first_name, last_name, handle, phone_number, message_count));
   else
      Head = new UserNode(first_name, last_name, handle, phone_number, message_count);
}

void UserList::Update(string handle)
{
    UserNode *temp = Head;
    
    while ((temp != NULL) && (temp->getHandle() != handle))
      temp = temp->getNext();

    if (temp != NULL)
    {
        cout << "Message Count Updated:" << endl;
        temp->setMessageCount(temp->getMessageCount() + 1);
        temp->printNode();
    }  
    else
        cout << "Message Count Couldn't Update" << endl;
}

void UserList::Find()
{
    UserNode *largest = Head;
    UserNode *temp = Head;
  
    while (temp != NULL)
    {
        if(temp->getMessageCount() > largest->getMessageCount())
            largest = temp;
        temp = temp->getNext();
    }
    if (largest != NULL)
    {
        cout << "Node with Largest Message Count:" << endl;
        largest->printNode();
    }
}

//----------------------------------------------
void UserList::PrintList() const
{
   UserNode *temp = Head;
   while (temp != NULL)
   {
      temp->printNode();
      temp = temp->getNext();
   }
}
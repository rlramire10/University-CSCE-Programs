/* 
 * File:   UserNode.cpp
 * Author: Rudy Ramirez
 * 
 * Created on February 7, 2020, 12:50 PM
 */

#include "UserNode.h"

//Constructor
UserNode::UserNode()
{
    FirstName = "";
    LastName = "";
    Handle = "";
    PhoneNumber = "";
    MessageCount = 0;
    Next = NULL;
}
//Other Constructor
UserNode::UserNode(string first_name, string last_name, string handle, string phone_number, int message_count)
{
    FirstName = first_name;
    LastName = last_name;
    Handle = handle;
    PhoneNumber = phone_number;
    MessageCount = message_count;
    Next = NULL;
}
//Copy Constructor
UserNode::UserNode(const UserNode & orig)
{
    FirstName = orig.FirstName;
    LastName = orig.LastName;
    Handle = orig.Handle;
    PhoneNumber = orig.PhoneNumber;
    MessageCount = orig.MessageCount;
    Next = orig.Next;
}
//Destructor
UserNode::~UserNode()
{
}
//Getters
string UserNode::getFirstName() const
{
    return FirstName;
}
string UserNode::getLastName() const
{
    return LastName;
}
string UserNode::getHandle() const
{
    return Handle;
}
string UserNode::getPhoneNumber() const
{
    return PhoneNumber;
}
int UserNode::getMessageCount() const
{
    return MessageCount;
}
UserNode* UserNode::getNext() const
{
    return Next;
}
//Setters
void UserNode::setFirstName(string first_name)
{
    FirstName = first_name;
}
void UserNode::setLastName(string last_name)
{
    LastName = last_name;
}
void UserNode::setHandle(string handle)
{
    Handle = handle;
}
void UserNode::setPhoneNumber(string phone_number)
{
    PhoneNumber = phone_number;
}
void UserNode::setMessageCount(int message_count)
{
    MessageCount = message_count;
}
void UserNode::setNext(UserNode* next)
{
    Next = next;
}
//Other Methods
void UserNode::printNode() const
{
   cout << "First Name: " << FirstName << endl
        << "Last Name: " << LastName << endl
        << "Handle: " << Handle << endl
        << "Phone Number: " << PhoneNumber << endl
        << "Message Count: " << MessageCount << endl
        << endl;
}
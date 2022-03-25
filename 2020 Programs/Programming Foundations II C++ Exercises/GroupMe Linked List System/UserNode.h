/* 
 * File:   UserNode.h
 * Author: Rudy Ramirez
 *
 * Created on February 7, 2020, 12:50 PM
 */

#include <iostream>
using namespace std;
#ifndef USERNODE_H
#define USERNODE_H

class UserNode {
public:
    UserNode();
    UserNode(string first_name, string last_name, string handle, string phone_number, int message_count);
    UserNode(const UserNode & orig);
    ~UserNode();
        
    // Methods
    string getFirstName() const;
    string getLastName() const;
    string getHandle() const;
    string getPhoneNumber() const;
    int getMessageCount() const;
    UserNode* getNext() const;
    
    void setFirstName(string first_name);
    void setLastName(string last_name);
    void setHandle(string handle);
    void setPhoneNumber(string phone_number);
    void setMessageCount(int message_count);
    void setNext(UserNode* next);
    void printNode() const;
    
private:
    string FirstName;
    string LastName;
    string Handle;
    string PhoneNumber;
    int MessageCount;
    UserNode *Next;
};

#endif /* USERNODE_H */


/* 
 * File:   UserList.h
 * Author: Rudy Ramirez
 *
 * Created on February 7, 2020, 12:51 PM
 */

#include "Usernode.h"

#ifndef USERLIST_H
#define USERLIST_H

class UserList {
public:
    UserList();
    UserList(const UserList& orig);
    ~UserList();
    
    void Add(string first_name, string last_name, string handle, string phone_number, int message_count);
    void Update(string handle);
    void Find();
    void PrintList() const;
private:
    UserNode *Head;
};

#endif /* USERLIST_H */


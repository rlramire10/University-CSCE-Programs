/* 
 * File:   MagicDeck.h
 * Author: Rudy Ramirez
 *
 * Created on January 23, 2020, 4:29 PM
 */

#include "Magic.h"

#ifndef MAGICDECK_H
#define MAGICDECK_H

const int MaxDeckSize = 100;

class MagicDeck {
public:    
    MagicDeck();
    MagicDeck(const MagicDeck& orig);
    virtual ~MagicDeck();
    //Methods
    void ReadDeck();
    void PrintDeck();
    void TotalValue();
    void MostValuable();
    void MostNumerous();
private:
    int count;
    Magic deck[MaxDeckSize];
};

#endif /* MAGICDECK_H */


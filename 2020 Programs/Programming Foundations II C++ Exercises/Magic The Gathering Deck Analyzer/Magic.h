/* 
 * File:   Magic.h
 * Author: Rudy Ramirez
 *
 * Created on January 23, 2020, 4:30 PM
 */

#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
using namespace std;
#ifndef MAGIC_H
#define MAGIC_H

class Magic {
public:
    Magic();
    Magic(const Magic& orig);
    virtual ~Magic();
    //Getter
    string getCardName();
    int getManaCost();
    string getType();
    string getCardText();
    int getCardNumber();
    string getRarity();
    string getCondition();
    float getPurchasePrice();
    float getCurrentValue();
    int getQuantity();
    //Setter
    void setCardName(string newCardName);
    void setManaCost(int newManaCost);
    void setType(string newType);
    void setCardText(string newCardText);
    void setCardNumber(int newCardNumber);
    void setRarity(string newRarity);
    void setCondition(string newCondition);
    void setPurchasePrice(float newPurchasePrice);
    void setCurrentValue(float newCurrentValue);
    void setQuantity(int newQuantity);
    //Print All Information
    void printAll();
    
private:
    //Primary Information
    string CardName;
    int ManaCost;
    string Type;
    string CardText;
    int CardNumber;
    //Secondary Information
    string Rarity;
    string Condition;
    float PurchasePrice;
    float CurrentValue;
    int Quantity;
};

#endif /* MAGIC_H */


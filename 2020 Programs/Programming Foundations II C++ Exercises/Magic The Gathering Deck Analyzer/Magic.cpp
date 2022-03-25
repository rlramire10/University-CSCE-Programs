/* 
 * File:   Magic.cpp
 * Author: Rudy Ramirez
 * 
 * Created on January 23, 2020, 4:30 PM
 */

#include "Magic.h"

//Constructor
Magic::Magic() {
    //cout << "In Magic Constructor\n";
    CardName = "Empty Card Name";
    ManaCost = 0;
    Type = "Unknown Type";
    CardText = "Empty Card Text";
    CardNumber = 0;
    Rarity = "Unknown Rarity";
    Condition = "Unknown Condition";
    PurchasePrice = 0.0;
    CurrentValue = 0.0;
    Quantity = 0;
}
//Copy Constructor
Magic::Magic(const Magic& orig) {
    //cout << "In Magic Copy Constructor\n";
    CardName = orig.CardName;
    ManaCost = orig.ManaCost;
    Type = orig.Type;
    CardText = orig.CardText;
    CardNumber = orig.CardNumber;
    Rarity = orig.Rarity;
    Condition = orig.Condition;
    PurchasePrice = orig.PurchasePrice;
    CurrentValue = orig.CurrentValue;
    Quantity = orig.Quantity;
}
//Destructor
Magic::~Magic() {
    //cout << "In Magic Destructor\n";
}
//Getters***********************************************************************
string Magic::getCardName()
{
    return CardName;
}
int Magic::getManaCost()
{
    return ManaCost;
}
string Magic::getType()
{
    return Type;
}
string Magic::getCardText()
{
    return CardText;
}
int Magic::getCardNumber()
{
    return CardNumber;
}
string Magic::getRarity()
{
    return Rarity;
}
string Magic::getCondition()
{
    return Condition;
}
float Magic::getPurchasePrice()
{
    return PurchasePrice;
}
float Magic::getCurrentValue()
{
    return CurrentValue;
}
int Magic::getQuantity()
{
    return Quantity;
}
//Setters***********************************************************************
void Magic::setCardName(string newCardName)
{
    CardName = newCardName;
}
void Magic::setManaCost(int newManaCost)
{
    ManaCost = newManaCost;
}
void Magic::setType(string newType)
{
    Type = newType;
}
void Magic::setCardText(string newCardText)
{
    CardText = newCardText;
}
void Magic::setCardNumber(int newCardNumber)
{
    CardNumber = newCardNumber;
}
void Magic::setRarity(string newRarity)
{
    Rarity = newRarity;
}
void Magic::setCondition(string newCondition)
{
    Condition = newCondition;
}
void Magic::setPurchasePrice(float newPurchasePrice)
{
    PurchasePrice = newPurchasePrice;
}
void Magic::setCurrentValue(float newCurrentValue)
{
    CurrentValue = newCurrentValue;
}
void Magic::setQuantity(int newQuantity)
{
    Quantity = newQuantity;
}
//Other Methods*****************************************************************
void Magic::printAll()
{
    cout << "Card Name: " << CardName << endl
         << "Mana Cost: " << ManaCost << endl
         << "Type: "      << Type << endl
         << "Card Text: " << CardText << endl
         << "Card Number: " << CardNumber << endl
         << "Rarity: " << Rarity << endl
         << "Condition: " << Condition << endl
         << "Purchase Price: $" << PurchasePrice << endl
         << "Current Value: $"  << CurrentValue << endl
         << "Quantity: " << Quantity << endl;
}
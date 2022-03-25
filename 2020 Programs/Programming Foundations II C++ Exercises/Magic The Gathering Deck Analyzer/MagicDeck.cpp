/* 
 * File:   MagicDeck.cpp
 * Author: Rudy Ramirez
 * 
 * Created on January 23, 2020, 4:29 PM
 */

#include "MagicDeck.h"

//Constructor
MagicDeck::MagicDeck() {
    //cout << "In MagicDeck Constructor\n";
}
//Copy Constructor
MagicDeck::MagicDeck(const MagicDeck& orig) {
    //cout << "In MagicDeck Copy Constructor\n";
}
//Destructor
MagicDeck::~MagicDeck() {
    //cout << "In MagicDeck Destructor\n";
}
//Other Methods
void MagicDeck::ReadDeck()
{
    // Open input file
   ifstream din;
   din.open("Magic Cards.txt");
   if (din.fail())
      cout << "Could not open Magic Cards.txt\n";

   // Read count of books
   string str;
   getline(din, str);
   count = atoi(str.c_str());

   // Loop reading book records
   for (int i = 0; i < count && !din.eof(); i++)
   {
      getline(din, str);
      deck[i].setCardName(str);
      getline(din, str);
      deck[i].setManaCost(atoi(str.c_str()));
      getline(din, str);
      deck[i].setType(str);
      getline(din, str);
      deck[i].setCardText(str);
      getline(din, str);
      deck[i].setCardNumber(atoi(str.c_str()));
      getline(din, str);
      deck[i].setRarity(str);
      getline(din, str);
      deck[i].setCondition(str);
      getline(din, str);
      deck[i].setPurchasePrice(atof(str.c_str()));
      getline(din, str);
      deck[i].setCurrentValue(atof(str.c_str()));
      getline(din, str);
      deck[i].setQuantity(atoi(str.c_str()));
   }

   // Close input file
   din.close();
}
void MagicDeck::PrintDeck()
{
    for (int i = 0; i < count; i++)
    {
        deck[i].printAll();
        cout << endl;
    }
}
void MagicDeck::TotalValue()
{
    float total_value = deck[0].getCurrentValue();
    for (int i = 1; i < count; i++)
    {
        total_value = total_value + (deck[i].getCurrentValue()*deck[i].getQuantity());
    }
    cout << "[Total Value]" << endl
         << "Total Value: $" << total_value << endl
         << endl;
}
void MagicDeck::MostValuable()
{
    string first_cardname = deck[0].getCardName();
    float first_value = deck[0].getCurrentValue();
    for (int i = 1; i < count; i++)
    {
        string second_cardname = deck[i].getCardName();
        float second_value = deck[i].getCurrentValue();
        if (second_value > first_value)
        {
            first_cardname = second_cardname;
            first_value = second_value;
        }
    }
    cout << "[Most Valuable]" << endl
         << "Card Name: " << first_cardname << endl
         << "Value: $" << first_value << endl
         << endl;
}
void MagicDeck::MostNumerous()
{
    string first_cardname = deck[0].getCardName();
    float first_quantity = deck[0].getQuantity();
    for (int i = 1; i < count; i++)
    {
        string second_cardname = deck[i].getCardName();
        float second_quantity = deck[i].getQuantity();
        if (second_quantity > first_quantity)
        {
            first_cardname = second_cardname;
            first_quantity = second_quantity;
        }
    }
    cout << "[Most Numerous]" << endl
         << "Card Name: " << first_cardname << endl
         << "Quantity: " << first_quantity << endl
         << endl;
}
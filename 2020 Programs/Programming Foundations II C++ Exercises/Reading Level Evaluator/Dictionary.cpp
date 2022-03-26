/* 
 * File:   Dictionary.cpp
 * Author: Rudy Ramirez
 * 
 * Created on February 22, 2020, 9:42 PM
 */

#include "Dictionary.h"

//Constructor
Dictionary::Dictionary()
{   
}
//Copy Constructor
Dictionary::Dictionary(const Dictionary& orig)
{
}
//Destructor
Dictionary::~Dictionary()
{
}

//Other Methods
void Dictionary::CreateDatabase()
{
    // Open And Create Ranked Database
   ifstream din;
   din.open("words.txt");
   if (din.fail())
      cout << "Error: Could not open words.txt" << endl;
   
   string str;
   // Loop reading book records
   for (int i = 0; i < MAXSIZE && !din.eof(); i++)
   {
       din >> str;
       database[i].setRank(atoi(str.c_str()));
       din >> str;
       database[i].setWord(CheckCase(str));
   }
   // Close input file
   din.close();
}

void Dictionary::ReadFile01(string filename)
{
    // Open And Create Array Of Words Within Books
    ifstream din;
    din.open(filename.c_str());
    if (din.fail())
       cout << "Error: Could not open " << filename << endl;
   
    string str;
    // Loop reading words into an array
    for (int i = 0; i < MAXSIZE && !din.eof(); i++)
    {
         //Adjust Line of Code
         din >> str;
         text[i].setWord(CheckCase(str));
    }
    
    // Close input file
    din.close();
}

void Dictionary::ReadFile02(string filename)
{   
    // Open And Create Array Of Words Within Books
    ifstream din;
    din.open(filename.c_str());
    if (din.fail())
       cout << "Error: Could not open " << filename << endl;
   
    string str;
    // Loop reading words into an array
    for (int i = 0; i < MAXSIZE && !din.eof(); i++)
    {
       //Adjust Line of Code
        din >> str;
        text[i].setRank(atoi(str.c_str()));
        din >> str;
        text[i].setWord(CheckCase(str));       
    }

    // Close input file
    din.close();
}

string Dictionary::CheckCase(string original_word)
{
    string revised_word;
    locale loc;
    for(int i = 0; i < original_word.length(); ++i)
    {
        if(( !isalpha(original_word[i]) )&&(original_word[i] != '\'') )
        {
            original_word.erase(i--,1);
        }
        else
        {
            revised_word = revised_word + tolower(original_word[i], loc);
        }
    }
    return revised_word;
}

int Dictionary::BinarySearch(Text data[], string target_word, int low, int high)
{
   int target_rank = 0;
   int mid = (low + high) / 2;
   string mid_word = data[mid].getWord();
   // Check terminating conditions
   if (low > high)
      target_rank = -1;

   else if (mid_word.compare(target_word) == 0)
      target_rank = data[mid].getRank();

   // Handle recursive 
   else if (mid_word.compare(target_word) > 0)
      target_rank = BinarySearch(data, target_word, low, mid - 1);

   else if (mid_word.compare(target_word) < 0)
      target_rank = BinarySearch(data, target_word, mid + 1, high);

   return target_rank;
}

float Dictionary::CalculateReadLevel()
{
    float word_count = 0;
    float sum_rank = 0.0;
    int low = 0;
    int high = MAXSIZE;
    int raw_level = 0;
    
    Dictionary temp;

    for (int i = 0; i < MAXSIZE; i++)
    {
        raw_level = temp.BinarySearch(database, text[i].getWord(), low, high);
        
        if(text[i].getWord() != "")
        {
            if(raw_level == -1)             
            {
                text[i].setRank(1001);
                sum_rank = sum_rank + 1001;
            }
            else
            {
                text[i].setRank(raw_level);
                sum_rank = sum_rank + (float)raw_level;
            }
            word_count = word_count + 1;
        }
    }
    ReadingLevel = sum_rank / word_count;
    
    return ReadingLevel;
}
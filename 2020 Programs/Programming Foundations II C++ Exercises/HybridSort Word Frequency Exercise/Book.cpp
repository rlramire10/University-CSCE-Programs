/* 
 * File:   Book.cpp
 * Author: Rudy Ramirez
 * 
 * Created on April 9, 2020, 5:26 PM
 */

#include "Book.h"

//Constructor
Book::Book()
{
    words;
    wordcount = 0;
}
//Copy Constructor
Book::Book(const Book& orig)
{
    words = orig.words;
    wordcount = orig.wordcount;
}
//Destructor
Book::~Book()
{
}
//Getters
vector <string>& Book::getWords()
{
    return words;
}
int Book::getWordCount()
{
    return wordcount;
}
//Setters
void Book::setWords(vector<string> &text)
{
    words = text;
}
void Book::setWordCount(int count)
{
    wordcount = count;
}
//Other Methods
void Book::ReadFile(string filename)
{
    // Open And Create Array Of Words Within Books
    ifstream din;
    din.open(filename.c_str());
    if (din.fail())
       cout << "Error: Could not open " << filename << endl;
   
    string str;
    string str1;
    string str2;
    bool hyphen = false;
    // Loop reading words into an array
    while( !din.eof() && (wordcount < MAXSIZE) )
    {
        //Adjust Line of Code
        din >> str;
        str = CheckCase(str); 
        if(!str.empty())
        {
            //Check String For A Hyphen
            for(int i = 0; i < (int)str.length(); ++i)
            {
                if(str[i] == '-')
                {
                    //hyphen = true;
                    
                    if((str[i+1] == '-')&&(str[i-1] == '-') && (str[i-2] == '-') && (str[i-3] == '-') && (str[i-4] == '-'))
                    {
                        hyphen = true;
                    }
                }
                else if( (hyphen == true)  )
                {
                    str2 += str[i];
                }
                else
                {
                    str1 += str[i];
                }
            }
            // Special Insert If Word Was Hyphenated
            if(str2.empty())
            {
                words.push_back(str1);
                wordcount = words.size();
                // Reset Variable
                str1 = "";
            }
            // Normal Insert Into Vector
            else
            {
                words.push_back(str1);
                words.push_back(str2);
                wordcount = words.size();
                // Reset Variables
                str1 = "";
                str2 = "";
                hyphen = false;
            }
        } 
    }
    
    // Close input file
    din.close();
}
string Book::CheckCase(string original_word)
{
    // Loop over characters to fix word
    string revised_word1;
    for (int i=0; i<(int)original_word.length(); i++)
    {
        if(original_word[i] == '-')
        {
            revised_word1 += original_word[i];
        }
        // Save lower case letters
        else if ((original_word[i] >= 'a') && (original_word[i] <= 'z'))
            revised_word1 += original_word[i];
        
        // Save upper case letters as lower case letters
        else if ((original_word[i] >= 'A') && (original_word[i] <= 'Z'))
            revised_word1 += original_word[i] - 'A' + 'a';
    }
    return revised_word1;
}

void Book::Print()
{
    
    for (int i = 0; i < wordcount; ++i)
    {
        cout << words.at(i) << endl;
    }
    
    /*
    cout << words.at(130786) << endl;
    cout << words.at(130787) << endl;
    cout << words.at(130788) << endl;
    cout << words.at(130789) << endl;
    cout << words.at(130790) << endl;
    cout << words.at(130791) << endl;
    cout << words.at(130792) << endl;
    */
}

void Book::CreateBookCountFile()
{
    ofstream dout;
    // Create Book.txt To Check If Method Works Correctly
    //dout.open("Book.txt");
    dout.open("Book.count");
    if(dout.fail())
        cout << "Error: Could not open Book.count" << endl;
    
    frequency = 1;
    string str;
    
    for(int i = 1; i < wordcount; ++i)
    {
        // Get Word In Vector
        str = words.at(i-1);
        if (str == words.at(i))
        {
            // If Case For End Of Vector
            if(i == wordcount - 1)
            {
                frequency++;
                // Input Info Into Output File
                dout << frequency << " ";
                dout << str << endl;
            }
            else
            {
                frequency++;
            }   
        }
        else
        {
            // Input Info Into Output File
            dout << frequency << " ";
            dout << str << endl;
            
            // Reset Counter
            frequency = 1;
        }
    }
    
    dout.close();
}
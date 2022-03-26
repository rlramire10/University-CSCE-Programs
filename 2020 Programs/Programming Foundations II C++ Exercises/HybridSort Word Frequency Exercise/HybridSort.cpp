/* 
 * File:   HybridSort.cpp
 * Author: Rudy Ramirez
 * 
 * Created on April 9, 2020, 4:48 PM
 */

#include "HybridSort.h"

//Constructor
HybridSort::HybridSort()
{
    low = 0;
    high = 0;
    LetterArray[26];
    original;
}
//Copy Constructor
HybridSort::HybridSort(const HybridSort& orig)
{
    low = orig.low;
    high = orig.high;
    LetterArray[26] = orig.LetterArray[26];
    original = orig.original;
}
//Destructor
HybridSort::~HybridSort()
{
}
//Getter
vector <string>& HybridSort::getOriginal()
{
    return original;
}
//Setter
void HybridSort::setOriginal(vector<string> &words)
{
    original = words;
}
//Other Methods
void HybridSort::Separate(vector<string> &word)
{   
    low = 0;
    high = word.size();
    string temp;
    //Checks Every Word In Book
    for(int index1 = 0; index1 < high; ++index1)
    {
        temp = word.at(index1);
        //cout << index1 << endl;
        LetterArray[temp[0] - 'a'].push_back(temp);
    }
    //Call MergeSort On All Vectors
    for(int index2 = 0; index2 < 26; ++index2)
    {
        MergeSort(LetterArray[index2], low, LetterArray[index2].size()-1);
    }
}
void HybridSort::MergeSort(vector<string> &word, int low, int high)
{
    // Check terminating condition
    int range = high - low + 1;
    if (range > 1)
    {
       // Divide the array and sort both halves
       int mid = (low + high) / 2;
       MergeSort(word, low, mid);
       MergeSort(word, mid + 1, high);

       // Create temporary array for merged data
       string *copy = new string[range];

       // Initialize array indices
       int index1 = low;
       int index2 = mid + 1;
       int index = 0;

       // Merge smallest data elements into copy array
       while (index1 <= mid && index2 <= high)
       {
          if (word.at(index1) < word.at(index2))
          {
              copy[index++] = word.at(index1++);
          }
          else
          {
              copy[index++] = word.at(index2++);
          }
       }

       // Copy any remaining entries from the first half
       while (index1 <= mid)
       {
           copy[index++] = word.at(index1++);
       } 

       // Copy any remaining entries from the second half
       while (index2 <= high)
       {
           copy[index++] = word.at(index2++);
       }

       // Copy data back from the temporary array
       for (index = 0; index < range; index++)
       {
           word.at(low + index) = copy[index];
       } 
       delete []copy;
    }
}
void HybridSort::CreateCompleteData()
{
    for(int index1 = 0; index1 < 26; ++index1)
    {
        for(int index2 = 0; index2 < LetterArray[index1].size()-1; ++index2)
        {
            original.push_back(LetterArray[index1].at(index2));
        }
    }
}
/* 
 * File:   HybridSort.h
 * Author: Rudy Ramirez
 *
 * Created on April 9, 2020, 4:48 PM
 */

#include "Book.h"

#ifndef HYBRIDSORT_H
#define HYBRIDSORT_H

class HybridSort
{
    public:
        HybridSort();
        HybridSort(const HybridSort& orig);
        ~HybridSort();
        
        vector <string>& getOriginal();
        void setOriginal(vector <string> &words);
        
        void Separate(vector <string> &word);
        void MergeSort(vector <string> &word, int low, int high);
        void CreateCompleteData();
    private:
        int low;
        int high;
        vector <string> LetterArray[26];
        vector <string> original;
};

#endif /* HYBRIDSORT_H */


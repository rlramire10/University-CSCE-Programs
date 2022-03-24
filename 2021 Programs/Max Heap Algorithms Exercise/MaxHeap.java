//Created By Rudy Ramirez
//Date: 11/10/21

import java.util.Arrays;

public class MaxHeap {
	private int[] Heap;
    private int size;
    private int maxsize;
 
    //Constructor
    public MaxHeap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new int[this.maxsize+1];
		Heap[0] = Integer.MAX_VALUE;
    }
 
    //Return position of Parent
    private int getParentPos(int childPos) 
	{
		return childPos / 2; 
	}
 
    //Return position of Left Children
    private int getLeftChildPos(int parentPos)
	{
		return (2 * parentPos);
	}
 
    //Return position of Right Children
    private int getRightChildPos(int parentPos)
    {
        return (2 * parentPos) + 1;
    }
	
    //Return TRUE if Node is leaf
    private boolean isLeaf(int pos)
    {
        //Checks if the position is at the lowest level of the Heap
		if (pos > (size / 2) && pos <= size)
		{
            return true;
        }
        return false;
    }
	
 
    //Swapping Nodes
    private void swap(int pos1, int pos2)
    {
        int temp;
        temp = Heap[pos1];
        Heap[pos1] = Heap[pos2];
        Heap[pos2] = temp;
    }
 
    //Recursive function to max heapify (Top-Down Approach)
    private void heapifyTD(int pos)
    {
		//Exit Recursive Function if current Node is a Leaf (Bottom of Heap)
		if (isLeaf(pos))
            return;	
 
        //Check if Current Node has Children with Greater Value
		if (Heap[pos] < Heap[getLeftChildPos(pos)] || 
			Heap[pos] < Heap[getRightChildPos(pos)] )
		{
            //If both children are greater than their parent, swap with
			//left child to maintain Max Heap
			if (Heap[getLeftChildPos(pos)] > Heap[getRightChildPos(pos)])
			{
                swap(pos, getLeftChildPos(pos));
                heapifyTD(getLeftChildPos(pos));
            }
            else 
			{
                swap(pos, getRightChildPos(pos));
                heapifyTD(getRightChildPos(pos));
            }
        }
    }
	
	//Recursive function to max heapify (Bottom-Up Approach)
    private void heapifyBU(int pos)
	{
		int temp = Heap[pos];
		while((pos > 0) && (temp > Heap[getParentPos(pos)]))
		{
			Heap[pos] = Heap[getParentPos(pos)];
			pos = getParentPos(pos);
		}
		Heap[pos] = temp;
	}
 
    //Insert new element into Max Heap
    public void insert(int element)
    {
        //Insert element at end of Heap
		Heap[++size] = element;
        int current = size;
		heapifyBU(current);
    }
 
    //Remove Root from Max Heap
    public int extractMax()
    {
        int popped = Heap[1];
        Heap[1] = Heap[size];
		Heap[size++] = 0;
		size -= 2;
        heapifyTD(1);
        return popped;
    }
	
	//Heapsort
	public int[] heapsort(int[] unsortedArray)
	{
		//Build a Heap
		for(int i = 0; i < unsortedArray.length; i++)
		{
			insert(unsortedArray[i]);
		}
		
		//Extract Max
		int origSize = size;
		int[] sortedArray = new int[origSize];
		for(int j = 1; j <= origSize; j++)
		{
			sortedArray[size - 1] = extractMax();
		}
		
		//Return Sorted Array
		return sortedArray;
	}
	
	//Displays Heap
    public void print()
    {
        for (int i = 1; i <= size / 2; i++)
		{
            System.out.println(
                "Parent : " + Heap[i]
                + ", LEFT CHILD : " + Heap[2 * i]
                + ", RIGHT CHILD :" + Heap[2 * i + 1]);
        }
    }
 
    public static void main(String[] arg)
    {
		//**********************************************************************
		
		//Build Max Heap
		MaxHeap maxHeap1 = new MaxHeap(15);
		
		System.out.println("Max Heap Tree: ");
		//int[] array1 = {1,4,2,5,13,6,17};
		//int[] array1 = {30,20,11,6,4,3,2};
		//int[] array1 = {11,30,20,4,6,3,60,2};
		int[] array1 = {2,8,5,14,20};
		for(int i = 0; i < array1.length; i++)
		{
			maxHeap1.insert(array1[i]);
		}
        maxHeap1.print();
		
		System.out.println();
		
		//**********************************************************************
		/*
		//Heapsort
		MaxHeap maxHeap2 = new MaxHeap(15);
		
		System.out.println("Heapsort Operation Test: ");
		System.out.println("Unsorted Array: ");
		//int[] array2 = {1,4,2,5,13,6,17};
		//int[] array2 = {30,20,11,6,4,3,2};
		//int[] array2 = {11,30,20,4,6,3,60,2};
		int[] array2 = {2,8,5,14,20};
		System.out.println(Arrays.toString(array2));
		
		System.out.println("Heapsort: ");
		int[] array3 = maxHeap2.heapsort(array2);
		System.out.println(Arrays.toString(array3));
		
		System.out.println();
		*/
		//**********************************************************************
		/*
		//Print Root of Max Heap
        System.out.println("The Maximum Value/Root is " + maxHeap1.extractMax());
		System.out.println("Updated Max Heap Tree: ");
		maxHeap1.print();
		
		System.out.println();
		*/
		//**********************************************************************
		
		//Insert new element into First Array
		maxHeap1.insert(40);
		System.out.println("Updated Max Heap Tree After Inserting 40: ");
		maxHeap1.print();
		
		//**********************************************************************
    }
}
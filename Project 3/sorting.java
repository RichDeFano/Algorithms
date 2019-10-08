package project3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
///////////////////////////////
//NOTE:
//Running all four trials in one run may cause a stack overflow error.
//This was solved by running each trial independantly of each other,
//and may need to be done depending on the computer.
////////////////////////////////

public class sorting {
    
    private static int[] arr;
    private static int[] arrCopy;
    private static int[] mergeArr;
    private static BufferedReader read;
    private static Random randomGenerator;
    
    private static int size;
    private static int random;
//Task One//////////////////////////////////////////////////////////////////////
    private static void mergesort2(int low, int high){
        if ((high-low) < 1000)
        {insertSort(low,high);}
        else
        {        
            if (low < high-1) 
            {
            int middle = (high + low) / 2;
            mergesort2(low, middle);
            mergesort2(middle, high);
            merge(low, middle, high);
            }
        }
    }
    private static void quicksort2(int low, int high){
        if ((high-low) < 1000)
        {insertSort(low,high);}
        else
        {
            int i = low, j = high;
	    int pivot = arr[(high+low)/2];
	    while (i <= j) {
    	      while (arr[i] < pivot) i++;
    	      while (arr[j] > pivot) j--;
    	      if (i < j) {
    	        exchange(i, j);
    	        i++;
    	        j--;
    	      } else if (i == j) { i++; j--; }
        }
    	if (low < j)
    	      quicksort2(low, j);
    	if (i < high)
    	      quicksort2(i, high);
        }
    }
    
    private static void task1(){
        Random randomGenerator = new Random();
        LinkedList<int[]> listOfArrays = new LinkedList<>();
        int numbOfArrays = 100;
        int sizeOfArrays = 1000000;
        size = sizeOfArrays;
        System.out.println("--Task One--");
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            for (int j = 0; j < sizeOfArrays; j++)
            {
                int tempInt = randomGenerator.nextInt();
                //System.out.println("TempInt: " + tempInt);
                tempArray[j] = tempInt;
            }
            listOfArrays.add(tempArray);
        }

        /* Printing the Arrays for testing
        while (!(listOfArrays.isEmpty()))
        {
            int[] arrayAt = listOfArrays.remove();
            System.out.println(Arrays.toString(arrayAt));
        }
        */
        long startMS, startMS2, startQS, startQS2, endMS, endMS2, endQS, endQS2,mergeTime,merge2Time,quickTime,quick2Time;
        int counter = 0;
        long[] mergeTimes = new long[numbOfArrays];
        long[] merge2Times = new long[numbOfArrays];
        long[] quickTimes = new long[numbOfArrays];
        long[] quick2Times = new long[numbOfArrays];

        while (!(listOfArrays.isEmpty()))
        {
            int[] arrayOn = listOfArrays.remove();
            arr = arrayOn;
            //for (int i=0; i<size; i++) arr[i] = arrCopy[i];
            //Merge Sort Testing
                startMS = System.currentTimeMillis();
                mergesort(0,size-1);
                endMS = System.currentTimeMillis();
                mergeTime = endMS-startMS;
                mergeTimes[counter] = mergeTime;
            //Merge Sort 2 Testing 
                arr = arrayOn;
                startMS2 = System.currentTimeMillis();
                mergesort2(0,size-1);
                endMS2 = System.currentTimeMillis();
                merge2Time = endMS2-startMS2;
                merge2Times[counter] = merge2Time;
            //Quick Sort Testing
                arr = arrayOn;
                startQS = System.currentTimeMillis();
                quicksort(0,size-1);
                endQS = System.currentTimeMillis();
                quickTime = endQS - startQS;
                quickTimes[counter] = quickTime;
            //Quick Sort 2 Testing 
                arr = arrayOn;
                startQS2 = System.currentTimeMillis();
                quicksort2(0,size-1);
                endQS2 = System.currentTimeMillis();
                quick2Time = endQS2 - startQS2;
                quick2Times[counter] = quickTime;
        System.out.println("Testing for Array " + (counter+1) + ":" + "Merge Sort: " + mergeTime + " ms" + "|" + "Merge Sort 2: " + merge2Time + " ms" + "|"
        + "Quick Sort: " + quickTime + " ms" + "|" + "Quick Sort 2: " + quick2Time + " ms" + "|");
        counter++;
        }
        calculateAverageTime(quickTimes,"Quick Sort");
        calculateAverageTime(quick2Times,"Quick Sort(2)");
        calculateAverageTime(mergeTimes,"Merge Sort");
        calculateAverageTime(merge2Times,"Merge Sort(2)");
    }    
//Task Two//////////////////////////////////////////////////////////////////////
    public static boolean isSorted(int low, int high){
        boolean isSorted = true;
        
        int lastValue = low;
        for(int i = low+1; i < high; i++)
        {
            int currentValue = arr[i];
            if (lastValue > currentValue)
            {isSorted = false;}
            lastValue = currentValue;
        }
        return isSorted;
    }   
    private static void quicksort3(int low, int high){
            boolean checkSorted = isSorted(low,high);
            if (checkSorted == false)
            {        
            int i = low, j = high;
	    int pivot = arr[(high+low)/2];
	    while (i <= j) {
    	      while (arr[i] < pivot) i++;
    	      while (arr[j] > pivot) j--;
    	      if (i < j) {
    	        exchange(i, j);
    	        i++;
    	        j--;
    	      } else if (i == j) { i++; j--; }
        }
    	if (low < j)
    	      quicksort3(low, j);
    	if (i < high)
    	      quicksort3(i, high);
        }
    }
    private static void quicksort4(int low, int high){
        if ((high-low) < 1000)
        {insertSort(low,high);}
        else
        {
            boolean checkSorted = isSorted(low,high);
            if (checkSorted == false)
                    {
                        int i = low, j = high;
                        int pivot = arr[(high+low)/2];
                        while (i <= j) {
                          while (arr[i] < pivot) i++;
                          while (arr[j] > pivot) j--;
                          if (i < j) {
                            exchange(i, j);
                            i++;
                            j--;
                          } else if (i == j) { i++; j--; }
                    }
                    //System.out.println("DEBUG: low: " + low + " High: " + high + " j: " + j + " i: " + i );
                    if (low < j)
                          quicksort4(low, j);
                    if (i < high)
                          quicksort4(i, high);
        }
        }
    }
    
    private static void task2(){
        
    ////Creation of Arrays for analysis
        Random randomGenerator = new Random();
        LinkedList<int[]> listOfArrays = new LinkedList<>();
        //Creating 10 random arrays of size 1000000
        int numbOfArrays = 10;
        int sizeOfArrays = 1000000;
        size = sizeOfArrays;
        System.out.println("--Task Two--");
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            for (int j = 0; j < sizeOfArrays; j++)
            {
                int tempInt = randomGenerator.nextInt();
                //System.out.println("TempInt: " + tempInt);
                tempArray[j] = tempInt;
            }
            listOfArrays.add(tempArray);
        }
        //Creating 10 sorted arrays of size 10000000
        sizeOfArrays = 10000000;
        size = sizeOfArrays;
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            for (int j = 0; j < sizeOfArrays; j++)
            {
                int tempInt = randomGenerator.nextInt();
                //System.out.println("TempInt: " + tempInt);
                tempArray[j] = tempInt;
            }
            arr = tempArray;
            quicksort(0,sizeOfArrays-1);
            tempArray = arr;
            listOfArrays.add(tempArray);
        }
        //Creating 10 reversely sorted arrays of size 10000000
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            for (int j = 0; j < sizeOfArrays; j++)
            {
                int tempInt = randomGenerator.nextInt();
                //System.out.println("TempInt: " + tempInt);
                tempArray[j] = tempInt;
            }
            arr = tempArray;
            quicksort(0,sizeOfArrays-1);
            int[] reversedArray = arr;
            for (int k=0; k< sizeOfArrays/2; k++)
            {
                int temp = reversedArray[k];
                reversedArray[k] = reversedArray[sizeOfArrays - k - 1];
                reversedArray[sizeOfArrays - k - 1] = temp;
            }
            
            listOfArrays.add(reversedArray);
        }
    ////Testing the performance of quicksorts
        int counter = 0;
        long startQS, startQS2, startQS3, startQS4,endQS,endQS2,endQS3,endQS4;
        long quickTime, quickTime2, quickTime3, quickTime4;
        
        for (int z = 0; z < 3; z++)
        {
            if (z == 0){System.out.println("--Comparison on Random Arrays--");}
            if (z == 1){System.out.println("--Comparison on Sorted Arrays--");}
            if (z == 2){System.out.println("--Comparison on Reversely Sorted Arrays--");}
            counter = 0;
        long[] quickTimes = new long[numbOfArrays];
        long[] quick2Times = new long[numbOfArrays];
        long[] quick3Times = new long[numbOfArrays];
        long[] quick4Times = new long[numbOfArrays];
        while ((counter <10) && (!listOfArrays.isEmpty()))
        {
            //System.out.println("DEBUG: counter = " + counter);
            int arrayOn[] = listOfArrays.remove();
            arr = arrayOn;
            size = arr.length;
            //Quick Sort Testing
                startQS = System.currentTimeMillis();
                quicksort(0,size-1);
                endQS = System.currentTimeMillis();
                quickTime = endQS-startQS;
                quickTimes[counter] = quickTime;
            //Quick Sort 2 Testing
                arr = arrayOn;
                startQS2 = System.currentTimeMillis();
                quicksort2(0,size-1);
                endQS2 = System.currentTimeMillis();
                quickTime2 = endQS2-startQS2;
                quick2Times[counter] = quickTime2;
            //Quick Sort 3 Testing
                arr = arrayOn;
                startQS3 = System.currentTimeMillis();
                quicksort3(0,size-1);
                endQS3 = System.currentTimeMillis();
                quickTime3 = endQS3-startQS3;
                quick3Times[counter] = quickTime3;
            //Quick Sort 4 Testing
                arr = arrayOn;
                startQS4 = System.currentTimeMillis();
                quicksort4(0,size-1);
                endQS4 = System.currentTimeMillis();
                quickTime4 = endQS4-startQS4;
                quick4Times[counter] = quickTime4;
                
                System.out.println("Testing for Array " + (counter+1) + ":" + "Quick Sort: " + quickTime + " ms" + "|" + "Quick Sort 2: " + quickTime2 + " ms" + "|"
        + "Quick Sort 3: " + quickTime3 + " ms" + "|" + "Quick Sort 4: " + quickTime4 + " ms" + "|");
        counter++;
        }
        calculateAverageTime(quickTimes,"Quick Sort");
        calculateAverageTime(quick2Times,"Quick Sort(2)");
        calculateAverageTime(quick3Times,"Quick Sort(3)");
        calculateAverageTime(quick4Times,"Quick Sort(4)");
        }
        
    }
//Task Three////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    //IMPORTANT/////////////////////////////////////////////
    //Calling Quicksort5 and Quicksort6 recursively cause errors.
    //The issue was in the pivot line, the code to calculate the more
    //extensive pivots was not working if QS5 or QS6 called itself.
    //Changing int pivot = arr[(high+low)/2] to arr[(high+low)/3];
    //Was enough to cause a stack overflow, and so the more in depth
    //median calculations also caused stack overflow.
    ////////////////////////////////////////////////////////
    private static void quicksort5(int low, int high){
                    
                        int i = low, j = high;
               
                        int pivot = arr[(high+low)/2];
                        while (i <= j) {
                          while (arr[i] < pivot) i++;
                          while (arr[j] > pivot) j--;
                          if (i < j) {
                            exchange(i, j);
                            i++;
                            j--;
                          } else if (i == j) { i++; j--; }
                    
                    //System.out.println("DEBUG: low: " + low + " High: " + high + " j: " + j + " i: " + i );
                    if (low < j)
                          quicksort4(low, j);
                    if (i < high)
                          quicksort4(i, high);
    }
    }
    private static void quicksort6(int low, int high){
            int i = low, j = high;
	    // Get the pivot element from the median of 9 equally spaced elements
            int sizeOfArray = size;
            int portionSize = sizeOfArray/8;
            int[] portionPoints = new int[9];
            for (int q = 0; q < 9; q++)
            {
                portionPoints[q] = (portionSize*q);
            }
            int med1 = (portionPoints[0] + portionPoints[1] + portionPoints[2])/3;
            int med2 = (portionPoints[3] + portionPoints[4] + portionPoints[5])/3;
            int med3 = (portionPoints[6] + portionPoints[7] + portionPoints[8])/3;
            int median = (med1 + med2 + med3)/3;
	    int pivot = arr[median];
	    // Divide into two lists
	    while (i <= j) {
    	      while (arr[i] < pivot) i++;
    	      while (arr[j] > pivot) j--;
    	      if (i < j) {
    	        exchange(i, j);
    	        i++;
    	        j--;
    	      } else if (i == j) { i++; j--; }
        }
    	// Recursion
    	if (low < j)
    	      quicksort4(low, j);
    	if (i < high)
    	      quicksort4(i, high);
       }
    private static void callQS5(int low, int high){
        if ((high-low) < 1000)
        {insertSort(low,high);}
        else
        {
            boolean checkSorted = isSorted(low,high);
            if (checkSorted == false)
                    {
                        quicksort5(low,high);
                    }
        }
        
        }
    private static void callQS6(int low, int high){
        if ((high-low) < 1000)
        {insertSort(low,high);}
        else
        {
            boolean checkSorted = isSorted(low,high);
            if (checkSorted == false)
            {quicksort6(low,high);}
        }
        
        }
   
    private static void task3(){
        Random randomGenerator = new Random();
        LinkedList<int[]> listOfArrays = new LinkedList<>();
        int numbOfArrays = 10;
        int sizeOfArrays = 1000000;
        size = sizeOfArrays;
        System.out.println("--Task Three--");
        //Creating 10 random arrays of size 1000000
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            for (int j = 0; j < sizeOfArrays; j++)
            {
                int tempInt = randomGenerator.nextInt();
                //System.out.println("TempInt: " + tempInt);
                tempArray[j] = tempInt;
            }
            listOfArrays.add(tempArray);
        }
        //Creating 10 reversely sorted arrays of size 1000000
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            for (int j = 0; j < sizeOfArrays; j++)
            {
                int tempInt = randomGenerator.nextInt();
                //System.out.println("TempInt: " + tempInt);
                tempArray[j] = tempInt;
            }
            arr = tempArray;
            quicksort(0,sizeOfArrays-1);
            int[] reversedArray = arr;
            for (int k=0; k< sizeOfArrays/2; k++)
            {
                int temp = reversedArray[k];
                reversedArray[k] = reversedArray[sizeOfArrays - k - 1];
                reversedArray[sizeOfArrays - k - 1] = temp;
            }
            
            listOfArrays.add(reversedArray);
        }
        
        //Creating 10 Organ Pipe arrays of size 1000000
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            int startPoint = sizeOfArrays/2;
            boolean isIncreasing = false;
            int temp = startPoint;
            for (int a = 0; a < sizeOfArrays; a++)
            {
                tempArray[a] = temp;
                if (temp == 1)
                {isIncreasing = true;}
                if (isIncreasing == false)
                {temp--;}
                else
                {temp++;}

            }
            
            listOfArrays.add(tempArray);
        }
            ////Testing the performance of quicksorts


            System.out.println("--Comparison on Random Arrays--");
            //calculateT3(numbOfArrays,listOfArrays);
            System.out.println("--Comparison on Reversely Sorted Arrays--");
            //calculateT3(numbOfArrays,listOfArrays);
            System.out.println("--Comparison on Organ-Pipe Shaped Arrays--");
            calculateT3(numbOfArrays,listOfArrays);
        
    
    
    }
    private static void calculateT3(int numbOfArrays, LinkedList<int[]> listOfArrays){
        int counter = 0;
        long startHS, endHS, startQS, endQS,startQS4, endQS4, startQS5, endQS5, startQS6, endQS6, quickTime, quickTime4, quickTime5, quickTime6, heapTime;
        long[] quickTimes = new long[numbOfArrays];
        long[] quick4Times = new long[numbOfArrays];
        long[] quick5Times = new long[numbOfArrays];
        long[] quick6Times = new long[numbOfArrays];
        long[] heapTimes = new long[numbOfArrays];
        while (!(listOfArrays.isEmpty()) && (counter < 10))
        {
            int[] arrayOn = listOfArrays.remove();
            arr = arrayOn;
            size = arr.length;
            //Quick Sort Testing
                startQS = System.currentTimeMillis();
                quicksort(0,size-1);
                endQS = System.currentTimeMillis();
                quickTime = endQS-startQS;
                quickTimes[counter] = quickTime;
            //Quick Sort 4 Testing
                arr = arrayOn;
                startQS4 = System.currentTimeMillis();
                quicksort4(0,size-1);
                endQS4 = System.currentTimeMillis();
                quickTime4 = endQS4-startQS4;
                quick4Times[counter] = quickTime4;
            //Quick Sort 5 Testing
                arr = arrayOn;
                startQS5 = System.currentTimeMillis();
                callQS5(0,size-1);
                endQS5 = System.currentTimeMillis();
                quickTime5 = endQS5-startQS5;
                quick5Times[counter] = quickTime5;
            //Quick Sort 6 Testing
                arr = arrayOn;
                startQS6 = System.currentTimeMillis();
                callQS6(0,size-1);
                endQS6 = System.currentTimeMillis();
                quickTime6 = endQS4-startQS4;
                quick6Times[counter] = quickTime6;
            //Heap Sort Testing
                arr = arrayOn;
                startHS = System.currentTimeMillis();
                heapsort();
                endHS = System.currentTimeMillis();
                heapTime = endHS-startHS;
                heapTimes[counter] = heapTime;
                
                
        System.out.println("Testing for Array " + (counter+1) + ":" + "Quick Sort: " + quickTime + " ms" + "|" + "Quick Sort 4: " + quickTime4 + " ms" + "|"
        + "Quick Sort 5: " + quickTime5 + " ms" + "|" + "Quick Sort 6: " + quickTime6 + " ms" + "|" + "Heap Sort: " + heapTime + " ms" + "|");
        counter++;
        }      
        calculateAverageTime(quickTimes,"Quick Sort");
        calculateAverageTime(quick4Times,"Quick Sort(4)");
        calculateAverageTime(quick5Times,"Quick Sort(5)");
        calculateAverageTime(quick6Times,"Quick Sort(6)");
        calculateAverageTime(heapTimes,"Heap Sort");
    }
//Task Four/////////////////////////////////////////////////////////////////////
    private static void task4(){
        System.out.println("--Task Four--");
        int[] kDisValues = new int[] {10,20,40,80,160,320};
        int[] kExValues =  new int[] {100,200,400,800,1600,3200};
        
        for(int i = 0; i < 6; i++)
        {
            int newK = kExValues[i];
            task4KEx(newK);
        }
        
        for(int i = 0; i < 6; i++)
        {
            int newK2 = kDisValues[i];
            task4KDis(newK2);
        }
        

    }
    private static void task4KEx(int k){
        Random randomGenerator = new Random();
        LinkedList<int[]> listOfArrays = new LinkedList<>();
        int numbOfArrays = 100;
        int sizeOfArrays = 5000000;
        size = sizeOfArrays;
        //Creating 100 k distance arrays of size 5000000
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            for (int j = 0; j < sizeOfArrays; j++)
            {
                int tempInt = randomGenerator.nextInt();
                //System.out.println("TempInt: " + tempInt);
                tempArray[j] = tempInt;
            }
            arr = tempArray;
            naturalMergesort();
          
            
            for (int z = 0; z < k; z++) 
            {
                int x  = randomGenerator.nextInt(size);
                int y  = randomGenerator.nextInt(size);
                exchange(x, y);
            }
            tempArray = arr;
            listOfArrays.add(tempArray);
        }
        
        ////Testing the performance of k-distance sorting
        System.out.println("--K Exchange Sorting--" + "K = " + k);
       
        int counter = 0;
        long startHS, endHS, startQS, endQS, startMS, endMS, startNMS, endNMS, startIS, endIS;
        long heapTime, quickTime, mergeTime, mergeNTime, insertTime;
        long[] heapTimes = new long[numbOfArrays];
        long[] quickTimes = new long[numbOfArrays];
        long[] mergeTimes = new long[numbOfArrays];
        long[] mergeNTimes = new long[numbOfArrays];
        long[] insertTimes = new long[numbOfArrays];
        
        while (!(listOfArrays.isEmpty()))
        {
            int[] arrayOn = listOfArrays.remove();
            arr = arrayOn;
            for (int i=0; i<size; i++) arr[i] = arrCopy[i];
            size = arr.length;
            //Quick Sort Testing
                startQS = System.currentTimeMillis();
                quicksort4(0,size-1);
                endQS = System.currentTimeMillis();
                quickTime = endQS-startQS;
                quickTimes[counter] = quickTime;
            //Heap Sort Testing
                arr = arrayOn;
                startHS = System.currentTimeMillis();
                heapsort();
                endHS = System.currentTimeMillis();
                heapTime = endHS-startHS;
                heapTimes[counter] = heapTime;
            //Merge Sort Testing
                arr = arrayOn;
                startMS = System.currentTimeMillis();
                mergesort(0,size);
                endMS = System.currentTimeMillis();
                mergeTime = endMS-startMS;
                mergeTimes[counter] = mergeTime;
            //Natural Merge Sort Testing
                arr = arrayOn;  
                startNMS = System.currentTimeMillis();
                naturalMergesort();
                endNMS = System.currentTimeMillis();
                mergeNTime = endNMS-startNMS;
                mergeNTimes[counter] = mergeNTime;
            //Insertion Sort Testing
                arr = arrayOn;
                startIS = System.currentTimeMillis();
                insertionSort();
                endIS = System.currentTimeMillis();
                insertTime = endIS-startIS;
                insertTimes[counter] = insertTime;
  
                System.out.println("Testing for Array " + (counter+1) + ":" + "Quick Sort: " + quickTime + " ms" + "|" + "Heap Sort: " + heapTime + " ms" + "|"
        + "Merge Sort: " + mergeTime + " ms" + "|" + "nMerge Sort: " + mergeNTime + " ms" + "|" + "Insertion Sort: " + insertTime + " ms" + "|");
        counter++;
        }
        calculateAverageTime(quickTimes,"Quick Sort");
        calculateAverageTime(heapTimes,"Heap Sort");
        calculateAverageTime(mergeTimes,"Merge Sort");
        calculateAverageTime(mergeNTimes,"Natural Merge Sort");
        calculateAverageTime(insertTimes,"Insertion Sort");

    }
            
    private static void task4KDis(int k){
        Random randomGenerator = new Random();
        LinkedList<int[]> listOfArrays = new LinkedList<>();
        int numbOfArrays = 100;
        int sizeOfArrays = 5000000;
        size = sizeOfArrays;
        //Creating 100 k distance arrays of size 5000000
        for (int i = 0; i < numbOfArrays; i++)
        {
            int tempArray[] = new int[sizeOfArrays];
            for (int j = 0; j < sizeOfArrays; j++)
            {
                int tempInt = randomGenerator.nextInt();
                //System.out.println("TempInt: " + tempInt);
                tempArray[j] = tempInt;
            }
            arr = tempArray;
            naturalMergesort();
            tempArray = arr;
            tempArray = shuffleSubArrays(tempArray,k);
            listOfArrays.add(tempArray);
        }
                ////Testing the performance of k-distance sorting
        System.out.println("--K Distance Sorting--" + "K = " + k);
       
        int counter = 0;
        long startHS, endHS, startQS, endQS, startMS, endMS, startNMS, endNMS, startIS, endIS;
        long heapTime, quickTime, mergeTime, mergeNTime, insertTime;
        long[] heapTimes = new long[numbOfArrays];
        long[] quickTimes = new long[numbOfArrays];
        long[] mergeTimes = new long[numbOfArrays];
        long[] mergeNTimes = new long[numbOfArrays];
        long[] insertTimes = new long[numbOfArrays];
        
        while (!(listOfArrays.isEmpty()))
        {
            int[] arrayOn = listOfArrays.remove();
            arr = arrayOn;
            for (int i=0; i<size; i++) arr[i] = arrCopy[i];
            size = arr.length;
            //Quick Sort Testing
                startQS = System.currentTimeMillis();
                quicksort4(0,size-1);
                endQS = System.currentTimeMillis();
                quickTime = endQS-startQS;
                quickTimes[counter] = quickTime;
            //Heap Sort Testing
                arr = arrayOn;
                startHS = System.currentTimeMillis();
                heapsort();
                endHS = System.currentTimeMillis();
                heapTime = endHS-startHS;
                heapTimes[counter] = heapTime;
            //Merge Sort Testing
                arr = arrayOn;
                startMS = System.currentTimeMillis();
                mergesort(0,size);
                endMS = System.currentTimeMillis();
                mergeTime = endMS-startMS;
                mergeTimes[counter] = mergeTime;
            //Natural Merge Sort Testing
                arr = arrayOn;  
                startNMS = System.currentTimeMillis();
                naturalMergesort();
                endNMS = System.currentTimeMillis();
                mergeNTime = endNMS-startNMS;
                mergeNTimes[counter] = mergeNTime;
            //Insertion Sort Testing
                arr = arrayOn;
                startIS = System.currentTimeMillis();
                insertionSort();
                endIS = System.currentTimeMillis();
                insertTime = endIS-startIS;
                insertTimes[counter] = insertTime;

                System.out.println("Testing for Array " + (counter+1) + ":" + "Quick Sort: " + quickTime + " ms" + "|" + "Heap Sort: " + heapTime + " ms" + "|"
        + "Merge Sort: " + mergeTime + " ms" + "|" + "nMerge Sort: " + mergeNTime + " ms" + "|" + "Insertion Sort: " + insertTime + " ms" + "|");
        counter++;
        }
        calculateAverageTime(quickTimes,"Quick Sort");
        calculateAverageTime(heapTimes,"Heap Sort");
        calculateAverageTime(mergeTimes,"Merge Sort");
        calculateAverageTime(mergeNTimes,"Natural Merge Sort");
        calculateAverageTime(insertTimes,"Insertion Sort");
    }
////////////////////////////////////////////////////////////////////////////////
    private static void printArray(String msg) {
    	System.out.print(msg + " [" + arr[0]);
        for(int i=1; i<size; i++) {
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
    }
    
    public static void exchange(int i, int j){
        int t=arr[i];
        arr[i]=arr[j];
        arr[j]=t; 
    }
    
    public static void insertSort(int left, int right) {
	  // insertSort the subarray arr[left, right]
	  int i, j;

	  for(i=left+1; i<=right; i++) {
	    int temp = arr[i];           // store a[i] in temp
	    j = i;                       // start shifts at i
	    // until one is smaller,
	    while(j>left && arr[j-1] >= temp) {
		  arr[j] = arr[j-1];        // shift item to right
		  --j;                      // go left one position
	    }
	    arr[j] = temp;              // insert stored item
	  }  // end for
    }  // end insertSort()
    
    public static void insertionSort() {
    	insertSort(0, size-1);  
    } // end insertionSort()

    public static void maxheapify(int i, int n) { 
	   // Pre: the left and right subtrees of node i are max heaps.
	   // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left=2*i+1;
        int right=2*i+2;
        
        if(left < n && arr[left] > arr[max]) max = left;
        if(right < n && arr[right] > arr[max]) max = right;
        
        if (max != i) {  // node i is not maximal
            exchange(i, max);
            maxheapify(max, n);
        }
    }
    
    public static void heapsort(){
    	// Build an in-place bottom up max heap
        for (int i=size/2; i>=0; i--) maxheapify(i, size);
         
        for(int i=size-1;i>0;i--) {
            exchange(0, i);       // move max from heap to position i.
            maxheapify(0, i);     // adjust heap
        }
    }
    
    private static void mergesort(int low, int high) {
    	// sort arr[low, high-1]
        // Check if low is smaller then high, if not then the array is sorted
        if (low < high-1) {
          // Get the index of the element which is in the middle
          int middle = (high + low) / 2;
          // Sort the left side of the array
          mergesort(low, middle);
          // Sort the right side of the array
          mergesort(middle, high);
          // Combine them both
          merge(low, middle, high);
        }
      }

    private static void merge(int low, int middle, int high) {
    	// merge arr[low, middle-1] and arr[middle, high-1] into arr[low, high-1]
    	
        // Copy first part into the arrCopy array
        for (int i = low; i < middle; i++)
        {
            //System.out.println("DEBUG: I = " + i);
            mergeArr[i] = arr[i];
        }

        int i = low;
        int j = middle;
        int k = low;

        // Copy the smallest values from either the left or the right side back        // to the original array
        while (i < middle && j < high) 
          if (mergeArr[i] <= arr[j]) 
            arr[k++] = mergeArr[i++];
          else 
            arr[k++] = arr[j++];
	
        // Copy the rest of the left part of the array into the original array
        while (i < middle) arr[k++] = mergeArr[i++];
    }
    
    public static void naturalMergesort() {          
    	int run[], i, j, s, t, m;                                                                         
    	                                                                                                                                                                           
    	run = new int[size/2];                                                       
    	                                                                                                   
	    // Step 1: identify runs from the input array arr[]                                                                               
    	i = m = 1; 
    	run[0] = 0;                                                                          
        while (i < size) {                                                                                
    	    if (arr[i-1] > arr[i])                                                                   
    	       if (run[m-1]+1 == i) {     // make sure each run has at least two                          
    	                                                                                                  
    	          j = i+1;                                                                                 
    	          s = 0;                                                                                   
    	          while (j < size && arr[j-1] >= arr[j]) j++;     // not stable                                    
          
    	          // reverse arr[i-1, j-1];     
    	          s = i - 1;
    	          t = j - 1;
    	          while (s < t) exchange(s++, t--);
    	          
    	          i = j;                                                                                   
    	        } else                                                                                     
    	          run[m++] = i++;                                                                          
    	     else i++;                                                                                    
    	}                               	                                                                                                                                                                                            
    	                                                                                                   
        // Step 2: merge runs bottom-up into one run                                                                       
        t = 1;                                                                                         
    	while (t < m) {                                                                                
    	    s = t;                                                                                       
    	    t = s<<1;                                                                                    
    	    i = 0;                                                                                       
    	    while (i+t < m) {                                                                            
    	        merge(run[i], run[i+s], run[i+t]);                                                    
    	        i += t;                                                                                    
    	    }                                                                                            
    	    if (i+s < m) merge(run[i], run[i+s], size);                                                
    	}                                                                                              
                                                
    }   
      
    private static void quicksort(int low, int high) {
	    int i = low, j = high;
            
	    // Get the pivot element from the middle of the list
	    int pivot = arr[(high+low)/2];

	    // Divide into two lists
	    while (i <= j) {
    	      // If the current value from the left list is smaller then the pivot
    	      // element then get the next element from the left list
    	      while (arr[i] < pivot) i++;
    	      
    	      // If the current value from the right list is larger then the pivot
    	      // element then get the next element from the right list
    	      while (arr[j] > pivot) j--;

    	      // If we have found a value in the left list which is larger than
    	      // the pivot element and if we have found a value in the right list
    	      // which is smaller then the pivot element then we exchange the
    	      // values.
    	      // As we are done we can increase i and j
    	      if (i < j) {
    	        exchange(i, j);
    	        i++;
    	        j--;
    	      } else if (i == j) { i++; j--; }
        }
            //System.out.println("DEBUG: i = " + i + " J = " + j + " High = " + high + " Low = " + low);
    	// Recursion
    	if (low < j)
    	      quicksort(low, j);
    	if (i < high)
    	      quicksort(i, high);
              
    }

    public static void demo1 (String input) {
    	// demonstration of sorting algorithms on random input
    	
        long start, finish;
        System.out.println();
        
        // Heap sort      
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        heapsort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("heapsort on " + input + " input: " + (finish-start) + " milliseconds.");
             
        // Merge sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        mergesort(0, size);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("mergesort on " + input + " input: " + (finish-start) + " milliseconds.");

        // Natural Merge sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        naturalMergesort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("natural mergesort on " + input + " input: " + (finish-start) + " milliseconds.");

        // Quick sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        quicksort(0, size-1);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");   
        System.out.println("quicksort on " + input + " input: " + (finish-start) + " milliseconds.");
    }
    
    public static void demo2 (String input) {
       	// demonstration of sorting algorithms on nearly sorted input
    	
        long start, finish;
	    
        demo1(input); 

        // Insert sort on nearly-sorted array      
        for(int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
	insertionSort();	    
	finish = System.currentTimeMillis();
	if (size < 101) printArray("out");
	System.out.println("insertsort on " + input + " input: " + (finish-start) + " milliseconds.");
    }
    
    public static void main(String[] args) {
        
        
        read = new BufferedReader(new InputStreamReader(System.in));
        
        randomGenerator = new Random();
        
        try {
            System.out.print("Please enter the array size : ");
            size = Integer.parseInt(read.readLine());
        } catch(Exception ex){
            ex.printStackTrace();
        }
           
        // create array
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
            
        // fill array
	random = size*10;
        for(int i=0; i<size; i++) 
           arr[i] = arrCopy[i] = randomGenerator.nextInt(random);    
        demo1("random");
        
	// arr[0..size-1] is already sorted. We randomly swap 100 pairs to make it nearly-sorted.
	for (int i = 0; i < 100; i++) {
	    int j  = randomGenerator.nextInt(size);
	    int k  = randomGenerator.nextInt(size);
	    exchange(j, k);
	}
	for(int i=0; i<size; i++) arrCopy[i] = arr[i];
	demo2("nearly sorted");
        
	for(int i=0; i<size; i++) arrCopy[i] = size-i;
	demo1("reversely sorted");
        
        
        remakeArrays(1000000-1);
        task1();
       
        remakeArrays(10000000);
        //task2();
        
        remakeArrays(1000000);
        task3();
        
        remakeArrays(5000000);
        task4();

        
    }
    
    private static void remakeArrays(int newSize){
        size = newSize;
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
    }
    
    private static int[] shuffleSubArrays(int[] fullArray, int k){
        //Random r = new Random();
        int aSize = fullArray.length;
        int numbSubArrays = aSize/k;
        int start = 0;
        int end = start+k;
        for (int j=0;j<numbSubArrays;j++)
        {
            for (int i = start ; i<end; i++)
            {
                int randomPos = ThreadLocalRandom.current().nextInt(start, end);

                int temp = fullArray[i];
                fullArray[i] = fullArray[randomPos];
                fullArray[randomPos] = temp;
            }
            start = end;
            end = end+k;
        }
        
        return fullArray;
    }
    
    private static void calculateAverageTime(long[] arrayOfTimes, String name){
        long sum = 0;
        for (int i = 0; i <arrayOfTimes.length;i++)
        {
            long temp = arrayOfTimes[i];
            sum = sum+temp;
        }
        long average = sum/arrayOfTimes.length;
        System.out.println("Array " + name + " has an average runtime of " + average + " ms");
    }

}

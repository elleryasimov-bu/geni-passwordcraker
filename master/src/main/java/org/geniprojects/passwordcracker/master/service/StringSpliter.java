package org.geniprojects.passwordcracker.master.service;

import org.geniprojects.passwordcracker.master.utils.Range;

import java.util.ArrayList;
import java.util.List;

public class StringSpliter {

   public static ArrayList<Range> splitStrforWorkers(int workerNum)
   {
      if (workerNum == 0) return null;

      String rangeStr ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

      int numOfWorker=workerNum;

      // divide by worker number
      int quotient = rangeStr.length()/numOfWorker;
      int remainder = rangeStr.length()%numOfWorker;

      int counter =0;

      String divided_subString ="";
      for (int i =0; i<rangeStr.length();i++)
      {
         if(counter==0)
            divided_subString+=rangeStr.charAt(i)+"AAAA->";

         counter++;

         if(counter ==quotient)
         {
            if(remainder>0) // distribute one unit of work from "remainder" to worker,  load balancing
            {
               i++;

               remainder--;

            }

            divided_subString+=rangeStr.charAt(i)+"zzzz";

            divided_subString+=" "; // prepare for split in future

            counter=0; // reset counter

         }
      }
      String[] arrOfStr = divided_subString.split(" ", numOfWorker);
      ArrayList<Range> workDistributionRange = new ArrayList<Range>();
      for (String s :arrOfStr )
      {
         String[] temp=s.split("->",2);

         workDistributionRange.add(new Range(temp[0],temp[1]) );

      }
      return workDistributionRange;
   }

   public static void main(String[] args) {
      List<Range> list = splitStrforWorkers(3);
      for (Range range: list) {
         System.out.println(range.leftBound.toString() + " " + range.rightBound.toString());
      }
   }


}
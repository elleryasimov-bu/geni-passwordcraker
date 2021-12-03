package org.geniprojects.passwordcracker.master.service;

import java.security.*;
import java.math.*;

public class StringSpliter {  
  
   public static String[] splitStrforWorkers(int workerNum,String inputString)
   {
      String rangeStr =inputString;

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
      return arrOfStr;
   }

   public static void main(String args[])  {     
      // //Encode     
      // String md5 = MD5("MD5Online");     
      // System.out.println("MD5 hash: "+md5);


      
      
      int numOfWorker =5;

      for (String s :splitStrforWorkers(numOfWorker,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz") )
      {
         System.out.println(s);
      }

   }
}
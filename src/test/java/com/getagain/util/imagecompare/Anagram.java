package com.getagain.util.imagecompare;

import java.util.HashMap;
import java.util.Map;


public class Anagram {
 
 public static void main(String args[])
 {
  String s1 = "A#cknowledgmentsGratefulacknowledgmentisgiventotheauthors,artists,photographers,museums,publishers,andagentsforpermissiontoreprintcopyrightedmaterial.Everyefforthasbeenmadetosecuretheappropriatepermission.Ifanyomissionshavebeenmadeorifcorrectionsarerequired,pleasecontactthePublisher.PhotographicCreditsCover:The“Unisphere”SculptureCelebratesHumanity’sInterdependence,Queens,NewYork,USA,JamesP.Blair.Photograph©JamesP.Blair/NationalGeographicStock.Acknowledgmentscontinueonpage650.Copyright©2014NationalGeographicLearning,CengageLearningALLRIGHTSRESERVED.Nopartofthisworkcoveredbythecopyrighthereinmaybereproduced,transmitted,stored,orusedinanyformorbyanymeansgraphic,electronic,ormechanical,includingbutnotlimitedtophotocopying,recording,scanning,digitizing,taping,webdistribution,informationnetworks,orinformationstorageandretrievalsystems,exceptaspermittedunderSection107or108ofthe1976UnitedStatesCopyrightAct,withoutthepriorwrittenpermissionofthepublisher.NationalGeographicandtheYellowBorderareregisteredtrademarksoftheNationalGeographicSociety.Forproductinformationandtechnologyasistance,contactusatCengageLearningCustomer&SalesSupport,1-800-354-9706Forpermissiontousematerialfromthistextorproduct,submitallrequestsonlineatwww.cengage.com/permissionsFurtherpermissionsquestionscanbeemailedtopermissionrequest@cengage.comNationalGeographicLearning|CengageLearning1LowerRagsdaleDriveBuilding1,Suite200Monterey,CA93940CengageLearningisaleadingproviderofcustomizedlearningsolutionswithofficelocationsaroundtheglobe,includingSingapore,theUnitedKingdom,Australia,Mexico,Brazil,andJapan.Locateyourlocalofficeatwww.cengage.com/global.VisitNationalGeographicLearningonlineatngl.cengage.comVisitourcorporatewebsiteatwww.cengage.comPrinter:RRDonnelley,Willard,OHISBN:9781285439600PrintedintheUnitedStatesofAmerica1314151617181920212210987654321"; 
		  // "taa";
  String s2 = "#AcknowledgmentsGratefulacknowledgmentisgiventotheauthors,artists,photographers,museums,publishers,andagentsforpermissiontoreprintcopyrightedmaterial.Everyefforthasbeenmadetosecuretheappropriatepermission.Ifanyomissionshavebeenmadeorifcorrectionsarerequired,pleasecontactthePublisher.PhotographicCreditsCover:The“Unisphere”SculptureCelebratesHumanity’sInterdependence,Queens,NewYork,USA,JamesP.Blair.Photograph©JamesP.Blair/NationalGeographicStock.Acknowledgmentscontinueonpage650.Copyright©2014NationalGeographicLearning,CengageLearningALLRIGHTSRESERVED.Nopartofthisworkcoveredbythecopyrighthereinmaybereproduced,transmitted,stored,orusedinanyformorbyanymeansgraphic,electronic,ormechanical,includingbutnotlimitedtophotocopying,recording,scanning,digitizing,taping,webdistribution,informationnetworks,orinformationstorageandretrievalsystems,exceptaspermittedunderSection107or108ofthe1976UnitedStatesCopyrightAct,withoutthepriorwrittenpermissionofthepublisher.NationalGeographicandtheYellowBorderareregisteredtrademarksoftheNationalGeographicSociety.Forproductinformationandtechnologyasistance,contactusatCengageLearningCustomer&SalesSupport,1-800-354-9706Forpermissiontousematerialfromthistextorproduct,submitallrequestsonlineatwww.cengage.com/permissionsFurtherpermissionsquestionscanbeemailedtopermissionrequest@cengage.comNationalGeographicLearning|CengageLearning1LowerRagsdaleDriveBuilding1,Suite200Monterey,CA93940CengageLearningisaleadingproviderofcustomizedlearningsolutionswithofficelocationsaroundtheglobe,includingSingapore,theUnitedKingdom,Australia,Mexico,Brazil,andJapan.Locateyourlocalofficeatwww.cengage.com/global.VisitNationalGeographicLearningonlineatngl.cengage.comVisitourcorporatewebsiteatwww.cengage.comPrinter:RRDonnelley,Willard,OHISBN:9781285439600PrintedintheUnitedStatesofAmerica1314151617181920212210987654321"; 
		  // "aat";
//  if(isAnagram(s1,s2))
//  {
//   System.out.println(" Both the strings are anagram");
//  }
  if(isAnagram(s1,s2))
  {
   System.out.println(" Both the strings are anagram");
  }
  else
  {
   System.out.println(" Strings are not anagram");
  }
 }

 // ------------
 
 public static boolean isAnagram(String a, String b) 
 {
       if (a.length() != b.length()) return false;
   int[] alphanum = new int[99999];
   int chars = 0; //no of unique characters
   int num_completed_b = 0;
   char[] a_array = a.toCharArray();
   for (char c : a_array) 
   { // count number of each char in a.
       if (alphanum[c] == 0) ++chars;
       ++alphanum[c];
   }
   for (int i = 0; i < b.length(); ++i) 
   {
       int c = (int) b.charAt(i);
       if (alphanum[c] == 0) 
       { // if you find more of char c in b than in a return false.
           return false;
       }
       --alphanum[c];
       if (alphanum[c] == 0) 
       {
              ++num_completed_b;
           if (num_completed_b == chars) 
           {
               // it’s a match if b has been processed completely
               return i == b.length() - 1;
           }
       }
   }
   return false;
}

}
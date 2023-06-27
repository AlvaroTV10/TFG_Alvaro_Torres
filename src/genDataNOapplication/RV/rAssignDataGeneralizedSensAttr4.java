// Copyright (C) 2018-2021  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.RV;

import java.lang.Math;
import java.util.Random;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import genDataNOapplication.User.User;
import genDataNOapplication.community.Community;
import genDataNOapplication.model.AttributeModel;
import genDataNOapplication.model.ConfigurationModel;
import javafx.util.Pair;

public class rAssignDataGeneralizedSensAttr4{
	
	public static ConfigurationModel configuration;
	
public static double dage(String agev1, String agev2, String[] gage)
{
int i=0, j=0;
 
String age[]  = new String[gage.length]; 
String ag="";
int agix = 0;

for(int k = 0; k < gage.length; k++) {
	age[k] = gage[k];
}


int pos1=0, pos2=0;

for(i=0; i<age.length; i++)
{
	if (agev1.equals(age[i]) == true ) // found it
	{
		pos1 = i;
	}
	if (agev2.equals(age[i]) == true ) // found it
	{
		pos2 = i;
	}
}
double distage = Math.abs(pos1 - pos2) / 6.0;

return(distage);

}

public static String assignAge(String ag, Random generator, Random generator2, double dagthresh, String[] age, String[] gage,
		                       int agL1, int agL2,int agL3,int agL4)
{

	double distage=0.0;
	int agix=0;
	String ag2="";
	int ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and 9
	int count = 0;
	
	boolean got = false;
	if (ranval < agL1)
		ag2 = ag; // 60% of the time it'll be the same so just assign it.
	else
		while (!got)
		{	
			count++;
			agix = 	 ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
			ag2 = age[agix];

			distage = dage(ag, ag2, gage);
		
			if ((ranval > agL2) && (ranval < agL3) && (distage == dagthresh))
				got = true;
			else if ((ranval > agL4) && (distage > dagthresh))
				got = true;
			else if(count > 200)//if after 200 attempts there isn't a result, exit
				got = true;
		}

    return(ag2);

}

public static double dpol(String polv1, String polv2, String[] gpoliticalorientation)
{
int i=0, j=0;
 
String pol[]  = gpoliticalorientation; 
String po="";
int poix = 0;


int pos1=0, pos2=0;

for(i=0; i<pol.length; i++)
{
	if (polv1.equals(pol[i]) == true ) // found it
	{
		pos1 = i;
	}
	if (polv2.equals(pol[i]) == true ) // found it
	{
		pos2 = i;
	}
}
double distpol = Math.abs(pos1 - pos2) / 6.0;

return(distpol);

}

public static String assignPol(String pol, Random generator, Random generator2, double dpolthresh, String[] politicalorientation,
        					   String[] gpoliticalorientation, int polL1, int polL2, int polL3, int polL4)
{
	double distpol=0.0; int polix=0;
	String pol2="";
	
	int ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and 9
	boolean got = false; 
	if (ranval < polL1)
		pol2 = pol; // 60% of the time it'll be the same so just assign it.
	else
		while (!got)
		{
			polix =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
			pol2 = politicalorientation[polix];

			distpol = dpol(pol, pol2, gpoliticalorientation);
	
			if ((ranval > polL2) && (ranval < polL3) && (distpol == dpolthresh))
				got = true;
			else if ((ranval > polL4) && (distpol > dpolthresh))
				got = true;
		}
return(pol2);
}


public static double dseo(String seov1, String seov2)
{

double distseo = 0.0;
if (seov1.equals(seov2) == true ) // just check for equality or not
	distseo=0.0;
else
	distseo=1.0;

return(distseo);
}

public static double dseo2(String seov1, String seov2, String[] gsexualOrientation)
{
	String sexual[] = gsexualOrientation;
	String sex="";

	double distseo = 0.0;

	if (seov1.equals(seov2) == true ) // found it
		distseo = 0.0;
	else if (seov1.equals(sexual[2]) == false && seov2.equals(sexual[2]) == false) 
		distseo = 0.5;
	else
		distseo = 1.0;

return(distseo);
}

public static String assignSeo(String seo, Random generator, Random generator2, double dseothresh, 
		   				       String[] sexualorientation, String[] gsexualorientation,
		   				       int seoL1, int seoL2,int seoL3,int seoL4,
		   				       int seoL1h, int seoL2h,int seoL3h,int seoL4h)
{

	double distseo=0.0; int seoix=0;
	String seo2="";
	int ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and 9
	int count = 0;
	
	boolean got = false;
	if (ranval < seoL1 && seo.equals(gsexualorientation[0]))
		seo2 = seo; // 60% of the time it'll be the same so just assign it.
	else if (ranval < seoL1h && !seo.equals(gsexualorientation[1]))
		seo2 = seo; // 40% of the time it'll be the same so just assign it.
	else
		while (!got)
		{
			count++;
			seoix =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
			seo2 = sexualorientation[seoix];

			distseo = dseo2(seo, seo2, gsexualorientation);
			
			if (seo.equals(gsexualorientation[1]) || seo.equals("Bisexual") || seo.equals("Homosexual"))
			{
				if ((ranval > seoL2h) && (ranval < seoL3h) && (distseo == dseothresh)) // 30% of time !hetero
					got = true;
				else if ((ranval > seoL4h) && (distseo > dseothresh)) // 30% of time hetero
					got = true;
			}
			else // hetero
			{
				if ((ranval > seoL2) && (ranval < seoL3) && (distseo == 0.0)) // 30% of time hetero
					got = true;
				else if ((ranval > seoL4) && (distseo > 0.0)) // 10% of time ! hetero
					got = true;
				else if(count > 200)
					got = true;
			}
		}
return(seo2);
}


public static double dres(String residencev1, String residencev2, String[] gresidence)
{
int i=0, j=0;

String residence[] = gresidence;
String county[]    = new String[6]; 
String state[]     = new String[6]; 
String division[]  = new String[6]; 
String region[]    = new String[6]; 

String res="";
int resix = 0;

county[0] = "Santa Clara";
county[1] = "Santa Barbara";
county[2] = "Suffolk";
county[3] = "Suffolk";
county[4] = "Middlesex";
county[5] = "Santa Clara";

state[0] = "California";
state[1] = "California";
state[2] = "Massachusetts";
state[3] = "Massachusetts";
state[4] = "Massachusetts";
state[5] = "California";

division[0] = "Pacific";
division[1] = "Pacific";
division[2] = "New England";
division[3] = "New England";
division[4] = "New England";
division[5] = "Pacific";

region[0] = "West";
region[1] = "West";
region[2] = "Northeast";
region[3] = "Northeast";
region[4] = "Northeast";
region[5] = "West";

int pos1=0, pos2=0;

double distresidence = 0.0;

for(i=0; i<residence.length; i++)
{
	if (residencev1.equals(residence[i]) == true ) // found it
	{
		pos1 = i;
	}
	if (residencev2.equals(residence[i]) == true ) // found it
	{
		pos2 = i;
	}
}
if (residence[pos1].equals(residence[pos2]))
	distresidence = 0.0;
else if (county[pos1].equals(county[pos2]))
	distresidence = 0.25;
else if (state[pos1].equals(state[pos2]))
	distresidence = 0.50;
else if (division[pos1].equals(division[pos2]))
	distresidence = 0.75;
else if (region[pos1].equals(region[pos2]))
	distresidence = 0.90;
else
	distresidence = 1.00;

return(distresidence);
}

public static String assignRes(String res, Random generator, Random generator2, double dresthresh1, 
							   double dresthresh2, double dresthresh3, String[] residence, String[] gresidence,
							   int resL1, int resL2,int resL3,int resL4)
{

	double distres=0.0; int resix=0;
	String res2="";
	//int count = 0;
	int ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and 9
	boolean got = false;
	if (ranval < resL1)
		res2 = res; // 60% of the time it'll be the same so just assign it.
	else
	while (!got)
	{
		//count++;
		resix =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
		res2 = residence[resix];
		distres = dres(res, res2, gresidence);
	

		if ((ranval >= resL1) && (ranval < resL3) && ((distres == dresthresh1) || (distres == dresthresh2)))
			got = true;
		else if ((ranval >= resL4) && (distres == dresthresh3))
			got = true;	

	}
	return(res2);
}


public static double dgen(String genderv1, String genderv2)
{
	double distgender = 0.0;

	if (genderv1.equals(genderv2) == true ) // just check for equality or not
		distgender=0.0;
	else
		distgender=1.0;

	return(distgender);
}

public static String assignGen(String gen, Random generator, Random generator2, 
                               int genL1, String[] gender, String[] ggender)

{
    int ranval=0, genix=0; 
    boolean got=false; double distgen=0.0;
	String gen2="";
	
	ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and limit-1

	while (!got)
	{
		
		gen2 = gender[ranval]; //genix -> ranval
		distgen = dgen(gen, gen2);
		
		if (ranval < genL1)
		{
			gen2 = gen; // 44% of the time assign the same
			got = true;
		}
		else 
		{
			got = true; // 56% of the time assign randomly (on average will result in 66% of first gender)
		}
	}
	
return(gen2);
}


public static double drel(String relv1, String relv2)
{

double distrel = 0.0;
if (relv1.equals(relv2) == true ) // just check for equality or not
	distrel=0.0;
else
	distrel=1.0;

return(distrel);
}

public static double drel2(String religionv1, String religionv2, String[] greligion)
{
	double distreligion = 0.0;
	String religion[]    	 = greligion; 


	if (religionv1.equals(religionv2) == true ) // found it
		distreligion = 0.0;

	// Buddhist, Hindu and Sikh are considered quite similar
	else if ( (religionv1.equals(religion[0]) == true || religionv1.equals(religion[2]) == true || religionv1.equals(religion[5]) == true)
			&&  (religionv2.equals(religion[0]) == true || religionv2.equals(religion[2]) == true || religionv2.equals(religion[5]) == true)
			)
		distreligion = 0.5;
	// Christian and Jewish are considered quite similar
	else if ( (religionv1.equals(religion[1]) == true || religionv1.equals(religion[3]) == true )
			&&  (religionv2.equals(religion[1]) == true || religionv2.equals(religion[3]) == true )
			)
		distreligion = 0.5;
	else
		distreligion = 1.0;


return(distreligion);
}

public static String assignRel(String rel, Random generator, Random generator2, double drelthresh, String[] religion, String[] greligion,
		int LimRel1,  int LimRel2, int LimRel3, int LimRel4, int LimRel5, int LimRel6, int LimRel7, int LimRel8)
{
	double distrel=0.0; int relix=0;
	String rel2="";
	int ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and 9
	
	//int LimRel1 = 40,  LimRel2 = 41, LimRel3 = 86, LimRel4 = 61, LimRel5 = 71, LimRel6 = 81, LimRel7 = 56, LimRel8 = 70; 
	
	
	int LimRel_X = LimRel1, 
		LimRel_J1 = LimRel2, LimRel_J2 = LimRel3,
		LimRel_C1 = LimRel4, LimRel_C2 = LimRel5,
		LimRel_B1 = LimRel4, LimRel_B2 = LimRel6, LimRel_B3 = LimRel3,
		LimRel_H1 = LimRel4, LimRel_H2 = LimRel6, LimRel_H3 = LimRel3,
		LimRel_S1 = LimRel2, LimRel_S2 = LimRel7, LimRel_S3 = LimRel8,
		LimRel_M = LimRel4;

	
	boolean got = false;
	int count = 0;

	while (!got)
	{
		count++;
		relix =  ((int)(generator.nextInt(1000))); // rnd will be between 0 and limit-1
		rel2 = religion[relix];
		
		distrel = drel2(rel, rel2, greligion);
		
		if (rel.equals("Jewish"))
		{
			if (ranval < 41 )
			{
				rel2 = rel; // 40% of the time it'll be the same so just assign it.
				got = true;
			}
			else if ((ranval > 40) && (ranval < 86)) // 45% of time
			{
				rel2 = "Christian"; // 45% of the time it'll be Christian
				got = true;
			}
			else if ((ranval > 85) && (distrel > drelthresh)) // 15% of time random
				got = true;
		}
		else if (rel.equals("Christian"))
		{
			if (ranval < 61 )
			{
				rel2 = rel; // 60% of the time it'll be the same so just assign it.
				got = true;
			}
			else if ((ranval > 60) && (ranval < 71)) // 10% of time 
			{
				rel2 = "Jewish"; // 10% of the time it'll be Jewish (not so many of them)
				got = true;
			}
			else if ((ranval > 70) && (distrel > drelthresh)) // 30% of time random
				got = true;
		}
		else if (rel.equals("Buddhist"))
		{
			if (ranval < 61 )
			{
				rel2 = rel; // 60% of the time it'll be the same so just assign it.
				got = true;
			}
			else if ((ranval > 60) && (ranval < 81)) // 20% of time 
			{
				rel2 = "Hindu"; // 20% of the time it'll be Hindu (not so many of them)
				got = true;
			}
			else if ((ranval > 80) && (ranval < 86)) // 5% of time 
			{
				rel2 = "Sikh"; // 5% of the time it'll be Sikh (even less of them)
				got = true;
			}
			else if ((ranval > 85) && (distrel > drelthresh)) // 15% of time random
				got = true;
		}
		else if (rel.equals("Hindu"))
		{
			if (ranval < 61 )
			{
				rel2 = rel; // 60% of the time it'll be the same so just assign it.
				got = true;
			}
			else if ((ranval > 60) && (ranval < 81)) // 20% of time 
			{
				rel2 = "Buddhist"; // 20% of the time it'll be Buddhist (not so many of them)
				got = true;
			}
			else if ((ranval > 80) && (ranval < 86)) // 5% of time 
			{
				rel2 = "Sikh"; // 5% of the time it'll be Sikh (even less of them)
				got = true;
			}
			else if ((ranval > 85) && (distrel > drelthresh)) // 15% of time random
				got = true;
		}
		else if (rel.equals("Sikh"))
		{
			if (ranval < 41 )
			{
				rel2 = rel; // 40% of the time it'll be the same so just assign it.
				got = true;
			}
			else if ((ranval > 40) && (ranval < 56)) // 15% of time 
			{
				rel2 = "Buddhist"; // 15% of the time it'll be Buddhist
				got = true;
			}
			else if ((ranval > 55) && (ranval < 70)) // 5% of time 
			{
				rel2 = "Hindu"; // 15% of the time it'll be Hindu
				got = true;
			}
			else if ((ranval > 71) && (distrel > drelthresh)) // 15% of time random
				got = true;
		}
		else if (rel.equals("Muslim"))
		{
			if (ranval < 61 )
			{
				rel2 = rel; // 60% of the time it'll be the same so just assign it.
				got = true;
			}
			else if ((ranval > 60) && (distrel > drelthresh)) // 40% of time random
				got = true;
		}
		else
		{
			if ((ranval < 40) && (distrel == 0.0))
			{
				got = true;
			}
			else if ((ranval >= 40) && (distrel > 0.0))
				got = true;
			else if(count > 200)
				got = true;
		}

	}

return(rel2);

}


public static double dmar(String marv1, String marv2)
{

double distmar = 0.0;
if (marv1.equals(marv2) == true ) // just check for equality or not
	distmar=0.0;
else
	distmar=1.0;

return(distmar);
}

public static double dmar2(String maritalv1, String maritalv2, String[] gmaritalstatus)
{
	String marital[]       	 = gmaritalstatus; 
	String mar="";
	//marital[0] = "Single";   marital[1] = "Married";  marital[2] = "Divorced"; marital[3] = "Widowed";

	double distmarital = 0.0;

	if (maritalv1.equals(maritalv2) == true ) // found it
		distmarital = 0.0;
	
	// married, divorced and widowed are considered quite similar
	else if (maritalv1.equals(maritalv2) == false &&  maritalv1.equals(marital[0]) == false &&
	maritalv2.equals(marital[0]) == false) 
		distmarital = 0.5;
	else
		distmarital = 1.0;

return(distmarital);
}


public static String assignMar(String mar, Random generator, Random generator2, double dagthresh, String[] maritalstatus,
							   String[] gmaritalstatus, int LimMar1, int LimMar2, int LimMar3, int LimMar4, int LimMar5)
{
	double distmar=0.0; int marix = 0;
	String mar2="";
	int ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and 9
	
	//int LimMar1=51, LimMar2=81, LimMar3=31, LimMar4=61, LimMar5=91;
	
	int LimMar_SM1 = LimMar1, LimMar_SM2 = LimMar2, 
		LimMar_D1  = LimMar3, LimMar_D2  = LimMar4, LimMar_D3 = LimMar5, 
		LimMar_W1  = LimMar3, LimMar_W2  = LimMar4, LimMar_W3 = LimMar5;
	
	boolean got = false;
	
	while (!got)
	{
		marix =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
		mar2 = maritalstatus[marix];

		distmar = dmar2(mar, mar2, gmaritalstatus);
		
		if (mar.equals("Single") || mar.equals("Married"))
		{
			if ((ranval < LimMar_SM1)) // 50% of time same
			{ 
				mar2=mar;
				got = true;
		    }
			else if ((ranval > (LimMar_SM1-1)) && (ranval < LimMar_SM2)) // 30% of time same
			{
				if (mar.equals("Single")) 
				{ 
					mar2="Married";
					got = true;
			    }	
				else if (mar.equals("Married")) 
				{ 
					mar2="Single";
					got = true;
			    }
			}
			else if ((ranval > (LimMar_SM2-1)) && (!mar2.equals("Single") && !mar2.equals("Married")))
			{
				got = true;
			}	
		} // end of case when single or married
		
		else if (mar.equals("Divorced"))
		{
			if ((ranval < LimMar_D1)) // 30% of time same
			{ 
				mar2=mar;
				got = true;
		    }
			else if ((ranval > (LimMar_D1-1)) && (ranval < LimMar_D2)) // 30% of time same
			{
				mar2="Single";
				got = true;
			}	
			else if ((ranval > (LimMar_D2-1)) && (ranval < LimMar_D3)) // 30% of time same
			{
				mar2="Married";
				got = true;
			}
			else if ((ranval > (LimMar_D3-1)))
			{
				mar2="Widowed";
				got = true;
			}	
		 } // end of case when divorced
		
		else if (mar.equals("Widowed"))
		{
			if ((ranval < LimMar_W1)) // 30% of time same
			{ 
				mar2=mar;
				got = true;
		    }
			else if ((ranval > (LimMar_W1-1)) && (ranval < LimMar_W2)) // 30% of time same
			{
				mar2="Single";
				got = true;
			}	
			else if ((ranval > (LimMar_W2-1)) && (ranval < LimMar_W3)) // 30% of time same
			{
				mar2="Married";
				got = true;
			}
			else if ((ranval > (LimMar_W3-1)))
			{
				mar2="Divorced";
				got = true;
			}	
		 } // end of case when widowed
		else {
			//If any condition is matched return the initial random value
			got = true;
		}

	}

return(mar2);

}

public static double dprof(String profv1, String profv2)
{

double distprof = 0.0;
if (profv1.equals(profv2) == true ) // just check for equality or not
	distprof=0.0;
else
	distprof=1.0;

return(distprof);
}

public static double dprof2(String professionv1, String professionv2, String[] gprofession)
{
	double distprof = 0.0;
	String profession[]    = gprofession; 

	if (professionv1.equals(professionv2) == true ) // found it
		distprof = 0.0;

	// Manager and professional are considered quite similar
	else if ( (professionv1.equals(profession[0]) == true || professionv1.equals(profession[1]) == true)
	&&  (professionv2.equals(profession[0]) == true || professionv2.equals(profession[1]) == true)
	)
		distprof = 0.5;
	//Service and sales are considered quite similar
	else if ( (professionv1.equals(profession[2]) == true || professionv1.equals(profession[3]) == true)
	&&  (professionv2.equals(profession[2]) == true || professionv2.equals(profession[3]) == true)
	)
		distprof = 0.5;
	//natural and prod are considered quite similar
	else if ( (professionv1.equals(profession[4]) == true || professionv1.equals(profession[5]) == true)
	&&  (professionv2.equals(profession[4]) == true || professionv2.equals(profession[5]) == true)
	)
		distprof = 0.5;
	else
		distprof = 1.0;

return(distprof);
}


public static String assignPro(String prof, Random generator, Random generator2, double dprothresh1, String[] profession,
		                       String[] gprofession, int LimPro1, int LimPro2, int LimPro3, int LimPro4, int LimPro5, int LimPro6)
{
	double distpro=0.0; int profix = 0;
	String pro2="";
	int ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and 9

	boolean got = false;
	
	int LimPro_MPSS = LimPro2, 
			
		LimPro_MPSS_M1 = LimPro1, LimPro_MPSS_M2 = LimPro3, LimPro_MPSS_M3 = LimPro4, LimPro_MPSS_M4 = LimPro5, 
		
		LimPro_MPSS_P1 = LimPro1, LimPro_MPSS_P2 = LimPro3, LimPro_MPSS_P3 = LimPro4, LimPro_MPSS_P4 = LimPro5, 
		
		LimPro_MPSS_SE1 = LimPro1, LimPro_MPSS_SE2 = LimPro3, LimPro_MPSS_SE3 = LimPro4, LimPro_MPSS_SE4 = LimPro5, 
		
		LimPro_MPSS_SA1 = LimPro1, LimPro_MPSS_SA2 = LimPro3, LimPro_MPSS_SA3 = LimPro4, LimPro_MPSS_SA4 = LimPro5, 
		
		LimPro_NP = LimPro2, 
		
		LimPro_NP_N1 = LimPro1, LimPro_NP_N2 = LimPro6, 
		
		LimPro_NP_P1 = LimPro1, LimPro_NP_P2 = LimPro6,
		
		LimPro_S = LimPro2; 
	
	
	while (!got)
	{
		profix =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
		pro2 = profession[profix];
		
		distpro = dprof2(prof, pro2, gprofession);
		
		if (prof.equals("Manager") || prof.equals("Professional") || prof.equals("Service") || prof.equals("Sales and office"))
		{
			if (ranval < LimPro_MPSS)
			{
				pro2 = prof; // 60% of the time assign the same
				got = true;
			}
			else if (prof.equals("Manager"))
			{
				if ((ranval > LimPro_MPSS_M1) && (ranval < LimPro_MPSS_M2)) // 10% of time
				{
					pro2 = "Professional"; // 10% of the time it'll be Professional
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_M2-1)) && (ranval < LimPro_MPSS_M3)) // 10% of time
				{
					pro2 = "Service"; // 10% of the time it'll be Service
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_M3-1)) && (ranval < LimPro_MPSS_M4)) // 10% of time
				{
					pro2 = "Sales and office"; // 10% of the time it'll be Service
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_M4-1)) && (distpro > dprothresh1)) // 10% of time random
					got = true;
			}
			else if (prof.equals("Professional"))
			{
				if ((ranval > LimPro_MPSS_P1) && (ranval < LimPro_MPSS_P2)) // 10% of time
				{
					pro2 = "Manager"; // 10% of the time it'll be Professional
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_P2-1)) && (ranval < LimPro_MPSS_P3)) // 10% of time
				{
					pro2 = "Service"; // 10% of the time it'll be Service
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_P3-1)) && (ranval < LimPro_MPSS_P4)) // 10% of time
				{
					pro2 = "Sales and office"; // 10% of the time it'll be Service
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_P4-1)) && (distpro > dprothresh1)) // 10% of time random
					got = true;
			}
			else if (prof.equals("Service"))
			{
				if ((ranval > LimPro_MPSS_SE1) && (ranval < LimPro_MPSS_SE2)) // 10% of time
				{
					pro2 = "Manager"; // 10% of the time it'll be Professional
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_SE2-1)) && (ranval < LimPro_MPSS_SE3)) // 10% of time
				{
					pro2 = "Professional"; // 10% of the time it'll be Service
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_SE3-1)) && (ranval < LimPro_MPSS_SE4)) // 10% of time
				{
					pro2 = "Sales and office"; // 10% of the time it'll be Service
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_SE4-1)) && (distpro > dprothresh1)) // 10% of time random
					got = true;
			}
			else if (prof.equals("Sales and office"))
			{
				if ((ranval > LimPro_MPSS_SA1) && (ranval < LimPro_MPSS_SA2)) // 10% of time
				{
					pro2 = "Manager"; // 10% of the time it'll be Professional
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_SA2-1)) && (ranval < LimPro_MPSS_SA3)) // 10% of time
				{
					pro2 = "Professional"; // 10% of the time it'll be Service
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_SA3-1)) && (ranval < LimPro_MPSS_SA4)) // 10% of time
				{
					pro2 = "Service"; // 10% of the time it'll be Service
					got = true;
				}
				else if ((ranval > (LimPro_MPSS_SA4-1)) && (distpro > dprothresh1)) // 10% of time random
					got = true;
			}
		}
		else if (prof.equals("Natural resources construction") || 
				 prof.equals("Production transportation"))
		{
			if (ranval < LimPro_NP)
			{
				pro2 = prof; // 60% of the time assign the same
				got = true;
			}
			else if (prof.equals("Natural resources construction"))
			{
				if ((ranval > LimPro_NP_N1) && (ranval < LimPro_NP_N2)) // 25% of time
				{
					pro2 = "Production transportation"; // 25% of the time it'll be other one
					got = true;
				}
				else if ((ranval > (LimPro_NP_N2-1)) && (distpro > dprothresh1)) // 15% of time random
					got = true;
			}
			else if (prof.equals("Production transportation"))
			{
				if ((ranval > LimPro_NP_P1) && (ranval < LimPro_NP_P2)) // 25% of time
				{
					pro2 = "Natural resources construction"; // 25% of the time it'll be other one
					got = true;
				}
				else if ((ranval > (LimPro_NP_P1-1)) && (distpro > dprothresh1)) // 15% of time random
					got = true;
			}
		}
		else if (prof.equals("Student"))
		{
			if (ranval < LimPro_S)
			{
				pro2 = prof; // 60% of the time assign the same
				got = true;
			}
			else if ((ranval > (LimPro_S-1)) && (distpro > dprothresh1)) // 40% of time random
				got = true;
		}
		else if (distpro > dprothresh1)
			got = true;
	}

return(pro2);
}


public static double dlikes(String lk1, String lk2, String lk3, String lk21, String lk22, String lk23)
{
int i=0, j=0;

String likes[] = new String[20];

double plikesd[][] = new double[20][20];

//Compute the distances based on similarity
plikesd[0][0] = 0.0;  	// Music_artist - Music_artist
plikesd[1][0] = 0.5;    // Drink - Music_artist
plikesd[2][0] = 0.25;   // TV_Show - Music_artist
plikesd[3][0] = 0.5;    // Sports - Music_artist
plikesd[4][0] = 0.5;  	// Animals - Music_artist
plikesd[5][0] = 0.25;   // Computer_games - Music_artist
plikesd[6][0] = 0.25;   // News - Music_artist
plikesd[7][0] = 0.5;    // Food - Music_artist
plikesd[8][0] = 0.5;    // Travel - Music_artist
plikesd[9][0] = 0.25;   // Fashion - Music_artist
plikesd[10][0] = 0.5;   // Cars - Music_artist
plikesd[11][0] = 0.5;   // Jokes - Music_artist
plikesd[12][0] = 0.5;   // Technology - Music_artist
plikesd[13][0] = 0.5;   // Watch - Music_artist
plikesd[14][0] = 0.25;  // Jewellery - Music_artist
plikesd[15][0] = 0.25;  // Dance - Music_artist
plikesd[16][0] = 0.5;   // Physical_fitness - Music_artist
plikesd[17][0] = 0.5;   // Architecture - Music_artist
plikesd[18][0] = 0.5;   // Interior_design - Music_artist
plikesd[19][0] = 0.25;  // Culture - Music_artist

plikesd[0][1] = 0.5;  	// Music_artist - Drink
plikesd[1][1] = 0.0;    // Drink - Drink
plikesd[2][1] = 0.5;    // TV_Show - Drink
plikesd[3][1] = 0.25;   // Sports - Drink
plikesd[4][1] = 0.5;  	// Animals - Drink
plikesd[5][1] = 0.25;   // Computer_games - Drink
plikesd[6][1] = 0.5;    // News - Drink
plikesd[7][1] = 0.25;   // Food - Drink
plikesd[8][1] = 0.5;    // Travel - Drink
plikesd[9][1] = 0.5;    // Fashion - Drink
plikesd[10][1] = 0.5;   // Cars - Drink
plikesd[11][1] = 0.5;   // Jokes - Drink
plikesd[12][1] = 0.5;   // Technology - Drink
plikesd[13][1] = 0.5;   // Watch - Drink
plikesd[14][1] = 0.5;   // Jewellery - Drink
plikesd[15][1] = 0.5;   // Dance - Drink
plikesd[16][1] = 0.5;   // Physical_fitness - Drink
plikesd[17][1] = 0.5;   // Architecture - Drink
plikesd[18][1] = 0.5;   // Interior_design - Drink
plikesd[19][1] = 0.5;   // Culture - Drink

plikesd[0][2] = 0.25;  	// Music_artist - TV_Show
plikesd[1][2] = 0.5;    // Drink - TV_Show
plikesd[2][2] = 0.0;    // TV_Show - TV_Show
plikesd[3][2] = 0.25;   // Sports - TV_Show
plikesd[4][2] = 0.25;  	// Animals - TV_Show
plikesd[5][2] = 0.25;   // Computer_games - TV_Show
plikesd[6][2] = 0.25;   // News - TV_Show
plikesd[7][2] = 0.25;   // Food - TV_Show
plikesd[8][2] = 0.5;    // Travel - TV_Show
plikesd[9][2] = 0.5;    // Fashion - TV_Show
plikesd[10][2] = 0.25;  // Cars - TV_Show
plikesd[11][2] = 0.25;  // Jokes - TV_Show
plikesd[12][2] = 0.5;   // Technology - TV_Show
plikesd[13][2] = 0.5;   // Watch - TV_Show
plikesd[14][2] = 0.5;   // Jewellery - TV_Show
plikesd[15][2] = 0.25;  // Dance - TV_Show
plikesd[16][2] = 0.5;   // Physical_fitness - TV_Show
plikesd[17][2] = 0.25;  // Architecture - TV_Show
plikesd[18][2] = 0.25;  // Interior_design - TV_Show
plikesd[19][2] = 0.25;  // Culture - TV_Show


plikesd[0][3] = 0.5;  	// Music_artist - Sports
plikesd[1][3] = 0.25;   // Drink - Sports
plikesd[2][3] = 0.25;   // TV_Show - Sports
plikesd[3][3] = 0.0;    // Sports - Sports
plikesd[4][3] = 0.5;  	// Animals - Sports
plikesd[5][3] = 0.5;    // Computer_games - Sports
plikesd[6][3] = 0.25;   // News - Sports
plikesd[7][3] = 0.25;   // Food - Sports
plikesd[8][3] = 0.5;    // Travel - Sports
plikesd[9][3] = 0.5;    // Fashion - Sports
plikesd[10][3] = 0.5;   // Cars - Sports
plikesd[11][3] = 0.5;   // Jokes - Sports
plikesd[12][3] = 0.25;  // Technology - Sports
plikesd[13][3] = 0.5;   // Watch - Sports
plikesd[14][3] = 0.5;   // Jewellery - Sports
plikesd[15][3] = 0.25;  // Dance - Sports
plikesd[16][3] = 0.25;  // Physical_fitness - Sports
plikesd[17][3] = 0.5;   // Architecture - Sports
plikesd[18][3] = 0.5;   // Interior_design - Sports
plikesd[19][3] = 0.5;   // Culture - Sports


plikesd[0][4] = 0.5;  	// Music_artist - Animals
plikesd[1][4] = 0.5;    // Drink - Animals
plikesd[2][4] = 0.25;   // TV_Show - Animals
plikesd[3][4] = 0.5;    // Sports - Animals
plikesd[4][4] = 0.0;  	// Animals - Animals
plikesd[5][4] = 0.5;    // Computer_games - Animals
plikesd[6][4] = 0.5;    // News - Animals
plikesd[7][4] = 0.5;    // Food - Animals
plikesd[8][4] = 0.5;    // Travel - Animals
plikesd[9][4] = 0.5;    // Fashion - Animals
plikesd[10][4] = 0.5;   // Cars - Animals
plikesd[11][4] = 0.25;  // Jokes - Animals
plikesd[12][4] = 0.5;   // Technology - Animals
plikesd[13][4] = 0.5;   // Watch - Animals
plikesd[14][4] = 0.5;   // Jewellery - Animals
plikesd[15][4] = 0.5;   // Dance - Animals
plikesd[16][4] = 0.5;   // Physical_fitness - Animals
plikesd[17][4] = 0.5;   // Architecture - Animals
plikesd[18][4] = 0.5;   // Interior_design - Animals
plikesd[19][4] = 0.5;   // Culture - Animals


plikesd[0][5] = 0.25;  	// Music_artist - Computer_games
plikesd[1][5] = 0.25;   // Drink - Computer_games
plikesd[2][5] = 0.25;   // TV_Show - Computer_games
plikesd[3][5] = 0.5;    // Sports - Computer_games
plikesd[4][5] = 0.5;  	// Animals - Computer_games
plikesd[5][5] = 0.0;    // Computer_games - Computer_games
plikesd[6][5] = 0.5;    // News - Computer_games
plikesd[7][5] = 0.5;    // Food - Computer_games
plikesd[8][5] = 0.5;    // Travel - Computer_games
plikesd[9][5] = 0.5;    // Fashion - Computer_games
plikesd[10][5] = 0.5;   // Cars - Computer_games
plikesd[11][5] = 0.25;  // Jokes - Computer_games
plikesd[12][5] = 0.5;   // Technology - Computer_games
plikesd[13][5] = 0.5;   // Watch - Computer_games
plikesd[14][5] = 0.5;   // Jewellery - Computer_games
plikesd[15][5] = 0.5;   // Dance - Computer_games
plikesd[16][5] = 0.5;   // Physical_fitness - Computer_games
plikesd[17][5] = 0.5;   // Architecture - Computer_games
plikesd[18][5] = 0.5;   // Interior_design - Computer_games
plikesd[19][5] = 0.5;   // Culture - Computer_games


plikesd[0][6] = 0.25;   // Music_artist - News
plikesd[1][6] = 0.5;    // Drink - News
plikesd[2][6] = 0.25;   // TV_Show - News
plikesd[3][6] = 0.25;   // Sports - News
plikesd[4][6] = 0.5;    // Animals - News
plikesd[5][6] = 0.5;    // Computer_games - News
plikesd[6][6] = 0.0;    // News - News
plikesd[7][6] = 0.5;    // Food - News
plikesd[8][6] = 0.5;    // Travel - News
plikesd[9][6] = 0.5;    // Fashion - News
plikesd[10][6] = 0.5;   // Cars - News
plikesd[11][6] = 0.5;   // Jokes - News
plikesd[12][6] = 0.25;  // Technology - News
plikesd[13][6] = 0.5;   // Watch - News
plikesd[14][6] = 0.5;   // Jewellery - News
plikesd[15][6] = 0.5;   // Dance - News
plikesd[16][6] = 0.5;   // Physical_fitness - News
plikesd[17][6] = 0.5;   // Architecture - News
plikesd[18][6] = 0.5;   // Interior_design - News
plikesd[19][6] = 0.25;   // Culture - News


plikesd[0][7] = 0.5;  	// Music_artist - Food
plikesd[1][7] = 0.25; 	// Drink - Food
plikesd[2][7] = 0.25; 	// TV_Show - Food
plikesd[3][7] = 0.25; 	// Sports - Food
plikesd[4][7] = 0.5;  	// Animals - Food
plikesd[5][7] = 0.5;  	// Computer_games - Food
plikesd[6][7] = 0.5;  	// News - Food
plikesd[7][7] = 0.0;  	// Food - Food
plikesd[8][7] = 0.25; 	// Travel - Food
plikesd[9][7] = 0.5;  	// Fashion - Food
plikesd[10][7] = 0.5; 	// Cars - Food
plikesd[11][7] = 0.5; 	// Jokes - Food
plikesd[12][7] = 0.5; 	// Technology - Food
plikesd[13][7] = 0.5; 	// Watch - Food
plikesd[14][7] = 0.5; 	// Jewellery - Food
plikesd[15][7] = 0.5; 	// Dance - Food
plikesd[16][7] = 0.25;	// Physical_fitness - Food
plikesd[17][7] = 0.5; 	// Architecture - Food
plikesd[18][7] = 0.5;   // Interior_design - Food
plikesd[19][7] = 0.25; 	// Culture - Food


plikesd[0][8] = 0.5; 	// Music_artist - Travel
plikesd[1][8] = 0.5; 	// Drink - Travel
plikesd[2][8] = 0.5; 	// TV_Show - Travel
plikesd[3][8] = 0.5; 	// Sports - Travel
plikesd[4][8] = 0.5; 	// Animals - Travel
plikesd[5][8] = 0.5; 	// Computer_games - Travel
plikesd[6][8] = 0.5; 	// News - Travel
plikesd[7][8] = 0.25; 	// Food - Travel
plikesd[8][8] = 0.0; 	// Travel - Travel
plikesd[9][8] = 0.5; 	// Fashion - Travel
plikesd[10][8] = 0.5;	// Cars - Travel
plikesd[11][8] = 0.5; 	// Jokes - Travel
plikesd[12][8] = 0.5; 	// Technology - Travel
plikesd[13][8] = 0.5;	// Watch - Travel
plikesd[14][8] = 0.5; 	// Jewellery - Travel
plikesd[15][8] = 0.5; 	// Dance - Travel
plikesd[16][8] = 0.5; 	// Physical_fitness - Travel
plikesd[17][8] = 0.25; 	// Architecture - Travel
plikesd[18][8] = 0.5; 	// Interior_design - Travel
plikesd[19][8] = 0.25; 	// Culture - Travel

plikesd[0][9] = 0.25; 	// Music_artist - Fashion
plikesd[1][9] = 0.25; 	// Drink - Fashion
plikesd[2][9] = 0.5;  	// TV_Show - Fashion
plikesd[3][9] = 0.5;  	// Sports - Fashion
plikesd[4][9] = 0.25; 	// Animals - Fashion
plikesd[5][9] = 0.25; 	// Computer_games - Fashion
plikesd[6][9] = 0.5; 	// News - Fashion
plikesd[7][9] = 0.5; 	// Food - Fashion
plikesd[8][9] = 0.5; 	// Travel - Fashion
plikesd[9][9] = 0.0; 	// Fashion - Fashion
plikesd[10][9] = 0.5; 	// Cars - Fashion
plikesd[11][9] = 0.5; 	// Jokes - Fashion
plikesd[12][9] = 0.5; 	// Technology - Fashion
plikesd[13][9] = 0.5; 	// Watch - Fashion
plikesd[14][9] = 0.25; 	// Jewellery - Fashion
plikesd[15][9] = 0.5; 	// Dance - Fashion
plikesd[16][9] = 0.5; 	// Physical_fitness - Fashion
plikesd[17][9] = 0.5; 	// Architecture - Fashion
plikesd[18][9] = 0.5; 	// Interior_design - Fashion
plikesd[19][9] = 0.5; 	// Culture - Fashion

plikesd[0][10] = 0.5; 	// Music_artist - Cars
plikesd[1][10] = 0.5; 	// Drink - Cars
plikesd[2][10] = 0.25; 	// TV_Show - Cars
plikesd[3][10] = 0.5; 	// Sports - Cars
plikesd[4][10] = 0.5; 	// Animals - Cars
plikesd[5][10] = 0.5; 	// Computer_games - Cars
plikesd[6][10] = 0.5; 	// News - Cars
plikesd[7][10] = 0.5; 	// Food - Cars
plikesd[8][10] = 0.5; 	// Travel - Cars
plikesd[9][10] = 0.0; 	// Fashion - Cars
plikesd[10][10] = 0.0; 	// Cars - Cars
plikesd[11][10] = 0.5; 	// Jokes - Cars
plikesd[12][10] = 0.25; // Technology - Cars
plikesd[13][10] = 0.5; 	// Watch - Cars
plikesd[14][10] = 0.5; 	// Jewellery - Cars
plikesd[15][10] = 0.5; 	// Dance - Cars
plikesd[16][10] = 0.5; 	// Physical_fitness - Cars
plikesd[17][10] = 0.5; 	// Architecture - Cars
plikesd[18][10] = 0.25; // Interior_design - Cars
plikesd[19][10] = 0.5; 	// Culture - Cars

plikesd[0][11] = 0.5; 	// Music_artist - Jokes
plikesd[1][11] = 0.5; 	// Drink - Jokes
plikesd[2][11] = 0.25; 	// TV_Show - Jokes
plikesd[3][11] = 0.5; 	// Sports - Jokes
plikesd[4][11] = 0.25; 	// Animals - Jokes
plikesd[5][11] = 0.25; 	// Computer_games - Jokes
plikesd[6][11] = 0.5; 	// News - Jokes
plikesd[7][11] = 0.5; 	// Food - Jokes
plikesd[8][11] = 0.5; 	// Travel - Jokes
plikesd[9][11] = 0.0; 	// Fashion - Jokes
plikesd[10][11] = 0.0; 	// Cars - Jokes
plikesd[11][11] = 0.0; 	// Jokes - Jokes
plikesd[12][11] = 0.5;  // Technology - Jokes
plikesd[13][11] = 0.5; 	// Watch - Jokes
plikesd[14][11] = 0.5; 	// Jewellery - Jokes
plikesd[15][11] = 0.5; 	// Dance - Jokes
plikesd[16][11] = 0.5; 	// Physical_fitness - Jokes
plikesd[17][11] = 0.5; 	// Architecture - Jokes
plikesd[18][11] = 0.5;  // Interior_design - Jokes
plikesd[19][11] = 0.5; 	// Culture - Jokes

plikesd[0][12] = 0.5;  	// Music_artist - Technology
plikesd[1][12] = 0.5;  	// Drink - Technology
plikesd[2][12] = 0.5;  	// TV_Show - Technology
plikesd[3][12] = 0.25; 	// Sports - Technology
plikesd[4][12] = 0.5;  	// Animals - Technology
plikesd[5][12] = 0.5;  	// Computer_games - Technology
plikesd[6][12] = 0.25; 	// News - Technology
plikesd[7][12] = 0.5;  	// Food - Technology
plikesd[8][12] = 0.5;  	// Travel - Technology
plikesd[9][12] = 0.5;  	// Fashion - Technology
plikesd[10][12] = 0.25; // Cars - Technology
plikesd[11][12] = 0.5; 	// Jokes - Technology
plikesd[12][12] = 0.0; 	// Technology - Technology
plikesd[13][12] = 0.25; // Watch - Technology
plikesd[14][12] = 0.5;  // Jewellery - Technology
plikesd[15][12] = 0.5;  // Dance - Technology
plikesd[16][12] = 0.5; 	// Physical_fitness - Technology
plikesd[17][12] = 0.25; // Architecture - Technology
plikesd[18][12] = 0.25; // Interior_design - Technology
plikesd[19][12] = 0.25; // Culture - Technology

plikesd[0][13] = 0.5;  	// Music_artist - Watch
plikesd[1][13] = 0.5;  	// Drink - Watch
plikesd[2][13] = 0.5;  	// TV_Show - Watch
plikesd[3][13] = 0.5; 	// Sports - Watch
plikesd[4][13] = 0.5;  	// Animals - Watch
plikesd[5][13] = 0.5;  	// Computer_games - Watch
plikesd[6][13] = 0.5; 	// News - Watch
plikesd[7][13] = 0.5;  	// Food - Watch
plikesd[8][13] = 0.5;  	// Travel - Watch
plikesd[9][13] = 0.5;  	// Fashion - Watch
plikesd[10][13] = 0.5;  // Cars - Watch
plikesd[11][13] = 0.5; 	// Jokes - Watch
plikesd[12][13] = 0.25; // Technology - Watch
plikesd[13][13] = 0.0;  // Watch - Watch
plikesd[14][13] = 0.25; // Jewellery - Watch
plikesd[15][13] = 0.5;  // Dance - Watch
plikesd[16][13] = 0.5; 	// Physical_fitness - Watch
plikesd[17][13] = 0.5;  // Architecture - Watch
plikesd[18][13] = 0.5;  // Interior_design - Watch
plikesd[19][13] = 0.5;  // Culture - Watch

plikesd[0][14] = 0.25;  // Music_artist - Jewellery
plikesd[1][14] = 0.5;  	// Drink - Jewellery
plikesd[2][14] = 0.5;  	// TV_Show - Jewellery
plikesd[3][14] = 0.5; 	// Sports - Jewellery
plikesd[4][14] = 0.5;  	// Animals - Jewellery
plikesd[5][14] = 0.5;  	// Computer_games - Jewellery
plikesd[6][14] = 0.5; 	// News - Jewellery
plikesd[7][14] = 0.5;  	// Food - Jewellery
plikesd[8][14] = 0.5;  	// Travel - Jewellery
plikesd[9][14] = 0.25;  // Fashion - Jewellery
plikesd[10][14] = 0.5;  // Cars - Jewellery
plikesd[11][14] = 0.5; 	// Jokes - Jewellery
plikesd[12][14] = 0.25; // Technology - Jewellery
plikesd[13][14] = 0.25; // Watch - Jewellery
plikesd[14][14] = 0.0;  // Jewellery - Jewellery
plikesd[15][14] = 0.5;  // Dance - Jewellery
plikesd[16][14] = 0.5; 	// Physical_fitness - Jewellery
plikesd[17][14] = 0.5;  // Architecture - Jewellery
plikesd[18][14] = 0.5;  // Interior_design - Jewellery
plikesd[19][14] = 0.25; // Culture - Jewellery

plikesd[0][15] = 0.25;  // Music_artist - Dance
plikesd[1][15] = 0.5;  	// Drink - Dance
plikesd[2][15] = 0.25;  // TV_Show - Dance
plikesd[3][15] = 0.25; 	// Sports - Dance
plikesd[4][15] = 0.5;  	// Animals - Dance
plikesd[5][15] = 0.5;  	// Computer_games - Dance
plikesd[6][15] = 0.5; 	// News - Dance
plikesd[7][15] = 0.5;  	// Food - Dance
plikesd[8][15] = 0.5;  	// Travel - Dance
plikesd[9][15] = 0.5;   // Fashion - Dance
plikesd[10][15] = 0.5;  // Cars - Dance
plikesd[11][15] = 0.5; 	// Jokes - Dance
plikesd[12][15] = 0.5;  // Technology - Dance
plikesd[13][15] = 0.5;  // Watch - Dance
plikesd[14][15] = 0.5;  // Jewellery - Dance
plikesd[15][15] = 0.0;  // Dance - Dance
plikesd[16][15] = 0.25; // Physical_fitness - Dance
plikesd[17][15] = 0.5;  // Architecture - Dance
plikesd[18][15] = 0.5;  // Interior_design - Dance
plikesd[19][15] = 0.25; // Culture - Dance

plikesd[0][16] = 0.5;   // Music_artist - Physical_fitness
plikesd[1][16] = 0.5;  	// Drink - Physical_fitness
plikesd[2][16] = 0.5;   // TV_Show - Physical_fitness
plikesd[3][16] = 0.25; 	// Sports - Physical_fitness
plikesd[4][16] = 0.5;  	// Animals - Physical_fitness
plikesd[5][16] = 0.5;  	// Computer_games - Physical_fitness
plikesd[6][16] = 0.5; 	// News - Physical_fitness
plikesd[7][16] = 0.25;  // Food - Physical_fitness
plikesd[8][16] = 0.5;  	// Travel - Physical_fitness
plikesd[9][16] = 0.5;   // Fashion - Physical_fitness
plikesd[10][16] = 0.5;  // Cars - Physical_fitness
plikesd[11][16] = 0.5; 	// Jokes - Physical_fitness
plikesd[12][16] = 0.5;  // Technology - Physical_fitness
plikesd[13][16] = 0.5;  // Watch - Physical_fitness
plikesd[14][16] = 0.5;  // Jewellery - Physical_fitness
plikesd[15][16] = 0.25; // Dance - Physical_fitness
plikesd[16][16] = 0.0;  // Physical_fitness - Physical_fitness
plikesd[17][16] = 0.5;  // Architecture - Physical_fitness
plikesd[18][16] = 0.5;  // Interior_design - Physical_fitness
plikesd[19][16] = 0.5;  // Culture - Physical_fitness

plikesd[0][17] = 0.5;   // Music_artist - Architecture
plikesd[1][17] = 0.5;  	// Drink - Architecture
plikesd[2][17] = 0.25;  // TV_Show - Architecture
plikesd[3][17] = 0.5; 	// Sports - Architecture
plikesd[4][17] = 0.5;  	// Animals - Architecture
plikesd[5][17] = 0.5;  	// Computer_games - Architecture
plikesd[6][17] = 0.5; 	// News - Architecture
plikesd[7][17] = 0.5;   // Food - Architecture
plikesd[8][17] = 0.25;  // Travel - Architecture
plikesd[9][17] = 0.5;   // Fashion - Architecture
plikesd[10][17] = 0.5;  // Cars - Architecture
plikesd[11][17] = 0.5; 	// Jokes - Architecture
plikesd[12][17] = 0.25; // Technology - Architecture
plikesd[13][17] = 0.5;  // Watch - Architecture
plikesd[14][17] = 0.5;  // Jewellery - Architecture
plikesd[15][17] = 0.5;  // Dance - Architecture
plikesd[16][17] = 0.5;  // Physical_fitness - Architecture
plikesd[17][17] = 0.0;  // Architecture - Architecture
plikesd[18][17] = 0.25; // Interior_design - Architecture
plikesd[19][17] = 0.25; // Culture - Architecture

plikesd[0][18] = 0.5;   // Music_artist - Interior_design
plikesd[1][18] = 0.5;  	// Drink - Interior_design
plikesd[2][18] = 0.25;  // TV_Show - Interior_design
plikesd[3][18] = 0.5; 	// Sports - Interior_design
plikesd[4][18] = 0.5;  	// Animals - Interior_design
plikesd[5][18] = 0.5;  	// Computer_games - Interior_design
plikesd[6][18] = 0.5; 	// News - Interior_design
plikesd[7][18] = 0.5;   // Food - Interior_design
plikesd[8][18] = 0.5;   // Travel - Interior_design
plikesd[9][18] = 0.5;   // Fashion - Interior_design
plikesd[10][18] = 0.5;  // Cars - Interior_design
plikesd[11][18] = 0.5; 	// Jokes - Interior_design
plikesd[12][18] = 0.25; // Technology - Interior_design
plikesd[13][18] = 0.5;  // Watch - Interior_design
plikesd[14][18] = 0.5;  // Jewellery - Interior_design
plikesd[15][18] = 0.5;  // Dance - Interior_design
plikesd[16][18] = 0.5;  // Physical_fitness - Interior_design
plikesd[17][18] = 0.25; // Architecture - Interior_design
plikesd[18][18] = 0.0;  // Interior_design - Interior_design
plikesd[19][18] = 0.25; // Culture - Interior_design

plikesd[0][19] = 0.25;   // Music_artist - Culture
plikesd[1][19] = 0.5;  	 // Drink - Culture
plikesd[2][19] = 0.25;   // TV_Show - Culture
plikesd[3][19] = 0.5; 	 // Sports - Culture
plikesd[4][19] = 0.5;  	 // Animals - Culture
plikesd[5][19] = 0.5;  	 // Computer_games - Culture
plikesd[6][19] = 0.25; 	 // News - Culture
plikesd[7][19] = 0.25;   // Food - Culture
plikesd[8][19] = 0.25;   // Travel - Culture
plikesd[9][19] = 0.5;    // Fashion - Culture
plikesd[10][19] = 0.5;   // Cars - Culture
plikesd[11][19] = 0.5; 	 // Jokes - Culture
plikesd[12][19] = 0.25;  // Technology - Culture
plikesd[13][19] = 0.5;   // Watch - Culture
plikesd[14][19] = 0.25;  // Jewellery - Culture
plikesd[15][19] = 0.25;  // Dance - Culture
plikesd[16][19] = 0.5;   // Physical_fitness - Culture
plikesd[17][19] = 0.25;  // Architecture - Culture
plikesd[18][19] = 0.25;  // Interior_design - Culture
plikesd[19][19] = 0.0;   // Culture - Culture

likes[0] = "Music_artist"		; 
likes[1] = "Drink"				; 
likes[2] = "TV_Show"			; 
likes[3] = "Sports"				; 
likes[4] = "Animals"			; 
likes[5] = "Computer_games"		; 
likes[6] = "News"				; 
likes[7] = "Food"				; 
likes[8] = "Travel"				; 
likes[9] = "Fashion"			; 
likes[10] = "Cars"				; 
likes[11] = "Jokes"				; 
likes[12] = "Technology"		; 
likes[13] = "Watch"				; 
likes[14] = "Jewellery"			; 
likes[15] = "Dance"				; 
likes[16] = "Physical_fitness"	; 
likes[17] = "Architecture"		; 
likes[18] = "Interior_design"	; 
likes[19] = "Culture"			; 


String likesA[]    = new String[3]; 
String likesB[]    = new String[3]; 

likesA[0] = lk1;  likesA[1] = lk2;  likesA[2] = lk3;
likesB[0] = lk21; likesB[1] = lk22; likesB[2] = lk23;

int pos1=0, pos2=0, k=0, ii=0, jj=0;

double samepos=0.33, posplus1=0.66, posplus2=1.00; // weighting for ranking of likes.

double distlike1 = 0.0, distlike2 = 0.0, distlike3 = 0.0, distlike = 0.0;

if ( lk1.equals(lk21) &&  lk2.equals(lk22) &&  lk3.equals(lk23) )
	distlike = 0.0;
else
	for(i=0;i<3;i++)
	{
		for(j=0;j<3;j++)
		{
			for(k=0;k<5;k++)
			{
				if (likes[k].equals(likesA[i]))
				{
					pos1=k;
				}
				if (likes[k].equals(likesB[j]))
				{
					pos2=k;
				}
			}
			distlike = distlike + plikesd[pos1][pos2];
			if (likesA[i].equals(likesB[j])) // bonus for relative position
			{
				if (Math.abs(i-j) == 0)
					distlike = distlike * 0.25;
				else if (Math.abs(i-j) == 1)
					distlike = distlike * 0.50;
				else if (Math.abs(i-j) == 2)
					distlike = distlike * 0.75;
			
			}
			
			
		} //j
		
	} //i



distlike = distlike / 9.0;


return(distlike);
}


public static String[] assignLik(String lk1, String lk2, String lk3, Random generator, Random generator2, 
		                         int likL1, int likL2, int likL3, int likL4, 
		                         double dlikthresh1, double dlikthresh2,  
		                         String[] likes1, String[] likes2, String[] likes3)
{
	String[] likes =new String[100]; boolean got=false;
	
	String lk21="",lk22="",lk23=""; 
	int ranval=0, lkix1=0;
	double distlikes = 0.0;

	ranval = 	 ((int)(generator2.nextInt(100))); // rnd will be between 0 and limit-1

	while (!got)
	{
		lkix1 =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
		lk21 = likes1[lkix1]; lk22 = likes2[lkix1]; lk23 = likes3[lkix1];
		distlikes = dlikes(lk1, lk2, lk3, lk21, lk22, lk23);
		
		if (ranval < likL1) // 34% of time the same
		{
			likes[0] = lk1;	likes[1] = lk2;	likes[2] = lk3;
			got = true;
		}
		else if ((ranval >= likL1) && (ranval < likL2) && (distlikes < dlikthresh1)) // 15% of time between 0.0 and 0.15
		{
			likes[0] = lk21;	likes[1] = lk22;	likes[2] = lk23;
			got = true;
		}
		else if ((ranval >= likL2) && (ranval < likL3) && (distlikes < dlikthresh2)) // 20% of time between 0.15 and 0.24
		{
			likes[0] = lk21;	likes[1] = lk22;	likes[2] = lk23;
			got = true;
		}
		else if ((ranval >= likL3) || (distlikes > 0.0)) // 30% of time random
		{
			likes[0] = lk21;	likes[1] = lk22;	likes[2] = lk23;
			got = true;
		}
	}
	
	
return(likes);
}


/*************************************************************************************************************/
/*********************************************** ASSIGN SEEDS ************************************************/
/*************************************************************************************************************/

public static int AssignSeeds(Random generator, String profile_age[],String profile_gen[],String profile_res[],
		                       String profile_rel[], String profile_mar[], String profile_prof[], String profile_pol[],
		                       String profile_seo[], String profile_lk1[], String profile_lk2[], String profile_lk3[],
		                       List<List<Integer>> profileList, int[] profileCommunityAssaign, int[] profilesFrequencies)

{
	int totalSeedsAssigned = 0;
	
	int i=0, j=0, count=0, profix=0;
	User nw;
	
	String age = "";
	
	String res=""; String gen=""; String ag=""; String rel=""; String mar=""; String prof="";
	String pol=""; String seo=""; String we="";
	String lk1="";	String lk2="";	String lk3=""; String lk21="";	String lk22="";	String lk23="";
	
	

		// FIRST ASSIGN PROTOTYPE PROFILES TO THE SEEDS OF EACH COMMUNITY
		try{
			
			Enumeration enc = RV.Communities.keys();
			double grafsize = RV.Users.size();
			double numcoms  = RV.Communities.size();

			int numprofiles=(int)(grafsize*1.1);
			int profilefreq[] = profilesFrequencies.clone(); // length 10
			int profilevalues[] = new int[numprofiles]; // was 1000
			
			System.out.println("Profile 0 frequency: " + profilefreq[0]);
			
			int freq=0;
			
			// assign profiles based on frequencies
			for(i=0, count=0; i<10; i++)
			{
				freq = profilefreq[i];
				for (j=0;j<freq;++j)
				{
					profilevalues[count]=i;
				    ++count;
				}
			}
			
			for(int k = 0; k < 10; k++) {
			profilevalues[k] = profileCommunityAssaign[k];
			}
			
			
	  		enc = RV.Communities.keys();
	  		int comcount=0;
			while(enc.hasMoreElements()) // For all communities
			{
	    		Community co = (Community)RV.Communities.get((Integer)enc.nextElement());
	    		int co1 = co.getCommunity();
	 			
				
				profix =  ((int)(generator.nextInt(numprofiles))); // rnd will be between 0 and limit-1
				int profileforthiscommunity = profilevalues[profix];
				
	    		
				int profixx = profileforthiscommunity;
				
				profixx = profilevalues[co1]; // ************ this line only for rmat ****************
				
				System.out.println("Profile "+profixx+" assigned to community "+co1);

				ag = profile_age[profixx];  
				gen = profile_gen[profixx];   
				res = profile_res[profixx]; 
				rel = profile_rel[profixx]; 
				mar = profile_mar[profixx]; 
				prof = profile_prof[profixx]; 
				pol = profile_pol[profixx]; 
				seo = profile_seo[profixx]; 
				lk1 = profile_lk1[profixx]; 
				lk2 = profile_lk2[profixx];   
				lk3 = profile_lk3[profixx];

				++comcount;

				
				for(i=0;i<co.seedsc.size();i++) // for each unassigned seed of current community
				{
					int ids = (Integer)co.seedsc.get(i);
					
					nw = (User)RV.Users.get((Integer)ids);
					
					age       = nw.getAge();
					
					if (age == null || age.equals(""))
					{
						nw.loadAge(ag); nw.loadGender(gen);nw.loadResidence(res); 
						nw.loadReligion(rel); nw.loadMaritalStatus(mar); nw.loadProfession(prof);
						nw.loadPoliticalOrientation(pol); nw.loadSexualOrientation(seo);
						nw.loadLike(lk1, 1); nw.loadLike(lk2, 2); nw.loadLike(lk3, 3);

						RV.Users.remove(nw.getUser());
						RV.Users.put(nw.getUser(),nw);
						
						totalSeedsAssigned += 1;
					}
				} // efor each seed
			} // ewhile for all communities
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("\nsomething went wrong writing the output file");
		
		}
		
		return totalSeedsAssigned;
}

/*************************************************************************************************************/
/*********************************************** ASSIGN NEIGHBOURS *******************************************/
/*************************************************************************************************************/
public static int AssignNeighbors(Random generator, Random generator2, String age[], String gage[], String residence[],
								   String gresidence[],
								   String gender[], String ggender[],
								   String sexualorientation[], String gsexualorientation[],
								   String politicalorientation[], String gpoliticalorientation[],
								   String religion[], String greligion[],
								   String maritalstatus[], String gmaritalstatus[],
								   String profession[], String gprofession[],
		                           String likes1[], String likes2[], String likes3[], boolean switchdistance)
		                        		   

{
	int totalNeighborsAssigned = 0;
	//*****************************************************************************************************//
    // KEY PARAMETERS TO TWEAK FOR SEED NEIGHBOR ATTRIBUTES
	int ranval=0;
	// RANLIM = 6; // Neighbors, 8 is equivalent to 20% random
	
	int i=0, j=0;
	User nw;
	
	String agef       = "";
	
	int genix = 0, resix = 0, agix = 0, weix = 0, lkix1 = 0, lkix2 = 0, lkix3 = 0;
	int relix = 0, marix = 0, profix = 0, polix = 0, seoix = 0;
	
	//Specific random thresholds for each attribute
	//double 	//distfage=1.1, 	limfage=0.5,   // AGE, that is, distage must be limage or less (0.2)
			//distfres=1.1, 	limfres=0.5,   // RESIDENCE (0.3)
			//distfpol=1.1, 	limfpol=0.5,   // POLITICAL ORIENTATION (0.2)
			//distfseo=1.1, 	limfseo=0.5,   // SEXUAL ORIENTATION (0.0)
			//distfrel=1.1, 	limfrel=0.5,   // RELIGION (0.0)
			//distfmar=1.1, 	limfmar=0.5,   // MARITAL STATUS (0.0)
			//distfpro=1.1, 	limfpro=0.5,   // PROFESSION (0.0)
			//distflikes=1.1, limflikes=0.5; // LIKES (0.3)
			//distfgen=1.1, 	limfgen=0.0;	  // GENDER (0.0)
	
	String res=""; String gen=""; String ag=""; String rel=""; String mar=""; String prof="";
	String pol=""; String seo=""; String we="";
	String lk1="";	String lk2="";	String lk3=""; String lk21="";	String lk22="";	String lk23="";
	
	boolean got = false;
	
	//************************************************************
	//PROBABILITY RANGES
	// The ranges may be attribute and attribute-value sensitive
	//************************************************************	
	//int polL1  = 61, polL2  = 60, polL3  = 91, polL4  = 90; // POLITICAL ORIENTATION (low)
	int polL1  = 51, polL2  = 50, polL3  = 76, polL4  = 75; // POLITICAL ORIENTATION (medium)
	//int polL1  = 41, polL2  = 40, polL3  = 71, polL4  = 70; // POLITICAL ORIENTATION (high)
	
	int genL1  = 34; // GENDER (low)  SHOULD BE THE OTHER WAY ROUND
	//int genL1  = 44; // GENDER (medium) 44 -> 34
	//int genL1  = 54; // GENDER (high)
	
	//int likL1  = 34, likL2  = 50, likL3  = 70, likL4  = 90; // LIKES (low)
	int likL1  = 29, likL2  = 45, likL3  = 65, likL4  = 85; // LIKES (medium)
	//int likL1  = 24, likL2  = 40, likL3  = 60, likL4  = 80; // LIKES (high)
	
	//int agL1   = 61, agL2   = 60, agL3   = 91, agL4   = 90; // AGE (low)
	int agL1   = 51, agL2   = 50, agL3   = 81, agL4   = 80; // AGE (medium)
	//int agL1   = 41, agL2   = 40, agL3   = 71, agL4   = 70; // AGE (high)
	
	//int resL1  = 61, resL2  = 60, resL3  = 91, resL4  = 90; // RESIDENCE (low)
	int resL1  = 51, resL2  = 50, resL3  = 81, resL4  = 80; // RESIDENCE (medium)
	//int resL1  = 41, resL2  = 40, resL3  = 71, resL4  = 70; // RESIDENCE (high)
	
	//int relL1  = 61, relL2  = 60, relL3  = 91, relL4  = 90; // unused
	
	//int seoL1  = 61, seoL2  = 60, seoL3  = 91, seoL4  = 90; // SEXUAL ORIENTATION (low)
	//int seoL1h = 41, seoL2h = 40, seoL3h = 71, seoL4h = 70;
	int seoL1  = 51, seoL2  = 50, seoL3  = 81, seoL4  = 80; // SEXUAL ORIENTATION (medium)
	int seoL1h = 31, seoL2h = 30, seoL3h = 61, seoL4h = 60;
	//int seoL1  = 41, seoL2  = 40, seoL3  = 71, seoL4  = 70; // SEXUAL ORIENTATION (high)
	//int seoL1h = 21, seoL2h = 20, seoL3h = 51, seoL4h = 50;
	
	//int marL1  = 61, marL2  = 60, marL3  = 91, marL4  = 90; // unused
	//int marL1h = 41, marL2h = 40, marL3h = 71, marL4h = 70; // unused
	
	//int limMar1=51, limMar2=81, limMar3=31, limMar4=61, limMar5=91; // MARITAL STATUS (low)
	int limMar1=46, limMar2=76, limMar3=26, limMar4=56, limMar5=86; // MARITAL STATUS (medium)
	//int limMar1=41, limMar2=71, limMar3=21, limMar4=51, limMar5=81; // MARITAL STATUS (high)
 
	
	//int LimPro1=60, LimPro2=61, LimPro3=71, LimPro4=81, LimPro5=91, LimPro6=86; // PROFESSION (low)
	int LimPro1=50, LimPro2=51, LimPro3=61, LimPro4=71, LimPro5=81, LimPro6=76; // PROFESSION (medium)
	//int LimPro1=40, LimPro2=41, LimPro3=51, LimPro4=61, LimPro5=71, LimPro6=66; // PROFESSION (high)
		 
		 
	
	//int LimRel1 = 40,  LimRel2 = 41, LimRel3 = 86, LimRel4 = 61, LimRel5 = 71, LimRel6 = 81, // RELIGION (low)
	//	LimRel7 = 56, LimRel8 = 70;
	int LimRel1 = 35,  LimRel2 = 36, LimRel3 = 81, LimRel4 = 56, LimRel5 = 66, LimRel6 = 76, // RELIGION (medium)
	  LimRel7 = 51, LimRel8 = 65;
	//int LimRel1 = 30,  LimRel2 = 31, LimRel3 = 76, LimRel4 = 51, LimRel5 = 61, LimRel6 = 71, // RELIGION (high)
	  //LimRel7 = 46, LimRel8 = 60;
	
	//************************************************************
	
	
	
	//************************************************************************************************************
	//DISTANCE THRESHOLDS
	// The distance threshold depends on how the distance is calculated and may differ from attribute to attribute
	// It indicates the distance of the two closest categories. In the case of residence it indicates the distance
	// for categories in the same county, state and max. distance, respectively
	//************************************************************************************************************
	double dpolthresh=(double)((double)1.0/(double)6.0);  // POLITICS
	
	double dprothresh=(double)((double)1.0/(double)2.0);  // PROFESSION
	
	double dgenthresh=(double)1.0; // GENDER
	
	double dagthresh=(double)((double)1.0/(double)6.0);  // AGE
	
	double dresthresh1=(double)((double)1.0/(double)4.0); //county =.25 RESIDENCE
	double dresthresh2=(double)((double)1.0/(double)2.0); //state =.5
	double dresthresh3=(double)1.0; //1.0
	
	double drelthresh=(double)((double)1.0/(double)2.0);   // RELIGION
	
	double dseothresh=(double)((double)1.0/(double)2.0); // =.5   SEO
	
	double dmarthresh=(double)((double)1.0/(double)2.0); // =.5    MARITAL
	
	double dlikthresh1 = 0.15; // v close  > 0.00 and < .15  LIKES
	double dlikthresh2 = 0.24; // m close  > 0.15 and < .24

	//************************************************************************************************************

	
    
    //if (1==1) return totalNeighborsAssigned;
    
    int mod1 = 0, mod2 = 0;
   
    
    //******************************************************************************************************************
	//ASSIGN THE FRIENDS ATTRIBUTES BASED ON THE SEED NODE ATTRIBUTES. BUT ONLY IF IN SAME COMMUNITY AS SEED NODE
	try{
		
		System.out.println("Number of communities:"+RV.Communities.size());
		
		Enumeration enc = RV.Communities.keys();
  		int comcount=0;
	
		while(enc.hasMoreElements())
		{
    		Community co = (Community)RV.Communities.get((Integer)enc.nextElement());
    		int co1 = co.getCommunity();
    		
			//System.out.println("Now process neighbors of seeds for community "+co1);
			++comcount;

			
			for(i=0;i<co.seedsc.size();i++) // for each seed of current community
			{
				int ids = (Integer)co.seedsc.get(i);
				
				nw = (User)RV.Users.get((Integer)ids);		
			
			
			if (nw == null)
				System.out.println("2 nw is null for seed id:"+ids);
			
			if(nw.friends.size()<1)
			{
				System.out.println("this seed has zero friends");
				return totalNeighborsAssigned;
			}
			
			ag = nw.getAge(); res = nw.getResidence(); gen = nw.getGender();
			rel = nw.getReligion(); mar = nw.getMaritalStatus(); prof = nw.getProfession();
			pol = nw.getPoliticalOrientation(); seo = nw.getSexualOrientation();
			lk1 = nw.getLike(1); lk2 = nw.getLike(2); lk3 = nw.getLike(3);
			//mod1 = nw.getMOD();
			
		
			for (j=0; j< nw.friends.size();++j)  // for each neighbor
			{

				int userf1 = nw.friends.get(j);
				User nwf1 = (User)RV.Users.get(userf1);
				int ok=1;
				if (nwf1 != null)
				{
					for (int k=0; k<nwf1.communities.size() && ok==1;++k)
					{
						mod2 = nwf1.getCommunity(k);
						if (nw.communities.contains(mod2))
							ok=0;
					}
				
					agef       = nwf1.getAge();
				
					if (agef != null) // check its not already assigned
						ok = 1;
					if(agef != null && !agef.equals("")) 
						ok = 1;

				}
				
				if (ok == 0) // only if neighbor is in same community as its seed and its not already assigned
				{
					
				//assign *****************AGE*****************
				
				String ag2 = assignAge(ag, generator, generator2, dagthresh, age, gage, agL1, agL2, agL3, agL4);
	
				nwf1.loadAge(ag2);

				// second assign *****************RESIDENCE*****************
				String res2 = assignRes(res, generator, generator2, dresthresh1, 
						   			    dresthresh2, dresthresh3, residence, gresidence,
						   			    resL1, resL2, resL3, resL4);

				nwf1.loadResidence(res2);

				// next assign *****************POLITICAL ORIENTATION*****************
				String pol2 = assignPol(pol, generator, generator2, dpolthresh, politicalorientation, gpoliticalorientation,
 					   					polL1, polL2, polL3, polL4);
				
				nwf1.loadPoliticalOrientation(pol2);

				// next assign *****************SEXUAL ORIENTATION*****************

				String seo2 = assignSeo(seo, generator, generator2, dseothresh, 
	   				                    sexualorientation, gsexualorientation, seoL1, seoL2, seoL3, seoL4, seoL1h, seoL2h, seoL3h, seoL4h);
	
				nwf1.loadSexualOrientation(seo2);

				// next assign *****************RELIGION*****************
				String rel2 = assignRel(rel, generator, generator2, drelthresh, religion, greligion,
						LimRel1,  LimRel2, LimRel3, LimRel4, LimRel5, LimRel6, LimRel7, LimRel8);				

				nwf1.loadReligion(rel2);
				

				// next assign *****************MARITAL STATUS*****************
				String mar2 = assignMar(mar, generator, generator2, dagthresh, maritalstatus, gmaritalstatus,
						                limMar1, limMar2, limMar3, limMar4, limMar5);

				nwf1.loadMaritalStatus(mar2);

				
				// next assign *****************PROFESSION*****************
				String pro2 =  assignPro(prof, generator, generator2, dprothresh, profession, gprofession,
	                                     LimPro1, LimPro2, LimPro3, LimPro4, LimPro5, LimPro6);				
				
				
				
				nwf1.loadProfession(pro2);
				

				// third assign *****************LIKES*****************
				String[] likes = assignLik(lk1, lk2, lk3, generator, generator2, 
                                           likL1, likL2, likL3, likL4, dlikthresh1, dlikthresh2, 
                                           likes1, likes2, likes3);
  
				nwf1.loadLike(likes[0], 1); nwf1.loadLike(likes[1], 2); nwf1.loadLike(likes[2], 3);
				
				
				
				// fourth assign *****************GENDER*****************
				String gen2 = assignGen(gen, generator, generator2, genL1, gender, ggender);
			
				nwf1.loadGender(gen2);

				
				RV.Users.remove(nwf1.getUser());
				RV.Users.put(nwf1.getUser(),nwf1);
				
				totalNeighborsAssigned += 1;
				
			} // end for each friend
				
			} // eif same community
			

		} // efor each seed
			
	} // ewhile each community
	
	return totalNeighborsAssigned;
}
catch(Exception e){
	e.printStackTrace();
	System.out.println("\nsomething went wrong doing the data asignment");
	return totalNeighborsAssigned;
}

}


/*************************************************************************************************************/
/*********************************************** ASSIGN UNASSIGNED *******************************************/
/*************************************************************************************************************/
public static void AssignUnassigned(Random generator, Random generator2, int RANLIMUNAS,String age[], String residence[],	
		   							String gender[],
		   							String sexualorientation[],
		   							String politicalorientation[],
		   							String religion[],
		   							String maritalstatus[],
		   							String profession[],
		   							String likes1[], String likes2[], String likes3[])
{
	int i=0, mod2=0;
	
	int genix = 0, resix = 0, agix = 0, weix = 0, lkix1 = 0, lkix2 = 0, lkix3 = 0;
	int relix = 0, marix = 0, profix = 0, polix = 0, seoix = 0;
	
	System.out.println("ASSIGN REST");
	//ASSIGN NODES THAT ARE NOT SEEDS NOR NEIGHBORS OF SEEDS.
	//THIS IS DONE FOR EACH NODE BY CALCULATING, FOR ALL NEIGHBORS WITH ASSIGNED VALUES, THE MODAL VALUE FOR EACH ATTRIBUTE.
	//THE MODAL VALUE IS THEN ASSIGNED WITH A X% PROBABILITY, WHERE 'X' IS BY DEFAULT EQUAL TO 70.
	String agel       = ""; String weightl    = "";	String genderl    = "";	String residencel = "";
	
	String religionl = "";	String maritalstatusl = "";	String professionl = "";
	String politicalorientationl = "";  String sexualorientationl = "";
	String like1 = "", like2 = "",like3 = "";
	
	String res=""; String gen=""; String ag=""; String rel=""; String mar=""; String prof="";
	String pol=""; String seo=""; String we="";
	String lk1="";	String lk2="";	String lk3=""; String lk21="";	String lk22="";	String lk23="";
	
    Vector attVals0, attVals1, attVals2, attVals3, attVals4, attVals5, attVals6, attVals7, attVals8, 
    attVals9, attVals10;
    Vector attVals0f, attVals1f, attVals2f, attVals3f, attVals4f, attVals5f, attVals6f, attVals7f, attVals8f, 
    attVals9f, attVals10f;
    
	attVals0 = new Vector();	attVals1 = new Vector();	attVals2 = new Vector();	attVals3 = new Vector();
    attVals4 = new Vector();	attVals5 = new Vector();	attVals6 = new Vector();	attVals7 = new Vector();
    attVals8 = new Vector();	attVals9 = new Vector();	attVals10 = new Vector();
	attVals0f = new Vector();	attVals1f = new Vector();	attVals2f = new Vector();	attVals3f = new Vector();
    attVals4f = new Vector();	attVals5f = new Vector();	attVals6f = new Vector();	attVals7f = new Vector();
    attVals8f = new Vector();	attVals9f = new Vector();	attVals10f = new Vector();
  	
	Enumeration en2 = RV.Users.keys();
	User nwn, nwf; int user1=0, ranval=0;
	int countall1 = 0, countall2=0, countall3=0, countall4=0;
	while (en2.hasMoreElements() ){  // for all users
		nwn = (User)RV.Users.get((Integer)en2.nextElement());
		user1 = nwn.getUser();
		
		++countall1;
		
		String agen       = nwn.getAge();
		
		int ix=0, f=1;
	
		if (agen == null || agen.equals("")) // only consider users whose data is currently unassigned
		{
			++countall2;
			
			//We assign the modal values 90% of the time, and random assignment 10% of the time
			
			ranval = 	 ((int)(generator2.nextInt(10))); // rnd will be between 0 and 9
			int ok=1;			
			for(i=0;i<nwn.friends.size() && ok==1;++i) // find at least one neighbor in the same community
			{
				int userf1 = nwn.friends.get(i);
				User nwf1 = (User)RV.Users.get(userf1);
				

				for (int k=0; k<nwf1.communities.size() && ok==1;++k)
				{
					mod2 = nwf1.getCommunity(k);
					if (nwn.communities.contains(mod2))
							ok=0;
				}
			}
			
			
			

			if ((ranval <= RANLIMUNAS) || (nwn.friends.size()<1) || ok==1)
			{ // ASSIGN RANDOMLY 10% OF THE TIME
				agix = 	 ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1  was 12
				genix =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
				resix =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1 was 12
				relix =  ((int)(generator.nextInt(1000)));   // rnd will be between 0 and limit-1
				marix =  ((int)(generator.nextInt(96)));   // rnd will be between 0 and limit-1
				profix =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
				polix =  ((int)(generator.nextInt(99)));   // rnd will be between 0 and limit-1
				seoix =  ((int)(generator.nextInt(100)));   // rnd will be between 0 and limit-1
			    lkix1 =  ((int)(generator.nextInt(100))); // rnd will be between 0 and limit-1
			
			    res = residence[resix]; gen = gender[genix]; ag = age[agix];
			    rel = religion[relix]; mar = maritalstatus[marix]; prof = profession[profix];
			    pol = politicalorientation[polix]; seo = sexualorientation[seoix];
			    lk1 = likes1[lkix1]; lk2 = likes2[lkix1]; lk3 = likes3[lkix1];
			
			    genix = 	 ((int)(generator.nextInt(10))); // rnd will be between 0 and limit-1
			    if (genix < 5) // eliminated soccer club bias
			    	gen = "male";
			    else if (genix >= 5) // idem
			    	gen="female";

			    nwn.loadAge(ag); nwn.loadResidence(res); nwn.loadGender(gen);
			    nwn.loadReligion(rel); nwn.loadMaritalStatus(mar); nwn.loadProfession(prof);
			    nwn.loadPoliticalOrientation(pol); nwn.loadSexualOrientation(seo);
			    nwn.loadLike(lk1, 1); nwn.loadLike(lk2, 2); nwn.loadLike(lk3, 3);

				RV.Users.remove(nwn.getUser());
				RV.Users.put(nwn.getUser(),nwn);
				++countall3;
			}
			else  // OTHERWISE USE THE MODAL VALUES OF ALL FRIENDS WHO HAVE AN ASSIGNED VALUE (90% OF THE TIME)
			{
				

			attVals0.clear();	attVals1.clear();	attVals2.clear();	attVals3.clear();
		    attVals4.clear();	attVals5.clear();	attVals6.clear();	attVals7.clear();
		    attVals8.clear();	attVals9.clear();	attVals10.clear();
			attVals0f.clear();	attVals1f.clear();	attVals2f.clear();	attVals3f.clear();
		    attVals4f.clear();	attVals5f.clear();	attVals6f.clear();	attVals7f.clear();
		    attVals8f.clear();	attVals9f.clear();	attVals10f.clear();
		    
		
			
			for(i=0;i<nwn.friends.size();++i) // for all neighbors who are in the same community
			{
				int userf1 = nwn.friends.get(i);
				User nwf1 = (User)RV.Users.get(userf1);
				
		
				
				ok=1;
				for (int k=0; k<nwf1.communities.size() && ok==1;++k)
				{
					mod2 = nwf1.getCommunity(k);
					if (nwn.communities.contains(mod2))
							ok=0;
				}
				
				if (ok == 0) // only if neighbor is in same community as user
				{

				agel       		= nwf1.getAge();
				genderl    		= nwf1.getGender();
				residencel 		= nwf1.getResidence();
				religionl 		= nwf1.getReligion();
				maritalstatusl 	= nwf1.getMaritalStatus();
				professionl 	= nwf1.getProfession();
				politicalorientationl 	= nwf1.getPoliticalOrientation();
				sexualorientationl 		= nwf1.getSexualOrientation();
				like1 			= nwf1.getLike(1);
				like2 			= nwf1.getLike(2);
				like3 			= nwf1.getLike(3);
				
				
		  	    if(agel != null)
				if (attVals0.indexOf(agel)==-1)		
		  	    {
		  	    	attVals0.addElement(agel); attVals0f.addElement(1); 
		  	    }
		  	    else
		  	    {
		  	    	ix = attVals0.indexOf(agel);	f = (Integer)attVals0f.elementAt(ix);
		  	    	f++;							attVals0f.setElementAt(f, ix);
		  	    }
		    			
		  	    if(genderl != null)
		    	if (attVals1.indexOf(genderl)==-1)	
		    	{
		    		attVals1.addElement(genderl); attVals1f.addElement(1);
		    	}
		    	else
		    	{
		  	    	ix = attVals1.indexOf(genderl);	f = (Integer)attVals1f.elementAt(ix);
		  	    	f++;							attVals1f.setElementAt(f, ix);
		    	}
		    				
		  	    if(residencel != null)
		  	    if (attVals2.indexOf(residencel)==-1)	
		        {
		        	attVals2.addElement(residencel); attVals2f.addElement(1);
		        }
		        else
		        {
		  	    	ix = attVals2.indexOf(residencel);	f = (Integer)attVals2f.elementAt(ix);
		  	    	f++;							    attVals2f.setElementAt(f, ix);
		        }
		        			
		  	    if(religionl != null)
		  	    if (attVals3.indexOf(religionl)==-1)		
		        {
		        	attVals3.addElement(religionl); attVals3f.addElement(1); 
		        }
		        else
		        {
		  	    	ix = attVals3.indexOf(religionl);	f = (Integer)attVals3f.elementAt(ix);
		  	    	f++;							    attVals3f.setElementAt(f, ix);
		        }
		            		
		  	    if(maritalstatusl != null)
		  	    if (attVals4.indexOf(maritalstatusl)==-1)
		        {
		        	attVals4.addElement(maritalstatusl); attVals4f.addElement(1);
		        }
		        else
		        {
		  	    	ix = attVals4.indexOf(maritalstatusl);	f = (Integer)attVals4f.elementAt(ix);
		  	    	f++;							        attVals4f.setElementAt(f, ix);
		        }
		                	
		  	    if(professionl != null)
		  	    if (attVals5.indexOf(professionl)==-1)		
		        {
		        	attVals5.addElement(professionl); attVals5f.addElement(1); 
		        }
		        else
		        {
		  	    	ix = attVals5.indexOf(professionl);	f = (Integer)attVals5f.elementAt(ix);
		  	    	f++;							    attVals5f.setElementAt(f, ix);
		        }
		                	
		  	    if(politicalorientationl != null)
		  	    	if (attVals6.indexOf(politicalorientationl)==-1)
		        {
		        	attVals6.addElement(politicalorientationl); attVals6f.addElement(1);
		        }
		        else
		        {
		  	    	ix = attVals6.indexOf(politicalorientationl);	f = (Integer)attVals6f.elementAt(ix);
		  	    	f++;							        		attVals6f.setElementAt(f, ix);		        	
		        }
		                	
		  	    if(sexualorientationl != null)
		  	    if (attVals7.indexOf(sexualorientationl)==-1)
		        {
		        	attVals7.addElement(sexualorientationl); attVals7f.addElement(1);
		        }
		        else
		        {
		  	    	ix = attVals7.indexOf(sexualorientationl);	f = (Integer)attVals7f.elementAt(ix);
		  	    	f++;							        	attVals7f.setElementAt(f, ix);
		        }
		                 	
		  	    if(like1 != null)
		  	    if (attVals8.indexOf(like1)==-1)
		        {
		        	attVals8.addElement(like1); attVals8f.addElement(1);
		        }
		        else
		        {
		  	    	ix = attVals8.indexOf(like1);	f = (Integer)attVals8f.elementAt(ix);
		  	    	f++;							attVals8f.setElementAt(f, ix);   	
		        }
		                	
		  	    if(like2 != null)
		  	    if (attVals9.indexOf(like2)==-1)			
		        {
		        	attVals9.addElement(like2); attVals9f.addElement(1);
		        }
		        else
		        {
		  	    	ix = attVals9.indexOf(like2);	f = (Integer)attVals9f.elementAt(ix);
		  	    	f++;							attVals9f.setElementAt(f, ix); 
		        }
		                	
		  	    if(like3 != null)
		  	    if (attVals10.indexOf(like3)==-1)			
		        {
		        	attVals10.addElement(like3); attVals10f.addElement(1);
		        }
		        else
		        {
		  	    	ix = attVals10.indexOf(like3);	f = (Integer)attVals10f.elementAt(ix);
		  	    	f++;							attVals10f.setElementAt(f, ix); 
		        }
		  	    
				} // eif neighbor in same community
				
			} // efor each friend
			
		

		String modeage="", modegender = "", moderesidence = "", modereligion = "", modemaritalstatus = "", modeprofession = "";
		String modepoliticalorientation = "", modesexualorientation = "", modelike1 = "", modelike2 = "", modelike3 = "";
		int maxfreq=0, maxix=0;
		//Now we find the MODAL VALUE for each attribute and assign it as the attribute-value for the present node
		for(i=0, maxfreq=0, maxix=0; i< attVals0f.size();i++) // age
		{	f = (Integer)attVals0f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modeage = (String)attVals0.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals1f.size();i++) // gender
		{	f = (Integer)attVals1f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modegender = (String)attVals1.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals2f.size();i++) // residence
		{	f = (Integer)attVals2f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) moderesidence = (String)attVals2.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals3f.size();i++) // religion
		{	f = (Integer)attVals3f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modereligion = (String)attVals3.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals4f.size();i++) // maritalstatus
		{	f = (Integer)attVals4f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modemaritalstatus = (String)attVals4.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals5f.size();i++) // profession
		{	f = (Integer)attVals5f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modeprofession = (String)attVals5.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals6f.size();i++) // politicalorientation
		{	f = (Integer)attVals6f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modepoliticalorientation = (String)attVals6.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals7f.size();i++) // sexualorientation
		{	f = (Integer)attVals7f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modesexualorientation = (String)attVals7.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals8f.size();i++) // like1
		{	f = (Integer)attVals8f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modelike1 = (String)attVals8.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals9f.size();i++) // like2
		{	f = (Integer)attVals9f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modelike2 = (String)attVals9.elementAt(maxix);
		
		for(i=0, maxfreq=0, maxix=0; i< attVals10f.size();i++) // like3
		{	f = (Integer)attVals10f.elementAt(i);
			if (f > maxfreq)
			{ maxfreq = f; maxix = i; }
		}; if (i>0) modelike3 = (String)attVals10.elementAt(maxix);
		
		// OK, got all the modal value, now assign them....
		//System.out.println("\nmodeage:"+modeage);
		nwn.loadAge(modeage);
		nwn.loadGender(modegender);
		nwn.loadResidence(moderesidence);
		nwn.loadReligion(modereligion);
		nwn.loadMaritalStatus(modemaritalstatus);
		nwn.loadProfession(modeprofession);
		nwn.loadPoliticalOrientation(modepoliticalorientation);
		nwn.loadSexualOrientation(modesexualorientation);
		nwn.loadLike(modelike1, 1); 	nwn.loadLike(modelike2, 2); 	nwn.loadLike(modelike3, 3);
		
		RV.Users.remove(nwn.getUser());
		RV.Users.put(nwn.getUser(),nwn);
		
		++countall4;
		
		} // eelse use the modal values
		
		} // eif null - only consider users whose data is currently unassigned
		
	} // ewhile for all users


}

/****************************************************************************************************************************/
/*********************************************** MAIN ASSIGNMENT ROUTINE *************************************/
/****************************************************************************************************************************/
// -1 if invalid otherwise returns number of assigned
public static int rAssignDataGeneralizedSensAttr4(int numnodes, ConfigurationModel Configuration)
{
	configuration = Configuration;
	List<AttributeModel> attributeList = configuration.getUserAttrributesList();
	
	for(AttributeModel attribute : attributeList) {
		System.out.println(attribute.getName());
	}
	Random generator = new Random(System.currentTimeMillis()*1000);
	Random generator2 = new Random(System.currentTimeMillis()*1000);
	Enumeration en1 = RV.Users.keys();
	String str="";
	User nw, nw2;
	int user1=0,user2=0,i=0, j=0, friends_ok=0;
	
	String profile_age[]       = new String[10]; 
	String profile_gen[]       = new String[10]; 
	String profile_res[]       = new String[10]; 
	String profile_rel[]       = new String[10]; 
	String profile_mar[]       = new String[10]; 
	String profile_prof[]      = new String[10]; 
	String profile_pol[]       = new String[10]; 
	String profile_seo[]       = new String[10]; 
	String profile_lk1[]       = new String[10]; 
	String profile_lk2[]       = new String[10]; 
	String profile_lk3[]       = new String[10]; 
	
	//String residence[] = new String[6]; 
	//String gender[]    = new String[2]; 
	//String age[]       = new String[12];
	
	List<Pair<String, Double>> ageParamList = attributeList.get(0).getParameterList();
	int ageNumParam = ageParamList.size();
	List<Pair<String, Double>> genderParamList = attributeList.get(1).getParameterList();
	int genderNumParam = genderParamList.size();
	List<Pair<String, Double>> residenceParamList = attributeList.get(2).getParameterList();
	int residenceNumParam = residenceParamList.size();
	List<Pair<String, Double>> religionParamList = attributeList.get(3).getParameterList();
	int religionNumParam = religionParamList.size();
	List<Pair<String, Double>> maritalParamList = attributeList.get(4).getParameterList();
	int maritalNumParam = maritalParamList.size();
	List<Pair<String, Double>> professionParamList = attributeList.get(5).getParameterList();
	int professionNumParam = professionParamList.size();
	List<Pair<String, Double>> politicalParamList = attributeList.get(6).getParameterList();
	int politicalNumParam = politicalParamList.size();
	List<Pair<String, Double>> sexualParamList = attributeList.get(7).getParameterList();
	int sexualNumParam = sexualParamList.size();
	List<Pair<String, Double>> like1ParamList = attributeList.get(8).getParameterList();
	int like1NumParam = like1ParamList.size();
	List<Pair<String, Double>> like2ParamList = attributeList.get(9).getParameterList();
	int like2NumParam = like2ParamList.size();
	List<Pair<String, Double>> like3ParamList = attributeList.get(10).getParameterList();
	int like3NumParam = like3ParamList.size();
	
	String gage[]					= new String[ageNumParam];				int agef[]					= new int[ageNumParam];
	String ggender[]				= new String[genderNumParam];			int genderf[]				= new int[genderNumParam];
	String gresidence[]				= new String[residenceNumParam];		int residencef[]			= new int[residenceNumParam];
	String greligion[]              = new String[religionNumParam]; 		int religionf[]             = new int[religionNumParam];
	String gmaritalstatus[]         = new String[maritalNumParam];			int maritalstatusf[]        = new int[maritalNumParam];
	String gprofession[]            = new String[professionNumParam];    	int professionf[]           = new int[professionNumParam];
	String gpoliticalorientation[]  = new String[politicalNumParam];     	int politicalorientationf[] = new int[politicalNumParam];
	String gsexualorientation[]     = new String[sexualNumParam];     		int sexualorientationf[]    = new int[sexualNumParam];
	String glike1[]     			= new String[like1NumParam];     		int like1f[]    = new int[like1NumParam];
	String glike2[]     			= new String[like2NumParam];     		int like2f[]    = new int[like2NumParam];
	String glike3[]     			= new String[like3NumParam];     		int like3f[]    = new int[like3NumParam];
	
	String age[]				   = new String[100];
	String gender[]				   = new String[100];
	String residence[]			   = new String[100];
	String religion[]              = new String[1000];
	String maritalstatus[]         = new String[100];
	String profession[]            = new String[100];
	String politicalorientation[]  = new String[100];
	String sexualorientation[]     = new String[100];
	
	
	String weight[]    = new String[3]; 
	String like[]      = new String[12]; 
	
	
	String likes1[]     = new String[100]; 
	String likes2[]     = new String[100]; 
	String likes3[]     = new String[100]; 
	//int    likef[]     = new int[4]; 

	
	String res="";
	String gen="";
	String ag="";
	
	String rel="";
	String mar="";
	String prof="";
	String pol="";
	String seo="";
	
	String we="";
	String lk1="";	String lk2="";	String lk3="";
	String lk21="";	String lk22="";	String lk23="";
	
	int genix = 0, resix = 0, agix = 0, weix = 0, lkix1 = 0, lkix2 = 0, lkix3 = 0;
	
	int relix = 0, marix = 0, profix = 0, polix = 0, seoix = 0;
	
	
	for(int k = 0; k < residenceNumParam; k++) {
		gresidence[k] = residenceParamList.get(k).getKey();
		Double freq = residenceParamList.get(k).getValue() * 100;
		residencef[k] = freq.intValue();
	}
	
	for(int k = 0; k < genderNumParam; k++) {
		ggender[k] = genderParamList.get(k).getKey();
		Double freq = genderParamList.get(k).getValue() * 100;
		genderf[k] = freq.intValue();
	}
	
	
	for(int k = 0; k < ageNumParam; k++) {
		gage[k] = ageParamList.get(k).getKey();
		Double freq = ageParamList.get(k).getValue() * 100;
		agef[k] = freq.intValue();
	}
	
	
	
	for(int k = 0; k < religionNumParam; k++) {
		greligion[k] = religionParamList.get(k).getKey();
		Double freq = religionParamList.get(k).getValue() * 1000;
		religionf[k] = freq.intValue();
	}
	

	for(int k = 0; k < maritalNumParam; k++) {
		gmaritalstatus[k] = maritalParamList.get(k).getKey();
		Double freq = maritalParamList.get(k).getValue() * 100;
		maritalstatusf[k] = freq.intValue();
	}
	
	
	for(int k = 0; k < professionNumParam; k++) {
		gprofession[k] = professionParamList.get(k).getKey();
		Double freq = professionParamList.get(k).getValue() * 100;
		professionf[k] = freq.intValue();
	}
	
	for(int k = 0; k < politicalNumParam; k++) {
		gpoliticalorientation[k] = politicalParamList.get(k).getKey();
		Double freq = politicalParamList.get(k).getValue() * 100;
		politicalorientationf[k] = freq.intValue();
	}
	
	
	for(int k = 0; k < sexualNumParam; k++) {
		gsexualorientation[k] = sexualParamList.get(k).getKey();
		Double freq = sexualParamList.get(k).getValue() * 100;
		sexualorientationf[k] = freq.intValue();
	}
	
	System.out.println("------------ like1");
	for(int k = 0; k < like1NumParam ; k++) {
		glike1[k] = like1ParamList.get(k).getKey();
		Double freq = like1ParamList.get(k).getValue() * 100;
		like1f[k] = freq.intValue();
	}
	
	System.out.println("------------ like2");
	for(int k = 0; k < like2NumParam ; k++) {
		glike2[k] = like2ParamList.get(k).getKey();
		Double freq = like2ParamList.get(k).getValue() * 100;
		like2f[k] = freq.intValue();
	}
	
	System.out.println("------------ like3");
	for(int k = 0; k < like3NumParam ; k++) {
		glike3[k] = like3ParamList.get(k).getKey();
		Double freq = like3ParamList.get(k).getValue() * 100;
		like3f[k] = freq.intValue();
	}
		
	weight[0] = "1-2";
	weight[1] = "3-6";
	weight[2] = "7-10";
		
	
	int num=0, count=0;    // assign likes based on frequencies
	for(i=0, count=0; i<20; i++)
	{
		num = like1f[i];
		for (j=0;j<num;++j)
		{
			likes1[count]=glike1[i];
		    ++count;
		}
		System.out.print(i);
	}
	
	for(i=0, count=0; i<20; i++)
	{
		num = like2f[i];
		for (j=0;j<num;++j)
		{
			likes2[count]=glike2[i];
		    ++count;
		}
	}

	for(i=0, count=0; i<20; i++)
	{
		num = like3f[i];
		for (j=0;j<num;++j)
		{
			likes3[count]=glike3[i];
		    ++count;
		}
	}
	
	// assign residence based on frequencies
	for(i=0, count=0; i<residenceNumParam; i++)
	{
		num = residencef[i];
		for (j=0;j<num;++j)
		{
			residence[count]=gresidence[i];
		    ++count;
		}
	}
	
	// assign gender based on frequencies
	for(i=0, count=0; i<genderNumParam; i++)
	{
		num = genderf[i];
		for (j=0;j<num;++j)
		{
			gender[count]=ggender[i];
		    ++count;
		}
	}
	// assign age based on frequencies
	for(i=0, count=0; i<ageNumParam; i++)
	{
		num = agef[i];
		for (j=0;j<num;++j)
		{
			age[count]=gage[i];
		    ++count;
		}
	}
	
	
	// assign religion based on frequencies
	for(i=0, count=0; i<religionNumParam; i++)
	{
		num = religionf[i];
		for (j=0;j<num;++j)
		{
			religion[count]=greligion[i];
		    ++count;
		}
	}
	
	// assign marital status based on frequencies
	for(i=0, count=0; i<maritalNumParam; i++)
	{
		num = maritalstatusf[i];
		for (j=0;j<num;++j)
		{
			maritalstatus[count]=gmaritalstatus[i];
		    ++count;
		}
	}
	
	// assign profession based on frequencies
	for(i=0, count=0; i<professionNumParam; i++)
	{
		num = professionf[i];
		for (j=0;j<num;++j)
		{
			profession[count]=gprofession[i];
		    ++count;
		}
	}
	
	// assign political orientation based on frequencies
	for(i=0, count=0; i<politicalNumParam; i++)
	{
		num = politicalorientationf[i];
		for (j=0;j<num;++j)
		{
			politicalorientation[count]=gpoliticalorientation[i];
		    ++count;
		}
	}
	
	// assign sexual orientation based on frequencies
	for(i=0, count=0; i<sexualNumParam; i++)
	{
		num = sexualorientationf[i];
		for (j=0;j<num;++j)
		{
			sexualorientation[count]=gsexualorientation[i];
		    ++count;
		}
	}
	
	double numseeds = RV.seeds.size();
	
	//System.out.println("numseeds: "+numseeds+" numnodes: "+numnodes);
	
	if (numseeds > numnodes)
	{
		System.out.println("ERROR. numseeds > numnodes");
		return -1;
	}
	
	
	
	// ASSIGN THE PROFILES
	List<List<Integer>> profileList = configuration.getProfileList();
	for(int k = 0; k < profileList.size(); k++) {
		List<Integer> profile = profileList.get(k);
		profile_age[k] = attributeList.get(0).getParameterList().get(profileList.get(k).get(0)).getKey();
		profile_gen[k] = attributeList.get(1).getParameterList().get(profileList.get(k).get(1)).getKey();
		profile_res[k] = attributeList.get(2).getParameterList().get(profileList.get(k).get(2)).getKey();
		profile_rel[k] = attributeList.get(3).getParameterList().get(profileList.get(k).get(3)).getKey();
		profile_mar[k] = attributeList.get(4).getParameterList().get(profileList.get(k).get(4)).getKey();
		profile_prof[k] = attributeList.get(5).getParameterList().get(profileList.get(k).get(5)).getKey();
		profile_pol[k] = attributeList.get(6).getParameterList().get(profileList.get(k).get(6)).getKey();
		profile_seo[k] = attributeList.get(7).getParameterList().get(profileList.get(k).get(7)).getKey();
		profile_lk1[k] = attributeList.get(8).getParameterList().get(profileList.get(k).get(8)).getKey();
		profile_lk2[k] = attributeList.get(9).getParameterList().get(profileList.get(k).get(9)).getKey();
		profile_lk3[k] = attributeList.get(10).getParameterList().get(profileList.get(k).get(10)).getKey();
	}
	
	int totalAssigned = 0;
	
    // The following three routines do all the work
	totalAssigned += AssignSeeds(generator, profile_age,profile_gen,profile_res, profile_rel, profile_mar, profile_prof, profile_pol,
                profile_seo, profile_lk1, profile_lk2, profile_lk3, profileList, configuration.getProfileCommunityAssaignment(),
                configuration.getCurrentProfilesFrequencies());
		
	boolean switchdistance = true;
	totalAssigned += AssignNeighbors(generator, generator2, age, gage, residence, gresidence, gender, ggender, sexualorientation, gsexualorientation, politicalorientation, gpoliticalorientation,
			        religion, greligion, maritalstatus, gmaritalstatus, profession, gprofession, likes1, likes2, likes3, switchdistance);
	
	
	int RANLIMUNAS= configuration.getRandomness(); // Unassigned nodes assignment, 0 is equivalent to 10% random assignment (low). 
	                  // 2 is 30% (medium), 4 is 50% (high). 
    // The remainder are assigned the modal values for each attribute for the 
    // neighbors with values assigned
	System.out.println("Randomness: " + RANLIMUNAS);
	
	AssignUnassigned(generator, generator2, RANLIMUNAS, age, residence, gender, sexualorientation, politicalorientation, 
	        religion, maritalstatus, profession, likes1, likes2, likes3);
	

	// AND FINALLY, WE ASSIGN THE WEIGHTS. WE DO THIS AS THE END BECAUSE THE WEIGHTS CALCULATION BETWEEN TWO NODES
	// IS BASED ON THE DISTANCE BETWEEN ALL THE RESPECTIVE ASSIGNED ATTRIBUTES.
	Enumeration en3 = RV.Users.keys();
	User na, nb; 
	double distattr = 0.0, maxdist=0.0;
	while (en3.hasMoreElements() ){  // for all users
		na = (User)RV.Users.get((Integer)en3.nextElement());
		user1 = na.getUser();
		String agen = na.getAge();
	
		if (agen != null)
		{

			for(i=0;i<na.friends.size();++i) // for all neighbors
			{
				user2 = na.friends.get(i);
				nb = (User)RV.Users.get(user2);
					
				distattr = 1.0 - calcDistanceBetweenUsers.calcDistanceBetweenUsers(user1, user2);

				na.weights.set(i, distattr);
				
				RV.Users.remove(na.getUser());
				RV.Users.put(na.getUser(),na);

			} // efor
		} // eif
	} // ewhile
	

	return totalAssigned;
	
} //fin de funcion 'rAssignDataGeneralizedSensAttr4'

} // end of public class rAssignDataGeneralizedSensAttr4
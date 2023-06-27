// Copyright (C) 2018-2021  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.RV;

import java.lang.Math;

import genDataNOapplication.User.User;

public class calcDistanceBetweenUsers{
	

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

	
public static double compareRefNodes(String agev1, String residencev1, String genderv1, 
		                             String agev2, String residencev2, String genderv2)
{
	double distref = 0.0;
	int i=0, j=0;
	
	String residence[] = new String[6]; 
	String county[]    = new String[6]; 
	String state[]     = new String[6]; 
	String division[]  = new String[6]; 
	String region[]    = new String[6]; 
	
	String gender[]    = new String[2]; 
	String age[]       = new String[7]; 
	
	String weight[]    = new String[3]; 
	Double weightav[]  = new Double[3]; 
	
	String res="";
	String gen="";
	String ag="";
	String we="";
	
	int genix = 0, resix = 0, agix = 0, weix = 0;
	
	residence[0] = "Palo Alto";
	residence[1] = "Santa Barbara";
	residence[2] = "Winthrop";
	residence[3] = "Boston";
	residence[4] = "Cambridge";
	residence[5] = "San Jose";

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
	
	gender[0] = "male";
	gender[1] = "female";
	
	age[0]  = "18-25";
	age[1]  = "26-35";
	age[2]  = "36-45";
	age[3]  = "46-55";
	age[4]  = "56-65";
	age[5]  = "66-75";
	age[6]  = "76-85";

	
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
	
	
	double distgender = 0.0;
	if (genderv1.equals(genderv2) == true ) // just check for equality or not
		distgender=1.0;
	else
		distgender=0.0;
	
	
	pos1=0; pos2=0;
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
    
    
    distref = (distage + distgender + distresidence) / 3.0;
	
	
return(distref);
}

public static double compareRefNodes2(String religionv1, String maritalv1, String professionv1, String politicalv1, String sexualv1,
		                              String religionv2, String maritalv2, String professionv2, String politicalv2, String sexualv2)
{
double distref = 0.0;
int i=0, j=0;


String religion[]    	 = new String[9]; 
String marital[]       	 = new String[4]; 
String profession[]    = new String[10]; 
String political[]       = new String[7]; 
String sexual[]    		 = new String[4]; 

String rel="";
String mar="";
String pro="";
String pol="";
String sex="";

int relix = 0, marix = 0, proix = 0, polix = 0, sexix=0;

religion[0] = "Buddhist";  religion[1] = "Christian"; 
religion[2] = "";     religion[3] = "Jewish";    
religion[4] = "Muslim";    religion[5] = "Sikh";      
religion[6] = "Traditional Spirituality";  religion[7] = "Other Religions"; religion[8] = "No religious affiliation";

marital[0] = "Single";   marital[1] = "Married";  marital[2] = "Divorced"; marital[3] = "Widowed";

profession[0] = "Manager";     		profession[1] = "Professional";  
profession[2] = "Service";          profession[3] = "Sales and office"; 
profession[4] = "Natural resources construction and maintenance"; 
profession[5] = "Production transportation and material moving"; 
profession[6] = "Student"; 

political[0] = "Far Left";    political[1] = "Left";         political[2] = "Center Left";  
political[3] = "Center";      political[4] = "Center Right"; political[5] = "Right";        
political[6] = "Far Right";

sexual[0] = "Asexual";      sexual[1] = "Bisexual";     sexual[2] = "Heterosexual"; sexual[3] = "Homosexual";

int pos1=0, pos2=0;

for(i=0; i<political.length; i++)
{
if (politicalv1.equals(political[i]) == true ) // found it
{
pos1 = i;
}
if (politicalv2.equals(political[i]) == true ) // found it
{
pos2 = i;
}
}
double distpolitical = Math.abs(pos1 - pos2) / 6.0;

double distsexual = 0.0;

if (sexualv1.equals(sexualv2) == true ) // found it
	distsexual = 0.0;
else if (sexualv1.equals(sexualv2) == false &&  sexualv1.equals(sexual[2]) == false &&
         sexualv2.equals(sexual[2]) == false) 
	distsexual = 0.5;
else
	distsexual = 1.0;


double distmarital = 0.0;

if (maritalv1.equals(maritalv2) == true ) // found it
	distmarital = 0.0;
else if (maritalv1.equals(maritalv2) == false &&  maritalv1.equals(marital[0]) == false &&
maritalv2.equals(marital[0]) == false) 
	distmarital = 0.5;
else
	distmarital = 1.0;


double distreligion = 0.0;

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



double distprof = 0.0;

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


distref = (distpolitical + distsexual + distmarital + distreligion + distprof) / 5.0;


return(distref);
}

public static double calcDistanceBetweenUsers(int user1, int user2)
{
	User nw, nw2;
	int userf1=0, userf2=0, i=0, j=0;
	double distref = 0.0, distref2 = 0.0, distlikesref = 0.0;
	
	String lk1="";	String lk2="";	String lk3="";
	String lk21="";	String lk22="";	String lk23="";
	
	String lkf1="";	String lkf2="";	String lkf3="";
	String lkf21="";	String lkf22="";	String lkf23="";

	try{
		nw  = (User)RV.Users.get(user1);
		nw2 = (User)RV.Users.get(user2);
			
		String agev1       	= nw.getAge();
		String residencev1 	= nw.getResidence();
		String genderv1    	= nw.getGender();
		String religionv1 	= nw.getReligion();
		String maritalv1 	= nw.getMaritalStatus();
		String professionv1 = nw.getProfession();
		String politicalv1 	= nw.getPoliticalOrientation();
		String sexualv1 	= nw.getSexualOrientation();
		lk1 = nw.getLike(1); 		lk2 = nw.getLike(2); 		lk3 = nw.getLike(3);
				
		if (agev1 != null)
		{		
				String agev2       = nw2.getAge();
				String residencev2 = nw2.getResidence();
				String genderv2    = nw2.getGender();
				String religionv2 	= nw2.getReligion();
				String maritalv2 	= nw2.getMaritalStatus();
				String professionv2 = nw2.getProfession();
				String politicalv2 	= nw2.getPoliticalOrientation();
				String sexualv2 	= nw2.getSexualOrientation();
				lk21 = nw2.getLike(1); 		lk22 = nw2.getLike(2); 		lk23 = nw2.getLike(3);
						
				if (agev1 != null && agev2 != null)
				{
					distref = compareRefNodes(agev1, residencev1, genderv1, agev2, residencev2, genderv2);
					
					distref2 = compareRefNodes2(religionv1, maritalv1, professionv1, politicalv1, sexualv1,
                                                religionv2, maritalv2, professionv2, politicalv2, sexualv2);
				
					distlikesref = dlikes(lk1, lk2, lk3, lk21, lk22, lk23);
				
					distref = ((distref * 3.0) + (distref2 * 5.0) + distlikesref) / 9.0;
				
	
				} // eif age1 != null
		}

	}
	catch(Exception e){
		e.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}

	return distref;
	
} //end of funcion 'rDataCategoryDistanceValuesNew'

} // end of public class rDataCategoryDistanceValuesNew
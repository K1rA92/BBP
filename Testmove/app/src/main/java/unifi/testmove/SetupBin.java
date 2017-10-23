package unifi.testmove;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by onion on 05/05/2017.
 */

//0.375

public class SetupBin {

    private List<float[]> Cestini;
    private short color[] = {};

    private final short binColorOrder1[] = {2, 3, 4, 3, 4, 5};
    private final short binColorOrder2[] = {4, 5, 6, 5, 6, 7};
    private final short binColorOrder3[] = {6, 7, 8, 7, 8, 9};
    private final short binColorOrder4[] = {8, 9, 10, 9, 10, 11};
    private final short binColorOrder5[] = {10, 11, 12, 11, 12, 13};
    private final short binColorOrder6[] = {12, 13, 14, 13, 14, 15};
    private final short binColorOrder7[] = {14, 15, 16, 15, 16, 17};
    private final short binColorOrder8[] = {16, 17, 18, 17, 18, 19};
    private final short binColorOrder9[] = {18, 19, 20, 19, 20, 21};
    private final short binColorOrder10[] = {20, 21, 0, 21, 0, 1};


    //i primi 4 sono fissi, dal 5° in poi sono quelli da modificare

    private int Cap;
    private int MaxCap;
    private float Larghezza;
    private float LargCest;
    private static float[] squareCoords;

    public SetupBin(int a, int d, int[] b) {
        MaxCap=a;
        Cap = choiceDifficult(d,b);
        prepareBin(Cap);
    }

    public void prepareBin(int a) {
        Cestini = new ArrayList<float[]>();
        Cap = a;
        Larghezza = 3.0f;
        LargCest = Larghezza / Cap;
        float x1 = 1.7f;
        createBin(x1, LargCest);
        squareCoords = createCord(x1, LargCest);
    }

    private float[] createCord(float x1, float larg){
        float x2 = x1-larg;

        float[] square = {
                x1, 0.70f, 0.0f,   // bottom left
                x2, 0.70f, 0.0f,   // bottom right
                x1, 0.75f, 0.0f,
                x2, 0.75f, 0.0f,
                x1, 0.80f, 0.0f,
                x2, 0.80f, 0.0f,
                x1, 0.85f, 0.0f,
                x2, 0.85f, 0.0f,
                x1, 0.90f, 0.0f,
                x2, 0.90f, 0.0f,
                x1, 0.95f, 0.0f,
                x2, 0.95f, 0.0f,
                x1, 1.00f, 0.0f,
                x2, 1.00f, 0.0f,
                x1, 1.05f, 0.0f,
                x2, 1.05f, 0.0f
        };

        return square;
    }

    private void createBin(float x1, float larg) {
        float x2 = x1 - larg;
        for (int i = 0; i < Cap; i++) {
            float binCoords[] = {
                    x1, -0.45f, 0.0f,
                    x2, -0.45f, 0.0f,
                    x1, -0.95f, 0.0f,
                    x2, -0.95f, 0.0f,
                    x1, -0.90f, 0.0f,
                    x2, -0.90f, 0.0f,
                    x1, -0.85f, 0.0f,
                    x2, -0.85f, 0.0f,
                    x1, -0.80f, 0.0f,
                    x2, -0.80f, 0.0f,
                    x1, -0.75f, 0.0f,
                    x2, -0.75f, 0.0f,
                    x1, -0.70f, 0.0f,
                    x2, -0.70f, 0.0f,
                    x1, -0.65f, 0.0f,
                    x2, -0.65f, 0.0f,
                    x1, -0.60f, 0.0f,
                    x2, -0.60f, 0.0f,
                    x1, -0.55f, 0.0f,
                    x2, -0.55f, 0.0f,
                    x1, -0.50f, 0.0f,
                    x2, -0.50f, 0.0f,
                    x1, -0.45f, 0.0f,
                    x2, -0.45f, 0.0f
            };
            Cestini.add(binCoords);
            x1 = x2;
            x2 = x1 - LargCest;
        }
    }

    public short[] SetupColor(int p){
        short[] color= {};

        switch (p) {
            case 10: {
                color = binColorOrder1;
                break;
            }
            case 9:{
                color = binColorOrder2;
                break;
            }
            case 8:{
                color = binColorOrder3;
                break;
            }
            case 7:{
                color = binColorOrder4;
                break;
            }
            case 6:{
                color = binColorOrder5;
                break;
            }
            case 5:{
                color = binColorOrder6;
                break;
            }
            case 4:{
                color = binColorOrder7;
                break;
            }
            case 3:{
                color = binColorOrder8;
                break;
            }
            case 2:{
                color = binColorOrder9;
                break;
            }
            case 1:{
                color = binColorOrder10;
                break;
            }
            default: break;
        }
        return color;
    }

    public int choiceDifficult(int i, int[] b) {
        int a=0;
        switch (i) {
            case 1:{
                a = NextFit(b, b.length, MaxCap);
                break;
            }
            case 2:{
                a = FirstFit(b, b.length, MaxCap);
                break;
            }
            case 3:{
                a=BestFit(b,b.length,MaxCap);
                break;
            }
            case 4:{
                a = FirstFitDescrescing(b);
                break;
            }
            case 5:{
                a = BestFitDescrescing(b);
                break;
            }
            default:break;

        }
        return a;
    }



    // Returns number of bins required using next fit
    // online algorithm
    private int NextFit(int weight[], int n, int c){
    // Initialize result (Count of bins) and remaining
    // capacity in current bin.
    int res = 1, bin_rem = c;

    // Place items one by one
    for (int i=0; i<n; i++) {
        // If this item can't fit in current bin
        if (weight[i] > bin_rem) {
            res++;  // Use a new bin
            bin_rem = c - weight[i];
        }
        else
            bin_rem -= weight[i];
        }
        return res;
    }

    public void bubbleSort(int [] array) {

        for(int i = 0; i < array.length; i++) {
            boolean flag = false;
            for(int j = 0; j < array.length-1; j++) {

                //Se l' elemento j e maggiore del successivo allora
                //scambiamo i valori
                if(array[j]>array[j+1]) {
                    int k = array[j];
                    array[j] = array[j+1];
                    array[j+1] = k;
                    flag=true; //Lo setto a true per indicare che é avvenuto uno scambio
                }


            }

            if(!flag) break; //Se flag=false allora vuol dire che nell' ultima iterazione
            //non ci sono stati scambi, quindi il metodo può terminare
            //poiché l' array risulta ordinato
        }
    }

    private void reverse(int[] validData) {

    bubbleSort(validData);
    for(int i = 0; i < validData.length / 2; i++) {
        int temp = validData[i];
        validData[i] = validData[validData.length - i - 1];
        validData[validData.length - i - 1] = temp;
        }

    }


    private int BestFitDescrescing(int[] b) {
        int[] NewBin = new int[b.length];
        System.arraycopy(b,0,NewBin,0,b.length);
        reverse(NewBin);
        int i = BestFit(NewBin,b.length,MaxCap);
        return i;
    }

        private int FirstFitDescrescing(int[] b) {
        int[] NewBin = new int[b.length];
        System.arraycopy(b,0,NewBin,0,b.length);
        reverse(NewBin);
        int i = FirstFit(NewBin, b.length, MaxCap);
        return i;
    }

    private int FirstFit(int[] weight, int n, int c) {

        //Inizializzazione delle varibili
        int res = 0;
        int[] bin_res = new int[n];


        // Place items one by one
        for (int i=0; i<n; i++)
        {
            // Find the first bin that can accommodate
            // weight[i]
            int j;
            for (j=0; j<res; j++)
            {
                if (bin_res[j] >= weight[i])
                {
                    bin_res[j] = bin_res[j] - weight[i];
                    break;
                }
            }

            // If no bin could accommodate weight[i]
            if (j==res)
            {
                bin_res[res] = c - weight[i];
                res++;
            }
        }
        return res;
    }

    // Returns number of bins required using best fit
    // online algorithm
    private int BestFit(int weight[], int n, int c)
    {
        // Initialize result (Count of bins)
        int res = 0;

        // Create an array to store remaining space in bins
        // there can be at most n bins
        int[] bin_rem = new int[n];

        // Place items one by one
        for (int i=0; i<n; i++)
        {
            // Find the best bin that ca\n accomodate
            // weight[i]
            int j;

            // Initialize minimum space left and index
            // of best bin
            int min = c+1, bi = 0;

            for (j=0; j<res; j++)
            {
                if (bin_rem[j] >= weight[i] &&
                        bin_rem[j] - weight[i] < min)
                {
                    bi = j;
                    min = bin_rem[j] - weight[i];
                }
            }

            // If no bin could accommodate weight[i],
            // create a new bin
            if (min==c+1)
            {
                bin_rem[res] = c - weight[i];
                res++;
            }
            else // Assign the item to best bin
                bin_rem[bi] -= weight[i];
        }
        return res;
    }


    public List<float[]> getBin() {
        return Cestini;
    }

    public float[] getCoord() {
        return squareCoords;
    }
    public float getLargCest() {
        return LargCest;
    }
    public int getCap() {
        return Cap;
    }
}

/*
░░░░░█████████████░░░░░
░░░░█░░░░░░░░░░░░░█░░░░
░░░█░░░░░░░░░░▄▄▄░░█░░░
░░░█░░▄▄▄░░▄░░███░░█░░░
░░░▄█░▄░░░▀▀▀░░░▄░█▄░░░
░░░█░░▀█▀█▀█▀█▀█▀░░█░░░
░░░▄██▄▄▀▀▀▀▀▀▀▄▄██▄░░░
░▄█░█▀▀█▀▀▀█▀▀▀█▀▀█░█▄░
▄▀░▄▄▀▄▄▀▀▀▄▀▀▀▄▄▀▄▄░▀▄
█░░░░▀▄░█▄░░░▄█░▄▀░░░░█
░▀▄▄░█░░█▄▄▄▄▄█░░█░▄▄▀░
░░░▀██▄▄███████▄▄██▀░░░
░░░████████▀████████░░░
░░▄▄█▀▀▀▀█░░░█▀▀▀▀█▄▄░░
░░▀▄▄▄▄▄▀▀░░░▀▀▄▄▄▄▄▀﻿
 */
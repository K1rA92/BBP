package unifi.testmove;

/**
 * Created by onion on 09/12/2016.
 */
/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

        import javax.microedition.khronos.egl.EGLConfig;
        import javax.microedition.khronos.opengles.GL10;

        import android.opengl.GLES20;
        import android.opengl.GLSurfaceView;
        import android.opengl.Matrix;
        import android.util.Log;

        import java.lang.reflect.Array;
        import java.nio.channels.NonWritableChannelException;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collections;
        import java.util.List;
        import java.util.Random;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";

    private Square mSquare2;
    private Square mSquare1;
    private Square mSquare3;

    private Bin mBin;

    int[] posizione;

    private SetupBin sb;

    private int[] messageOption = new int[2];

    private int MAXPESO;
    private int MAXCAPIENZA;
    private int NUMBERITEM;
    private int DIFFICOLTA;

    //Bisogna cambiare il numero da fisso a dinamico

    private String status = "ok";


    private int[][] matrice;
    private int[] Elementi;
    private List<float[]> Cest = new ArrayList<float[]>();
    private int ElementoAttuale=0;

    private final short drawOrder1[] = { 0, 2, 2, 3, 3, 1, 1, 0};
    private final short drawOrder2[] = { 0, 4, 4, 5, 5, 1, 1, 0, 2, 3};
    private final short drawOrder3[] = { 0, 6, 6, 7, 7, 1, 1, 0, 2, 3, 4, 5};
    private final short drawOrder4[] = { 0, 8, 8, 9, 9, 1, 1, 0, 2, 3, 4, 5, 6, 7};
    private final short drawOrder5[] = { 0, 10, 10, 11, 11, 1, 1, 0, 2, 3, 4, 5, 6, 7, 8, 9};
    private final short drawOrder6[] = { 0, 12, 12, 13, 13, 1, 1, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private final short drawOrder7[] = { 0, 14, 14, 15, 15, 1, 1, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    private final short binOrder1[] = {0, 2, 2, 3, 3, 1};
    private final short binOrder2[] = {0, 2, 2, 3, 3, 1, 4, 5};
    private final short binOrder3[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7};
    private final short binOrder4[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9};
    private final short binOrder5[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11};
    private final short binOrder6[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private final short binOrder7[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private final short binOrder8[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
    private final short binOrder9[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 , 19};
    private final short binOrder10[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 , 19, 20, 21};
    private final short binOrder11[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 , 19, 20, 21, 22, 23};
    private final short binOrder12[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 , 19, 20, 21, 22, 23, 24, 25};
    private final short binOrder13[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 , 19, 20, 21, 22, 23, 24, 25, 26, 27};
    private final short binOrder14[] = {0, 2, 2, 3, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 , 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private int maxBin;
    private int collum;
    private int rigum;

    private float Larg;
    private int peso;

    // Dichiarazione delle coordinate delle palline di carta da 2 a 6
    private static float[] squareCoords;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // per i colori http://prideout.net/archive/colors.php
        // per convertire i colori http://www.easyrgb.com/en/convert.php#inputFORM
        // Set the background frame color
        GLES20.glClearColor(0.95f, 0.95f, 0.95f, 1.0f);

        collum=1;
        rigum=0;

        //La nostra grandezza N
        MAXPESO = 7;

        //Gli elementi
        NUMBERITEM = messageOption[0];

        //La difficoltà
        DIFFICOLTA = messageOption[1];
        //La nostra B
        MAXCAPIENZA = 10;

        Elementi = new int[NUMBERITEM];
        generateItem();
        sb = new SetupBin(MAXCAPIENZA, DIFFICOLTA, Elementi);

        maxBin = sb.getCap();

        matrice = new int[MAXCAPIENZA][maxBin];
        ResetMatrix();


        Cest = sb.getBin();
        Larg = sb.getLargCest();
        squareCoords = sb.getCoord();
        posizione = new int[maxBin];
        for (int i=0;i<maxBin;i++) {
            posizione[i] =0 ;
        }
        nextItem();
    }

    private int FirstFitDescrescing(int[] b) {
        int[] NewBin = new int[b.length];
        System.arraycopy(b,0,NewBin,0,b.length);
        Arrays.sort(NewBin);
        int i = FirstFit(NewBin);
        return i;
    }

    private int FirstFit(int[] Summer) {

        //Inizializzazione delle varibili
        int res = 0;
        int[] bin_res = new int[NUMBERITEM];


        // Place items one by one
        for (int i=NUMBERITEM-1; i>=0; i--)
        {
            // Find the first bin that can accommodate
            // weight[i]
            int j;
            for (j=0; j<res; j++)
            {
                if (bin_res[j] >= Summer[i])
                {
                    bin_res[j] = bin_res[j] - Summer[i];
                    break;
                }
            }

            // If no bin could accommodate weight[i]
            if (j==res)
            {
                bin_res[res] = MAXCAPIENZA - Summer[i];
                res++;
            }
        }
        return res;
    }

    // Returns number of bins required using best fit
    // online algorithm
    private int bestFit(int weight[], int n, int c)
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

    public void generateItem() {
        for(int i=0;i < NUMBERITEM;i++) {
            Elementi[i] = 1 + (int) (Math.random() * (MAXPESO));
        }
    }

    private void nextItem(){

        if(ElementoAttuale < Elementi.length) {
            peso = Elementi[ElementoAttuale];
            ElementoAttuale++;
        }else{
            peso=0;
            setStatus("win");
        }

    }
    
    @Override
    public void onDrawFrame(GL10 unused) {



        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw square
        if(peso!=0){
            short n=(short)(peso*2);
            short n1=(short)(n+1);
            final short backgroundOrder[] = { 0, 1, n, 1, n, n1};

            mSquare1 = new Square(squareCoords, backgroundOrder, peso);
            mSquare1.draw(mMVPMatrix, GLES20.GL_TRIANGLES);
            mSquare2 = new Square(squareCoords, changeSqOr(peso), 0);
            mSquare2.draw(mMVPMatrix, GLES20.GL_LINES);
        }

        for (int j = 0; j < maxBin; j++) {
            float [] binCord = Cest.get(j);
            for (int i = MAXCAPIENZA - 1; i >= 0; i--) {
                int x = matrice[i][j];
                if(x !=0 ){
                    short[] coords = sb.SetupColor(i+1);
                    mSquare3 = new Square(binCord, coords, x);
                    mSquare3.draw(mMVPMatrix, GLES20.GL_TRIANGLES);
                }
            }

        }


        for(int i=0; i<maxBin;i++) {
            float[] binCoords = Cest.get(i);
            mBin = new Bin(binCoords, changeBin(posizione[i]));
            mBin.draw(mMVPMatrix);
        }

    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }
    
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    //scende di uno altrimenti lo rimette in cima
    public void GetDown () {
        if (rigum < 7) {
            for (int i = 1; i < squareCoords.length; ) {
                //squareCoords[i] += 0.54f;
                squareCoords[i] -= 0.15f;
                i += 3;
            }
            rigum++;
        }else{
            int oldpeso=peso;
            squareCoords = resetCord(squareCoords);
            rigum=0;
            changeSquare();
            upgradeBin(collum-1,oldpeso);
        }
    }

    //incrementa il valore del cestino + controlli sul fatto che è andato oltre oppure se era l'ultimo a destra
    private void upgradeBin(int i, int p) {

        int c = posizione[i]+p;
        posizione[i] = c;

        if(c> MAXCAPIENZA) {
            setStatus("gameover");
        }else{
            MatrixElement(p, i);
        }
    }

    //cambio del peso scelto randomicamente
    public void changeSquare() {
        nextItem();
    }

    // sposto a destra
    public void changecoordDx() {
        if (collum < maxBin) {
            collum++;
            squareCoords = provaDx(squareCoords);
        }
    }
    // sposto a sinistra
    public void changecoordSx() {
        if (collum > 1) {
            collum--;
            squareCoords = provaSx(squareCoords);

        }
    }

    private float[] resetCord(float[] test) {
        //Da sistemare perché nonbinc
        float x1= 0.70f;
        for (int i = 1; i < test.length; ) {
            test[i] = x1;
            test[i+3] = x1;
            x1 += 0.05f;
            i += 6;
        }
        return test;
    }

    private short[] changeBin(int i) {
        short test[] = {};
        switch (i) {
            case 0: {
                test = binOrder1;
                break;
            }
            case 1:{
                test = binOrder2;
                break;
            }
            case 2:{
                test = binOrder3;
                break;
            }
            case 3:{
                test = binOrder4;
                break;
            }
            case 4:{
                test = binOrder5;
                break;
            }
            case 5:{
                test = binOrder6;
                break;
            }
            case 6:{
                test = binOrder7;
                break;
            }
            case 7:{
                test = binOrder8;
                break;
            }
            case 8:{
                test = binOrder9;
                break;
            }
            case 9:{
                test = binOrder10;
                break;
            }
            case 10:{
                test = binOrder11;
                break;
            }
            case 11:{
                test = binOrder12;
                break;
            }
            case 12:{
                test = binOrder13;
                break;
            }
            case 13:{
                test = binOrder14;
                break;
            }
            default:
                break;
        }
        return test;
    }


    //cambia le coordinate grafiche del cestino

    private short[] changeSqOr(int i) {
        short test[] = {};
        switch (i) {
            case 1: {
                test = drawOrder1;
                break;
            }
            case 2:{
                test = drawOrder2;
                break;
            }
            case 3:{
                test = drawOrder3;
                break;
            }
            case 4:{
                test = drawOrder4;
                break;
            }

            case 5:{
                test = drawOrder5;
                break;
            }
            case 6:{
                test = drawOrder6;
                break;
            }
            case 7:{
                test = drawOrder7;
                break;
            }
            default:
                break;
        }
        return test;
    }


    private float[] provaSx(float[] test) {

        for (int i = 0; i < test.length; ) {
            test[i] += Larg;
            i += 3;
        }
        return test;
    }

    private float[] provaDx(float[] test) {
        /*
            Amici siamo simili ma certo non uguali
            (cit viva - Zen Circus)
            Stesso ragionamento che succede a sinistra:
            -> controllo se ci sono 6 elementi o più

         */
        for (int i = 0; i < test.length; ) {
            test[i] -= Larg;
            i += 3;
        }

        return test;
    }

    private void ResetMatrix() {
        for(int i=0;i<MAXCAPIENZA;i++){
            for(int j=0;j<maxBin;j++){
                matrice[i][j] = 0;
            }
        }
    }

    private void MatrixElement(int p, int c){
        int i;
        for (i= MAXCAPIENZA-1;i>=0;i--){
            if(matrice[i][c] == 0) {
                break;
            }
        }
        for(int j=0;j<p;j++){
            matrice[i][c] = p;
            i--;
        }
    }

    //getter and setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRigum(int rigum) {
        this.rigum = rigum;
    }

    public void setMessageOption(int[] messageOption) {
        this.messageOption = messageOption;
    }

}
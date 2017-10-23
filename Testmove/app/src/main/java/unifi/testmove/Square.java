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
        import java.nio.ByteBuffer;
        import java.nio.ByteOrder;
        import java.nio.FloatBuffer;
        import java.nio.ShortBuffer;

        import android.opengl.GLES20;

/**
 * A two-dimensional square for use as a drawn object in OpenGL ES 2.0.
 */
public class Square {

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position =  vPosition * uMVPMatrix;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    //static float squareCoords[] = {
    //        0.86f,  0.95f, 0.0f,   // top right
    //        0.86f, 0.7f, 0.0f,   // bottom right
    //        1.3f, 0.7f, 0.0f,   // bottom left
    //        1.3f,  0.95f, 0.0f }; // top left

    private final short dOrder[]; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private float colorTurquoise[] = { 0.08235f, 0.85098f, 0.94510f, 1.0f };
    private float colorYellow[] = { 0.90980f, 0.92941f, 0.09020f, 1.0f };
    private float colorRed[] = {0.94118f, 0.03922f, 0.14902f, 1.0f };
    private float colorOrange[] = {0.93333f, 0.54118f, 0.00392f, 1.0f};
    private float colorLime[] = {0.39608f, 0.92157f, 0.00392f, 1.0f};
    private float colorMagenta[] = {0.87451f,  0.05098f,  0.91765f, 1.0f};
    private float colorBlack[] = {0.000f, 0.000f, 0.000f, 1.0f};
    private float colorIndigo[] = {0.576f, 0.439f, 0.859f, 1.0f};

    private float color[];


    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Square(float squareCoords[], short drawOrder[],int i) {
        dOrder = drawOrder;

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);
        color = changeColor(i);
        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables


    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix, int dType) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        GLES20.glLineWidth(2.0f);
        // Draw the square
        GLES20.glDrawElements(
                dType, dOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    private float[] changeColor(int i) {
        float test [] = {};
        switch (i) {
            case 1: {
                test = colorRed;
                break;
            }
            case 2: {
                test = colorOrange;
                break;
            }
            case 3: {
                test = colorYellow;
                break;
            }
            case 4: {
                test = colorLime;
                break;
            }
            case 5:  {
                test = colorTurquoise;
                break;
            }
            case 6: {
                test = colorIndigo;
                break;
            }
            case 7: {
                test = colorMagenta;
                break;
            }
            default: {
                test = colorBlack;
                break;
            }

        }
        return test;
    }
}
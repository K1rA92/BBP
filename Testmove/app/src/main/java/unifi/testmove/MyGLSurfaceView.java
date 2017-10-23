package unifi.testmove;
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
        import android.content.Context;
        import android.opengl.GLSurfaceView;
        import android.util.AttributeSet;

        import java.util.ArrayList;
        import java.util.List;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private String status = "ok";
    private final MyGLRenderer mRenderer;


    public MyGLSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


    }

    //MOVIMENTI

    //I nomi dei comandi sono messi a caso
    //avanti = destra
    public void avanti() {
        mRenderer.changecoordDx();
        //requestRender();
    }
    //indietro = sinistra
    public void indietro() {
        mRenderer.changecoordSx();
        //requestRender();
    }

    //testgiu = scende di una casella
    public void testgiu() {

            try {
                mRenderer.GetDown();
                //requestRender();
                status = mRenderer.getStatus();
                if((status.equals("gameover")) || (status.equals("win"))) {
                    setStatus(mRenderer.getStatus());
                }

            }catch (Exception e) {

            }
        }

    //forcedown = impongo al gioco che il blocco scende immediamente
    public void forcedown() {
        mRenderer.setRigum(7);
        mRenderer.GetDown();
        requestRender();
        status = mRenderer.getStatus();
        if((status.equals("gameover")) || (status.equals("win"))) {
            setStatus(mRenderer.getStatus());
        }
    }

    //getter and setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessaggio(int[] messaggio) {
        mRenderer.setMessageOption(messaggio);
    }
}

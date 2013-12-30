package com.example.androidebow;

import java.util.HashMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import FileReader.MeshObject;
import OpenGLTools.GraphicsLibrary;
import OpenGLTools.Light;
import OpenGLTools.ShaderProgram;
import VMQ.Vec3;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class EbowRenderer implements GLSurfaceView.Renderer {
	/** Used for debug logs. */
	private static final String TAG = "Ebow Renderer";
	
	private final EbowActivity ebowActivity;
	private final GLSurfaceView mGLSurfaceView;
	
	/** Android's OpenGL bindings are broken until Gingerbread, so we use LibGDX bindings here. */
//	private final AndroidGL20 mGlEs20;
	
	/**
	 * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
	 * of being located at the center of the universe) to world space.
	 */
	private float[] mModelMatrix = new float[16];

	/**
	 * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
	 * it positions things relative to our eye.
	 */
	private float[] mViewMatrix = new float[16];

	/** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
	private float[] mProjectionMatrix = new float[16];
	
	/** Allocate storage for the final combined matrix. This will be passed into the shader program. */
	private float[] mMVPMatrix = new float[16];
	
	private HashMap<String,ShaderProgram> shaders = new HashMap<String,ShaderProgram>();
	private HashMap<String,Light> lights = new HashMap<String,Light>();
	private GraphicsLibrary graphicsLibrary;

	private float[] mLightModelMatrix = new float[16];
	private float[] mLightPosInWorldSpace = new float[16];
	private float[] mLightPosInEyeSpace = new float[16];
	private float[] mLightPosInModelSpace = new float[16];

	private float[] mCurrentRotation = new float[16];
	private float[] mTemporaryMatrix = new float[16];

	private float mDeltaX;
	private float mDeltaY;
	private boolean setup = false;
	
	public EbowRenderer(final EbowActivity ebowActivity, final GLSurfaceView glSurfaceView) {
		System.out.println("New object created !!");
		this.ebowActivity = ebowActivity;	
		this.mGLSurfaceView = glSurfaceView;		
	}
	
	private void setup() {
		lights.put("NaiveLight", new Light (new Vec3(1.0f,1.0f,1.0f),new Vec3 (1.0f,1.0f,1.0f),new Vec3 (0.0f,0.0f,1.0f),0.2f,new Vec3 (0.0f,0.0f,1.0f)));
		
		// Naive shader
		shaders.put("NaiveShader", new ShaderProgram(mGLSurfaceView.getContext(),"lesson_seven_vertex_shader.glsl","lesson_seven_fragment_shader.glsl",new String[] {"a_Position",  "a_Normal", "a_TexCoordinate"}));
		shaders.get("NaiveShader").use();
		// Setup shader
//		shaderSetup(shaders.get("NaiveShader")); 
		shaders.get("NaiveShader").putLocation("material_Alpha");
		
        shaders.get("NaiveShader").putLocation("u_MVPMatrix");
		shaders.get("NaiveShader").putLocation("u_MVMatrix"); 
		shaders.get("NaiveShader").putLocation("u_LightPos");
		shaders.get("NaiveShader").putLocation("u_Texture");
		
		// Load the texture and vbos
		graphicsLibrary = new GraphicsLibrary(mGLSurfaceView.getContext(),"EbowGraphics.egf");
	}
	
	@Override
	public void onDrawFrame(GL10 arg0) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		ShaderProgram currentShader = shaders.get("NaiveShader");
		currentShader.use();
		
        // Calculate position of the light. Push into the distance.
        Matrix.setIdentityM(mLightModelMatrix, 0);                     
        Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, -1.0f);
               
        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, mViewMatrix, 0, mLightPosInWorldSpace, 0);                      
        
        // Draw ship on the screen.
        // Translate the cube into the screen.
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, -40.0f);     
        
    	mDeltaY = 90.0f;
        // Set a matrix that contains the current rotation.
        Matrix.setIdentityM(mCurrentRotation, 0);        
    	Matrix.rotateM(mCurrentRotation, 0, mDeltaX, 0.0f, 1.0f, 0.0f);
    	Matrix.rotateM(mCurrentRotation, 0, mDeltaY, 1.0f, 0.0f, 0.0f);
    	    	
        // Rotate the cube taking the overall rotation into account.     	
    	Matrix.multiplyMM(mTemporaryMatrix, 0, mModelMatrix, 0, mCurrentRotation, 0);
    	System.arraycopy(mTemporaryMatrix, 0, mModelMatrix, 0, 16);   
    	
    	// This multiplies the view matrix by the model matrix, and stores
		// the result in the MVP matrix
		// (which currently contains model * view).
		Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

		// Pass in the modelview matrix.
		GLES20.glUniformMatrix4fv(shaders.get("NaiveShader").putLocation("u_MVMatrix"), 1, false, mMVPMatrix, 0);

		// This multiplies the modelview matrix by the projection matrix,
		// and stores the result in the MVP matrix
		// (which now contains model * view * projection).
		Matrix.multiplyMM(mTemporaryMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
		System.arraycopy(mTemporaryMatrix, 0, mMVPMatrix, 0, 16);

		// Pass in the combined matrix.
		GLES20.glUniformMatrix4fv(shaders.get("NaiveShader").putLocation("u_MVPMatrix"), 1, false, mMVPMatrix, 0);

		// Pass in the light position in eye space.
		GLES20.glUniform3f(shaders.get("NaiveShader").putLocation("u_LightPos"), mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);
		
		/**** BIND VBOS !!! ****/
		
		// Set the active texture unit to texture unit 0
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		MeshObject meshObj[] = graphicsLibrary.getMeshObj("Ship");
		
		String currentTexture = "";
		for (MeshObject curMeshObj: meshObj) {
			// if the texture is different to the current texture bind the texture
			if (!(curMeshObj.getTextureName().equals(currentTexture))) {
				currentTexture = curMeshObj.getTextureName();
				graphicsLibrary.bindTexture(currentTexture);
			}
			// render the mesh (vertices/normals/texturecoords)
			graphicsLibrary.getVBOObject(curMeshObj.getVBOIndex()).renderVBO(
												currentShader.getAttribute("a_Position"),
												currentShader.getAttribute("a_Normal"),
												currentShader.getAttribute("a_TexCoordinate"));
		}
//		GLES20.glFlush();
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		// Set the OpenGL viewport to the same size as the surface.
		GLES20.glViewport(0, 0, width, height);

		// Create a new perspective projection matrix. The height will stay the same
		// while the width will vary as per aspect ratio.
		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 1000.0f;
		
		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		// Set the background clear color to black.
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		// Use culling to remove back faces.
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		
		// Enable depth testing
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);						
		
		// Position the eye in front of the origin.
		final float eyeX = 0.0f;
		final float eyeY = 0.0f;
		final float eyeZ = -0.5f;

		// We are looking toward the distance
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -5.0f;

		// Set our up vector. This is where our head would be pointing were we holding the camera.
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;

		// Set the view matrix. This matrix can be said to represent the camera position.
		// NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
		// view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
		Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
		if (!this.setup) {
			System.out.println("Running Setup");
			setup();
			this.setup = true;
		}
	}
	
    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mDeltaX;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mDeltaX = angle;
    }
}



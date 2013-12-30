package GameEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.opengl.GLES20;

import FileReader.Material;
import FileReader.MeshObject;
import OpenGLTools.GraphicsLibrary;
import OpenGLTools.Light;
import OpenGLTools.ShaderProgram;
import OpenGLTools.WindowDimensions;
import VMQ.Mat4x4;
import VMQ.Vec3;
import VMQ.Vec4;

public class Display {

	private GraphicsLibrary graphicsLibrary;
	private WindowDimensions windowDimensions;
	private HashMap<String,ShaderProgram> shaders = new HashMap<String,ShaderProgram>();
	private HashMap<String,Light> lights = new HashMap<String,Light>();
	
	private List<GameObj> backgroundObjects;
	private List<InPlayObj> enemys;
	private List<InPlayObj> players;
	private List<Particle> particles;
	private List<GameObj> HUDObjects;
	 
	
	public Display(Context context,WindowDimensions windowDimensions) {
		this.windowDimensions = windowDimensions;
		graphicsLibrary = new GraphicsLibrary(context,"EbowGraphics.egf");
		
		lights.put("NaiveLight", new Light (new Vec3(1.0f,1.0f,1.0f),new Vec3 (1.0f,1.0f,1.0f),new Vec3 (0.0f,0.0f,1.0f),0.2f,new Vec3 (0.0f,0.0f,1.0f)));
		
		// Naive shader
		shaders.put("NaiveShader", new ShaderProgram(context,"MaterialDirectionalLightingVertexShader.vertexshader","MaterialDirectionalLightingFragmentShader.fragmentshader",new String[] {"position","normal","textureCoords"}));
		shaders.get("NaiveShader").use();
		// Setup shader
		shaderSetup(shaders.get("NaiveShader")); 
		shaders.get("NaiveShader").putLocation("material_Alpha");

		// Transparent shader
		shaders.put("TransparentShader", new ShaderProgram(context,"MaterialDirectionalLightingVertexShader.vertexshader","TransparentFragmentShader.fragmentshader",new String[] {"position","normal","textureCoords"}));
		shaders.get("TransparentShader").use();
		// Setup shader 
		shaderSetup(shaders.get("TransparentShader"));
		shaders.get("TransparentShader").putLocation("object_Color");
	}
	
	private void shaderSetup(ShaderProgram shader) {
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(shader.getID(), "ambient_Light_Color"), 1, lights.get("NaiveLight").getAmbientColor().getAsArray(), 0);
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(shader.getID(), "diffuse_Light_Color"),1, lights.get("NaiveLight").getDiffuseColor().getAsArray(),0);
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(shader.getID(), "diffuse_Light_Direction"),1, lights.get("NaiveLight").getDiffuseDirection().getAsArray(),0);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(shader.getID(), "diffuse_Light_Strength"), lights.get("NaiveLight").getDiffuseStrength());
		GLES20.glUniform3fv(GLES20.glGetUniformLocation(shader.getID(),"eye_Direction"), 1, lights.get("NaiveLight").getEyeDirection().getAsArray(), 0);
		// Setup locations
		shader.putLocation("perspectiveMatrix");
		shader.putLocation("modelMatrix");
		shader.putLocation("normalMatrix");
		shader.putLocation("texture_color");
		shader.putLocation("material_Emission");
		shader.putLocation("material_Ambient");
		shader.putLocation("material_Diffuse");
		shader.putLocation("material_Specular");
		shader.putLocation("material_Shininess");
		
	}
	
	public void setBackgroundObjects(List<GameObj> backgroundObjects) {
		this.backgroundObjects = backgroundObjects;
	}

	public void setEnemyObjects(List<InPlayObj> enemys) {
		this.enemys = enemys;
	}

	public void setPlayerObjects(List<InPlayObj> players) {
		this.players = players;
	}
	
	public void setParticleObjects(List<Particle> particles) {
		this.particles = particles;
	}
	
	public void setHUDObjects(List<GameObj> HUDObjects) {
		this.HUDObjects = HUDObjects;
	}
	
	public HashMap<String,Vec3> getObjectDimensions() {
		return graphicsLibrary.getDimensions();
	}
	
	public void tick() {
//		renderObjects(backgroundObjects);
//		renderObjects(enemys);
		renderObjects(players);
//		renderParticles(particles);
//		renderObjects(HUDObjects);
	}
	
	/*
	 * return a translation matrix for an object
	 */
	private Mat4x4 getTranslationMatrix(GameObj gameObj) {
		// Column matrix
		return new Mat4x4(new float[] {1,0,0,0,
										0,1,0,0,
										0,0,1,0,
										gameObj.getPosition().getX(),gameObj.getPosition().getY(),gameObj.getPosition().getZ(),1});
	}
	
	/*
	 * return a scaling matrix for an object
	 */
	private Mat4x4 getScalingMatrix(GameObj gameObj) {
		// Column matrix
		return  new Mat4x4(new float[] {gameObj.getScale().getX(),0,0,0,
						0,gameObj.getScale().getY(),0,0,
						0,0,gameObj.getScale().getZ(),0,
						0,0,0,1f});
	}
	
	/*
	 * Renders explosions (Transparent billboards) 
	 */
	private void renderParticles(List<Particle> particles) {
		ShaderProgram currentShader = shaders.get("TransparentShader");
		currentShader.use();
	    GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		
		// pass the perspective matrix to the shader
		GLES20.glUniformMatrix4fv(currentShader.getLocation("perspectiveMatrix"), 1, false, windowDimensions.getPerspectiveMatrix(), 0);
		// make sure the texture is linked to the location
		GLES20.glUniform1i(currentShader.getLocation("texture_color"),0);
		GLES20.glActiveTexture(0);
		// Sort particles by z
		Collections.sort(particles);
		
		String currentTexture = null;
		
		for (Particle curParticle: particles) {
			
			MeshObject curObj = graphicsLibrary.getMeshObj(curParticle.getType())[0];
			
			// if the texture is different to the current texture bind the texture
			if (!(curObj.getTextureName().equals(currentTexture))) {
				currentTexture = curObj.getTextureName();
				graphicsLibrary.bindTexture(currentTexture);
			}
			
			Mat4x4 modelMatrix = getTranslationMatrix(curParticle).multiply(curParticle.getRotation().rotationMatrix().multiply(getScalingMatrix(curParticle)));
			// pass the model matrix to the shader
			GLES20.glUniformMatrix4fv(currentShader.getLocation("modelMatrix"), 1, false, modelMatrix.getAsArray(), 0);
			
			// pass the material information to the shader
			Material curMaterial = graphicsLibrary.getMaterialObject(curObj.getMaterialIndex());
			GLES20.glUniform3fv(currentShader.getLocation("material_Emission"),1,curMaterial.getEmission().getAsArray(),0);
			GLES20.glUniform3fv(currentShader.getLocation("material_Ambient"),1,curMaterial.getAmbient().getAsArray(),0);
			GLES20.glUniform3fv(currentShader.getLocation("material_Diffuse"),1,curMaterial.getDiffuse().getAsArray(),0);
			GLES20.glUniform3fv(currentShader.getLocation("material_Specular"),1,curMaterial.getSpecular().getAsArray(),0);
			GLES20.glUniform1f(currentShader.getLocation("material_Shininess"),curMaterial.GetSpecularWeight());
			GLES20.glUniform4fv(currentShader.getLocation("object_Color"),1,new Vec4(curParticle.getColor(),curParticle.getAlpha()).getAsArray(),0);
			
			// render the mesh (vertices/normals/texturecoords)
			//graphicsLibrary.getVBOObject(curObj.getVBOIndex()).renderVBO(currentShader.getAttribute("position"),currentShader.getAttribute("normal"),currentShader.getAttribute("textureCoords"));
		}
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDisable(GLES20.GL_BLEND);
	}
	
	/*
	 * Returns all the meshes to be drawn and computes their Model Matrices  
	 */
	private ArrayList<MeshObjectToDraw> getMeshObjectToDraw(List<? extends GameObj> gameObjects) {
		ArrayList<MeshObjectToDraw> retMeshObjectsToDraw = new ArrayList<MeshObjectToDraw>();
		for (GameObj curObj: gameObjects) {
			MeshObject[] meshObject = graphicsLibrary.getMeshObj(curObj.getType());
			Mat4x4 modelMatrix = getTranslationMatrix(curObj).multiply(curObj.getRotation().rotationMatrix().multiply(getScalingMatrix(curObj)));
			Mat4x4 normalMatrix = modelMatrix.getInverse().getTranspose();
			for (MeshObject curMeshObject: meshObject) {
				retMeshObjectsToDraw.add(new MeshObjectToDraw(curMeshObject,modelMatrix,normalMatrix,
										new Vec4(curObj.getColor(),curObj.getLife()),
										curObj.getDisplayType(),curObj.useObjectColor()));
			}
		}
		return retMeshObjectsToDraw;
	}
	
	/*
	 * Renders a list of gameObjects
	 */
	private void renderObjects(List<? extends GameObj> gameObjects) { 
		ShaderProgram currentShader = shaders.get("NaiveShader");
		currentShader.use();
		// pass the perspective matrix to the shader
		GLES20.glUniformMatrix4fv(currentShader.getLocation("perspectiveMatrix"), 1, false, windowDimensions.getPerspectiveMatrix(), 0);
		// make sure the texture is linked to the location
		GLES20.glUniform1i(currentShader.getLocation("texture_color"),0);
		GLES20.glActiveTexture(0);
				
		// get the meshs to draw
		ArrayList<MeshObjectToDraw> meshObjects = getMeshObjectToDraw(gameObjects);
		// sort them into order dependent on the textures being used
		Collections.sort(meshObjects);
		
		String currentTexture = null;
		GameObj.DISPLAYTYPE currentDisplayType = null;
				
		for (MeshObjectToDraw meshObject: meshObjects) {
			// if the display type has changed change the opengl state
			if (meshObject.getDisplayType()!=currentDisplayType) {
				currentDisplayType = meshObject.getDisplayType();
				switch (currentDisplayType) {
					case TRANSPARENT_ONE:	GLES20.glEnable(GLES20.GL_BLEND);
											GLES20.glEnable(GLES20.GL_DEPTH_TEST);
											GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);
											break;
					case SOLID:				GLES20.glEnable(GLES20.GL_DEPTH_TEST);
											GLES20.glDisable(GLES20.GL_BLEND);
					case TEXT:
					case TRANSPARENT:
											break;
				}
			}
			
			// if the texture is different to the current texture bind the texture
			if (!(meshObject.getMeshObject().getTextureName().equals(currentTexture))) {
				currentTexture = meshObject.getMeshObject().getTextureName();
				System.out.println("Current Texture "+currentTexture);
				graphicsLibrary.bindTexture(currentTexture);
			}
			// pass the model matrix to the shader
			GLES20.glUniformMatrix4fv(currentShader.getLocation("modelMatrix"), 1, false, meshObject.getModelMatrix().getAsArray(), 0);
			// pass the material information to the shader
			Material curMaterial = graphicsLibrary.getMaterialObject(meshObject.getMeshObject().getMaterialIndex()); 
			GLES20.glUniform3fv(currentShader.getLocation("material_Emission"),1,curMaterial.getEmission().getAsArray(),0);
			if (meshObject.useColorObject()) {
				GLES20.glUniform3fv(currentShader.getLocation("material_Ambient"),1,meshObject.getObjectColor().getVec3().getAsArray(),0);
				GLES20.glUniform3fv(currentShader.getLocation("material_Diffuse"),1,meshObject.getObjectColor().getVec3().getAsArray(),0);
				GLES20.glUniform1f(currentShader.getLocation("material_Alpha"), meshObject.getObjectColor().getW());
			} else {
				GLES20.glUniform3fv(currentShader.getLocation("material_Ambient"),1,curMaterial.getAmbient().getAsArray(),0);
				GLES20.glUniform3fv(currentShader.getLocation("material_Diffuse"),1,curMaterial.getDiffuse().getAsArray(),0);
			}
			GLES20.glUniform3fv(currentShader.getLocation("material_Specular"),1,curMaterial.getSpecular().getAsArray(),0);
			GLES20.glUniform1f(currentShader.getLocation("material_Shininess"),curMaterial.GetSpecularWeight());

			// render the mesh (vertices/normals/texturecoords)
//			graphicsLibrary.getVBOObject(meshObject.getMeshObject().getVBOIndex()).renderVBO(
//												currentShader.getAttribute("position"),
//												currentShader.getAttribute("normal"),
//												currentShader.getAttribute("textureCoords"));
		}
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDisable(GLES20.GL_BLEND);
	}	
}

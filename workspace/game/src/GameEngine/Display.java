package GameEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.media.opengl.GL3;

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

	private GL3 gl;
	private GraphicsLibrary graphicsLibrary;
	private WindowDimensions windowDimensions;
	private HashMap<String,ShaderProgram> shaders = new HashMap<String,ShaderProgram>();
	private HashMap<String,Light> lights = new HashMap<String,Light>();
	
	private List<GameObj> backgroundObjects;
	private List<InPlayObj> enemys;
	private List<InPlayObj> players;
	private List<Particle> particles;
	private List<GameObj> HUDObjects;
	
	public Display(GL3 gl,WindowDimensions windowDimensions) {
		this.gl = gl;
		this.windowDimensions = windowDimensions;
		graphicsLibrary = new GraphicsLibrary(gl,"c:/Users/Jack/My Documents/Game/"+"EbowGraphics.egf");
		
		lights.put("NaiveLight", new Light (new Vec3(1.0f,1.0f,1.0f),new Vec3 (1.0f,1.0f,1.0f),new Vec3 (0.0f,0.0f,1.0f),0.2f,new Vec3 (0.0f,0.0f,1.0f)));
		
		// Naive shader
		shaders.put("NaiveShader", new ShaderProgram(gl,"MaterialDirectionalLightingVertexShader.vertexshader","MaterialDirectionalLightingFragmentShader.fragmentshader"));
		shaders.get("NaiveShader").use();
		// Setup shader
		shaderSetup(shaders.get("NaiveShader")); 
		shaders.get("NaiveShader").putLocation("material_Alpha");

		// Transparent shader
		shaders.put("TransparentShader", new ShaderProgram(gl,"MaterialDirectionalLightingVertexShader.vertexshader","TransparentFragmentShader.fragmentshader"));
		shaders.get("TransparentShader").use();
		// Setup shader 
		shaderSetup(shaders.get("TransparentShader"));
		shaders.get("TransparentShader").putLocation("object_Color");
		
		// Hud shader
		shaders.put("HudShader", new ShaderProgram(gl,"MaterialDirectionalLightingVertexShader.vertexshader","HudFragmentShader.fragmentshader"));
		shaders.get("HudShader").use();
		// Setup shader
		shaderSetup(shaders.get("HudShader"));
		shaders.get("HudShader").putLocation("object_Color");
	}
	
	private void shaderSetup(ShaderProgram shader) {
		gl.glUniform3fv(gl.glGetUniformLocation(shader.getID(), "ambient_Light_Color"), 3, lights.get("NaiveLight").getAmbientColor().getAsArray(), 0);
		gl.glUniform3fv(gl.glGetUniformLocation(shader.getID(), "diffuse_Light_Color"),3, lights.get("NaiveLight").getDiffuseColor().getAsArray(),0);
		gl.glUniform3fv(gl.glGetUniformLocation(shader.getID(), "diffuse_Light_Direction"),3, lights.get("NaiveLight").getDiffuseDirection().getAsArray(),0);
		gl.glUniform1f(gl.glGetUniformLocation(shader.getID(), "diffuse_Light_Strength"), lights.get("NaiveLight").getDiffuseStrength());
		gl.glUniform3fv(gl.glGetUniformLocation(shader.getID(),"eye_Direction"), 3, lights.get("NaiveLight").getEyeDirection().getAsArray(), 0);
		// Setup locations
		shader.putLocation("perspectiveMatrix");
		shader.putLocation("modelMatrix");
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
		renderObjects(backgroundObjects);
		renderObjects(enemys);
		renderObjects(players);
		renderParticles(particles);
		renderObjects(HUDObjects);
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
	    gl.glEnable(GL3.GL_BLEND);
		gl.glBlendFunc(GL3.GL_SRC_ALPHA, GL3.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDepthMask(false);
		
		// pass the perspective matrix to the shader
		gl.glUniformMatrix4fv(currentShader.getLocation("perspectiveMatrix"), windowDimensions.getPerspectiveMatrix().getAsArray().length, false, windowDimensions.getPerspectiveMatrix().getAsArray(), 0);
		// make sure the texture is linked to the location
		gl.glUniform1i(currentShader.getLocation("texture_color"),0);
		gl.glActiveTexture(0);
		// Sort particles by z
		Collections.sort(particles);
		
		String currentTexture = null;
		
		for (Particle curParticle: particles) {
			
			MeshObject curObj = graphicsLibrary.getMeshObj(curParticle.getType())[0];
			
			// if the texture is different to the current texture bind the texture
			if (!(curObj.getTextureName().equals(currentTexture))) {
				currentTexture = curObj.getTextureName();
				graphicsLibrary.getTexture(currentTexture).bind(gl);
			}
			
			Mat4x4 modelMatrix = getTranslationMatrix(curParticle).multiply(curParticle.getRotation().rotationMatrix().multiply(getScalingMatrix(curParticle)));
			// pass the model matrix to the shader
			gl.glUniformMatrix4fv(currentShader.getLocation("modelMatrix"), modelMatrix.getAsArray().length, false, modelMatrix.getAsArray(), 0);
			
			// pass the material information to the shader
			Material curMaterial = graphicsLibrary.getMaterialObject(curObj.getMaterialIndex());
			gl.glUniform3fv(currentShader.getLocation("material_Emission"),3,curMaterial.getEmission().getAsArray(),0);
			gl.glUniform3fv(currentShader.getLocation("material_Ambient"),3,curMaterial.getAmbient().getAsArray(),0);
			gl.glUniform3fv(currentShader.getLocation("material_Diffuse"),3,curMaterial.getDiffuse().getAsArray(),0);
			gl.glUniform3fv(currentShader.getLocation("material_Specular"),3,curMaterial.getSpecular().getAsArray(),0);
			gl.glUniform1f(currentShader.getLocation("material_Shininess"),curMaterial.GetSpecularWeight());
			gl.glUniform4fv(currentShader.getLocation("object_Color"),3,new Vec4(curParticle.getColor(),curParticle.getAlpha()).getAsArray(),0);
			
			// render the mesh (vertices/normals/texturecoords)
			graphicsLibrary.getVBOObject(curObj.getVBOIndex()).renderVBO(gl);
		}
		gl.glDepthMask(true);
		gl.glDisable(GL3.GL_BLEND);
	}
	
	/*
	 * Returns all the meshes to be drawn and computes their Model Matrices  
	 */
	private ArrayList<MeshObjectToDraw> getMeshObjectToDraw(List<? extends GameObj> gameObjects) {
		ArrayList<MeshObjectToDraw> retMeshObjectsToDraw = new ArrayList<MeshObjectToDraw>();
		for (GameObj curObj: gameObjects) {
			MeshObject[] meshObject = graphicsLibrary.getMeshObj(curObj.getType());
			Mat4x4 modelMatrix = getTranslationMatrix(curObj).multiply(curObj.getRotation().rotationMatrix().multiply(getScalingMatrix(curObj)));
			for (MeshObject curMeshObject: meshObject) {
				retMeshObjectsToDraw.add(new MeshObjectToDraw(curMeshObject,modelMatrix,
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
		gl.glUniformMatrix4fv(currentShader.getLocation("perspectiveMatrix"), windowDimensions.getPerspectiveMatrix().getAsArray().length, false, windowDimensions.getPerspectiveMatrix().getAsArray(), 0);
		// make sure the texture is linked to the location
		gl.glUniform1i(currentShader.getLocation("texture_color"),0);
		gl.glActiveTexture(0);
				
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
					case TRANSPARENT_ONE:	gl.glEnable(GL3.GL_BLEND);
											gl.glDepthMask(false);
											gl.glBlendFunc(GL3.GL_SRC_ALPHA,GL3.GL_ONE_MINUS_SRC_ALPHA);
											break;
					case SOLID:				gl.glDepthMask(true);
											gl.glDisable(GL3.GL_BLEND);
					case TEXT:
					case TRANSPARENT:
											break;
				}
			}
			
			// if the texture is different to the current texture bind the texture
			if (!(meshObject.getMeshObject().getTextureName().equals(currentTexture))) {
				currentTexture = meshObject.getMeshObject().getTextureName();
				graphicsLibrary.getTexture(currentTexture).bind(gl);
			}
			// pass the model matrix to the shader
			gl.glUniformMatrix4fv(currentShader.getLocation("modelMatrix"), meshObject.getModelMatrix().getAsArray().length, false, meshObject.getModelMatrix().getAsArray(), 0);
			// pass the material information to the shader
			Material curMaterial = graphicsLibrary.getMaterialObject(meshObject.getMeshObject().getMaterialIndex()); 
			gl.glUniform3fv(currentShader.getLocation("material_Emission"),3,curMaterial.getEmission().getAsArray(),0);
			if (meshObject.useColorObject()) {
				gl.glUniform3fv(currentShader.getLocation("material_Ambient"),3,meshObject.getObjectColor().getVec3().getAsArray(),0);
				gl.glUniform3fv(currentShader.getLocation("material_Diffuse"),3,meshObject.getObjectColor().getVec3().getAsArray(),0);
				gl.glUniform1f(currentShader.getLocation("material_Alpha"), meshObject.getObjectColor().getW());
			} else {
				gl.glUniform3fv(currentShader.getLocation("material_Ambient"),3,curMaterial.getAmbient().getAsArray(),0);
				gl.glUniform3fv(currentShader.getLocation("material_Diffuse"),3,curMaterial.getDiffuse().getAsArray(),0);
			}
			gl.glUniform3fv(currentShader.getLocation("material_Specular"),3,curMaterial.getSpecular().getAsArray(),0);
			gl.glUniform1f(currentShader.getLocation("material_Shininess"),curMaterial.GetSpecularWeight());

			// render the mesh (vertices/normals/texturecoords)
			graphicsLibrary.getVBOObject(meshObject.getMeshObject().getVBOIndex()).renderVBO(gl);
		}
		gl.glDepthMask(true);
		gl.glDisable(GL3.GL_BLEND);
	}	
}

package com.gurbx.ld39.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.utils.sound.SoundHandler;

public class World implements GameInterface {
	private final float COUNTDOWN_TIME = 120;
	private float countdownTimer = COUNTDOWN_TIME;
	private final float GRAVITY = 800f;
	private final Application app;
	private float groundX, groundY;
	private float groundWidth, groundHeight;
	private float levelWidth, levelHeight;
	private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private PowerGenerator powerGeneratorLeft, powerGeneratorRight;
    
    public World(Application app, TextureAtlas atlas) {
    	this.app = app;
    	tiledMap = app.assets.get("maps/worldMap.tmx", TiledMap.class);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        levelWidth = layer.getWidth() * layer.getTileWidth();
        levelHeight = layer.getHeight() * layer.getTileHeight();
        initGround();
        initPowerGenerators(atlas);
	}
	
	private void initPowerGenerators(TextureAtlas atlas) {
		TextureRegion generatorTexture = atlas.findRegion("powerGenerator");
		powerGeneratorLeft = new PowerGenerator(400, groundHeight + generatorTexture.getRegionHeight()*0.5f , generatorTexture);
		powerGeneratorRight = new PowerGenerator(1200, groundHeight + generatorTexture.getRegionHeight()*0.5f , generatorTexture);
		
	}

	private void initGround() {
        MapObjects objects = tiledMap.getLayers().get("ground").getObjects();
        for (MapObject object : objects) {
//            String name = object.getName();
            RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
            Rectangle rectangle = rectangleMapObject.getRectangle();
            groundX = rectangle.x;
            groundY = rectangle.y;
            groundWidth = rectangle.width;
            groundHeight = rectangle.height -7;
        }
	}

	@Override
	public void update(float delta) {
		countdownTimer -= delta;
		powerGeneratorLeft.update(delta);
		powerGeneratorRight.update(delta);
		
		//Game over if generators die
		if (powerGeneratorLeft.getHealth() <= 0 || powerGeneratorRight.getHealth() <= 0) {
			SoundHandler.muteSounds();
			app.setScreen(app.gameOverScreen);
		}

		
	}
	
	public void renderMap() {
        tiledMapRenderer.setView(app.camera);
        tiledMapRenderer.render();
	}
	
	
	@Override
	public void render(SpriteBatch batch) {
		powerGeneratorLeft.render(batch);
		powerGeneratorRight.render(batch);
		
	}
	
	public boolean isStoppedByGround(float x, float y, float width, float height) {
		if (groundX <= x + width*0.5f && (groundX + groundWidth) >= (x - width*0.5f) &&
				y-height*0.5f <= groundHeight && y-height*0.5f > groundHeight - 32f) {
			return true;
		}
		return false;
	}
	
	//Returns closest generator to point given
	public PowerGenerator getClosestPowerGenerator(float x1, float y1) {
		float y2 = powerGeneratorLeft.getY();
		float x2 = powerGeneratorLeft.getX();
		float distance1 = (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		y2 = powerGeneratorRight.getY();
		x2 = powerGeneratorRight.getX();
		float distance2 = (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		if (distance1 > distance2) return powerGeneratorRight;
		return powerGeneratorLeft;
	}
	
	
	@Override
	public void dispose() {
		tiledMap.dispose();
		powerGeneratorLeft.dispose();
		powerGeneratorRight.dispose();
	}

	
	public float getGroundX() {
		return groundX;
	}

	public float getGroundY() {
		return groundY;
	}

	public float getGroundWidth() {
		return groundWidth;
	}

	public float getGroundHeight() {
		return groundHeight;
	}

	public float getLevelWidth() {
		return levelWidth;
	}

	public float getLevelHeight() {
		return levelHeight;
	}
	
	public float getGravity() {
		return GRAVITY;
	}
	
	public PowerGenerator getLeftGenerator() {
		return powerGeneratorLeft;
	}
	
	public PowerGenerator getRightGenerator() {
		return powerGeneratorRight;
	}
	
	public int getCountdownTimer() {
		return (int) countdownTimer;
	}
	
	
	
	

}

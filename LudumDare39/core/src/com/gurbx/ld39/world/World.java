package com.gurbx.ld39.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class World implements GameInterface {
	private final Application app;
	private float groundX, groundY;
	private float groundWidth, groundHeight;
	private float levelWidth, levelHeight;
	private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    
    public World(Application app) {
    	this.app = app;
    	tiledMap = app.assets.get("maps/worldMap.tmx", TiledMap.class);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        levelWidth = layer.getWidth() * layer.getTileWidth();
        levelHeight = layer.getHeight() * layer.getTileHeight();
        initGround();
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
            groundHeight = rectangle.height;
        }
	}

	@Override
	public void update(float delta) {

		
	}
	
	public void renderMap() {
        tiledMapRenderer.setView(app.camera);
        tiledMapRenderer.render();
	}
	
	
	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void dispose() {
		tiledMap.dispose();
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
	
	
	
	

}

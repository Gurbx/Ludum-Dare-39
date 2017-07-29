package com.gurbx.ld39.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
	
	

}

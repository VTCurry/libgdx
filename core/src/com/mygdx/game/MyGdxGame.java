package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	TextureRegion down,up,left,right;
	TextureRegion enemyDown,enemyUp,enemyLeft,enemyRight;
	Animation walkRight,walkLeft,walkUp,walkDown,idle;
	Animation enemyWalkRight, enemyWalkLeft, enemyWalkUp, enemyWalkDown;
	float x,y,xv,yv;
	float enemyX=300, enemyY=300,enemyXV,enemyYV;
	float time;

	TextureRegion playerImg;
	TextureRegion enemyImg;

	TiledMap map;
	OrthogonalTiledMapRenderer orthoRenderer;
	OrthographicCamera camera;

	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final int DRAW_WIDTH = WIDTH*5;
	static final int DRAW_HEIGHT = HEIGHT*5;
	static final float MAX_VELOCITY = 100;
	static final float SPACE_MAX_VELOCITY=200;


	@Override
	public void create () {
		batch = new SpriteBatch();


		camera = new OrthographicCamera();
		camera.setToOrtho(true, 400,400);
		map = new TmxMapLoader().load("level1.tmx");
		orthoRenderer= new OrthogonalTiledMapRenderer(map);

		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);

		down = grid[6][0];
		TextureRegion downWalk1=down;
		TextureRegion downWalk2=new TextureRegion(downWalk1);
		downWalk2.flip(true,false);

		up = grid[6][1];
		TextureRegion upWalk1=up;
		TextureRegion upWalk2=new TextureRegion(upWalk1);
		upWalk2.flip(true,false);


		right = grid[6][2];
		TextureRegion rightWalk1=right;
		TextureRegion rightWalk2=new TextureRegion(grid[6][3]);


		left = new TextureRegion(right);
		left.flip(true, false);
		TextureRegion left2=new TextureRegion(grid[6][3]);
		left2.flip(true,false);
		TextureRegion leftWalk1=left;
		TextureRegion leftWalk2=new TextureRegion(left2);

		enemyDown=grid[6][4];
		TextureRegion enemyDownWalk2=new TextureRegion(enemyDown);
		enemyDownWalk2.flip(true,false);


		enemyUp=grid[6][5];
		TextureRegion enemyUpWalk2=new TextureRegion(enemyUp);
		enemyUpWalk2.flip(true,false);


		enemyRight=grid [6][6];
		TextureRegion enemyRightWalk2=new TextureRegion(grid[6][7]);


		enemyLeft=new TextureRegion(enemyRight);
		enemyLeft.flip(true,false);
		TextureRegion enemyLeftWalk2=new TextureRegion(grid[6][7]);
		enemyLeftWalk2.flip(true,false);


		enemyImg=enemyDown;

		enemyWalkDown=new Animation(0.2f,enemyDown,enemyDownWalk2);
		enemyWalkLeft=new Animation(0.2f,enemyLeft,enemyLeftWalk2);
		enemyWalkRight=new Animation(0.2f,enemyRight,enemyRightWalk2);
		enemyWalkUp=new Animation(0.2f,enemyUp,enemyUpWalk2);


		playerImg = right;


		walkRight = new Animation(0.2f, rightWalk1, rightWalk2);
		walkLeft = new Animation(0.2f,leftWalk1,leftWalk2);
		walkDown= new Animation(0.2f,downWalk1,downWalk2);
		walkUp=new Animation(0.2f,upWalk1,upWalk2);
		idle = new Animation(10.0f,rightWalk1,upWalk1,leftWalk1,downWalk1);



	}



	@Override
	public void render () {
		time += Gdx.graphics.getDeltaTime();

		move();
		enemyMove();

		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			playerImg = up;
			if (yv != 0)
				playerImg = walkUp.getKeyFrame(time, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			playerImg = down;
			if (yv != 0)
				playerImg = walkDown.getKeyFrame(time, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			playerImg = left;
			if (xv != 0)
				playerImg = walkLeft.getKeyFrame(time, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			playerImg = right;
			if (xv != 0)
				playerImg = walkRight.getKeyFrame(time, true);
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		orthoRenderer.setView(camera);
		orthoRenderer.render();
		batch.begin();
		batch.draw(playerImg, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.end();

	}

	public void enemyMove(){
		int enemyDirection =(int)(Math.random()*200-1);
		switch(enemyDirection){
			case 1:
				enemyXV=SPACE_MAX_VELOCITY;
				enemyImg = enemyWalkRight.getKeyFrame(time, true);
				break;
			case 14:
				enemyXV=SPACE_MAX_VELOCITY*-1;
				enemyImg = enemyWalkLeft.getKeyFrame(time, true);
				break;
			case 48:
				enemyYV=SPACE_MAX_VELOCITY;
				enemyImg = enemyWalkUp.getKeyFrame(time, true);
				break;
			case 69:
				enemyYV=SPACE_MAX_VELOCITY*-1;
				enemyImg = enemyWalkDown.getKeyFrame(time, true);
				break;
		}
		enemyY += enemyYV * Gdx.graphics.getDeltaTime();
		enemyX += enemyXV * Gdx.graphics.getDeltaTime();

		if (enemyY < (-20)) {
			enemyY = 450;
		}
		if(enemyY>450){
			enemyY= -20;
		}
		if(enemyX < (-20)){
			enemyX=590;
		}
		if(enemyX>590){
			enemyX= -20;
		}
		enemyYV = decelerate(enemyYV);
		enemyXV = decelerate(enemyXV);
	}

	void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
				yv = SPACE_MAX_VELOCITY;
			else
				yv = MAX_VELOCITY;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
				yv = SPACE_MAX_VELOCITY*-1;
			else
				yv = MAX_VELOCITY * -1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
				xv=SPACE_MAX_VELOCITY;
			else
				xv = MAX_VELOCITY;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
				xv=SPACE_MAX_VELOCITY*-1;
			else
				xv = MAX_VELOCITY * -1;
		}

		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		if (y < (-20)) {
			y = 450;
		}
		if(y>450){
			y= -20;
		}
		if(x < (-20)){
			x=590;
		}
		if(x>590){
			x= -20;
		}
		yv = decelerate(yv);
		xv = decelerate(xv);
	}

	float decelerate(float velocity) {
		float deceleration = 0.90f; // the closer to 1, the slower the deceleration
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}


	@Override
	public void dispose () {
		batch.dispose();
		map.dispose();
		orthoRenderer.dispose();
	}
}
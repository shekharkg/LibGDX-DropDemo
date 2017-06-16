package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;

import java.util.Iterator;

/**
 * Created by shekhar on 6/12/17.
 */

public class GameScreen implements Screen {

  private final MyGdxGame game;


  //Render camera related
  private OrthographicCamera camera;

  //For positioning textures
  private Rectangle bucket;
  private Array<Rectangle> raindrops;
  private long lastDropTime;

  //For updating bucket position in gameplay area
  private Vector3 touchPos;

  //Holds sound and images
  private Texture dropImage;
  private Texture bucketImage;
  private Sound dropSound;

  private int score;


  public GameScreen(MyGdxGame game) {
    this.game = game;


    //Initializing camera and setting dimensions
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    touchPos = new Vector3();

    //Initial position of bucket
    bucket = new Rectangle();
    bucket.x = 800 / 2 - 64 / 2;
    bucket.y = 20;
    bucket.width = 64;
    bucket.height = 64;


    //Array to hold the drops
    raindrops = new Array<Rectangle>();
    spawnRainDrop();

    //Initializing images
    dropImage = new Texture(Gdx.files.internal("droplet.png"));
    bucketImage = new Texture(Gdx.files.internal("bucket.png"));

    //Initializing sound
    dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
//Setting the background color
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();

    //creating batch for OpenGL
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();
    game.batch.draw(bucketImage, bucket.x, bucket.y);
    game.font.draw(game.batch, "Score : " + score, 700, 420);
    for (Rectangle raindrop : raindrops)
      game.batch.draw(dropImage, raindrop.x, raindrop.y);
    game.batch.end();


    //Listen for touch/mouse input
    if (Gdx.input.isTouched()) {
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);
      bucket.x = touchPos.x - 64 / 2;
    }

    //listen for left and A keyboard input
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
      bucket.x -= 200 * Gdx.graphics.getDeltaTime();

    //listen for right and B keyboard input
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.W))
      bucket.x += 200 * Gdx.graphics.getDeltaTime();

    //restricting bucket to go beyond gameplay area
    if (bucket.x < 0) bucket.x = 0;
    if (bucket.x > 800 - 64) bucket.x = 800 - 64;


    if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
      spawnRainDrop();

    Iterator<Rectangle> iterator = raindrops.iterator();
    while (iterator.hasNext()) {
      Rectangle raindrop = iterator.next();
      raindrop.y -= 200 * Gdx.graphics.getDeltaTime();

      if (raindrop.overlaps(bucket)) {
        dropSound.play();
        score++;
        iterator.remove();
      }

      if (raindrop.y + 64 < 0) iterator.remove();
    }
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  //Method to add random drop
  private void spawnRainDrop() {
    Rectangle raindrop = new Rectangle();
    raindrop.x = MathUtils.random(0, 800 - 64);
    raindrop.y = 480;
    raindrop.width = 64;
    raindrop.height = 64;
    raindrops.add(raindrop);
    lastDropTime = TimeUtils.nanoTime();
  }


  @Override
  public void dispose() {
    dropImage.dispose();
    bucketImage.dispose();
    dropSound.dispose();
  }
}

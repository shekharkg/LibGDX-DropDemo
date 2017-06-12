package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {

  //Render camera related
  private OrthographicCamera camera;
  private SpriteBatch batch;

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
  private Music rainMusic;

  @Override
  public void create() {

    //Initializing camera and setting dimensions
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    batch = new SpriteBatch();

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
    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

    //Set the rain music in loop and play
    rainMusic.setLooping(true);
    rainMusic.play();
  }

  @Override
  public void render() {
    //Setting the background color
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();

    //creating batch for GL
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    batch.draw(bucketImage, bucket.x, bucket.y);
    batch.end();


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
    if (bucket.x < 0)
      bucket.x = 0;
    if (bucket.x > 800 - 64)
      bucket.x = 800 - 64;


  }


  private void spawnRainDrop() {
    Rectangle raindrop = new Rectangle();
    raindrop.x = MathUtils.random(0, 800 - 64);
    raindrop.y = 480;
    raindrop.width = 64;
    raindrop.height = 64;
    raindrops.add(raindrop);
    lastDropTime = TimeUtils.nanoTime();
  }
}

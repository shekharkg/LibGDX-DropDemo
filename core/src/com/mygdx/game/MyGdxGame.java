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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {

  private OrthographicCamera camera;
  private SpriteBatch batch;

  private Rectangle bucket;

  private Vector3 touchPos;

  private Texture dropImage;
  private Texture bucketImage;
  private Sound dropSound;
  private Music rainMusic;

  @Override
  public void create() {

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    batch = new SpriteBatch();

    touchPos = new Vector3();

    bucket = new Rectangle();
    bucket.x = 800 / 2 - 64 / 2;
    bucket.y = 20;
    bucket.width = 64;
    bucket.height = 64;

    dropImage = new Texture(Gdx.files.internal("droplet.png"));
    bucketImage = new Texture(Gdx.files.internal("bucket.png"));

    dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

    rainMusic.setLooping(true);
    rainMusic.play();
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();

    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    batch.draw(bucketImage, bucket.x, bucket.y);
    batch.end();


    if (Gdx.input.isTouched()) {
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);
      bucket.x = touchPos.x - 64 / 2;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
      bucket.x -= 200 * Gdx.graphics.getDeltaTime();

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.W))
      bucket.x += 200 * Gdx.graphics.getDeltaTime();

    if (bucket.x < 0)
      bucket.x = 0;
    if (bucket.x > 800 - 64)
      bucket.x = 800 - 64;
  }
}

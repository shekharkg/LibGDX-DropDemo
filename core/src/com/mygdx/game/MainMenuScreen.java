package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


/**
 * Created by shekhar on 6/12/17.
 */

public class MainMenuScreen implements Screen {

  private final MyGdxGame game;
  private OrthographicCamera camera;

  private Texture playImage;
  private Texture dropImage;
  private Array<Rectangle> rainDrops;
  private long lastDropTime;

  MainMenuScreen(final MyGdxGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    playImage = new Texture(Gdx.files.internal("play.png"));
    dropImage = new Texture(Gdx.files.internal("droplet.png"));

    rainDrops = new Array<Rectangle>();
    spawnRainDrop();
  }


  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();

    game.batch.begin();
    for (Rectangle drop : rainDrops)
      game.batch.draw(dropImage, drop.x, drop.y);

    game.font.draw(game.batch, "Welcome to Drop !!!", camera.position.x - 60, camera.position.y + 100);
    game.batch.draw(playImage, camera.position.x - (playImage.getWidth() / 2),
        camera.position.y - (playImage.getHeight() / 2));
    game.font.draw(game.batch, "Tap to START", camera.position.x - 40, camera.position.y - 100);

    game.batch.end();

    if (Gdx.input.isTouched()) {
      game.setScreen(new GameScreen(game));
      dispose();
    }

    if (TimeUtils.nanoTime() - lastDropTime > 200000000)
      spawnRainDrop();


    Iterator<Rectangle> iterator = rainDrops.iterator();
    while (iterator.hasNext()) {
      Rectangle drop = iterator.next();
      drop.y -= 400 * Gdx.graphics.getDeltaTime();

      if (drop.y + 32 < 0) iterator.remove();
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

  @Override
  public void dispose() {
    playImage.dispose();
    dropImage.dispose();
  }

  private void spawnRainDrop() {
    Rectangle drop = new Rectangle();
    drop.x = MathUtils.random(0, 800 - 32);
    drop.y = 480;
    drop.height = 32;
    drop.width = 32;
    rainDrops.add(drop);
    lastDropTime = TimeUtils.nanoTime();
  }


}

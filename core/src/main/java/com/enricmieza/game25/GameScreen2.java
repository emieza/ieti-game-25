package com.enricmieza.game25;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

public class GameScreen2 implements Screen {

    final Main game;
    TextureRegion framesWalk[] = new TextureRegion[4];
    TextureRegion framesIdle[] = new TextureRegion[3];
    Animation<TextureRegion> walk, idle;
    Texture sheet = new Texture("yeti-spritesheet2.png");
    Texture background = new Texture("glacial-background.png");
    TextureRegion bgRegion;
    float stateTime = 0;
    float posx = 0; // posició en el mon (en píxels)
    float posy = 500; // offset pel background
    Rectangle up, down, left, right, fire;
    final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4;
    int state = IDLE;
    int lastLR = RIGHT;
    float vel = 0.7f;

    public class MyInputProcessor implements InputProcessor {

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            // Este método se llama cuando el usuario toca la pantalla
            Vector3 touchPos = new Vector3();
            touchPos.set(screenX, screenY, 0);
            // traducció de coordenades reals (depen del dispositiu) a 800x480
            game.viewport.getCamera().unproject(touchPos);
            if (up.contains(touchPos.x, touchPos.y)) {
                state = UP;
            } else if (down.contains(touchPos.x, touchPos.y)) {
                state = DOWN;
            } else if (left.contains(touchPos.x, touchPos.y)) {
                state = LEFT;
            } else if (right.contains(touchPos.x, touchPos.y)) {
                state = RIGHT;
            }
            return true; // Devuelve true si manejas el evento
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            // Este método se llama cuando el usuario levanta el dedo o el botón del mouse
            System.out.println("Touch up en: " + screenX + ", " + screenY);
            state = IDLE;
            return true; // Devuelve true si manejas el evento
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            state = IDLE;
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            // Este método se llama cuando el usuario arrastra el dedo o el mouse
            return true; // Devuelve true si manejas el evento
        }

        // Otros métodos de la interfaz InputProcessor
        @Override
        public boolean keyDown(int keycode) {
            if( keycode == Input.Keys.LEFT ) {
                state = LEFT;
            } else if( keycode == Input.Keys.RIGHT ) {
                state = RIGHT;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            state = IDLE;
            return true;
        }

        @Override
        public boolean keyTyped(char character) { return false; }

        @Override
        public boolean mouseMoved(int screenX, int screenY) { return false; }

        @Override
        public boolean scrolled(float amountX, float amountY) { return false; }
    }
    public GameScreen2(final Main game) {
        this.game = game;
        Gdx.input.setInputProcessor(new MyInputProcessor());

        // per cada frame cal indicar x,y,amplada,alçada
        framesWalk[0] = new TextureRegion(sheet,9,190,107,112);
        framesWalk[1] = new TextureRegion(sheet,124,190,113,112);
        framesWalk[2] = new TextureRegion(sheet,248,190,101,112);
        framesWalk[3] = new TextureRegion(sheet,364,190,122,112);
        walk = new Animation<TextureRegion>(0.25f,framesWalk);
        framesIdle[0] = new TextureRegion(sheet,9,30,107,112);
        framesIdle[1] = new TextureRegion(sheet,122,30,113,112);
        framesIdle[2] = new TextureRegion(sheet,236,30,101,112);
        idle = new Animation<TextureRegion>(0.33f,framesIdle);

        // facilities per calcular el "touch"
        float width = game.viewport.getWorldWidth();
        float height = game.viewport.getWorldHeight();
        up = new Rectangle(0, height*2/3, width, height/3);
        down = new Rectangle(0, 0, width, height/3);
        left = new Rectangle(0, 0, width/3, height);
        right = new Rectangle(width*2/3, 0, width/3, height);

        // bg
        //background = new Texture(Gdx.files.internal("background12.jpeg"));
        background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        bgRegion = new TextureRegion(background);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        stateTime += delta;

        // calcul background
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        bgRegion.setRegion( (int)posx, (int)posy, (int)worldWidth, (int)worldHeight );

        game.batch.begin();

        // primer pintem el background
        game.batch.draw(bgRegion,0,0);

        if( state==LEFT ) {
            TextureRegion frame = walk.getKeyFrame(stateTime,true);
            game.batch.draw(frame, 200, 100);
            lastLR = LEFT;
            posx -= vel;
        } else if ( state==RIGHT ){
            // si volem invertir el sentit, ho podem fer amb el paràmetre scaleX=-1
            TextureRegion frame = walk.getKeyFrame(stateTime,true);
            game.batch.draw(frame, 200, 100, 40, 0,
                frame.getRegionWidth(), frame.getRegionHeight(), -1, 1, 0);
            lastLR = RIGHT;
            posx += vel;
        } else {
            // idle
            TextureRegion frame = idle.getKeyFrame(stateTime,true);
            if ( lastLR==RIGHT ) {
                game.batch.draw(frame, 200, 100, 40, 0,
                    frame.getRegionWidth(), frame.getRegionHeight(), -1, 1, 0);
            } else {
                game.batch.draw(frame, 200, 100);
            }
        }
        game.batch.end();

        // input processor per sortir amb touchUp
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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

    }

}

package com.smeanox.games.aj4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class AJ4 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture dude, dudeparachute, dudedead, dudealive, plane, sky, ground;

	int awidth = Consts.WIDTH;
	int aheight = Consts.HEIGHT;

	// game vars
	float time;
	float planex, planey;
	float dudex, dudey, dudevy;
	boolean dudeFlying, dudeParachuted, dudeGround, dudeDead;
	float groundtimeout;

	@Override
	public void create () {
		batch = new SpriteBatch();
		dude = new Texture("dude.jpg");
		dudeparachute = new Texture("dudeparachute.jpg");
		dudedead = new Texture("dudedead.jpg");
		dudealive = new Texture("dudealive.jpg");
		plane = new Texture("plane.jpg");
		sky = new Texture("sky.jpg");
		ground = new Texture("ground.jpg");

		init();
	}

	private void init() {
		time = 0;
		planex = awidth + 10;
		planex = awidth - 100;
		planey = aheight * 0.9f;
		dudex = 0;
		dudey = 0;
		dudeFlying = false;
		dudeParachuted = false;
		dudeGround = false;
		dudeDead = false;
		groundtimeout = 0;
	}

	private void update(float delta) {
		time += delta;

		if (planex > -100) {
			planex -= delta * 64;
			planey = aheight * 0.9f + 10 * MathUtils.sin(time*2) + 3 * MathUtils.sin(time*3);
		}

		if (Math.abs(awidth * 0.5 - planex) < 5) {
			dudeFlying = true;
			dudevy = 0;
			dudex = planex;
			dudey = planey;
		}

		if (dudeFlying) {
			if (!dudeParachuted) {
				if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
					dudeParachuted = true;
				}
			}

			dudevy += delta * 98.1;
			if (dudeParachuted) {
				dudevy -= dudevy * 0.05f;
			}
			dudey -= dudevy * delta;

			if (dudey < aheight * 0.1) {
				dudeGround = true;
				dudeDead = dudevy > 42;
				dudevy = 0;
				dudeFlying = false;
				groundtimeout = 1;
			}
		}

		if (dudeGround) {
			groundtimeout -= delta;
			if (groundtimeout < 0) {
				if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
					init();
				}
			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(Gdx.graphics.getDeltaTime());

		batch.begin();
		batch.draw(sky, 0, 0);
		batch.draw(ground, 0, 0);
		batch.draw(plane, planex - 32, planey - 16, 64, 32);
		if (dudeFlying) {
			batch.draw(dudeParachuted ? dudeparachute : dude, dudex - 16, dudey - 16, 32, 32);
		}
		if (dudeGround) {
			batch.draw(dudeDead ? dudedead : dudealive, dudex - 16, dudey - 16, 32, 32);
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		float ratio = width / (float)height;
		awidth = (int) (Consts.HEIGHT * ratio);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		dude.dispose();
		dudeparachute.dispose();
		dudedead.dispose();
		dudealive.dispose();
		plane.dispose();
		sky.dispose();
		ground.dispose();
	}
}
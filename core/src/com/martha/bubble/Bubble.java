package com.martha.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.martha.bubble.listener.IAdController;
import com.martha.bubble.listener.IDeviceMetrics;
import com.martha.bubble.listener.IRate;
import com.martha.bubble.screen.GameScreen;

public class Bubble extends Game {

    public IDeviceMetrics deviceMetrics;
    public IAdController adController;
	public IRate iRate;

	// region Testing Variables.
	private int renderCount = 0;
	private int fpsCount = 0;
	// endregion


	public Bubble(IAdController adController, IDeviceMetrics deviceMetrics, IRate iRate) {
		super();
		this.adController = adController;
        this.deviceMetrics = deviceMetrics;
		this.iRate = iRate;
	}

    public Bubble() {
        super();
    }

    @Override
	public void create () {
		GLProfiler.enable();
        initialize();
        gameController.initialize();
        setScreen(new GameScreen(this));
	}
	@Override
	public void resume() {
		super.resume();
		if (adController.isConnectedToInternet()) adController.loadAndShowAD();

	}
	@Override
	public void pause() {
		super.pause();
		adController.hide();
	}
	@Override
	public void render () {
		super.render();
		this.tweenManager.update(Gdx.graphics.getDeltaTime() / 1.05f);
	}

	// region Testing Methods
	private void printGLInfo() {
		System.out.println("***********************");
		System.out.println("***********************");
		System.out.println("Texture Bindings: " + GLProfiler.textureBindings);
		System.out.println("_____________________");
		System.out.println("Draw Calls: " + GLProfiler.drawCalls);
		System.out.println("_____________________");
		System.out.println("***********************");
		System.out.println("***********************");
		GLProfiler.reset();
	}
	private void printFfps() {
		int curFps = Gdx.graphics.getFramesPerSecond();
		++renderCount;
		fpsCount += curFps;
		System.out.println("_____________________");
		System.out.println("Current FPS: " + curFps);
		System.out.println("_____________________");
		System.out.println("Avearage FPS: " + fpsCount / renderCount);
		System.out.println("_____________________");
		System.out.println();
	}
	// endregion.
}

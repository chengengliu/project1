package project1;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class World {
	private ArrayList<Sprite> sprites;
	
	public World() {
		sprites = Loader.loadSprites("res/levels/0.lvl");
	}
	
	public void update(Input input, int delta) {
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				sprite.update(input, delta);
			}
		}		
	}
	
	public void render(Graphics g) {
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				sprite.render(g);
			}
		}
	}
}

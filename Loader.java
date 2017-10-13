package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {
	private static String[][] types;

	private static int world_width;
	private static int world_height;
	private static int offset_x;
	private static int offset_y;
	
	/**
	 * Create the appropriate sprite given a name and location.
	 * @param name	the name of the sprite
	 * @param x		the x position
	 * @param y		the y position
	 * @return		the sprite object
	 */
	private static Sprite createSprite(String name, float x, float y) {
		switch (name) {
			case "wall":
				return new Wall(x, y);
			case "floor":
				return new Floor(x, y);
			case "stone":
				return new Stone(x, y);
			case "target":
				return new Target(x, y);
			case "player":
				return new Player(x, y);
		}
		return null;
	}

	// Converts a world coordinate to a tile coordinate,
	// and returns if that location is a blocked tile
	public static boolean isBlocked(float x, float y) {
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;

		// Rounding is important here
		x = Math.round(x);
		y = Math.round(y);

		// Do bounds checking!
		if (x >= 0 && x < world_width && y >= 0 && y < world_height) {
			return types[(int)x][(int)y].equals("wall");
		}
		// Default to blocked
		return true;
	}

	/**
	 * Loads the sprites from a given file.
	 * @param filename
	 * @return
	 */
	public static ArrayList<Sprite> loadSprites(String filename) {
		ArrayList<Sprite> list = new ArrayList<>();

		// Open the file
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;

			// Find the world size
			line = reader.readLine();
			String[] parts = line.split(",");
			world_width = Integer.parseInt(parts[0]);
			world_height = Integer.parseInt(parts[1]);

			// Create the array of object types
			types = new String[world_width][world_height];

			// Calculate the top left of the tiles so that the level is
			// centred
			offset_x = (App.SCREEN_WIDTH - world_width * App.TILE_SIZE) / 2;
			offset_y = (App.SCREEN_HEIGHT - world_height * App.TILE_SIZE) / 2;

			// Loop over every line of the file
			while ((line = reader.readLine()) != null) {
				String name;
				float x, y;

				// Split the line into parts
				parts = line.split(",");
				name = parts[0];
				x = Integer.parseInt(parts[1]);
				y = Integer.parseInt(parts[2]);
				types[(int)x][(int)y] = name;

				// Adjust for the grid
				x = offset_x + x * App.TILE_SIZE;
				y = offset_y + y * App.TILE_SIZE;

				// Create the sprite
				list.add(createSprite(name, x, y));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}

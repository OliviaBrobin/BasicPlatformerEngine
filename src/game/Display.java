package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.LinkedList;

import static java.lang.Integer.parseInt;

public class Display extends JFrame implements Runnable {

	//Editor properties
	static boolean editorMode = true;
	static final boolean STARTED_IN_EDITOR_MODE = editorMode;
	static String currentDirectory = "basicLevels";
	public static String currentLevelName = "0";

	//Editor selected blocks
	public static Block[][] selectedBlocksGrid = {{new Block(new String[] {"blockType-void"})}};

	//Current block
	public static Block selectedBlock = new Block(new String[] {"blockType-solid"});

	//Block select grid
	public static Block[][] blockSelectGrid;

	//Blocks for block select grid
	Block[][] basicBlocks = {
			{new Block(new String[] {"blockType-void"}), new Block(new String[] {"blockType-solid"})},
			{new Block(new String[] {"blockType-solid", "isDeath-true"})},
			{new Block(new String[]{"blockType-void", "playerSpawn-true"}), new Block(new String[] {"blockType-void", "isFinish-true"})}
	};

	Block[] textBlocks = {
			new Block(new String[] {"blockType-void", "gameText-This is text."})
	};

	//Blocks select screen
	static BasicScreen blockSelectScreen = new BasicScreen();

	//Visual editor properties
	public static final int EDITOR_GRID_HEIGHT = (int) Math.floor(540 * 1.2);
	public static final int EDITOR_GRID_WIDTH = (int) Math.floor(960 * 1.2);
	static boolean printEditorText = true;
	static boolean levelHasGridOutline = true;

	//Game characteristics
	public static final int GAME_SPEED = 4;
	public static boolean gamePaused = false;

	//Visual Game properties
	public static String EDITOR_BACKGROUND_COLOR = "editorBackground";
	public static String GAME_BACKGROUND_COLOR = "gameBackground";

	//Level Properties
	public static int levelNumber = 0;

	//Level grid
	public static Block[][] levelGrid = null;

	//Level screen
	static LevelScreen levelScreen = new LevelScreen();

	//Visual Level Properties
	public static int levelScreenHeight;
	public static int levelScreenWidth;


	//Player
	public static Player player = new Player();

	//Screen constants
	public static final int TOP_BUFFER = 30;
	public static final int LEFT_BUFFER = 7;

	//Blocks grid visuals
	public static final int BLOCKS_SELECT_SCREEN_SCALING_FACTOR = 50;
	public static int BLOCK_SELECT_SCREEN_LEFT_BUFFER = LEFT_BUFFER + levelScreenWidth + 50;

	//Mouse Variables
	public Mouse mouse = new Mouse();
	public static int previousLevelGridMouseX = 0;
	public static int previousLevelGridMouseY = 0;

	//Images variables
	private Image dbImage;
	private Graphics dbg;

	static Block[] backgroundImageBlocks;
	Block[] midgroundImageBlocks;
	Block[] foregroundImageBlocks;

	//Buttons
	static LinkedList<Button> buttons = new LinkedList<>();

	static Button levelNameButton = new Button();
	static Button currentDirectoryButton = new Button();
	static Button addPropertyButton = new Button();

	static LinkedList<Button> removePropertyButtons = new LinkedList<>();

	static String propertyToAdd = "";

	//Constructor
	public Display(){

		ImageFunctions.loadImageBlocksArrays();


		buttons.add(levelNameButton);
		buttons.add(currentDirectoryButton);
		buttons.add(addPropertyButton);


		LevelFunctions.openCurrentLevel();


		//Title above screen
		setTitle("Basic Game engine");

		//How many pixels by how many
		setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50);

		//If it can be resized
		setResizable(true);

		//If you can see it
		setVisible(true);

		//What will happen when the screen is closed. Right now it is to be exited
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Listeners
		addKeyListener(new keyInput());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	//Double buffering function
	public void paint(Graphics g){
		dbImage=createImage(getWidth(), getHeight());
		dbg=dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	};

	//Paint graphics area
	public void paintComponent(Graphics g){
		//For testing, we can change these values to make sure that everything is being cut off right.
		int secondLeftBuffer;
		int secondTopBuffer;

		if (editorMode) {
			secondLeftBuffer = 50;
			secondTopBuffer = 50;

			setBackground(MyColor.getColor(EDITOR_BACKGROUND_COLOR));

			levelScreenWidth = EDITOR_GRID_WIDTH;
			levelScreenHeight = EDITOR_GRID_HEIGHT;
		} else {
			secondLeftBuffer = 0;
			secondTopBuffer = 0;

			setBackground(MyColor.getColor(GAME_BACKGROUND_COLOR));

			levelScreenWidth = getWidth() - LEFT_BUFFER;
			levelScreenHeight = getHeight() - TOP_BUFFER;
		}

		levelScreen = new LevelScreen(g, levelGrid, LEFT_BUFFER + secondLeftBuffer, TOP_BUFFER + secondTopBuffer, levelScreenWidth, levelScreenHeight);

		//levelScreen.scrollHorizontallyRigid();
		levelScreen.useGridHeight();
		//levelScreen.scrollHorizontally();

		levelScreen.display();

		if (editorMode) {

			EditorStatsScreen editorStatsScreen = new EditorStatsScreen((int) (levelScreen.getX() + levelScreen.getWidth() + 50), (int) levelScreen.getY(), g);

			editorStatsScreen.display();

			LinkedList<Block[]> blockSelectBlocks = new LinkedList<>();

			//Add basic blocks to blocksSelectBlocks
			for (Block[] basicBlock : basicBlocks) {
				blockSelectBlocks.add(basicBlock);
			}

			blockSelectBlocks.add(backgroundImageBlocks);
			blockSelectBlocks.add(textBlocks);


			//Convert blockSelectBlocks to nested array.
			blockSelectGrid = new Block[blockSelectBlocks.size()][];

			for (int x = 0; x < blockSelectGrid.length; x++) {
				blockSelectGrid[x] = blockSelectBlocks.get(x);
			}

			//Display block select screen
			blockSelectScreen = new BasicScreen(g, blockSelectGrid, editorStatsScreen.x, editorStatsScreen.getBottom(), BLOCKS_SELECT_SCREEN_SCALING_FACTOR);

			blockSelectScreen.display();

			//Display selectedBlocksScreen
			BasicScreen selectedBlocksScreen = new BasicScreen(g, selectedBlocksGrid, (int) levelScreen.getX(), (int) levelScreen.getY() + (int) levelScreen.getHeight() + 50, (int) levelScreen.getScalingFactor());

			selectedBlocksScreen.display();
		}

		if(gamePaused){
			//Tint level screen
			g.setColor(MyColor.getColor("pauseTint"));
			g.fillRect((int) levelScreen.getX(),(int) levelScreen.getY(),(int) levelScreen.getWidth(),(int) levelScreen.getHeight());
		}

		repaint();
	}

	public class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			MouseFunctions.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e){

		}

		@Override
		public void mouseMoved(MouseEvent e){ MouseFunctions.mouseHovered(e); }

	}

	public class keyInput extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			int keyCode = e.getKeyCode();

			TypingFunctions.keyPressActions(keyCode);
		}

		@Override
		public void keyReleased(KeyEvent e){
			int keyCode = e.getKeyCode();

			TypingFunctions.keyReleaseActions(keyCode);
		}
	}

	public static void main(String[] args) {
		new Thread(new Display()).start();
	}

	@Override
	public void run() {
		while(true) {
			if(!gamePaused) {
				if (player.exists()) {

					player.move();



					//Next level starts if player intersects finish
					if (player.intersects("isFinish", "true")) {
						if (editorMode) {
							LevelFunctions.saveCurrentLevel();
						}

						LevelFunctions.importNextLevel();
					}
				}
			}
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
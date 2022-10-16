package game;

import java.util.LinkedList;

import static game.Display.*;

public class Player{
	//If we change the size of the player, we have to modify fall height and horizontal and vertical speeds and jump height and horizontal distance to jump height. Player could maybe be slightly bigger?

	//We need to make it so that the calling of the isCloseTo or isTouching functions of the jump method change their direction depending on the direction of gravity.
	//We also need to make there be isOnTopOf, isToRightOf, isToLeftOf, and isOnBottomOf. Possibly generalize these by putting these into functions. We could call functions like isTouchingBlockInDirectionOfGravity() and jumpInDirectionOfGravity. Also, fix isOnTopOfSolid.
	//We need to change everything to refer to direction of gravity. isMovingLeft should be isMovingLeftOfGravity and isMovingUp should be isMovingAgainstDirectionOfGravity, etc.

	//Wall jump blocks are really cool, we may want to implement that as well. It might be super simple since the only difference between them and sticky blocks is that sticky blocks prevent falling due to gravity.
	//We could maje it so that whether STICKY, GRAVITY_CHANGES, NORMAL_MODE, GRAPPLE, or DOUBLE_JUMPING are true is dependent on whether or not the player is in the corresponding field. This may be a waste of time though since it's not the final functionality that we're looking for.
	//PLayer should die if they go out of bounds.
	//What occurs if player tries to grapple out of the screen. Do they die or does nothing occur. Death seems more responsive but more intense and unexpected.

	private boolean startJump = false;

	boolean hasJumpedTwice = false;

	private boolean exists = true;

	private boolean isMovingLeft = false;
	private boolean isMovingRight = false;
	private boolean isMovingUp = false;
	private boolean isMovingDown = false;

	private final boolean STICKY = false;
	private final boolean GRAVITY_CHANGES = false;
	//We may want to make it so that when the player changes the direction of gravity, gravity is super fast until the player hits something. If this is more like a grappling hook, then gravity also returns to normal. We can, obviously, do both.
	//TODO Fix sticky jump
	//Re-organize functions. We would likely benefit from making a second class to store the helper functions.
	private final boolean NORMAL_MODE = true;
	private final boolean DYNAMIC_JUMPING = false;
	private final boolean GRAPPLE = false;
	private final boolean DOUBLE_JUMPING = false;

	boolean swapGravity = false;

	boolean gravityDown = true;

	boolean grappleLeft = false;
	boolean grappleRight = false;
	boolean grappleUp = false;
	boolean grappleDown = false;

	private double x;
	private double y;

	private double currentYVelocity = 0;

	static private double width = .57;
	static private double height = .57;

	static private double MAX_HORIZONTAL_SPEED = .025;
	static private double MAX_VERTICAL_SPEED = .025;

	static private double horizontalSpeed = 0;
	static private double verticalSpeed = 0;

	static private double EDITOR_VERTICAL_SPEED = .015;

	static private String color = "player";

	static public boolean holdingJump = false;
	static public int holdingJumpTime = -1;
	boolean startedDragCountdown = false;
	boolean dragStarted = false;
	int dragCountdown = -1;

	boolean canSwapGravity = false;

	static private double jumpHeight = 3.08;
	static private double horizontalDistanceToJumpHeight = 2.84;


	static private final double GRAVITY = (-2 * jumpHeight) * Math.pow(MAX_HORIZONTAL_SPEED, 2) / Math.pow(horizontalDistanceToJumpHeight, 2);

	/*
	static private double width = .55;
	static private double height = .55;

	static private double MAX_HORIZONTAL_SPEED = .025;
	static private double MAX_VERTICAL_SPEED = .025;

	static private double horizontalSpeed = 0;
	static private double verticalSpeed = .015;

	static private String color = "player";

	static public boolean holdingJump = false;
	static public int holdingJumpTime = -1;
	boolean startedDragCountdown = false;
	boolean dragStarted = false;
	int dragCountdown = -1;

	static private double jumpHeight = 3.05;
	static private double horizontalDistanceToJumpHeight = 3;
	 */

	/*
	static private double jumpHeight = 3.00;
	static private double horizontalDistanceToJumpHeight = 3;
	 */

	/*
	static private double horizontalSpeed = .035;
	private double jumpHeight = 4;
	private double horizontalDistanceToJumpHeight = 3;
	 */


	public Player()
	{
		this.x = 0;
		this.y = 0;
	}

	public Player(Player player) {
		this.x = player.x;
		this.y = player.y;
	}

	public void move() {
		moveLeftAndRight();

		if (NORMAL_MODE) {
			jumpNormal();
		}

		//Player resets if touches death block
		if (player.touches("isDeath","true") || player.entersBottomOfScreen()) {
			player.resetPosition();
		}

		//Bounce on bouncy blocks
		if (player.touches("isBouncy","true")) {
			player.jumpUpwards();
		}
	}

	public void moveLeftAndRight()
	{
		//Change horizontal speed if player is trying to move left and right.
		if (isMovingLeft) {
			if(horizontalSpeed > MAX_HORIZONTAL_SPEED * -1) {
				horizontalSpeed -= MAX_HORIZONTAL_SPEED / 24;
			}
		}
		if (isMovingRight) {
			if(horizontalSpeed < MAX_HORIZONTAL_SPEED) {
				horizontalSpeed += MAX_HORIZONTAL_SPEED / 24;
			}
		}

		//If player is not moving right or holding both the A and the D key reduce the horizontal speed to zero.
		if((!isMovingRight && !isMovingLeft) || (isMovingLeft && isMovingRight))
		{
			if(horizontalSpeed > 0)
			{
				horizontalSpeed -= MAX_HORIZONTAL_SPEED / 24;
			}
			if(horizontalSpeed < 0)
			{
				horizontalSpeed += MAX_HORIZONTAL_SPEED / 24;
			}

			if(horizontalSpeed > 0)
			{
				horizontalSpeed -= MAX_HORIZONTAL_SPEED / 24;
			}
			if(horizontalSpeed < 0)
			{
				horizontalSpeed += MAX_HORIZONTAL_SPEED / 24;
			}

			if(horizontalSpeed < MAX_HORIZONTAL_SPEED / 24 || horizontalSpeed > -1 * MAX_HORIZONTAL_SPEED / 24)
			{
				horizontalSpeed = 0;
			}
		}

		//Move left or move right depending on whether horizontal speed is positive or negative.
		if(horizontalSpeed > 0)
		{
			moveRight(horizontalSpeed);
		}

		if(horizontalSpeed < 0)
		{
			moveLeft(horizontalSpeed * -1);
		}
	}

	public void moveLeft(double distance) {
		Player clone = new Player(this);
		clone.setX(x - distance);
		if (clone.hasValidPosition()) {
			x -= distance;
		}
		else
		{
			x = Math.floor(x + .0001);
		}
	}

	public void moveRight(double distance) {
		Player clone = new Player(this);
		clone.setX(x + distance);
		if (clone.hasValidPosition()) {
			x += distance;
		}
		else
		{
			x = Math.floor(x) + 1 - width;
		}
	}

	public void moveUp(double distance) {
		Player clone = new Player(this);
		clone.setY(y - distance);
		if (clone.hasValidPosition()) {
			y -= distance;
		}
		else
		{
			y = Math.floor(y + .0001);
		}
	}

	public void moveDown(double distance) {
		Player clone = new Player(this);
		clone.setY(y + distance);
		if (clone.hasValidPosition()) {
			y += distance;
		}
		else
		{
			y = Math.floor(y) + 1 - height;
		}
	}

	public boolean canMoveLeft(double distance) {
		Player clone = new Player(this);
		clone.setX(x - distance);
		if (clone.hasValidPosition()) {
			return  true;
		}
		else
		{
			return false;
		}
	}

	public boolean canMoveRight(double distance) {
		Player clone = new Player(this);
		clone.setX(x + distance);
		if (clone.hasValidPosition()) {
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean canMoveUp(double distance) {
		Player clone = new Player(this);
		clone.setY(y - distance);
		if (clone.hasValidPosition()) {
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean canMoveDown(double distance) {
		Player clone = new Player(this);
		clone.setY(y + distance);
		if (clone.hasValidPosition()) {
			return true;
		}
		else
		{
			return false;
		}
	}

	public void jumpNormal()
	{
		//Player starts jumping up
		if(startJump)
		{
			//Set YVelocity to a positive integer so that player starts going up
			currentYVelocity = 2 * jumpHeight * MAX_HORIZONTAL_SPEED / horizontalDistanceToJumpHeight;

			//Update other variables
			startJump = false;
		}

		//Player continues moving up
		if(currentYVelocity > 0)
		{
			moveUp(currentYVelocity);
		}

		//Gravity changes player vertical speed by negative increments.
		currentYVelocity += GRAVITY;


		//After apex, drag stops, player moves down at a constant speed.
		if(currentYVelocity < 0)
		{
			if (currentYVelocity * -1 < MAX_VERTICAL_SPEED) {
				moveDown(currentYVelocity * -1);
			} else {
				moveDown(MAX_VERTICAL_SPEED);
			}

		}

		//If player touches ground, velocity is set to 0.
		if(isOnTopOfSolid()) {
			currentYVelocity = 0;
		}
	}

	public void jumpDynamically()
	{
		//Player starts jumping up
		if(startJump)
		{
			//Set YVelocity to a positive integer so that player starts going up
			currentYVelocity = 2 * jumpHeight * MAX_HORIZONTAL_SPEED / horizontalDistanceToJumpHeight;

			//Update other variables
			startedDragCountdown = false;
			startJump = false;
			dragStarted = false;
			player.holdingJump = true;
			player.holdingJumpTime = 0;
		}

		//Player continues moving up
		if(currentYVelocity > 0)
		{
			if(!gravityDown)
			{
				if (currentYVelocity < MAX_VERTICAL_SPEED) {
					moveUp(currentYVelocity);
				} else {
					moveUp(MAX_VERTICAL_SPEED);
				}
			}
			else
			{
				moveUp(currentYVelocity);
			}
		}

		//Gravity pulls player down
		if(gravityDown) {
			currentYVelocity += GRAVITY;
		}
		else
		{
			currentYVelocity -= GRAVITY;
		}

		//Player continues holding jump
		if(holdingJump)
		{
			holdingJumpTime++;
		}

		//Player stops holding jump
		if(!holdingJump && !startedDragCountdown)
		{
			holdingJump = false;
			startedDragCountdown = true;
			//TODO We need to make dragCountdown and holdingJumpTime's relationships more complex. If holdingJumpTime is super small, dragCountdown should be super small. If holdingJumpTime is medium, dragCountdown might as well might be maxed. This might be an exponential curve.
			//Drag countdown is determined using holdingJumpTime
			dragCountdown = ((holdingJumpTime + 1) / 2);
		}

		//Drag countdown starts
		if(startedDragCountdown) {
			dragCountdown--;
		}

		//Drag countdown ends, drag starts
		if(dragCountdown == 0)
		{
			dragStarted = true;
		}

		//Drag pulls player down more
		if(dragStarted)
		{
			currentYVelocity += GRAVITY * 4;
		}

		//After apex, drag stops, player moves down at a constant speed.
		if(currentYVelocity < 0)
		{
			holdingJump = false;
			dragStarted = false;
			dragCountdown = -1;

			if (currentYVelocity * -1 < MAX_VERTICAL_SPEED) {
				moveDown(currentYVelocity * -1);
			} else {
				moveDown(MAX_VERTICAL_SPEED);
			}
		}

		//If player touches ground, velocity resets
		if(isOnTopOfSolid()) {
			currentYVelocity = 0;
			dragCountdown = -1;
			holdingJump = false;
			dragStarted = false;
		}
	}

	public boolean resetPosition() {

		currentYVelocity = 0;

		for(int x1 = 0; x1 < Display.levelGrid.length ; x1++)
		{
			for(int y1 = 0; y1 < Display.levelGrid[x1].length ; y1++)
			{
				boolean gridHasPlayerBlock = false;
				try {
					gridHasPlayerBlock = Display.levelGrid[x1][y1].hasProperty("playerSpawn", "true");
				}
				catch (ArrayIndexOutOfBoundsException e)
				{

				}

				if(gridHasPlayerBlock)
				{
					x = x1 + (1 - width) / 2;
					y = y1 + 1 - height;
					return true;
				}
			}
		}
		x = 0;
		y = 0;


		return false;
	}
	public boolean hasValidPosition() {
		if(outOfBounds()) {
			return false;
		}
		if(intersects("blockType", "solid")) {
			return false;
		}

		return true;
	}

	public boolean outOfBounds() {
		if(x < 0) {
			return true;
		}
		if(y < 0){
			return true;
		}
		if(x + width > Display.levelGrid.length) {
			return true;
		}
		if(y + height > Display.levelGrid[0].length) {
			return true;
		}
		return false;
	}

	public boolean entersBottomOfScreen()
	{
		if((int) Math.floor(y + height) >= Display.levelGrid[0].length)
		{
			return true;
		}
		return false;
	}

	public boolean intersects(String propertyName, String propertyValue)
	{
		//Player cannot be bigger than blocks for this code to work properly.
		for(Block intersectingBlock : getIntersectingBlocks())
		{
			if(intersectingBlock.hasProperty(propertyName,propertyValue)) {
					return true;
			}
		}
		return false;
	}

	public boolean touches(String propertyName, String propertyValue)
	{
		//Player cannot be bigger than blocks for this code to work properly.
		for(Block touchingBlock : getTouchingBlocks())
		{
			if(touchingBlock.hasProperty(propertyName,propertyValue)) {
				return true;
			}
		}
		return false;
	}

	public boolean isCloseToSideOf(String propertyName, String propertyValue)
	{
		//Player cannot be bigger than blocks for this code to work properly.
		for(Block closeToSideOfBlock : getCloseToSideOfBlocks())
		{
			if(closeToSideOfBlock.hasProperty(propertyName,propertyValue)) {
				return true;
			}
		}
		return false;
	}

	public boolean isCloseToLeftSideOf(String propertyName, String propertyValue)
	{
		//Player cannot be bigger than blocks for this code to work properly.
		for(Block closeToLeftSideOfBlock : getCloseToLeftSideOfBlocks())
		{
			if(closeToLeftSideOfBlock.hasProperty(propertyName,propertyValue)) {
				return true;
			}
		}
		return false;
	}

	public boolean isCloseToRightSideOf(String propertyName, String propertyValue)
	{
		//Player cannot be bigger than blocks for this code to work properly.
		for(Block closeToRightSideOfBlock : getCloseToRightSideOfBlocks())
		{
			if(closeToRightSideOfBlock.hasProperty(propertyName,propertyValue)) {
				return true;
			}
		}
		return false;
	}

	public boolean isBelow(String propertyName, String propertyValue)
	{
		//Player cannot be bigger than blocks for this code to work properly.
		for(Block aboveBlock : getAboveBlocks())
		{
			if(aboveBlock.hasProperty(propertyName,propertyValue)) {
				return true;
			}
		}
		return false;
	}

	public LinkedList<Block> getIntersectingBlocks()
	{
		LinkedList<Block> intersectingBlocks = new LinkedList<>();

		try {
			intersectingBlocks.add(Display.levelGrid[(int) Math.floor(x + .0001)][(int) Math.floor(y + .0001)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			intersectingBlocks.add(Display.levelGrid[(int) Math.floor(x + width - .0001)][(int) Math.floor(y + .0001)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			intersectingBlocks.add(Display.levelGrid[(int) Math.floor(x + .0001)][(int) Math.floor(y + height - .0001)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			intersectingBlocks.add(Display.levelGrid[(int) Math.floor(x + width - .0001)][(int) Math.floor(y + height - .0001)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		return intersectingBlocks;
	}

	public LinkedList<Block> getTouchingBlocks()
	{
		LinkedList<Block> touchingBlocks = new LinkedList<>();

		try {
			touchingBlocks.add(Display.levelGrid[(int) Math.floor(x-.000001)][(int)Math.floor(y)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			touchingBlocks.add(Display.levelGrid[(int) Math.floor(x + width)][(int)Math.floor(y)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			touchingBlocks.add(Display.levelGrid[(int) Math.floor(x-.000001)][(int)Math.floor(y + height)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			touchingBlocks.add(Display.levelGrid[(int) Math.floor(x + width)][(int)Math.floor(y + height)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		return touchingBlocks;
	}

	public LinkedList<Block> getCloseToSideOfBlocks()
	{
		LinkedList<Block> isCloseToSideOfBlocks = new LinkedList<>();

		double closenessConstant = .35;

		try {
			isCloseToSideOfBlocks.add(Display.levelGrid[(int) Math.floor(x - closenessConstant)][(int)Math.floor(y)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			isCloseToSideOfBlocks.add(Display.levelGrid[(int) Math.floor(x + width + closenessConstant)][(int)Math.floor(y)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			isCloseToSideOfBlocks.add(Display.levelGrid[(int) Math.floor(x - closenessConstant)][(int)Math.floor(y + height)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			isCloseToSideOfBlocks.add(Display.levelGrid[(int) Math.floor(x + width + closenessConstant)][(int)Math.floor(y + height)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		isCloseToSideOfBlocks.addAll(getTouchingBlocks());

		return isCloseToSideOfBlocks;
	}

	public LinkedList<Block> getCloseToLeftSideOfBlocks()
	{
		LinkedList<Block> isCloseToLeftSideOfBlocks = new LinkedList<>();

		double closenessConstant = .35;

		try {
			isCloseToLeftSideOfBlocks.add(Display.levelGrid[(int) Math.floor(x - closenessConstant)][(int)Math.floor(y)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			isCloseToLeftSideOfBlocks.add(Display.levelGrid[(int) Math.floor(x - closenessConstant)][(int)Math.floor(y + height)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		isCloseToLeftSideOfBlocks.addAll(getTouchingBlocks());

		return isCloseToLeftSideOfBlocks;
	}

	public LinkedList<Block> getCloseToRightSideOfBlocks()
	{
		LinkedList<Block> isCloseToRightSideOfBlocks = new LinkedList<>();

		double closenessConstant = .35;

		try {
			isCloseToRightSideOfBlocks.add(Display.levelGrid[(int) Math.floor(x + width + closenessConstant)][(int)Math.floor(y)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			isCloseToRightSideOfBlocks.add(Display.levelGrid[(int) Math.floor(x + width + closenessConstant)][(int)Math.floor(y + height)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		isCloseToRightSideOfBlocks.addAll(getTouchingBlocks());

		return isCloseToRightSideOfBlocks;
	}

	public LinkedList<Block> getAboveBlocks()
	{
		LinkedList<Block> aboveBlocks = new LinkedList<>();

		double closenessConstant = .05;

		try {
			aboveBlocks.add(Display.levelGrid[(int) Math.floor(x)][(int)Math.floor(y - closenessConstant)]);
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			aboveBlocks.add(Display.levelGrid[(int) Math.floor(x + width)][(int)Math.floor(y - closenessConstant)]);
		} catch (ArrayIndexOutOfBoundsException e) {}


		aboveBlocks.addAll(getTouchingBlocks());

		return aboveBlocks;
	}

	public void startJump() {
		if (isOnTopOfSolid()) {
			startJump = true;
		}
		else if(isBelowSolid())
		{
			currentYVelocity = -1 * 2 * jumpHeight * MAX_HORIZONTAL_SPEED / horizontalDistanceToJumpHeight;
		}
		else if(isCloseToLeftSideOf("isSticky", "true") || (intersects("playerSticky", "true") && isCloseToLeftSideOf("blockType", "solid")))
		{
			//Leap left.
			//horizontalSpeed = -1 * MAX_HORIZONTAL_SPEED * 1.5;
			jumpUpwards();
		}
		else if(isCloseToRightSideOf("isSticky", "true") || (intersects("playerSticky", "true") && isCloseToRightSideOf("blockType", "solid")))
		{
			//Leap right.
			//horizontalSpeed = MAX_HORIZONTAL_SPEED * 1.5;
			jumpUpwards();
		}
		else if(intersects("playerDoubleJump", "true") && !hasJumpedTwice)
		{
			jumpUpwards();
			hasJumpedTwice = true;
		}

		if(isBelow("isSticky", "true") || (intersects("playerSticky", "true") && isBelow("blockType", "solid")))
		{
			moveDown(.051);
			currentYVelocity = -1 * 2 * jumpHeight * MAX_HORIZONTAL_SPEED / horizontalDistanceToJumpHeight;
		}
	}

	public boolean isOnTopOfSolid()
	{
		//TODO We can probably make this a much simpler function? We only have to check if the player is touching two blocks below them so there could instead be a touchingBelow function. This may be a waste of time to fix though.
		if((!player.canMoveDown(.001))) {
			try {
				Block someBlock1 = levelGrid[(int) Math.floor(player.getX() + 1)][(int) Math.floor(player.getY()) + 1];
				Block someBlock2 = levelGrid[(int) Math.floor(player.getX())][(int) Math.floor(player.getY()) + 1];
				if (someBlock1.hasProperty("blockType", "solid") || someBlock2.hasProperty("blockType", "solid")) {
					return true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}
		return  false;
	}

	public boolean isBelowSolid()
	{
		//TODO We can probably make this a much simpler function? We only have to check if the player is touching two blocks below them so there could instead be a touchingBelow function. This may be a waste of time to fix though.
		/*
		if(player.isBelow("blockType", "solid"))
		{
			return true;
		}
		 */

		if((!player.canMoveUp(.001))) {
			try {
				Block someBlock1 = levelGrid[(int) Math.floor(player.getX() + 1)][(int) Math.floor(player.getY()) - 1];
				Block someBlock2 = levelGrid[(int) Math.floor(player.getX())][(int) Math.floor(player.getY()) - 1];
				if (someBlock1.hasProperty("blockType", "solid") || someBlock2.hasProperty("blockType", "solid")) {
					return true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}
		return  false;
	}

	public void jumpUpwards() {
		this.startJump = true;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public static double getWidth() {
		return width;
	}

	public static double getHeight() {
		return height;
	}

	public void setMovingLeft(boolean movingLeft) {
		isMovingLeft = movingLeft;
	}

	public void setMovingRight(boolean movingRight) {
		isMovingRight = movingRight;
	}

	public void setMovingUp(boolean movingUp) {
		isMovingUp = movingUp;
	}

	public void setMovingDown(boolean movingDown) {
		isMovingDown = movingDown;
	}

	public boolean isMovingLeft() {
		return isMovingLeft;
	}

	public boolean isMovingRight() {
		return isMovingRight;
	}

	public boolean isMovingUp() {
		return isMovingUp;
	}

	public boolean isMovingDown() {
		return isMovingDown;
	}

	public void setGrappleLeft(boolean grappleLeft) {
		this.grappleLeft = grappleLeft;
	}

	public void setGrappleRight(boolean grappleRight) {
		this.grappleRight = grappleRight;
	}

	public void setGrappleUp(boolean grappleUp) {
		this.grappleUp = grappleUp;
	}

	public void setGrappleDown(boolean grappleDown) {
		this.grappleDown = grappleDown;
	}

	public boolean exists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public String getColor()
	{
		return color;
	}
}

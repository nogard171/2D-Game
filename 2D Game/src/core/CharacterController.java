package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import game.Data;
import utils.FPSCounter;

public class CharacterController {
	private Entity character;
	private EntityAnimator animator;
	float speed = 1;

	public void setup() {
		character = new Entity();
		character.setPosition(new Vector2f(100, 100));

		animator = new EntityAnimator();

	}

	Vector2f velocity = new Vector2f(0, 0);

	public void update() {
		if (character != null) {
			velocity = new Vector2f(0, 0);
			float velocitySpeed = 1;

			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				velocitySpeed = 2;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				velocity.x = -velocitySpeed;
				animator.setFrameY(3);
				character.setWalking(true);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				velocity.x = velocitySpeed;
				animator.setFrameY(2);
				character.setWalking(true);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				velocity.y = -velocitySpeed;
				animator.setFrameY(1);
				character.setWalking(true);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				velocity.y = velocitySpeed;
				animator.setFrameY(0);
				character.setWalking(true);
			}
			if (!Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)
					&& !Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) {
				character.setWalking(false);
			}
			if (velocity != null) {
				move(velocity);
			}

			if (character.isWalking()) {
				animator.cycle();
			} else {
				animator.setFrameX(0);
			}
		}
	}

	public void move(Vector2f velocity) {
		if (character != null) {
			float newSpeed = FPSCounter.getDelta() * speed / 10;
			velocity.x = velocity.x * newSpeed;
			velocity.y = velocity.y * newSpeed;
			System.out.println(newSpeed);
			character.setPosition(new Vector2f(character.getPosition().getX() + velocity.getX(),
					character.getPosition().getY() + velocity.getY()));
		}
	}

	public void render() {
		if (character != null) {
			SpriteSheet sheet = Data.spriteSheets.get(character.getSpriteSheet());
			if (sheet != null) {
				Texture tex = sheet.getTexture();
				if (tex != null) {
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
					SpriteFrame frame = sheet.getFrame(animator.getFrameX(), animator.getFrameY());
					if (frame != null) {

						GL11.glPushMatrix();
						GL11.glTranslatef(character.getPosition().getX(), character.getPosition().getY(), 0);
						GL11.glCallList(frame.getID());
						GL11.glPopMatrix();
					}
				}
			}
		}
	}
}

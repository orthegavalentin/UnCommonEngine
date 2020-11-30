package components;

import Renderer.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private Texture texture;
    private List<Sprite> sprites;

    public SpriteSheet(Texture texture, int spriteWidth, int spriteHeight, int numSprites, float spacing) {
        this.sprites = new ArrayList<>();
        this.texture = texture;
        float currentX = 0;
        float currentY = texture.getHeight() - spriteHeight;
        for (int i = 0; i < numSprites; i++) {

            float topY = (currentY + spriteHeight) / (float) texture.getHeight();
            float bottomY = currentY / (float) texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float) texture.getWidth();
            float leftX = currentX / (float) texture.getWidth();
            Vector2f[] texCoords = {
                    new Vector2f(rightX, bottomY),
                    new Vector2f(rightX, topY),
                    new Vector2f(leftX, topY),
                    new Vector2f(leftX, bottomY)};
            Sprite sprite = new Sprite();
            sprite.setTexture(this.texture);
            sprite.setTexCoords(texCoords);
            this.sprites.add(sprite);

            currentX += spriteWidth + spacing;
            //on parcours le sprite sheet de gauche à droite et de bas en haut
            if (currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;

            }


        }


    }

    public Sprite getSprite(int index){
        return this.sprites.get(index);
    }

}

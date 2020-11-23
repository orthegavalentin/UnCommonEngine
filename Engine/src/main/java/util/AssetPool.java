package util;

import Renderer.Shader;
import Renderer.Texture;
import components.SpriteSheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String , Shader> shaders=new HashMap<>();
    private static Map<String , Texture> textures=new HashMap<>();
    private static Map<String , SpriteSheet> spriteSheets=new HashMap<>();

    public static Shader getShader(String resourceName){
        File file= new File(resourceName);
        if(shaders.containsKey(file.getAbsolutePath())){
            return shaders.get(file.getAbsolutePath());
        }else{
            Shader shader=new Shader(resourceName);
            shader.compileAndLink();
            AssetPool.shaders.put(file.getAbsolutePath(),shader);
            return shader;


        }

    }
    public static Texture getTexture(String resourceName){
        File file= new File(resourceName);
        if(AssetPool.textures.containsKey(file.getAbsolutePath())){
            return AssetPool.textures.get(file.getAbsolutePath());
        }else{
            Texture texture=new Texture(resourceName);

            AssetPool.textures.put(file.getAbsolutePath(),texture);

            return texture;


        }

    }

    public static void addSpriteSheet(String resourceName, SpriteSheet spriteSheet){
     File file =new File(resourceName);
     if(!AssetPool.spriteSheets.containsKey((file.getAbsolutePath()))){
         AssetPool.spriteSheets.put(file.getAbsolutePath(), spriteSheet);

     }



    }
    public static SpriteSheet getSpriteSheet(String resourceName){
        File file =new File(resourceName);
        if(!AssetPool.spriteSheets.containsKey((file.getAbsolutePath()))){
            assert false :"Error : tried to access spritesheet " +resourceName+" and it has not been added to asset pool";


        }
        return AssetPool.spriteSheets.getOrDefault(file.getAbsolutePath(),null);



    }


}

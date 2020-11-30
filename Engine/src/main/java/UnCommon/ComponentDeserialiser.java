package UnCommon;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ComponentDeserialiser  implements JsonSerializer<Component>, JsonDeserializer {
    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result=new JsonObject();
        result.add("type",new JsonPrimitive(src.getClass().getCanonicalName()));
        result.add("properties",context.serialize(src,src.getClass()));
        return result;
    }

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject= json.getAsJsonObject();
        String type=jsonObject.get("type").getAsString();
        JsonElement element=jsonObject.get("properties");
        try{
         return  context.deserialize(element,Class.forName(type));
        }catch(ClassNotFoundException e) {
            throw new JsonParseException("unknown element type "+type,e);

        }

    }
}

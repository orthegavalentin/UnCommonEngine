package editor;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class JImGui {
    private static float defaultColumnWidth = 220.0f;


    public static void drawVec2Control(String label, Vector2f values) {
        drawVec2Control(label, values, 0.0f, defaultColumnWidth);

    }

    public static void drawVec2Control(String label, Vector2f values, float resetValue) {
        drawVec2Control(label, values, resetValue, defaultColumnWidth);
    }

    public static void drawVec2Control(String label, Vector2f values, float resetValue, float columnwidth) {
        ImGui.pushID(label);
        ImGui.columns(2);
        ImGui.setColumnWidth(0, columnwidth);
        ImGui.text(label);
        ImGui.nextColumn();


        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0, 0);
        float lineHeight = ImGui.getFontSize() + ImGui.getStyle().getFramePaddingY() * 2.0f;
        Vector2f buttonSize = new Vector2f(lineHeight + 3.0f, lineHeight);
        float widthEach = (ImGui.calcItemWidth() - buttonSize.x * 2.0f) / 2.0f;


        ImGui.pushItemWidth(widthEach);
        ImGui.pushStyleColor(ImGuiCol.Button, 0.0f, 0.2f, 0.8f, 0.6f);
        ;
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.0f, 0.2f, 0.8f, 1.0f);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.4f, 0.4f, 0.0f, 1.0f);


        if (ImGui.button("x", buttonSize.x, buttonSize.y)) {
            values.x = resetValue;

        }
        ImGui.popStyleColor(3);
        ImGui.sameLine();
        float[] vecValueX = {values.x};
        ImGui.dragFloat("##x", vecValueX, 0.1f);
        ImGui.popItemWidth();
        ImGui.sameLine();

        ImGui.pushItemWidth(widthEach);
        ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.2f, 0.2f, 0.6f);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.8f, 0.2f, 0.3f, 1.0f);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.2f, 0.7f, 0.2f, 1.0f);


        if (ImGui.button("y", buttonSize.x, buttonSize.y)) {
            values.y = resetValue;

        }
        ImGui.popStyleColor(3);
        ImGui.sameLine();
        float[] vecValueY = {values.y};
        ImGui.dragFloat("##y", vecValueY, 0.5f);
        ImGui.popItemWidth();
        ImGui.sameLine();



        ImGui.nextColumn();
     values.x= vecValueX[0];
     values.y = vecValueY[0];
        ImGui.popStyleVar();
        ImGui.columns(1);


        ImGui.popID();
    }

    public static float dragFloat(String label, float value) {
        ImGui.pushID(label);
        ImGui.columns(2);
        ImGui.setColumnWidth(0, defaultColumnWidth);
        ImGui.text(label);
        ImGui.nextColumn();


        float[] valArr = {value};
        ImGui.dragFloat("##dragFloat", valArr, 0.8f);



        ImGui.columns(1);
        ImGui.popID();
        return valArr[0];

    }

    public static int dragInt(String label, int value) {
        ImGui.pushID(label);
        ImGui.columns(2);
        ImGui.setColumnWidth(0, defaultColumnWidth);
        ImGui.text(label);
        ImGui.nextColumn();


        int[] valArr = {value};
        ImGui.dragInt("##dragInt", valArr, 0.1f);


        ImGui.columns(1);
        ImGui.popID();
        return valArr[0];
    }

    public static boolean colorPicker4(String label, Vector4f color) {
        boolean res = false;
        ImGui.pushID(label);
        ImGui.columns(2);
        ImGui.setColumnWidth(0, defaultColumnWidth);
        ImGui.text(label);
        ImGui.nextColumn();


        float[] imcolor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorEdit4("##colorval", imcolor)) {
            color.set(imcolor[0], imcolor[1], imcolor[2], imcolor[3]);
            res = true;

        }


        ImGui.columns(1);
        ImGui.popID();
        return res;
    }
}

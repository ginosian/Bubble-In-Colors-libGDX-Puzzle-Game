package com.martha.bubble.actor;

import com.badlogic.gdx.graphics.Color;
import com.martha.bubble.constant.ParamConst;
import com.martha.bubble.enums.BallType;
import com.martha.bubble.util.Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Martha on 11/5/2015.
 */
public class Pallet {
    // region Instance Fields
    private LinkedHashMap<BallType, Color> typeColorMap;
    private ArrayList<BallType> validBallTypes;
    // endregion

    // region Ctor
    public Pallet(Color color_1, Color color_2, Color color_3, Color color_4, Color color_5) {
        this.typeColorMap = new LinkedHashMap<BallType, Color>();
        this.validBallTypes = new ArrayList<BallType>(5);
        initPallet(color_1, color_2, color_3, color_4, color_5);
    }
    public Pallet() {
    }
    // endregion

    // region Member Methods
    public void initPallet(Color color_1, Color color_2, Color color_3, Color color_4, Color color_5){
        typeColorMap.clear();
        ArrayList<Color> tempColorList = new ArrayList<Color>();
        tempColorList.add(color_1);
        tempColorList.add(color_2);
        tempColorList.add(color_3);
        tempColorList.add(color_4);
        tempColorList.add(color_5);
        BallType ballTypes[] = BallType.values();
        for (int i = 0; i < ParamConst.COLOR_QUANTITY; i++) {
            this.typeColorMap.put(ballTypes[i], tempColorList.get(i));
            this.validBallTypes.add(ballTypes[i]);
        }
    }
    public void applyRandomColor(Ball ball){
        int index = Util.random.nextInt(validBallTypes.size());
        Object[] entries = typeColorMap.entrySet().toArray();
        Map.Entry entry = (Map.Entry)entries[index];
        BallType randType = (BallType)entry.getKey();
        Color randColor = (Color)entry.getValue();
        ball.setType(randType);
        ball.setColor(randColor);
    }
    public Color acquireColor(BallType ballType){
        return typeColorMap.get(ballType);
    }
    // endregion

    // region Getters and Setters
    public ArrayList<BallType> getValidBallTypes() {
        return validBallTypes;
    }
    public Color getColor1(){
        return typeColorMap.get(BallType.TYPE_1);
    }
    public Color getColor2(){
        return typeColorMap.get(BallType.TYPE_2);
    }
    public Color getColor3(){
        return typeColorMap.get(BallType.TYPE_3);
    }
    public Color getColor4(){
        return typeColorMap.get(BallType.TYPE_4);
    }
    public Color getColor5(){
        return typeColorMap.get(BallType.TYPE_5);
    }
    // endregion
}

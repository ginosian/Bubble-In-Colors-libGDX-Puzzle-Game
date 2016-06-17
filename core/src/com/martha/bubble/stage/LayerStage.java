package com.martha.bubble.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.martha.bubble.enums.LayerType;

import java.util.LinkedHashMap;

/**
 * Created by Martha on 11/3/2015.
 */
public class LayerStage extends Stage {

    // region Instance Fields
    private LinkedHashMap<LayerType, Layer> layerMap;
    // endregion

    // region Ctor
    public LayerStage(){
        super();
        initLayerMap();
        addLayers();
    }
    // endregion

    // region Member Methods
    private void initLayerMap(){ // Initializes the layer map with LayerType enum values.
        layerMap = new LinkedHashMap<LayerType, Layer>();
        for (LayerType type : LayerType.values()) {
            layerMap.put(type, new Layer(type));
        }
    }
    private void addLayers(){ // Adds the layers to the stage.
        for(Layer layer : layerMap.values()){
            super.addActor(layer);
        }
    }
    public void addActor(Actor actor, LayerType layer){
        if(layerMap.get(layer) == null){
            throw new GdxRuntimeException("No such layer specified");
        }
        layerMap.get(layer).addActor(actor);
    }
    // endregion

    // region Overrides
    @Override
    public void addActor(Actor actor) { // Prevents the use of this overload to prevent adding actor directly in stage. Actors need to be added in any layer instead.
        throw new GdxRuntimeException("You have called addActor(Actor actor). This method must not" +
                "be called for LayerStage object." + "Call addActor(Actor actor, LayerType layer) overload instead.");
    }
    // endregion

    // region Inner classes
    public static class Layer extends Group {

        public final LayerType type;

        public Layer(LayerType type){
            this.type = type;
            this.setTransform(false); // Transform flag must be false not to add a bind and draw call.
        }
    }
    // endregion
}

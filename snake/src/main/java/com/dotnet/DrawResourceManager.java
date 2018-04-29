package com.dotnet;

import com.dotnet.character.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawResourceManager   {
    private List<Unit> drawResources;
    public DrawResourceManager(){
        drawResources = new ArrayList<>();
    }


    public Unit[] getDrawResources(){
        Unit[] temp= new Unit[drawResources.size()];
        return drawResources.toArray(temp);
    }


    public void addUnit(Unit[] bodyResource) {
        drawResources.addAll(Arrays.asList(bodyResource));
    }

    public void addUnit(Unit resource){
        drawResources.add(resource);
    }

    public void addUnit(List<Unit> drawResource) {
        drawResources.addAll(drawResource);
    }
}

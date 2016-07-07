package com.example.divya.recycletesting;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by divya on 13/6/16.
 */
public class RecyclerOne extends Activity {
    ArrayList<Scene> scenes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_one);
        setScenes();
        SceneAdapter adapter = new SceneAdapter(this,scenes);
        ListView listView = (ListView)findViewById(R.id.listId);
        listView.setAdapter(adapter);
    }

    public void setScenes(){
        Scene sceneOne = new Scene();
        sceneOne.setName("college");
        sceneOne.setImg(R.drawable.college);
        scenes.add(sceneOne);
        Scene sceneTwo = new Scene();
        sceneTwo.setName("office");
        sceneTwo.setImg(R.drawable.office);
        scenes.add(sceneTwo);
        Scene sceneThree = new Scene();
        sceneThree.setName("pragati");
        sceneThree.setImg(R.drawable.pragati);
        scenes.add(sceneThree);
    }
}

package com.example.helloworldgrpc;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Vibrator;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MyTileService extends TileService {

    Tile qsTile;
    Vibrator vibrator;

    @Override
    public void onClick() {
        super.onClick();
        Log.d("revathi", "on click of service tile");
        Toast.makeText(this, "Clicked on Service Tile", Toast.LENGTH_SHORT).show();
        Intent calendarTile = new Intent(Intent.ACTION_EDIT);
        calendarTile.setType("vnd.android.cursor.item/event");
        calendarTile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //By this system closes the Quick settings menu to show the respective activity's UI after the click action
        startActivityAndCollapse(calendarTile);

        // Vibrating based on the tile state

/*      vibrator =(Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        if(qsTile.getState() == Tile.STATE_INACTIVE) {
            qsTile.setState(Tile.STATE_ACTIVE);
            startVibrating(); 
        } else {
            qsTile.setState(Tile.STATE_INACTIVE);
            stopVibrating(); 
        }
        qsTile.updateTile();*/
    }

    private void stopVibrating() {
        vibrator.cancel();
    }

    private void startVibrating() {
        while (qsTile.getState() == Tile.STATE_ACTIVE) {
            vibrator.vibrate(1000); //vibrate for a second
        }
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();

        //Tile will be off once removed and added back
        qsTile.setState(Tile.STATE_INACTIVE);
        qsTile.updateTile();

    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    // when quick settings are visible on the screen
    @Override
    public void onStartListening() {
        super.onStartListening();

        Tile tile = getQsTile();
        tile.setLabel("Tile Exists");
        tile.setState(Tile.STATE_ACTIVE);
        tile.setIcon(Icon.createWithResource(this, R.drawable.ic_home_black_24dp));

        tile.updateTile();
    }

    // when quick settings are not visible on the screen
    @Override
    public void onStopListening() {
        super.onStopListening();
    }

}

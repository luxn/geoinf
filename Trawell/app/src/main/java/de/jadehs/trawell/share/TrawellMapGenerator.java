package de.jadehs.trawell.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.graph.Coordinate;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.models.Tour;

/**
 * Created by Lux on 18.06.2017.
 */

public class TrawellMapGenerator {

    private Bitmap map;
    private Context context;
    private TrawellGraph graph;
    private Canvas canvas;
    private Paint paint;

    public TrawellMapGenerator(Context context, TrawellGraph graph) {
        Bitmap b = BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.europe_background));
        this.context = context;
        this.map = b.copy(Bitmap.Config.ARGB_8888, true);
        this.canvas = new Canvas(this.map);
        this.graph = graph;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(0xFF6600);
        this.paint.setTextSize(14);
        this.paint.setStrokeWidth(4);
        this.paint.setStyle(Paint.Style.FILL);

    }


    public void drawRoute(Date startDate, List<Location> locations) {
        drawText("TrawellShare: " + startDate, new Coordinate(54, -2));

        int idx = 0;
        for (Location l : locations) {
            drawCircle(10f, l.getPosition());
            drawText(l.toString(), l.getPosition());

            if (idx < locations.size() -1) {
                drawLine(l.getPosition(), locations.get(idx+1).getPosition());
            }
            idx++;
        }

    }


    private void drawText(String text, Coordinate c) {
        Coordinate pixels = toPixel(c);
        float x = (float) pixels.getLongitude() + 10f;
        float y = (float) pixels.getLatitude() + 10f;
        this.canvas.drawText(text, x,y, this.paint);
    }

    private void drawLine(Coordinate c1, Coordinate c2) {
        Coordinate pixels1 = toPixel(c1);
        float x1 = (float) pixels1.getLongitude();
        float y1 = (float) pixels1.getLatitude();

        Coordinate pixels2 = toPixel(c2);
        float x2 = (float) pixels2.getLongitude();
        float y2 = (float) pixels2.getLatitude();

        this.canvas.drawLine(x1, y1, x2, y2, paint);
    }

    private void drawCircle(float radius, Coordinate c) {
        Coordinate pixels = toPixel(c);
        float x = (float) pixels.getLongitude();
        float y = (float) pixels.getLatitude();
        this.canvas.drawCircle(x,y, radius, this.paint);
    }

    private Coordinate toPixel(Coordinate c) {
        /**
         * Georeferenzierte WorldDatei zu dem Bild
         *
         0.02289067533229275
         0
         0
         -0.01597895458953465
         -3.07591001908242667
         55.13200895542749436
         */
        double x, y;
        x = c.getLongitude() + 3;
        y = c.getLatitude() - 55;

        x = x / 0.022;
        y = y / -0.015;

        return new Coordinate(x,y);
    }

    public Bitmap getMap() {
        this.canvas.save();
        return this.map;
    }


    private Uri saveBitmapToCache() {

        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput("share.png", Context.MODE_PRIVATE);
            this.map.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        File f = new File(context.getFilesDir() + "/share.png");
        return Uri.parse(f.getAbsolutePath());
    }

    @Nullable
    public Intent getShareIntent() {


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, saveBitmapToCache());
        shareIntent.setType("image/*");
        return Intent.createChooser(shareIntent, "trawellShare");
    }

}

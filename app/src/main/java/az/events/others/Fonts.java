package az.events.others;

import android.content.Context;
import android.graphics.Typeface;

public class Fonts {

    public static String helvetiacNeueLight= "fonts/HelveticaNeueLight.ttf";
    public static String helveticaNeueThin= "fonts/HelveticaNeueThin.ttf";
    public static String helveticaNeue = "fonts/HelveticaNeue.ttf";
    public static String helveticaNeueBold = "fonts/HelveticaNeuBold.ttf";
    public static String helveticaNeueMedium = "fonts/HelveticaNeueMedium.ttf";

    public static Typeface getTypeface(Context context, String path){
        return Typeface.createFromAsset(context.getAssets(),path);
    }

}
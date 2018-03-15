package az.events.others;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ziyeddinovchiyev on 6/24/16.
 */
public class CustomImageView2  extends ImageView {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView2(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width*1/3);
    }
}

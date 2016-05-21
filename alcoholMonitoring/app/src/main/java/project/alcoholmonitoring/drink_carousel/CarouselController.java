package project.alcoholmonitoring.drink_carousel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import project.alcoholmonitoring.R;

/**
 * Created by jason_liu on 7/05/16.
 */
public class CarouselController extends AppCompatActivity {

    CarouselView carouselView;

    int[] drinkImages = { };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_carousel);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(drinkImages.length);

        carouselView.setImageListener(imageListener);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(drinkImages[position]);
        }
    };
}

package project.alcoholmonitoring.alcohol_details;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import project.alcoholmonitoring.R;

/**
 * Created by jieliang on 2/05/2016.
 */
public class AlcoholDetailsTrackActivity extends AppCompatActivity {

    CarouselView carouselView;

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    int[] drinkImages = { R.drawable.ic_action_beer, R.drawable.ic_action_cocktail,R.drawable.ic_action_wine };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alcohol_details);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(drinkImages.length);

        carouselView.setImageListener(imageListener);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(drinkImages[position]);
            imageView.setOnClickListener(imageClickListener);

        }
    };

    CarouselView.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            carouselView.pauseCarousel();
//
            Toast.makeText(getApplicationContext(), "clicked!",
                    Toast.LENGTH_LONG).show();
        }
    };
}

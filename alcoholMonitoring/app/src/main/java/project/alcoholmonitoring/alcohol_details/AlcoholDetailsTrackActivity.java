package project.alcoholmonitoring.alcohol_details;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import project.alcoholmonitoring.R;

/**
 * Created by jieliang on 2/05/2016.
 */
public class AlcoholDetailsTrackActivity extends AppCompatActivity {



    CarouselView carouselView;
    FloatingActionButton plus;
    FloatingActionButton minus;
    TextView quantity;
    TextView volume;
    EditText Alcohol_content;
    SeekBar verticalBar;
    //// TODO: 22/05/16 click listener
    FloatingActionButton mood_sad;
    FloatingActionButton mood_smile;
    FloatingActionButton mood_happy;
    FloatingActionButton mood_angry;
    TextView mood_lable;
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    int[] drinkImages = { R.drawable.ic_action_beer, R.drawable.ic_action_cocktail,R.drawable.ic_action_wine };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alcohol_details);
        //init carousel
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(drinkImages.length);
        carouselView.setImageListener(imageListener);
        //bind action buttons
        plus=(FloatingActionButton)findViewById(R.id.DrinkPlus);
        plus.setOnClickListener(plusB);
        minus=(FloatingActionButton)findViewById(R.id.DrinkMinus);
        minus.setOnClickListener(minusB);
        //bind quantify text view
        quantity=(TextView)findViewById(R.id.quantity);
        //bind volume&standard text view
        volume=(TextView)findViewById(R.id.volume);
        //edit text view
        Alcohol_content=(EditText) findViewById(R.id.alcoholContent);
        //bind vertical bar
        verticalBar=(SeekBar)findViewById(R.id.alcoholSlider);
        verticalBar.setOnSeekBarChangeListener(seekBarChangeListener);
        //mood images views // TODO: 22/05/16 save mood to db
        mood_sad=(FloatingActionButton) findViewById(R.id.mood_sad);
        mood_angry=(FloatingActionButton)findViewById(R.id.mood_angry);
        mood_happy=(FloatingActionButton)findViewById(R.id.mood_happy);
        mood_smile=(FloatingActionButton)findViewById(R.id.mood_smile);
        //Fab listener
        mood_sad.setOnClickListener(sadbtn);
        mood_angry.setOnClickListener(angrybtn);
        mood_happy.setOnClickListener(happybtn);
        mood_smile.setOnClickListener(smilebtn);
        //mood label
        mood_lable=(TextView)findViewById(R.id.moodLable);

    }
    //mood listeners
    FloatingActionButton.OnClickListener sadbtn= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
           mood_lable.setText("Sad");

        }
    };
    FloatingActionButton.OnClickListener happybtn= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            mood_lable.setText("Happy");

        }
    };
    FloatingActionButton.OnClickListener smilebtn= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            mood_lable.setText("Normal");

        }
    };
    FloatingActionButton.OnClickListener angrybtn= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            mood_lable.setText("Angry");
        }
    };
    //seek bar listener
    SeekBar.OnSeekBarChangeListener seekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            volume.setText(progress+"0");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // carousel image listener
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(drinkImages[position]);
            imageView.setOnClickListener(imageClickListener);
        }
    };
    //plus listener
    FloatingActionButton.OnClickListener plusB= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Integer temp=Integer.parseInt(quantity.getText().toString())+1;

           quantity.setText(temp.toString());

        }
    };
    //minus listener
    FloatingActionButton.OnClickListener minusB=new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            Integer temp=Integer.parseInt(quantity.getText().toString())-1;
            if(temp >=0) {
                quantity.setText(temp.toString());
            }


        }
    };

    //image click listener
    CarouselView.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
////            carouselView.pauseCarousel();
////  // TODO: 21/05/16 click select alcohol
          String temp=  Integer.toString(carouselView.getVerticalScrollbarPosition());
           Toast.makeText(getApplicationContext(), temp,
                    Toast.LENGTH_LONG).show();

        }

    };

}

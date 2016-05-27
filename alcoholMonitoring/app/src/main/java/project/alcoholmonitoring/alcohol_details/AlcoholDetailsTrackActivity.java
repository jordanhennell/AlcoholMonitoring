package project.alcoholmonitoring.alcohol_details;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.CarouselViewPager;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.Date;

import project.alcoholmonitoring.R;
import project.alcoholmonitoring.db.tables.Drinks_track;

/**
 * Created by jieliang on 2/05/2016.
 */
public class AlcoholDetailsTrackActivity extends AppCompatActivity {


    CarouselView carouselView;
    CarouselViewPager carouselViewPager;
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
    Button log_button;
    String alcohol_type;
    Date choosen_date;
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    int[] drinkImages = { R.drawable.ic_action_beer, R.drawable.ic_action_cocktail,R.drawable.ic_action_wine };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alcohol_details);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
//        setSupportActionBar(myToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //init carousel
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(drinkImages.length);
        carouselView.setImageListener(imageListener);
        //carouselView.setViewListener(viewListener);
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
        //log button
        log_button=(Button)findViewById(R.id.Logbutton);
        log_button.setOnClickListener(log_listener);
    }
    //mood listeners
    FloatingActionButton.OnClickListener sadbtn= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
           mood_lable.setText("Sad");}
    };
    FloatingActionButton.OnClickListener happybtn= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            mood_lable.setText("Happy");}
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
            imageView.setOnClickListener(imageClickListener(position));
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

    CarouselView.OnClickListener imageClickListener(final int position) { return  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
////            carouselView.pauseCarousel();
////  // TODO: 21/05/16 link index with alcohol

            switch (position)
            {
                // TODO: 27/05/16 from spec query? hard coded for now
                case 0: Alcohol_content.setText("7"); alcohol_type="Beer"; break;
                case 1: Alcohol_content.setText("20");alcohol_type="CockTail"; break;
                default:Alcohol_content.setText("10"); alcohol_type="Wine";break;
            }
            //Alcohol_content.setText(Integer.toString(position));
        }
    };
    }
//log button listenner
    Button.OnClickListener log_listener=new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            Integer strength=Integer.valueOf(Alcohol_content.getText().toString());
            Drinks_track.insert_record(
                    get_date(),
                    alcohol_type,
                    data_converter.string_convert_int(volume),
                    strength,
                    mood_lable.getText().toString(),
                    data_converter.string_convert_int(quantity)
                    );

            android.util.Log.d(Integer.toString(Drinks_track.checkEmpty()),"data_drinks");
            Toast.makeText(AlcoholDetailsTrackActivity.this,"sucessful",Toast.LENGTH_LONG).show();
        }
    };


     public Integer get_date()
     {
         Bundle extras = getIntent().getExtras();
         choosen_date = ((CalendarDay)extras.get("chosen_date")).getDate();
       return   data_converter.date_convert_to_int(choosen_date);
     }


}

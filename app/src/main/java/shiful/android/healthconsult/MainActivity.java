package shiful.android.healthconsult;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private ViewPager mSliderViewPager;
    private LinearLayout mDotLayout;
    private SlideAdapter slideAdapter;
    private TextView[] mDots;
    private Button mNextBtn, mBackBtn;
    private int mCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNextBtn=findViewById(R.id.finishBtn);
        mBackBtn=findViewById(R.id.backBtn);
        mSliderViewPager=findViewById(R.id.viewpagerid);
        mDotLayout=findViewById(R.id.ll);

        if (!isFirstTimeStartApp()){
            startMainActivity();
            finish();
        }

        slideAdapter=new SlideAdapter(this);
        mSliderViewPager.setAdapter(slideAdapter);

        addDotsIndicator(0);
        mSliderViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage=mSliderViewPager.getCurrentItem()+1;
                if (currentPage<mDots.length){
                mSliderViewPager.setCurrentItem(mCurrentPage+1);
                }
                else {
                    startMainActivity();
                }
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
    }

    public void addDotsIndicator(int position){
        mDots=new TextView[3];
        mDotLayout.removeAllViews();
        for (int i=0; i<mDots.length; i++){

            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage=i;
            if (i==0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("Next");
                mBackBtn.setText("");
            }
            else if (i==mDots.length-1){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");
            }
            else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    private boolean isFirstTimeStartApp(){
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("Medicine Provider", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("FirstTimeStartFlag",true);
    }
    private void setFirstTimeStartStatus(boolean firstTimeStartStatus){
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("Medicine Provider", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("FirstTimeStartFlag",firstTimeStartStatus);
        editor.commit();
    }
    private void startMainActivity(){
        setFirstTimeStartStatus(false);
        startActivity(new Intent(MainActivity.this,HomeActivity.class));
        finish();
    }
}
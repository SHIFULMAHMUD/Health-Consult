package shiful.android.healthconsult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SlideAdapter(Context context){
        this.context=context;
    }
    public int[] slide_images={
            R.drawable.heart,
            R.drawable.medicine,
            R.drawable.message
    };
    public String[] slide_headings={
            "HEALTHY HEART",
            "PROPER MEDICATION",
            "SHARE US"
    };
    public String[] slide_descs={
            "There are many things you can do to help keep your heart healthy and disease-free. You can schedule an annual checkup, exercise daily, quit smoking, or take steps to reduce the level of stress in your life. All of these things can have a positive effect on heart health. But, one of the simplest lifestyle changes that will benefit your heart is watching what you eat.",
            "Follow the label instructions carefully. Some medications need to be taken with food, others should be taken on an empty stomach. Ask your doctor or pharmacist if there are any foods that should be avoided while taking your medication. If you are taking medications with water, drink a full 8-ounce glass of water.",
            "Please share with us your experience of using this app & your problems, thoughts to grow up a better service."
    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slidelayout,container,false);
        ImageView slideImageView=view.findViewById(R.id.imageviewid);
        TextView slideHeading=view.findViewById(R.id.headingTextview);
        TextView slideDescription=view.findViewById(R.id.descTextview);
        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}

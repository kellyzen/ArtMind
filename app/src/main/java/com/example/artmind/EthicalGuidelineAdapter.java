package com.example.artmind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Ethical Guideline page (adapter)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class EthicalGuidelineAdapter extends PagerAdapter {
    Context context;
    int[] images = {
            R.drawable.guideline1,
            R.drawable.guideline2,
            R.drawable.guideline3,
            R.drawable.guideline4,
    };
    int[] description = {
            R.string.desc1,
            R.string.desc2,
            R.string.desc3,
            R.string.desc4,
    };

    int[] headings = {
            R.string.guideline1,
            R.string.guideline2,
            R.string.guideline3,
            R.string.guideline4,
    };

    /**
     * Constructor method for Find Ethical Guideline Adapter
     *
     * @param context ethical guideline fragment's context
     */
    public EthicalGuidelineAdapter(Context context) {
        this.context = context;
    }

    /**
     * Get ethical guideline view pager list size
     */
    @Override
    public int getCount() {
        return headings.length;
    }

    /**
     * Determine is view from object
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * Set text/ image for each view item on the view pager
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_ethical_guideline, container, false);

        ImageView image = view.findViewById(R.id.guideline_image_view);
        TextView title = view.findViewById(R.id.guideline_title);
        TextView content = view.findViewById(R.id.guideline_content);

        image.setImageResource(images[position]);
        title.setText(context.getString(headings[position]));
        content.setText(context.getString(description[position]));

        container.addView(view);
        return view;
    }

    /**
     * Remove view item when navigates to another fragment
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}

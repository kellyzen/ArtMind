package com.example.artmind;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * Ethical Guideline page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class EthicalGuidelineFragment extends Fragment {
    ViewPager viewPager;
    LinearLayout dotLayout;
    TextView[] dots;
    EthicalGuidelineAdapter ethicalGuidelineAdapter;

    /**
     * Constructor method for Ethical Guideline Fragment
     */
    public EthicalGuidelineFragment() {
    }

    /**
     * Create view for Ethical Guideline Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ethical_guideline, container, false);
        viewPager = view.findViewById(R.id.guideline_view_pager);
        dotLayout = view.findViewById(R.id.ellipses);

        // Setup view pager
        ethicalGuidelineAdapter = new EthicalGuidelineAdapter(getContext());
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setAdapter(ethicalGuidelineAdapter);

        setUpEllipses(0);
        viewPager.addOnPageChangeListener(viewListener);

        return view;
    }

    /**
     * Set up ellipses to navigate between pages
     *
     * @param position the which page is the user at now
     */
    public void setUpEllipses(int position) {
        dots = new TextView[4];
        dotLayout.removeAllViews();

        // Create and set color for each ellipses
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(R.color.gray));
            dotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.red));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        // Set up ellipses on for every page
        @Override
        public void onPageSelected(int position) {
            setUpEllipses(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
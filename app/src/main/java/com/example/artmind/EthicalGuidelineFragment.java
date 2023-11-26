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

public class EthicalGuidelineFragment extends Fragment {
    ViewPager viewPager;
    LinearLayout dotLayout;
    TextView[] dots;
    EthicalGuidelineAdapter ethicalGuidelineAdapter;

    public EthicalGuidelineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ethical_guideline, container, false);
        viewPager = view.findViewById(R.id.guideline_view_pager);
        dotLayout = view.findViewById(R.id.ellipses);

        ethicalGuidelineAdapter = new EthicalGuidelineAdapter(getContext());
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setAdapter(ethicalGuidelineAdapter);

        setUpEllipses(0);
        viewPager.addOnPageChangeListener(viewListener);

        return view;
    }

    public void setUpEllipses(int position) {
        dots = new TextView[4];
        dotLayout.removeAllViews();

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

        @Override
        public void onPageSelected(int position) {
            setUpEllipses(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
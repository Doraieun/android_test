package doraieun.testapp4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     * 수평으로 view를 스크롤할때 사용하는 class
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//onCreateOptionsMenu 이 호출됨


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        System.out.println("fragmentManager : " + fragmentManager);
//
//        List<Fragment> fragmentList = fragmentManager.getFragments();
//
//        System.out.println("fragmentList : " + fragmentList);
//
//        Log.d("debug", fragmentList.size() + "");
//        for(int i = 0; i < fragmentList.size(); i++){
//            Fragment f = fragmentList.get(0);
//            Log.d("debug", f.getId() + "");
//            Log.d("debug", f.getTag() + "");
//        }




        Log.d("#################", "TabActivity.onCreate 1");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Log.d("#################", "TabActivity.onCreate 2");
    }


    /**
     * Toolbar가 있을대만 call 됨
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d("#################", "TabActivity.onCreateOptionsMenu Called!");
        //Log.d("#################", menu.getItem(0).getItemId() + "");
        Log.d("#################", menu.size() + "");
//        if (menu.getItem(100) != null){
//            Log.d("#################", menu.getItem(100).getItemId() + "");
//        }
//        Log.d("#################", menu.toString());
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("#################", "TabActivity.onOptionsItemSelected Called!");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            Log.d("#################", "SectionsPagerAdapter Creater Called!");
        }

        //FragmentPagerAdapter에서 호출
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.d("#################", "SectionsPagerAdapter.getItem is called! + position : " + position);
            return PlaceholderFragment.newInstance(position + 1);
        }

        //viewPager.setAdapter() 시 호출
        @Override
        public int getCount() {
            // Show 3 total pages.
            //Log.d("debug", "SectionsPagerAdapter.getCount is called!");
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            Log.d("##########", "PlaceholderFragment.newInstance called!");
            PlaceholderFragment fragment = new PlaceholderFragment();
            Log.d("##########", "craeted PlaceholderFragment");
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            Log.d("##########", "1111111111111111");
            return fragment;
        }

        public PlaceholderFragment() {
        }

        //Fragment에서 호출
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.d("##########", "PlaceholderFragment.onCreateView called!");


            Log.d("##########", getArguments().toString());
            View rootView = null;
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            if(sectionNumber == 1){
                rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText("첫 페이지데스.");
                //textView.setText(getString(R.string.section_format, "일"));
            }else if(sectionNumber == 2){
                //textView.setText("2page");
                rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText("둘째 페이지데스.");
                ImageView imageView = (ImageView) rootView.findViewById(R.id.section_image);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.nav_logo242));

            }else{
                String[] arrList = {"menu1", "menu2", "menu3"};

                rootView = inflater.inflate(R.layout.fragment_tab3, container, false);
                TextView textView = (TextView)rootView.findViewById(R.id.textView4);
                textView.setText("셋째 페이지데스.");

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.fragment_tab3_view, R.id.listViewText);
                arrayAdapter.addAll(arrList);

                ListView listView = (ListView)rootView.findViewById(R.id.menuList);

                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(onItemClickListener);

                //textView.setText("3page");
                //textView.setText(getString(R.string.section_format, "삼"));
            }

            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("#############", "Clicked!");
                System.out.println("############ : " + parent);
                System.out.println("############ : " + view);
                System.out.println("############ : " + position);
                System.out.println("############ : " + id);

                System.out.println("############## " + view.getTag());
                System.out.println("############## " + view.getId());
                System.out.println("############## " + view.findViewById(R.id.listViewText));
                TextView tv = (TextView) view.findViewById(R.id.listViewText);
                System.out.println("############## " + tv.getText());
                Log.d("############", view.getResources().toString());

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("알림");
                alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Dialog Button Clicked!");
                        System.out.println("which : " + which);
                    }
                });

                alertDialog.setMessage(tv.getText() + " Click");
                alertDialog.show();
            }
        };
    }
}

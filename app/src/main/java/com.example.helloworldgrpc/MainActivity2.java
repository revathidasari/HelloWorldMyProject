package com.example.helloworldgrpc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    PagerAdapter pagerAdapter;
    private Visualizer audioOutput = null;
    public float intensity = 0; //intensity is a value between 0 and 1. The intensity in this case is the system output volume
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        createVisualizer();
        ViewPager vpPager = (ViewPager) findViewById(R.id.c);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(pagerAdapter);


        /* to run script from an application
                Process p=null;
                try {
                    p = new ProcessBuilder()
                    .command("PathToYourScript")
                    .start();
                    (or)
            //Process process = Runtime.getRuntime().exec("sh /sdcard/test.sh");
            //BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //String listOfFiles = "";
            //String line;
            //while ((line = in.readLine()) != null) {
            //    listOfFiles += line;
            //}
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(p!=null) p.destroy();
                }*/
    }

    private void createVisualizer() {
        int rate = Visualizer.getMaxCaptureRate();
        audioOutput = new Visualizer(0); // get output audio stream
        audioOutput.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                intensity = ((float) waveform[0] + 128f) / 256;
                Log.d("revathi", String.valueOf(intensity));
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }
        },rate , true, false); // waveform not freq data
        Log.d("revathi", String.valueOf(Visualizer.getMaxCaptureRate()));
        audioOutput.setEnabled(true);
    }

    public static class MyPagerAdapter extends PagerAdapter {
        private static int NUM_ITEMS = 3;
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super();
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }

        // Returns the fragment to display for that page
/*        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FirstFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return SecondFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }*/

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}

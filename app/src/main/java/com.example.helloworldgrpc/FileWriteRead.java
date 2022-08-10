package com.example.helloworldgrpc;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

public class FileWriteRead extends AppCompatActivity {

    MyHashMap myHashMap;
    Button btnMap, btnScan;
    EditText tvMap;
    RecyclerView recyclerView;
    ImageView img_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_write_read);

        btnMap = findViewById(R.id.buttonMap);
        tvMap = findViewById(R.id.textViewMap);
        recyclerView = findViewById(R.id.recyclerView);
        img_qr = findViewById(R.id.imgQR);
        btnScan = findViewById(R.id.buttonScan);
        myHashMap = new MyHashMap();
        MyListData[] myListData = new MyListData[] {
                /*To print hash map data
                new MyListData(1, myHashMap.getDataAtPosition(1)),
                new MyListData(2, myHashMap.getDataAtPosition(2)),
                new MyListData(3, myHashMap.getDataAtPosition(3))*/
                new MyListData(1,"qwe"),
                new MyListData(2,"asd"),
                new MyListData(3,"zxc"),
                new MyListData(4,"zaq")
        };

        MyListAdapter myListAdapter = new MyListAdapter(myListData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myListAdapter);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myHashMap.getMapData();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadQRCode readQRCode = new ReadQRCode(getBaseContext());
                img_qr.setImageBitmap(readQRCode.createQR());
                readQRCode.readQRFromImage();
            }
        });
        try {

            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"mydir");
//           File dir = new File(Environment.getExternalStorageDirectory()+"/mydir");
            if(!dir.exists())
                dir.mkdirs();
            Random rgenerator = new Random();
            int n = 10000;
            n = rgenerator.nextInt(n);
            String fname = "filename-" + n + ".txt";
            File file = new File(dir, fname);
            Log.d("revathi","file name "+file);//  /storage/emulated/0/Download/mydir/filename-8280.txt
            /* creates at storage/emulated/0/Android/data/<pkgName>/files/file.txt --------- File file = new File(this.getExternalFilesDir(null),"file.txt");*/
            if (file.exists())
                file.delete();
            //FileOutputStream out = this.openFileOutput(fname, Context.MODE_PRIVATE);
            FileOutputStream out = new FileOutputStream(file);
            out.write("writing to the file 46".getBytes());
            out.flush();
            out.close();

            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();

            String contents = new String(bytes);
            Toast.makeText(this, "contents "+contents, Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "excep "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private class MyListData {
        int keyId;
        String valueName;
        public MyListData(int keyId, String valueName) {
            this.keyId = keyId;
            this.valueName = valueName;
        }

        public void setKeyId(int keyId){
            this.keyId =keyId;
        }
        public int getKeyId() {
            return keyId;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }
        public String getValueName() {
            return valueName;
        }
    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {

        private MyListData[] listData;

        public MyListAdapter(MyListData[] listData) {
            this.listData = listData;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView id, name;
            public ConstraintLayout constraintLayout;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                this.id =  itemView.findViewById(R.id.textId);
                this.name = itemView.findViewById(R.id.textValue);
                constraintLayout = itemView.findViewById(R.id.constraintlayout);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_data,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            MyListData list = listData[position];

            holder.id.setText(String.valueOf(listData[position].getKeyId()));
            holder.name.setText(listData[position].getValueName());
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(recyclerView.getContext(), "clicked "+list.getValueName(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return listData.length;
        }

    }
    public void stringToKeyValuePairConveresion(){
        /*        String string = "AA.BB-CC-DD.zip";
        String[] arr = string.split("\\.");
        Log.d("revathi","splitter"+Arrays.toString(arr));
        for(int i =0; i<arr.length;i++) {
            if (arr[i].contains("-")) {
                String[] arr2 = arr[i].split("\\-");
                Log.d("revathi", "splitter 2 " + Arrays.toString(arr2));
            }
        }*/
        String rawData ="1-a;2-b;3-c;4-d;5-e;6-f;7-g";
        String[] baseArray = rawData.split("\\;");
        String[] keyDataArray = new String[rawData.length()];
        String[] valueDataArray = new String[rawData.length()];
        int index = 0;
        for(String items : baseArray) {
            String[] splittedArray = items.split("\\-");
            keyDataArray[index] = splittedArray[0];
            valueDataArray[index] = splittedArray[1];
            index++;
        }
        for (int i = 0; i< index; i++) {
            Log.d("revathi","index"+index+"key "+keyDataArray[i]+" values "+valueDataArray[i]);
        }
    }

}
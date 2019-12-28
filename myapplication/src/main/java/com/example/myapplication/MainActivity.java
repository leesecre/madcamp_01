package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.view.Window;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.ListView;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import android.database.Cursor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    DisplayMetrics mMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) findViewById(R.id.host);
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon1, null));
        spec.setContent(R.id.listView);
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon2, null));
        spec.setContent(R.id.gridview);
        host.addTab(spec);

        spec = host.newTabSpec("tab3");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon3, null));
        spec.setContent(R.id.tab_content3);
        host.addTab(spec);

        mListView = (ListView) findViewById(R.id.listView);
//        getList();

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        ImageAdapter gAdapter = new ImageAdapter(this);
        gridview.setAdapter(gAdapter);


        /* 아이템 추가 및 어댑터 등록 */
        ListAdapter mMyAdapter = new ListAdapter();
        try {
            JSONObject jsonObject = new JSONObject(getJsonString());

                JSONArray contactArray = jsonObject.getJSONArray("Contacts");

                for (int i = 0; i < contactArray.length(); i++) {
                    JSONObject contactObject = contactArray.getJSONObject(i);

                    mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon), contactObject.getString("name"), contactObject.getString("num"));

                }
                mListView.setAdapter(mMyAdapter);
            }
          catch(JSONException e){
                e.printStackTrace();
            }/* 리스트뷰에 어댑터 등록 */
            mListView.setAdapter(mMyAdapter);

    }

    private String getJsonString()
        {
            String json = "";

            try {
                InputStream is = getAssets().open("contact");
                int fileSize = is.available();

                byte[] buffer = new byte[fileSize];
                is.read(buffer);
                is.close();

                json = new String(buffer, "UTF-8");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

            return json;
        }
    public void getList(){

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        };

        String[] selectionArgs = null;

        //정렬
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        //조회해서 가져온다
        Cursor contactCursor = getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);

        //정보를 담을 array 설정
        ArrayList persons = new ArrayList();

        if(contactCursor.moveToFirst()){
            do{
                persons.add(contactCursor.getString(1) + "/" + contactCursor.getString(0));
            }while(contactCursor.moveToNext());
        }

        //리스트에 연결할 adapter 설정
        ArrayAdapter adp = new ArrayAdapter(this, R.layout.listview_item, persons);

        //리스트뷰에 표시
        mListView.setAdapter(adp);

    }
    public class ImageAdapter extends BaseAdapter {
        Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }
        Integer[] mThumbIds = {R.drawable.kr, R.drawable.kr2, R.drawable.kr3,
            R.drawable.mx, R.drawable.mx2, R.drawable.mx3, R.drawable.fr, R.drawable.fr2, R.drawable.fr3,
            R.drawable.pl, R.drawable.pl2, R.drawable.pl3, R.drawable.sa, R.drawable.sa2, R.drawable.sa3,
            R.drawable.cn, R.drawable.cn2, R.drawable.cn3, R.drawable.a1, R.drawable.a2, R.drawable.a3,
                R.drawable.a4, R.drawable.a5, R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5,
                R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5,
                R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5
        };

        // create a new ImageView for each item referenced by the Adapter
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(300,400));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(1,1,1,1);
        imageView.setImageResource(mThumbIds[position]);
        final int pos = position;
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                View diaglogView = (View) View.inflate(MainActivity.this,R.layout.dialog, null);
                ImageView ivPoster = (ImageView)diaglogView.findViewById(R.id.ivPoster);
                ivPoster.setImageResource(mThumbIds[pos]);
                final Dialog dlg = new Dialog(mContext, android.R.style.Theme_Light);
                dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dlg.setContentView(diaglogView);
                dlg.show();
                diaglogView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dlg.dismiss();
                    }
                });
                }
            });
            return imageView;
        }

    }
}


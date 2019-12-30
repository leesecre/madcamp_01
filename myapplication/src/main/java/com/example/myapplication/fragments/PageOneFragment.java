package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.myapplication.ListAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class PageOneFragment extends Fragment {
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;
    ListView mListView;

    public PageOneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.page_one_fragment,container,false);
        ListAdapter mMyAdapter = new ListAdapter(getActivity());
        mListView = (ListView)rootview.findViewById(R.id.listView1);
        mListView.setAdapter(mMyAdapter);
        try{
            JSONObject jsonObject = new JSONObject(getJsonString());
            JSONArray contactArray = jsonObject.getJSONArray("Contacts");
            for(int i=0; i<contactArray.length(); i++)
            {
                JSONObject contactObject = contactArray.getJSONObject(i);

                mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.vfdvdfdfv),contactObject.getString("name"), contactObject.getString("num"));

            }
            mListView.setAdapter(mMyAdapter);
        }catch (JSONException e) {
            e.printStackTrace();
        }
//        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.vfdvdfdfv),
//                "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.



        return rootview;

    }
    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = ((MainActivity)getActivity()).getAssets().open("contact");
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

}

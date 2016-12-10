package com.example.ashwin.firebasemultiplejson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ListItemAdapter.ItemListener
{
    private RecyclerView mRecyclerView;
    private ListItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Item> mItemsList, mAnimalsList, mPeopleList;
    private DatabaseReference mAnimalsReadRef, mPeopleReadRef;
    private Query mAnimalsQueryRef, mPeopleQueryRef;
    private boolean mIsAnimalsListLoaded, mIsPeopleListLoaded;
    private CircleProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (CircleProgressBar) findViewById(R.id.circleProgressBar);
        mProgressBar.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);

        mItemsList = new ArrayList<Item>();
        mAnimalsList = new ArrayList<Item>();
        mPeopleList = new ArrayList<Item>();

        mAdapter = new ListItemAdapter(this, mItemsList);

        initAnimalsList();

        initPeopleList();

        initRecyclerView();
    }


    private void initPeopleList()
    {
        mPeopleReadRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://android-firebase-test-a2516.firebaseio.com/multiple-jsons/people/");

        mPeopleQueryRef = mPeopleReadRef.orderByChild("position");

        mPeopleQueryRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    //Getting the data from snapshot
                    Item item = postSnapshot.getValue(Item.class);

                    Log.d("people string", item.toString());

                    //Adding video to videos list
                    mPeopleList.add(item);
                }

                //people list load complete
                mIsPeopleListLoaded = true;

                if(mIsAnimalsListLoaded && mIsPeopleListLoaded)
                {
                    initItemsList();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //do nothing
            }
        });
    }


    private void initAnimalsList()
    {
        mAnimalsReadRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://android-firebase-test-a2516.firebaseio.com/multiple-jsons/animals/");

        mAnimalsQueryRef = mAnimalsReadRef.orderByChild("position");

        mAnimalsQueryRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    //Getting the data from snapshot
                    Item item = postSnapshot.getValue(Item.class);

                    Log.i("animal string", item.toString());

                    //Adding video to videos list
                    mAnimalsList.add(item);
                }

                //animals list load complete
                mIsAnimalsListLoaded = true;

                if(mIsAnimalsListLoaded && mIsPeopleListLoaded)
                {
                    initItemsList();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //do nothing
            }
        });
    }


    private void initItemsList()
    {
        //Log.d("MainActivity", "AnimalsList size : " + mAnimalsList.size());
        //Log.d("MainActivity", "PeopleList size : " + mPeopleList.size());

        mItemsList.addAll(mAnimalsList);

        mItemsList.addAll(mPeopleList);

        //Log.d("MainActivity", "ItemsList size : " + mItemsList.size());

        mAdapter.notifyDataSetChanged();

        mProgressBar.setVisibility(View.GONE);
    }


    private void initRecyclerView()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onItemClicked(View view, int position)
    {
        Toast.makeText(this, "Selected : " + mItemsList.get(position).getName(), Toast.LENGTH_LONG).show();
    }
}

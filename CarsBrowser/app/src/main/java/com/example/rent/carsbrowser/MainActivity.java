package com.example.rent.carsbrowser;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FilterQueryProvider;

import com.example.rent.carsbrowser.add.AddNewActivityCar;
import com.example.rent.carsbrowser.listing.ListingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.autocomplete_textviewID)
    AutoCompleteTextView autoCompleteTextView;

    private MotoDatabaseOpenHelper databaseOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        databaseOpenHelper = new MotoDatabaseOpenHelper(this);

        AutocompleteAdapter adapter = new AutocompleteAdapter(this, databaseOpenHelper.getAllItem() );

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return databaseOpenHelper.searchQuery(constraint);
            }
        });
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor= (Cursor) adapter.getItem(position);
                autoCompleteTextView.setText(cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_MAKE)));
            }
        });

    }
    @OnClick(R.id.add_new_car_buttonID)
    void onAddNewCarButtonClick (){
        Intent intent = new Intent(this, AddNewActivityCar.class);
        startActivity(intent);
    }
    @OnClick (R.id.search_buttonID)
    void onSearchButtonClick() {
        startActivity(ListingActivity.createIntent(MainActivity.this, autoCompleteTextView.getText().toString()));
    }
}

package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnViewAll, btnAdd;
    EditText etGrade, etClass;
    Switch swtState;
    ListView lvClassesGrades;
    TextView txvAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnViewAll = findViewById(R.id.btnViewAll);
        etGrade = findViewById(R.id.etGrade);
        etClass = findViewById(R.id.etClass);
        swtState = findViewById(R.id.swtState);
        lvClassesGrades = findViewById(R.id.lvClassesGrades);
        txvAverage = findViewById(R.id.txvAverage);

        showAllValues();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassModel classModel = null;

                try{
                    classModel = new ClassModel(-1, etClass.getText().toString(), Float.valueOf(etGrade.getText().toString()), swtState.isChecked());
                    etClass.setText("");
                    etGrade.setText(null);
                    swtState.setChecked(false);
                    Toast.makeText(MainActivity.this, classModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch(Exception ex)
                {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    classModel = new ClassModel(-1, "Error", 0, false);
                }

                dbHelper dbhelper = new dbHelper(MainActivity.this);

                boolean success = dbhelper.addValue(classModel);

                if(success)
                    showAllValues();
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllValues();
            }
        });

        lvClassesGrades.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dbHelper dbhelper = new dbHelper(MainActivity.this);
                ClassModel longClickedClass = (ClassModel) parent.getItemAtPosition(position);
                dbhelper.deleteValue(longClickedClass);
                showAllValues();

                return false;
            }
        });
    }

    private void showAllValues(){
        dbHelper dbhelper = new dbHelper(MainActivity.this);
        List<ClassModel> classList = dbhelper.getAllValues();

        ArrayAdapter classArrayAdapter = new ArrayAdapter<ClassModel>(MainActivity.this, android.R.layout.simple_list_item_1, classList);
        lvClassesGrades.setAdapter(classArrayAdapter);

        txvAverage.setText(String.valueOf(new DecimalFormat("##.##").format(dbhelper.gradeAvg())));
        //Toast.makeText(MainActivity.this, String.valueOf(dbhelper.gradeAvg()), Toast.LENGTH_SHORT).show();
    }
}
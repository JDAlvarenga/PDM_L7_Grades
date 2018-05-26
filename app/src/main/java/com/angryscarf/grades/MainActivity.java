package com.angryscarf.grades;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.angryscarf.grades.Fragments.AddFragment;
import com.angryscarf.grades.Fragments.EditFragment;
import com.angryscarf.grades.Fragments.ListFragment;
import com.angryscarf.grades.Model.Student;

public class MainActivity extends AppCompatActivity  implements
        NavigationView.OnNavigationItemSelectedListener,
        ListFragment.OnListFragmentInteractionListener,
        EditFragment.OnEditFragmentInteractionListener,
        AddFragment.OnAddFragmentInteractionListener {

    private DrawerLayout mDrawer;
    private ListFragment listFrag;
    private EditFragment editFrag;
    private AddFragment addFrag;
    private Menu navigationItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFrag = new ListFragment();
        editFrag = new EditFragment();
        addFrag = new AddFragment();

        mDrawer = findViewById(R.id.main_drawer_layout);
        NavigationView navView = findViewById(R.id.main_nav_view);
        navView.setNavigationItemSelectedListener(this);

        navigationItems = navView.getMenu();

        this.onNavigationItemSelected(navigationItems.getItem(1));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        mDrawer.closeDrawers();
        switch(item.getItemId()) {
            //Id = 0
            case R.id.menu_item_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, addFrag).commit();
                break;
            // Id = 1
            case R.id.menu_item_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, listFrag).commit();
                break;

            //Id = 2
            case R.id.menu_item_edit:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, editFrag).commit();
                break;

            default:

                break;
        }
        return true;
    }

    @Override
    public void OnFinishEdit() {
        editFrag.clearViews();
        Toast.makeText(this, "Student saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnStudentSelected(Student student) {
        this.onNavigationItemSelected(navigationItems.getItem(2));
        editFrag.setStudent(student);
    }

    @Override
    public void StudentAdded() {
        addFrag.clearViews();
        Toast.makeText(this, "Student saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void FailStudentAdd() {
        Toast.makeText(this, "Student already exists", Toast.LENGTH_SHORT).show();
    }
}

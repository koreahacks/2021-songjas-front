package com.example.timmo_songjas;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.timmo_songjas.chatting.fragment.ChatlistFragment;
import com.example.timmo_songjas.chatting.fragment.PeopleFragment;
import com.example.timmo_songjas.feature.member.MemberFindFragment;
import com.example.timmo_songjas.feature.profile.ProfileFragment;
import com.example.timmo_songjas.feature.project.ProjectFindFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    //연결한 프래그먼트
    private ProjectFindFragment projectFindFragment = new ProjectFindFragment();
    private MemberFindFragment memberFindFragment = new MemberFindFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ((FragmentTransaction) transaction).replace(R.id.main_content, projectFindFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.cube_menu:
                    transaction.replace(R.id.main_content, projectFindFragment).commitAllowingStateLoss();
                    break;
                case R.id.overlab_menu:
                    transaction.replace(R.id.main_content, memberFindFragment).commitAllowingStateLoss();
                    break;
                case R.id.profile_menu:
                    transaction.replace(R.id.main_content, profileFragment).commitAllowingStateLoss();
                    return true;

                case R.id.chat_menu:
                    transaction.replace(R.id.main_content, new ChatlistFragment()).commitAllowingStateLoss();
                    return true;
                case R.id.people_menu:
                    transaction.replace(R.id.main_content, new PeopleFragment()).commitAllowingStateLoss();
                    return true;
            }
            return true;
        }
    }
}
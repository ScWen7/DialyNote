package scwen.com.dialynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        enableShortcuts();

        setSupportActionBar(mToolbar);
        mToolbar.setTitle("首页");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);


        mNavView.setNavigationItemSelectedListener(this);


    }

    //如果有Menu,创建完后,系统会自动添加到ToolBar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main_drawer,menu);
        return true;
    }

    private void enableShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager manager = getSystemService(ShortcutManager.class);

            ShortcutInfo search = new ShortcutInfo.Builder(this, "search_weather")
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_search))
                    .setLongLabel(getString(R.string.shortcut_label_search))
                    .setShortLabel(getString(R.string.shortcut_label_search))
                    .setIntent(
                            new Intent(MainActivity.this, WeatherActivity.class)
                                    .setAction(Intent.ACTION_VIEW)
                                    .addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                    )
                    .build();
            ShortcutInfo addArticle = new ShortcutInfo.Builder(this, "publish_article")
                    .setLongLabel(getString(R.string.shortcut_label_publish_topic))
                    .setShortLabel(getString(R.string.shortcut_label_publish_topic))
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_add))
                    .setIntent(
                            new Intent(MainActivity.this, PublishTopicActivity.class)
                                    .setAction(Intent.ACTION_VIEW)
                                    .addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                    )
                    .build();
            ShortcutInfo addTopic = new ShortcutInfo.Builder(this, "publish_topic")
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_add))
                    .setLongLabel(getString(R.string.shortcut_label_publish_article))
                    .setShortLabel(getString(R.string.shortcut_label_publish_article))
                    .setIntent(
                            new Intent(MainActivity.this, PublishTopicActivity.class)
                                    .setAction("io.github.marktony.espresso.mvp.addpackage.AddPackageActivity")
                                    .addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                    )
                    .build();

            manager.setDynamicShortcuts(Arrays.asList(addTopic, addArticle, search));
        }

    }

    /**
     * Close the drawer when a back click is called.
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Handle different items of the navigation drawer
     * @param item The selected item.
     * @return Selected or not.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            mNavView.setCheckedItem(R.id.nav_home);
            Toast.makeText(MainActivity.this, "首页", Toast.LENGTH_SHORT).show();

        }  else if (id == R.id.nav_settings) {

            mNavView.setCheckedItem(R.id.nav_settings);
           Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_about) {

            mNavView.setCheckedItem(R.id.nav_about);
          Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

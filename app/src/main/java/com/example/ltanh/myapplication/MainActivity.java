package com.example.ltanh.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView studentListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> studentInfoList;
    private Classroom classroom;
    private TextView contextMenuTarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Tìm nút hiển thị Popup Menu
        Button showPopupButton = (Button) findViewById(R.id.showPopupButton);

        // Xử lý sự kiện nhấn nút
        showPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        // Tìm TextView để hiển thị Context Menu
        contextMenuTarget = (TextView) findViewById(R.id.contextMenuTarget);

        // Đăng ký Context Menu cho TextView
        registerForContextMenu(contextMenuTarget);
    }
    // Tạo Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Kiểm tra View đang hiển thị Context Menu
        if (v.getId() == R.id.contextMenuTarget) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }
    }

    // Xử lý sự kiện khi chọn một mục trong Context Menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_option1:
                Toast.makeText(this, "Bạn chọn Tùy chọn 1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.context_option2:
                Toast.makeText(this, "Bạn chọn Tùy chọn 2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.context_option3:
                Toast.makeText(this, "Bạn chọn Tùy chọn 3", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void showPopupMenu(View view) {
        // Tạo PopupMenu
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popupmenu_demo, popupMenu.getMenu());

        // Xử lý sự kiện khi chọn một mục trong Popup Menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_option1:
                        Toast.makeText(MainActivity.this, "Bạn chọn Tùy chọn 1", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.popup_option2:
                        Toast.makeText(MainActivity.this, "Bạn chọn Tùy chọn 2", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.popup_option3:
                        Toast.makeText(MainActivity.this, "Bạn chọn Tùy chọn 3", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show(); // Hiển thị Popup Menu
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_demo, menu); // Gắn tệp menu_demo.xml vào Activity
        return true;
    }

    // Xử lý sự kiện khi chọn một mục menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Toast.makeText(this, "Thêm được chọn", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_edit:
                Toast.makeText(this, "Sửa được chọn", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_delete:
                Toast.makeText(this, "Xóa được chọn", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

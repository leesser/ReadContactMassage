package com.lss.readcontactmassage;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private List<Person> persons;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    lv.setAdapter(new MyAdapter());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= (ListView) findViewById(R.id.lv);
        new Thread(){
            @Override
            public void run() {
                addData();
                getPersons();
                if (persons.size()>0){
//                    Message msg = Message.obtain();
//                    msg.what=100;
//                    handler.handleMessage(msg);
                    handler.sendEmptyMessage(100);
                }
            }
        }.start();

    }
    public void addData(){
        PersonDao dao = new PersonDao(this);
        long number = 110;
        Random random=new Random();
        for (int i=0;i<10;i++) {
            dao.add("love"+i,Long.toString(number+i),random.nextInt(5000));
        }

    }
    private void getPersons(){
        String path="content://com.lss.readcontactmassage.PersonDBProvider/query";
        Uri uri = Uri.parse(path);
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        persons=new ArrayList<Person>();
        if (cursor==null){
            return;
        }
        while (cursor.moveToLast()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            Person person = new Person(id, name, number);
            persons.add(person);
        }
        cursor.close();
    }
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            return persons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Person person = persons.get(position);
            View view = View.inflate(MainActivity.this, R.layout.list_item, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText("姓名:"+person.getName());
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_phone.setText("电话:"+person.getNumber());
            return view;
        }
    }
}

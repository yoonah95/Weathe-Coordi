package com.example.parkeunjeong.mycoordinator;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView recyclerView;

    private MyAdapter myAdapter;
    private List<Board> mBoardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        recyclerView = findViewById(R.id.main_recycler_view);
        findViewById(R.id.write_floating).setOnClickListener(this);

        mBoardList = new ArrayList<>();
        mStore.collection("Board").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                    String id = (String)dc.getDocument().getData().get("id");
                    String title = (String)dc.getDocument().get("title");
                    String contents = (String) dc.getDocument().get("contents");
                    String name = (String)dc.getDocument().getData().get("name");
                    Board data = new Board(id, title, contents, name);

                    mBoardList.add(data);
                }
                myAdapter = new MyAdapter(mBoardList);
                recyclerView.setAdapter(myAdapter);
            }
        });

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, WriteActivity.class));
    }

    private class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MainViewHolder>{
        private List<Board> mBoardList;

        public MyAdapter(List<Board> mBoardList){
            this.mBoardList = mBoardList;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MainViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
            Board data = mBoardList.get(i);
            mainViewHolder.title.setText(data.getTitie());
            mainViewHolder.name.setText(data.getName());

        }

        @Override
        public int getItemCount() {
            return mBoardList.size();
        }

        class MainViewHolder extends  RecyclerView.ViewHolder{

            private TextView title;
            private  TextView name;
            public MainViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title_txt);
                name = itemView.findViewById(R.id.name_txt);
            }
        }

    }
}

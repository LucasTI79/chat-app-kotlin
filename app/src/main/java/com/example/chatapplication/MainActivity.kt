package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView;
    private lateinit var usernameText: TextView;
    private lateinit var userList: ArrayList<User>;
    private lateinit var adapter: UserAdapter;
    private lateinit var mAuth: FirebaseAuth;
    private lateinit var mDebRef: DatabaseReference;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance();
        mDebRef = FirebaseDatabase.getInstance().getReference();

        userList = ArrayList();
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView);
        usernameText = findViewById(R.id.txt_username);
        println(mAuth.currentUser)
        usernameText.setText(mAuth.currentUser?.email.toString());

        userRecyclerView.layoutManager = LinearLayoutManager(this);
        userRecyclerView.adapter = adapter;

        mDebRef.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear();

                for(postSnapshot in snapshot.children){
                    var currentUser = postSnapshot.getValue(User::class.java);
                    if(currentUser?.uid != mAuth.currentUser?.uid) {
                        userList.add(currentUser!!)
                    }
                }

                adapter.notifyDataSetChanged();
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            mAuth.signOut()
            var intent = Intent(this@MainActivity, Login::class.java)
            finish()
            startActivity(intent)
            return true;
        }
        return true;
    }
}
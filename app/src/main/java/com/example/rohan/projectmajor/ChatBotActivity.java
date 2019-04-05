package com.example.rohan.projectmajor;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;



public class ChatBotActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    EditText editText;
    RelativeLayout addBtn;
    MessageAdapter messageAdapter;
    List<ResponseMessage> responseMessageList =new ArrayList<>();
    FirebaseUser user;
    DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);




        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        addBtn = findViewById(R.id.addBtn);

        recyclerView.setHasFixedSize(true);

        messageAdapter = new MessageAdapter(responseMessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);
        user= FirebaseAuth.getInstance().getCurrentUser();
       //databaseReference= FirebaseDatabase.getInstance().getReference("UsersChat");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s= editText.getText().toString().toLowerCase();
                CharSequence cs = "home";
                CharSequence cs2="house", cs3="residence", cs4="apartment",cs5="flat";
                CharSequence office="office", office2="workplace", office3="work", office4="job";
                CharSequence friend="friend", friend2="partner";
                CharSequence d1="doctor", d2="hospital", d3="clinic", d4="nursing home";
                CharSequence e1="contact", e2="father", e3="family", e4="emergency", e5="call";
                CharSequence who="who am i", who2="my name", who3="name";
                CharSequence where ="where";
                //ans=s.indexOf("home");
                if(s.contains(cs) || s.contains(cs2) || s.contains(cs3) || s.contains(cs4) || s.contains(cs5))
                {
                    ResponseMessage responseMessage = new ResponseMessage(editText.getText().toString(), true);

                    //Toast.makeText(ChatBotActivity.this, "gyaa", Toast.LENGTH_SHORT).show();
                    responseMessageList.add(responseMessage);
                    String st= "ATS One Hamlet A-503 near JIIT Sec 62, Noida, UP. Go to map to navigate. ";
                    //  ans=-2;
                    ResponseMessage responseMessage1 = new ResponseMessage(st,false);
                    responseMessageList.add(responseMessage1);
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                }
                else if (s.contains(friend) || s.contains(friend2))
                {
                    ResponseMessage responseMessage = new ResponseMessage(editText.getText().toString(), true);

                    //Toast.makeText(ChatBotActivity.this, "gyaa", Toast.LENGTH_SHORT).show();
                    responseMessageList.add(responseMessage);
                    String st= "Your bestfriend is Sheema Rathore you can contact her by calling on 8905765410 ";
                    //  ans=-2;
                    ResponseMessage responseMessage1 = new ResponseMessage(st,false);
                    responseMessageList.add(responseMessage1);
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                }
                else if (s.contains(office) || s.contains(office2) || s.contains(office3) || s.contains(office4) )
                {
                    ResponseMessage responseMessage = new ResponseMessage(editText.getText().toString(), true);

                    //Toast.makeText(ChatBotActivity.this, "gyaa", Toast.LENGTH_SHORT).show();
                    responseMessageList.add(responseMessage);
                    String st= "Your office is HCL technologies in Sector 127 Noida. Mr. Ashish Goel[BOSS]. You can navigate to your office by going on the map present in this app ";
                    //  ans=-2;
                    ResponseMessage responseMessage1 = new ResponseMessage(st,false);
                    responseMessageList.add(responseMessage1);
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                }
                else if(s.contains(d1) || s.contains(d2) || s.contains(d3) || s.contains(d4))
                {
                    ResponseMessage responseMessage = new ResponseMessage(editText.getText().toString(), true);

                    //Toast.makeText(ChatBotActivity.this, "gyaa", Toast.LENGTH_SHORT).show();
                    responseMessageList.add(responseMessage);
                    String st= "Dr. Kunal Malhotra. Jaypee Hospital Block A Sec 132. Your family doctor. ";
                    //  ans=-2;
                    ResponseMessage responseMessage1 = new ResponseMessage(st,false);
                    responseMessageList.add(responseMessage1);
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                else if(s.contains(e1) || s.contains(e2) || s.contains(e3) || s.contains(e4) || s.contains(e5))
                {
                    ResponseMessage responseMessage = new ResponseMessage(editText.getText().toString(), true);

                    //Toast.makeText(ChatBotActivity.this, "gyaa", Toast.LENGTH_SHORT).show();
                    responseMessageList.add(responseMessage);
                    String st= "Ramesh Tripathi, your Father Mob: 9988776352 ";
                    //  ans=-2;
                    ResponseMessage responseMessage1 = new ResponseMessage(st,false);
                    responseMessageList.add(responseMessage1);
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                else if(s.contains(who) || s.contains(who2) || s.contains(who3))
                {
                    ResponseMessage responseMessage = new ResponseMessage(editText.getText().toString(), true);

                    //Toast.makeText(ChatBotActivity.this, "gyaa", Toast.LENGTH_SHORT).show();
                    responseMessageList.add(responseMessage);
                    String st= "you are: "+user.getDisplayName();
                    //  ans=-2;
                    ResponseMessage responseMessage1 = new ResponseMessage(st,false);
                    responseMessageList.add(responseMessage1);
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                else if (s.contains(where))
                {
                    ResponseMessage responseMessage = new ResponseMessage(editText.getText().toString(), true);

                    //Toast.makeText(ChatBotActivity.this, "gyaa", Toast.LENGTH_SHORT).show();
                    responseMessageList.add(responseMessage);
                    String st= "Sector 128 JIIT Noida ";
                    //  ans=-2;
                    ResponseMessage responseMessage1 = new ResponseMessage(st,false);
                    responseMessageList.add(responseMessage1);
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                else
                {
                    //Toast.makeText(ChatBotActivity.this, "false", Toast.LENGTH_SHORT).show();
                    if(!editText.getText().toString().equals(""))
                    {
                        ResponseMessage responseMessage = new ResponseMessage(editText.getText().toString(), true);

                        responseMessageList.add(responseMessage);
                        //int ans=-2;

                        new simisimiAPI().execute(responseMessageList);

                    }
                    else
                    {
                        Toast.makeText(ChatBotActivity.this, "Please enter any text !!!", Toast.LENGTH_SHORT).show();
                    }
                }


                editText.setText("");

            }
        });

    }
    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }


    private class simisimiAPI extends AsyncTask<List<ResponseMessage>,Void,String> {

        String stream = null;
        List<ResponseMessage> models;
        String text= editText.getText().toString();



        @SafeVarargs
        @Override
        protected final String doInBackground(List<ResponseMessage>... lists) {
            //String simi=;
            String url= String.format("http://sandbox.api.simsimi.com/request.p?key=1836677c-e14c-493f-8570-b3152a7be262&lc=en&ft=1.0&text=%s",text);
            models=lists[0];
            HttpDataHandler httpDataHandler= new HttpDataHandler();
            stream=httpDataHandler.GetHttpData(url);

            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson=new Gson();
            SimisimiModel response=gson.fromJson(s,SimisimiModel.class);

            ResponseMessage responseMessage = new ResponseMessage(response.getResponse(),false);
            models.add(responseMessage);
            //responseMessageList.add(responseMessage);




            messageAdapter.notifyDataSetChanged();
           // databaseReference.child(user.getUid()).setValue(responseMessageList);
            if (!isLastVisible())
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);


        }
    }
}

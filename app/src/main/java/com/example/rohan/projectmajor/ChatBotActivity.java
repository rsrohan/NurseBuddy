package com.example.rohan.projectmajor;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;


public class ChatBotActivity extends AppCompatActivity implements AIListener {

    RecyclerView recyclerView;
    EditText editText;
    RelativeLayout addBtn;
    DatabaseReference ref;
    FirebaseRecyclerAdapter<ChatMessage,chat_rec> adapter;
    Boolean flagFab = true;
    FirebaseUser user;

    private AIService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},1);


        recyclerView = findViewById(R.id.recyclerViewqq);
        editText = findViewById(R.id.editTextqq);
        addBtn = findViewById(R.id.addBtnqq);


        user=FirebaseAuth.getInstance().getCurrentUser();
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        ref = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        ref.keepSynced(true);

        final AIConfiguration config = new AIConfiguration("b3d569ed97794988a54ef77dbcb1fd50",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        final AIDataService aiDataService = new AIDataService(config);

        final AIRequest aiRequest = new AIRequest();



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString().trim();

                if (!message.equals("")) {


                    ChatMessage chatMessage = new ChatMessage(message, "user");
                    ref.child("chat").push().setValue(chatMessage);

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

                        String st= "ATS One Hamlet A-503 near JIIT Sec 62, Noida, UP. Go to map to navigate. ";
                        //  ans=-2;
                        ChatMessage chatMessage1 = new ChatMessage(st, "bot");
                        ref.child("chat").push().setValue(chatMessage1);


                    }
                    else if (s.contains(friend) || s.contains(friend2))
                    {

                        String st= "Your bestfriend is Sheema Rathore you can contact her by calling on 8905765410 ";
                        //  ans=-2;
                        ChatMessage chatMessage1 = new ChatMessage(st, "bot");
                        ref.child("chat").push().setValue(chatMessage1);


                    }
                    else if (s.contains(office) || s.contains(office2) || s.contains(office3) || s.contains(office4) )
                    {

                        String st= "Your office is HCL technologies in Sector 127 Noida. Mr. Ashish Goel[BOSS]. You can navigate to your office by going on the map present in this app ";
                        //  ans=-2;
                        ChatMessage chatMessage1 = new ChatMessage(st, "bot");
                        ref.child("chat").push().setValue(chatMessage1);


                    }
                    else if(s.contains(d1) || s.contains(d2) || s.contains(d3) || s.contains(d4))
                    {

                        String st= "Dr. Kunal Malhotra. Jaypee Hospital Block A Sec 132. Your family doctor. ";
                        //  ans=-2;
                        ChatMessage chatMessage1 = new ChatMessage(st, "bot");
                        ref.child("chat").push().setValue(chatMessage1);
                    }
                    else if(s.contains(e1) || s.contains(e2) || s.contains(e3) || s.contains(e4) || s.contains(e5))
                    {

                        String st= "Ramesh Tripathi, your Father Mob: 9988776352 ";
                        //  ans=-2;
                        ChatMessage chatMessage1 = new ChatMessage(st, "bot");
                        ref.child("chat").push().setValue(chatMessage1);
                    }
                    else if(s.contains(who) || s.contains(who2) || s.contains(who3))
                    {

                        String st= "you are: "+user.getDisplayName();
                        //  ans=-2;
                        ChatMessage chatMessage1 = new ChatMessage(st, "bot");
                        ref.child("chat").push().setValue(chatMessage1);

                    }
                    else if (s.contains(where))
                    {

                        String st= "Sector 128 JIIT Noida ";
                        //  ans=-2;
                        ChatMessage chatMessage1 = new ChatMessage(st, "bot");
                        ref.child("chat").push().setValue(chatMessage1);
                    }else{

                    aiRequest.setQuery(message);
                    new AsyncTask<AIRequest,Void, AIResponse>(){

                        @Override
                        protected AIResponse doInBackground(AIRequest... aiRequests) {
                            final AIRequest request = aiRequests[0];
                            try {
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }
                        @Override
                        protected void onPostExecute(AIResponse response) {
                            if (response != null) {

                                Result result = response.getResult();
                                String reply = result.getFulfillment().getSpeech();
                                ChatMessage chatMessage = new ChatMessage(reply, "bot");
                                ref.child("chat").push().setValue(chatMessage);
                            }
                        }
                    }.execute(aiRequest);
                }}
                else {
                    aiService.startListening();
                }

                editText.setText("");

            }
        });



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ImageView fab_img = (ImageView)findViewById(R.id.fab_img);
                Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.ic_send_white_24dp);
                Bitmap img1 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_mic_white_24dp);


                if (s.toString().trim().length()!=0 && flagFab){
                    ImageViewAnimatedChange(ChatBotActivity.this,fab_img,img);
                    flagFab=false;

                }
                else if (s.toString().trim().length()==0){
                    ImageViewAnimatedChange(ChatBotActivity.this,fab_img,img1);
                    flagFab=true;

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<ChatMessage, chat_rec>(ChatMessage.class,R.layout.msglist,chat_rec.class,ref.child("chat")) {
            @NonNull



            @Override
            protected void populateViewHolder(chat_rec viewHolder, ChatMessage model, int position) {
                if (model.getMsgUser().equals("user")) {


                    viewHolder.rightText.setText(model.getMsgText());

                    viewHolder.rightText.setVisibility(View.VISIBLE);
                    viewHolder.leftText.setVisibility(View.GONE);
                }
                else {
                    viewHolder.leftText.setText(model.getMsgText());

                    viewHolder.rightText.setVisibility(View.GONE);
                    viewHolder.leftText.setVisibility(View.VISIBLE);
                }
            }




        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int msgCount = adapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 ||
                        (positionStart >= (msgCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);

                }

            }
        });

        recyclerView.setAdapter(adapter);


    }
    public void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.zoom_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, R.anim.zoom_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    @Override
    public void onResult(ai.api.model.AIResponse response) {


        Result result = response.getResult();

        String message = result.getResolvedQuery();
        ChatMessage chatMessage0 = new ChatMessage(message, "user");
        ref.child("chat").push().setValue(chatMessage0);


        String reply = result.getFulfillment().getSpeech();
        ChatMessage chatMessage = new ChatMessage(reply, "bot");
        ref.child("chat").push().setValue(chatMessage);


    }

    @Override
    public void onError(ai.api.model.AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}


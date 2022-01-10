package com.example.sunshine.user;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sunshine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import androidx.appcompat.app.AppCompatActivity;

public class SettingFAQActivity extends AppCompatActivity {

    TextView first_faq_answer;
    TextView second_faq_answer;
    TextView third_faq_answer;
    TextView fourth_faq_answer;
    TextView fifth_faq_answer;
    TextView sixth_faq_answer;
    TextView seventh_faq_answer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_faq);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        first_faq_answer = (TextView) findViewById(R.id.first_answer);
        second_faq_answer = (TextView) findViewById(R.id.second_answer);
        third_faq_answer = (TextView) findViewById(R.id.third_answer);
        fourth_faq_answer = (TextView) findViewById(R.id.fourth_answer);
        fifth_faq_answer = (TextView) findViewById(R.id.fifth_answer);
        sixth_faq_answer = (TextView) findViewById(R.id.sixth_answer);
        seventh_faq_answer = (TextView) findViewById(R.id.seventh_answer);

        StringBuilder firstAnswer = new StringBuilder();
        firstAnswer.append("<strong>").append("A: ").append("</strong>");
        firstAnswer.append("First, you must request for reviewer permission in settings. " +
                "Then, you need to contact the administrators through <strong>john@example.com</strong> and follow further instructions there to be approved as a reviewer.");


        StringBuilder secondAnswer = new StringBuilder();
        secondAnswer.append("<strong>").append("A: ").append("</strong>");
        secondAnswer.append("No, it won’t be deleted unless it violates our Community Rules. " +
                "But keep in mind that if you have too many of negative reviews, your posts will be less likely displayed on homepage");

        StringBuilder thirdAnswer = new StringBuilder();
        thirdAnswer.append("<strong>").append("A: ").append("</strong>");
        thirdAnswer.append(" You need to contact the administrators through <strong>john@example.com</strong> " +
                "and follow further instructions there to unban your account.");

        StringBuilder fourthAnswer = new StringBuilder();
        fourthAnswer.append("<strong>").append("A: ").append("</strong>");
        fourthAnswer.append("Yes, you can.");

        StringBuilder fifthAnswer = new StringBuilder();
        fifthAnswer.append("<strong>").append("A: ").append("</strong>");
        fifthAnswer.append("Yes, you can. As long as you have your password.");

        StringBuilder sixthAnswer = new StringBuilder();
        sixthAnswer.append("<strong>").append("A: ").append("</strong>");
        sixthAnswer.append(" You can do that by pressing <strong>...</strong> on the top right corner and then choose “Report”. " +
                "Please notice that we only allow a user to report once every 5 minutes to avoid spamming.");

        StringBuilder seventhAnswer = new StringBuilder();
        seventhAnswer.append("<strong>").append("A: ").append("</strong>");
        seventhAnswer.append("There is not. But there are some books/stories that are inappropriate to children. " +
                "We suggest what you should be at least 13 to use the app");

        first_faq_answer.setText(Html.fromHtml(firstAnswer.toString()));
        second_faq_answer.setText(Html.fromHtml(secondAnswer.toString()));
        third_faq_answer.setText(Html.fromHtml(thirdAnswer.toString()));
        fourth_faq_answer.setText(Html.fromHtml(fourthAnswer.toString()));
        fifth_faq_answer.setText(Html.fromHtml(fifthAnswer.toString()));
        sixth_faq_answer.setText(Html.fromHtml(sixthAnswer.toString()));
        seventh_faq_answer.setText(Html.fromHtml(seventhAnswer.toString()));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}

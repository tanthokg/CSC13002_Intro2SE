package com.example.sunshine.database;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DatabaseUtil {

    private FirebaseAuth authentication = FirebaseAuth.getInstance();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    public boolean signUp(Authentication auth) {
        if (!checkExistedUser(auth.getUsername())) {
            authentication.createUserWithEmailAndPassword(auth.getUsername() + "@gmail.com", auth.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                auth.setPassword("");
                                database.collection("Authentication")
                                        .add(auth)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(@NonNull DocumentReference documentReference) {
                                                User user = new User(auth.getUsername());
                                                database.collection("User")
                                                        .add(user);
                                            }
                                        });
                            }
                        }
                    });
            authentication.signOut();
            return true;
        }
        return false;
    }

    public boolean signIn(String username, String password) {
        authentication.signInWithEmailAndPassword(username + "@gmail.com", password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            return;
                    }
                });
        return false;
    }

    public boolean logOut() {
        return false;
    }

    public boolean updateProfile(User user) {
        return false;
    }

    public boolean createPost(Post post) {
        return false;
    }

    public boolean updatePost(int postID, String content) {
        return false;
    }

    public boolean deletePost(int postID) {
        return false;
    }

    public boolean updateUpvote(int postID) {
        return false;
    }

    public boolean updateDownvote(int postID) {
        return false;
    }

    public boolean addComment(Comment comment, int postID) {
        return false;
    }

    public boolean deleteComment(int commentID) {
        return false;
    }

    public boolean updateUserType(int userID) {
        return false;
    }

    private boolean checkExistedUser(String username) {
        Query query = database.collection("Authentication").whereEqualTo("username", username);
        if (query == null)
            return false;
        return true;
    }
}

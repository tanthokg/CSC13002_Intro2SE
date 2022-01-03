package com.example.sunshine.admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Request;
import com.example.sunshine.user.PostAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;


public class PermissionFragment extends Fragment {
    RecyclerView permissionRecView;
    PermissionAdapter adapter;
    Context context;
    private List<Request> perList;
    private String currentUserId;
    public PermissionFragment()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View permissionFragment = inflater.inflate(R.layout.admin_fragment_permission, container, false);

        permissionRecView = permissionFragment.findViewById(R.id.permissionRecView);
        adapter = new PermissionAdapter(context, perList, currentUserId);
        permissionRecView.setAdapter(adapter);
        permissionRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        return permissionFragment;
    }
}
//package com.example.sunshine.admin;
//
//        import android.content.Context;
//        import android.os.Bundle;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.Toast;
//
//        import androidx.annotation.NonNull;
//        import androidx.annotation.Nullable;
//        import androidx.fragment.app.Fragment;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import com.example.sunshine.R;
//        import com.example.sunshine.TimestampConverter;
//        import com.example.sunshine.database.Post;
//        import com.example.sunshine.database.Request;
//        import com.google.android.gms.tasks.OnCompleteListener;
//        import com.google.android.gms.tasks.Task;
//        import com.google.firebase.auth.FirebaseAuth;
//        import com.google.firebase.firestore.DocumentChange;
//        import com.google.firebase.firestore.DocumentSnapshot;
//        import com.google.firebase.firestore.EventListener;
//        import com.google.firebase.firestore.FirebaseFirestore;
//        import com.google.firebase.firestore.FirebaseFirestoreException;
//        import com.google.firebase.firestore.ListenerRegistration;
//        import com.google.firebase.firestore.Query;
//        import com.google.firebase.firestore.QuerySnapshot;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//public class PermissionFragment extends Fragment {
//
//    RecyclerView permissionRecView;
//    PermissionAdapter adapter;
//    Context context;
//    private List<Request> permisionList;
//    private FirebaseFirestore firebaseFirestore;
//    private FirebaseAuth auth;
//    private String currentUserId;
//    //  private String bookName;
////    private boolean isReadLater;
//
//    public PermissionFragment(Context context, ) {
//        this.context = context;
//        // this.bookName = bookName;
//        //  this.isReadLater = false;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View postFragment = inflater.inflate(R.layout.fragment_post, container, false);
//
//        List = new ArrayList<>();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//        currentUserId = auth.getCurrentUser().getUid();
//        if (bookName == currentUserId)
//            isReadLater = true;
//        //listenDataChanged();
//
//        postRecView = postFragment.findViewById(R.id.postRecView);
//        adapter = new PostAdapter(context, postList, currentUserId, isReadLater, this);
//        postRecView.setAdapter(adapter);
//        postRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        listenDataChanged();
//        return postFragment;
//    }
//
//    public void listenDataChanged() {
//        if (!isReadLater) {
//            firebaseFirestore.collection("Post").whereEqualTo("bookName", bookName)
//                    .orderBy("postTime", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
//                @Override
//                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                    if (error == null) {
//                        if (!value.isEmpty()) {
//                            for (DocumentChange doc : value.getDocumentChanges()) {
//                                if (doc.getType() == DocumentChange.Type.ADDED) {
//                                    String postId = doc.getDocument().getId();
//                                    Post reviewPost = doc.getDocument().toObject(Post.class).withId(postId);
//                                    postList.add(reviewPost);
//                                    adapter.notifyDataSetChanged();
//                                } else
//                                    adapter.notifyDataSetChanged();
//                            }
//                        } else
//                            Toast.makeText(context, "No review post for " + bookName, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//        else {
//            postList.clear();
//            adapter.notifyDataSetChanged();
//            firebaseFirestore.collection("User/" + currentUserId + "/Read Later").orderBy("saveTime", Query.Direction.DESCENDING)
//                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                            if (error == null) {
//                                if (!value.isEmpty()) {
//                                    for (DocumentChange doc : value.getDocumentChanges()) {
//                                        if (doc.getType() == DocumentChange.Type.ADDED) {
//                                            String id = doc.getDocument().getId();
//                                            firebaseFirestore.collection("Post").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                    if (task.isSuccessful()) {
//                                                        Post reviewPost = task.getResult().toObject(Post.class).withId(id);
//                                                        if (reviewPost != null)
//                                                        {
//                                                            postList.add(reviewPost);
//                                                            adapter.notifyDataSetChanged();
//                                                        }
//                                                    }
//                                                }
//                                            });
//                                        }
//                                        else
//                                            adapter.notifyDataSetChanged();
//                                    }
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//}

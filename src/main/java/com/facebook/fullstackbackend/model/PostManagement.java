package com.facebook.fullstackbackend.model;

import java.util.ArrayList;
import java.util.Scanner;

import com.facebook.fullstackbackend.repository.DatabaseSql;

public class PostManagement {
    Scanner sc = new Scanner(System.in);
    DatabaseSql<String> database = new DatabaseSql<>();

    public PostManagement(){}

    // Create new post
    public void createPost(User user){
        PostBuilder postBuilder = new PostBuilder(user);
        StringBuilder strBuilder = new StringBuilder();
        System.out.println("Create Post");
        System.out.println("-------------------------");
        System.out.println(user.getName());
        System.out.println("What's on your mind?");
        System.out.println("(Enter \"/end\" to end your content)");
        System.out.println("*************************");
        String content = sc.nextLine();
        while(!content.contains("/end")){
            strBuilder.append(content + "\n");
            content = sc.nextLine();
        }
        postBuilder.setContent(strBuilder.toString());
        System.out.println("*************************");
        System.out.println("Setting of post");
        System.out.println("-------------------------");
        System.out.println("1 - Public");
        System.out.println("2 - Private");
        System.out.println("*************************");
        int choice = sc.nextInt();
        System.out.println("*************************");
        switch(choice){
            case 1 -> postBuilder.setStatus(Post.Status.PUBLIC);
            case 2 -> postBuilder.setStatus(Post.Status.PRIVATE);
        }
        Post post = postBuilder.build();
        System.out.println("0 - Delete draft");
        System.out.println("1 - Post draft");
        System.out.println("*************************");
        choice = sc.nextInt();
        if(choice==1){
            database.uploadPost(post); 
        }

        // check for inappropriate content   
    }

    // Delete existing post
    public void deletePost(Post post){
        database.deletePost(post);
    }

    public Post likePost(Post post, User user){
        post.setLikes(post.getLikes()+1);
        ArrayList<String> likeList = database.getPostList(post, "likeList");
        likeList.add(user.getUsername());
        database.updatePostList(post, "likeList", likeList);
        DatabaseSql<Integer> databaseInt = new DatabaseSql<>();
        databaseInt.updatePost(post, "likes", post.getLikes());
        return post;
    }

    public Post commentPost(Post post, User user){
        StringBuilder strBuilder = new StringBuilder();
        System.out.println("Write your comment.");
        System.out.println("(Enter \"/end\" to end your comment)");
        System.out.println("*************************");
        String content = sc.nextLine();
        while(!content.contains("/end")){
            strBuilder.append(content + "\n");
            content = sc.nextLine();
        }
        String comment = strBuilder.toString();
        System.out.println("0 - Back");
        System.out.println("1 - Post comment");
        int choice = sc.nextInt();
        if(choice==1){
            post.setComments(post.getComments()+1);
            String userComments = user.getUsername() + ":" + comment;
            ArrayList<String> commentList = database.getPostList(post, "commentList");
            commentList.add(userComments);
            database.updatePostList(post, "commentList", commentList);
            DatabaseSql<Integer> databaseInt = new DatabaseSql<>();
            databaseInt.updatePost(post, "comments", post.getComments());
        }
        return post;
    }

    public void viewPost(Post post){
        User user = database.getProfile(post.getUserID());
        System.out.println("*************************");
        System.out.println("\u001B[1m" + user.getName() + "\u001B[0m");     // Bold text
        System.out.println(post.getContent());
        System.out.println("-------------------------");
        System.out.println(post.getLikes() + " likes\t" + post.getComments() + " comments");
        System.out.println("*************************");
    }

    public void viewLikes(Post post){
        ArrayList<String> likeList = database.getPostList(post, "likeList");    // List of usernames of users who like the post
        System.out.println("<" + post.getLikes() + " likes>");
        System.out.println("-------------------------");
        for(String x : likeList){
            System.out.println(database.getProfile(x).getName());   // Display the name of user account, not username
        }
        System.out.println("*************************");
    }   

    public void viewComments(Post post){
        ArrayList<String> commentList = database.getPostList(post, "commentList");
        System.out.println("<" + post.getComments() + " comments>");
        System.out.println("-------------------------");
        for(String x : commentList){
            String[] commentInfo = x.split(":");
            System.out.println(database.getProfile(commentInfo[0]).getName() + ":");
            System.out.println(commentInfo[1]);
            System.out.println();
        }
        System.out.println("*************************");
    }

}
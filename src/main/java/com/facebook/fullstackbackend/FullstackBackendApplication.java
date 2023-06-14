package com.facebook.fullstackbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.facebook.fullstackbackend.model.AccountManagement;
import com.facebook.fullstackbackend.model.User;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class FullstackBackendApplication {

        public static void main(String[] args) {
	        SpringApplication.run(FullstackBackendApplication.class, args);
                FullstackBackendApplication f  = new FullstackBackendApplication();
                System.out.println(LocalDate.now());
                f.startFacebook();
		
        }

        public void startFacebook(){
                Scanner sc = new Scanner(System.in);
                AccountManagement manager = new AccountManagement();
  
                try{
                        int choice1 = 0;
                        while(choice1==0){    
                                User user = null;
                                System.out.println("*************************");
                                System.out.println("\tFacebook");
                                System.out.println("-------------------------");
                                int choice = 0;
                                while(choice!=1 && choice!=2){
                                        System.out.println("1 - Register");
                                        System.out.println("2 - Login");
                                        System.out.println("*************************");
                                        choice = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("*************************");
                                
                                        switch(choice){
                                                case 1: manager.registration();
                                                        user = manager.login();
                                                        break;
                                                case 2: user = manager.login();
                                                        break;
                                        }  
                                }     
                                if(user!=null){
                                        choice1 = 1;
                                        while(choice1>0){
                                                System.out.println("\tHome Page");
                                                System.out.println("-------------------------");
                                                System.out.println("0 - Log out");
                                                System.out.println("1 - My Page");
                                                System.out.println("2 - Search Facebook");
                                                System.out.println("3 - Your friends");
                                                System.out.println("4 - Suggestions");
                                                System.out.println("5 - History");
                                                System.out.println("*************************");
                                                choice1 = sc.nextInt();
                                                sc.nextLine();
                                                System.out.println("*************************");
                                                switch(choice1){
                                                        case 1 -> manager.viewMyPage();
                                                        case 2 -> manager.searchUsers();
                                                        case 3 -> manager.displayFriends();
                                                        case 4 -> manager.displayRecommendedUsers();
                                                        case 5 -> manager.displayHistory();
                                                }
                                        }
                                }
                        }
                }catch(InputMismatchException e){
                        sc.nextLine();
                        startFacebook();
                }
        }

}

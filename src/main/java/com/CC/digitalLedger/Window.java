package com.CC.digitalLedger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Window {
    public Window() throws Exception {
        ServerRequest server = new ServerRequest("https://7e65-192-70-253-78.ngrok.io"); //https://e2ab-192-70-253-79.ngrok.io

        //FRAME
        JFrame createFrame = new JFrame("Generation Page");
        createFrame.setSize(600, 400);
        createFrame.setVisible(true);
        createFrame.setDefaultCloseOperation(3);

        JFrame newFrame = new JFrame("Gold Card Money Transferring System");
        newFrame.setDefaultCloseOperation(3);

         //BUTTON
        JButton sendButton = new JButton("Transfer Funds");
        JButton userButton = new JButton("Find user");
        JButton createButton = new JButton("Create Account");

        //TEXT AREA
        JTextField inputUser = new JTextField();
        JTextField getUsers = new JTextField();

        JTextArea displayTransactions = new JTextArea();
        displayTransactions.setWrapStyleWord(true);
        displayTransactions.setLineWrap(true);
        displayTransactions.setText(server.getLedger());
        JTextArea displayBalance = new JTextArea();
        displayBalance.setText(server.getBalance());

        //JLABEL
        JLabel currentBalance = new JLabel("Current Balance:");
        JLabel enterPublicKey = new JLabel("Search user by public key or name: "); //finds user based on public key or name
        JLabel transactionHistory = new JLabel("Transaction History:");
        JLabel publicKey = new JLabel("Public Key: ");
        publicKey.setText("<html>"+ server.secret.publicKeyAsString() +"</html>");
        JLabel privateKey = new JLabel("Private Key: ");
        privateKey.setText("<html>"+ server.secret.privateKeyAsString() +"</html>");
        JLabel createUser = new JLabel("Input your name: ");
        JLabel welcomeUser = new JLabel("Welcome to the Gold Card Money Transferring System, " + server.currentUser());
        JLabel information = new JLabel();
        information.setText("<html>"+ "Welcome to the Gold Card money transferring website! By clicking 'Create Account', we will have generated a pair of public and private keys for you, which you will be able to see on the home page. DO NOT SHARE YOUR PRIVATE KEY INFORMATION." +"</html>");

        JTextField displayFunds = new JTextField(); //for the second window
        JLabel newWindowLabel = new JLabel("              Enter amount to transfer to " + server.currentUser());

        //PANEL
        JPanel newPanel = new JPanel();
        JPanel createPanel = new JPanel();
        createFrame.add(createPanel);
        GridLayout loginGrid = new GridLayout(6, 1);
        createPanel.setLayout(loginGrid);
        createPanel.add(createUser);
        createPanel.add(inputUser);
        createPanel.add(information);
        createPanel.add(createButton);
        createPanel.revalidate();


        class createButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                //open new window
                inputUser.getText(); //Name is already on server
                try {
                    server.instantiateUser(inputUser.getText());
                }
                catch(Exception e2) {
                    System.out.println("Failed to retrieve user info");
                }
                newFrame.setSize(900, 600);
                newFrame.setVisible(true);

                newFrame.add(newPanel);
                GridLayout newGrid = new GridLayout(10, 1);
                newPanel.setLayout(newGrid);
                newPanel.add(welcomeUser);
                newPanel.add(currentBalance);
                newPanel.add(displayBalance);
                newPanel.add(enterPublicKey);
                newPanel.add(getUsers);
                newPanel.add(userButton);
                newPanel.add(transactionHistory);
                newPanel.add(displayTransactions);
                newPanel.add(publicKey);
                newPanel.add(privateKey);
                createFrame.setVisible(false);
            }
        }
        createButton.addActionListener(new createButtonListener());

        JFrame amountFrame = new JFrame("User: " + server.currentUser());
        class userButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e){
                //OPEN NEW WINDOW
                getUsers.getText();
                try {
                server.currentUser();
                }
                catch(Exception e3) {
                    System.out.println("Failed to retrieve user from public key or name. Please try again");
                }
                amountFrame.setVisible(true);
                amountFrame.setSize(400, 200);

                //make sure this specific window is with that specific user
                JPanel amountPanel = new JPanel();
                amountFrame.add(amountPanel);

                GridLayout grid = new GridLayout(3, 1);
                amountPanel.setLayout(grid);
                amountPanel.add(newWindowLabel);
                amountPanel.add(displayFunds);
                amountPanel.add(sendButton);
            }
        }
        userButton.addActionListener(new userButtonListener());

        class sendButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    server.send();
                }
                catch(Exception e1) {
                    System.out.println("info didn't send. check button");
                    }
                amountFrame.dispose();
                System.out.println("Money successfully transferred");
                }
            }
        sendButton.addActionListener(new sendButtonListener());
        //makes everything appear on GUI window
        newPanel.revalidate();
    }
}


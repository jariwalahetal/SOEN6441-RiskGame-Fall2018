package com.risk.view;

import com.risk.model.Game;
import com.risk.model.Player;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PlayerWorldDominationView implements Observer {



    // Player World Domination Button
    private static JButton playerWorldDominationViewJButton;
    private static JTable playerRecordsJTable;
    private static JFrame playerWorldDominationViewJFrame;
    private static JPanel playerWorldDominationViewJPanel;


    public void setPlayerWorldDominationView(Game game){

               /* // array of percentage of map controlled by each player
                Float[] mapPercent = new Float[5];
                int i=0;
                HashMap<Integer,Float> percentageMap =  gameObj.getPercentageOfMapControlledForEachPlayer();
                for (java.util.Map.Entry<Integer, Float> entry : percentageMap.entrySet()) {
                    float value = entry.getValue();
                    mapPercent[i] = value;
                    i++;
                }*/
               ArrayList<Player> playerNames = game.getAllPlayers();
                String[] p_name = new String[5];
                int i=0;
                for (Player obj : playerNames ) {
                    String name = obj.getName();
                    p_name[i] = name;
                    i++;
                }
                String[] columns_header = { "Attributes", "Player A", "Player B", "Player C", "Player D", "Player E" };
                String[][] rows = { { "percentage", "10", "2", "5", "6", "3" },
                        { "continents controlled", "1", "0", "0", "0", "0" },
                        { "total army", "50", "10", "12", "0", "18" } };

                playerWorldDominationViewJFrame = new JFrame("Player World Domination View");
                playerWorldDominationViewJPanel = new JPanel(new BorderLayout());
                //JTable playerRecordsJTable = new JTable(rows, columns_header);
                playerRecordsJTable = new JTable(rows,columns_header);
                playerRecordsJTable.setBounds(20,
                        playerWorldDominationViewJFrame.getY() + 20 + playerWorldDominationViewJFrame.getHeight(), 550,
                        350);

                JTableHeader header = playerRecordsJTable.getTableHeader();
                playerWorldDominationViewJFrame.setSize(600, 200);
                playerWorldDominationViewJFrame.setLocationRelativeTo(null);
                playerWorldDominationViewJFrame.setVisible(true);
                playerWorldDominationViewJFrame.add(playerWorldDominationViewJPanel);
                playerWorldDominationViewJPanel.add(header, BorderLayout.NORTH);
                playerWorldDominationViewJPanel.add(playerRecordsJTable, BorderLayout.CENTER);

    }

    @Override
    public void update(Observable obj, Object arg) {
        Game game = ((Game)obj);
        setPlayerWorldDominationView(game);
    }
}

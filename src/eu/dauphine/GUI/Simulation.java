package eu.dauphine.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulation {
    private JButton azeaButton;

    public Simulation() {
        azeaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("OKKKKK");
            }
        });
    }
}

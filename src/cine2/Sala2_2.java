/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Sala2_2 implements ActionListener{
    
    JButton []arrBtn = new JButton[56];
    final int []control = new int[56];
    Timer []tim = new Timer[56];
    JPanel jp1;
    JLabel jl1, jl2;
    JRadioButton rbApartar;
    JRadioButton rbComprar;
    ConexionDB con = new ConexionDB();
    Connection on = con.conexion();
    JButton bt1;

    public Sala2_2 (){
        
        JFrame frMain = new JFrame("Sala de cine 3D pelicula 3");
        frMain.setLayout(new BorderLayout(20, 20));
 
        mostrar();
        
        
        frMain.add(jp1, BorderLayout.SOUTH);
        
        bt1 = new JButton("Regresar");
         bt1.setMargin(new Insets(3, 5, 3, 5));
        bt1.addActionListener(this);
        frMain.setSize(1000, 380);
        frMain.setLocationRelativeTo(null);
        frMain.setVisible(true);
        frMain.setResizable(false);
        frMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }
    
     public void mostrar(){
        jp1 = new JPanel(new GridLayout(9, 3, 20, 7));

        JRadioButton rbComprar = new JRadioButton("compra");
        
        JRadioButton rbApartar = new JRadioButton("apartado");
        
        for(int i=arrBtn.length-1; i>=0; i--){
            arrBtn[i] = new JButton("Asiento"+(i-56));
            final int contador = i;
            try
            {        
                String datos[] = new String [7];
                Statement st = on.createStatement();
                ResultSet rs = st.executeQuery("SELECT status2 FROM `sala2` WHERE `id` = "+(i+1)+";");
                while(rs.next())
                {
                    datos[0] = rs.getString(1);
                    if(datos[0].equals("0"))
                    {
                        arrBtn[contador].setBackground(Color.yellow);
                    }
                    else if(datos[0].equals("1"))
                    {
                        arrBtn[i].setBackground(Color.green);

                    }
                    else if(datos[0].equals("2"))
                    {
                        arrBtn[i].setBackground(Color.red);
                        arrBtn[i].setEnabled(false);
                    }
                }
            }
            catch (SQLException ex)
            {
                Logger.getLogger(Sala1_1.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error: "+ex);
            }
            jp1.add(arrBtn[i]);
            arrBtn[i].setMargin(new Insets(1, 1, 1, 1));
           
            arrBtn[i].addActionListener(new ActionListener() {

                ActionListener evento = new ActionListener() {
                @Override
               public void actionPerformed(ActionEvent evt) {
                    arrBtn[contador].setBackground(Color.yellow);
                    tim[contador].stop();
                    try
                    {
                        PreparedStatement pps = on.prepareStatement("UPDATE `sala2` SET `status2`='0' WHERE `id`= '"+(contador+1)+"';");    
                        pps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Listo");
                    }
                    catch (SQLException ex)
                    {
                        JOptionPane.showMessageDialog(null, "No guardado");
                        JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage());
                    }
                }
            }; 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(rbApartar.isSelected() && control[contador] != 1)
                {
                    arrBtn[contador].setBackground(Color.green);
                    tim[contador] = new Timer(5000, evento);
                    tim[contador].start();
                    try{
                        PreparedStatement pps = on.prepareStatement("UPDATE `sala2` SET `status1`='2' WHERE `id`= '"+(contador+1)+"';");    
                        pps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Listo");
                    }
                    catch (SQLException ex)
                    {
                        JOptionPane.showMessageDialog(null, "No guardado");
                        JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage());
                    }
                    
                }
                else if(rbComprar.isSelected())
                {
                    control[contador] = 1;
                    arrBtn[contador].setBackground(Color.red);
                    arrBtn[contador].setEnabled(false);
                    try{
                        PreparedStatement pps = on.prepareStatement("UPDATE `sala2` SET `status2`='2' WHERE `id`= '"+(contador+1)+"';");    
                        pps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Listo"+(contador+1));
                    }
                    catch (SQLException ex)
                    {
                        JOptionPane.showMessageDialog(null, "No guardado");
                        JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage());
                    }
                    
                }
                
                
                
            }

        });
           
        }
        
        jp1.add(rbComprar, BorderLayout.SOUTH);
        jp1.add(rbApartar, BorderLayout.SOUTH);
        
    }

    public static void main(String[] args) {        
        Sala2_2 trin = new Sala2_2();        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
}
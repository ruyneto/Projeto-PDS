/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author sandr
 */
public class LoginTela2 extends javax.swing.JFrame {

    /**
     * Creates new form LoginTela2
     */
    public LoginTela2() {
        initComponents();
        
        setLocationRelativeTo(null);
    }

    public JButton getBtEntrar() {
        return btEntrar;
    }

    public JButton getBtSair() {
        return btSair;
    }

    public JRadioButton getRdAluno() {
        return rdAluno;
    }

    public JRadioButton getRdCoordenador() {
        return rdCoordenador;
    }

    public JRadioButton getRdMonitor() {
        return rdMonitor;
    }

    public JTextField getCpSenha() {
        return cpSenha;
    }

    public JTextField getCpUsuario() {
        return cpUsuario;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cpUsuario = new view.JTextFieldHint(new JTextField(), "user", "Id do Usuário");
        ;
        cpSenha = new JPassWordFieldHint(new JPasswordField(), "padlock", "Senha")
        ;
        btEntrar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        rdAluno = new javax.swing.JRadioButton();
        rdCoordenador = new javax.swing.JRadioButton();
        rdMonitor = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));

        jLabel1.setBackground(new java.awt.Color(0, 51, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login de Acesso");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        btEntrar.setBackground(new java.awt.Color(58, 65, 84));
        btEntrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btEntrar.setText("Entrar");
        btEntrar.setBorderPainted(false);
        btEntrar.setFocusPainted(false);
        btEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btEntrarMouseExited(evt);
            }
        });

        btSair.setBackground(new java.awt.Color(217, 81, 51));
        btSair.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btSair.setForeground(new java.awt.Color(255, 255, 255));
        btSair.setText("Sair");
        btSair.setBorderPainted(false);
        btSair.setFocusPainted(false);
        btSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btSairMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btSairMouseExited(evt);
            }
        });

        buttonGroup1.add(rdAluno);
        rdAluno.setText("Aluno");

        buttonGroup1.add(rdCoordenador);
        rdCoordenador.setText("Coordenador");

        buttonGroup1.add(rdMonitor);
        rdMonitor.setText("Monitor");

        jLabel2.setText("Modo de Acesso");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cpUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cpSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator2)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel2)
                                .addGap(2, 2, 2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdAluno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdCoordenador)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rdMonitor)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cpUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cpSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdAluno)
                    .addComponent(rdCoordenador)
                    .addComponent(rdMonitor))
                .addGap(18, 18, 18)
                .addComponent(btEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btEntrarMouseEntered
        btEntrar.setBackground(new Color(235, 235, 235));
        btEntrar.setForeground(new Color(58, 65, 84));
    }//GEN-LAST:event_btEntrarMouseEntered

    private void btEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btEntrarMouseExited
        btEntrar.setBackground(new Color(58, 65, 84));
        btEntrar.setForeground(new Color(235, 235, 235));
    }//GEN-LAST:event_btEntrarMouseExited

    private void btSairMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSairMouseEntered
        btSair.setBackground(new Color(235, 235, 235));
        btSair.setForeground(new Color(217, 81, 51));
    }//GEN-LAST:event_btSairMouseEntered

    private void btSairMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSairMouseExited
        btSair.setForeground(new Color(235, 235, 235));
        btSair.setBackground(new Color(217, 81, 51));
    }//GEN-LAST:event_btSairMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginTela2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginTela2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginTela2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginTela2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginTela2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEntrar;
    private javax.swing.JButton btSair;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cpSenha;
    private javax.swing.JTextField cpUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JRadioButton rdAluno;
    private javax.swing.JRadioButton rdCoordenador;
    private javax.swing.JRadioButton rdMonitor;
    // End of variables declaration//GEN-END:variables
}
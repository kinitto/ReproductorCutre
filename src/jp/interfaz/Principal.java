/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.interfaz;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import jp.auxiliar.Estados;
import jp.auxiliar.Modos;
import jp.auxiliar.OrdenacionDeDirectorios;
import jp.interfaz.errores.ErrorInicio;
import jp.interfaz.errores.ReproductorError;
import jp.main.Main;

/**
 *
 * @author Joaquín
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() {
        este = this;
        initComponents();
    }
    
    public void entrada(String entrada){
        if (entrada != null) {
            archivo = entrada;
            nombreArch = entrada.substring(entrada.lastIndexOf('\\') + 1);
            OrdenacionDeDirectorios.crear(entrada.substring(0, entrada.lastIndexOf('\\')), nombreArch);
            TextNombre.setText(nombreArch);
            estadoActual = Estados.PLAY;
            alterarEstado();
        }
    }

    public void abrir() {
        JFileChooser fileChooser = new JFileChooser(archivo);
        int seleccion = fileChooser.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == seleccion) {
            File file = fileChooser.getSelectedFile();
            archivo = file.getAbsolutePath();
            nombreArch = file.getName();
            //OrdenacionDeDirectorios.crear(file.getParent(), nombreArch);
            OrdenacionDeDirectorios.crear(archivo.substring(0, archivo.lastIndexOf("\\")), nombreArch);//Con esta forma se puede usar el autoavanzar en las bibliotecas de windows
            TextNombre.setText(nombreArch);
            estadoActual = Estados.PLAY;
            alterarEstado();
        }
    }

    public void stop() {
        estadoActual = Estados.STOP;
        alterarEstado();
    }

    public void alterarEstado() {
        BotonAvanzar.setEnabled(true);
        BotonRetroceder.setEnabled(true);
        LabelRuta.setText(OrdenacionDeDirectorios.getDirectorioActual());
        switch (estadoActual) {
            case STOP:
                BotonPP.setText("Play");
                BotonStop.setEnabled(false);
                BotonOtraVez.setEnabled(false);
                CheckAvanceA.setEnabled(false);
                avance = Modos.AVANCEN;
                Main.parar();
                break;
            case PLAY:
                BotonPP.setText("Pausa");
                BotonStop.setEnabled(true);
                BotonOtraVez.setEnabled(true);
                CheckAvanceA.setEnabled(true);
                try {
                    Main.inicio(archivo, avance);
                    numErrores = 0;
                } catch (ReproductorError exc) {
                    numErrores += 1;
                    if (numErrores > 5) {
                        new ErrorInicio(this, true, "Error en el avance automatico, se obtivieron 5 o mas intentos erroneos de apertura seguidos").setVisible(true);
                        BotonStop.doClick();
                    } else {
                        BotonAvanzar.doClick();
                    }
                }
                break;
            case RESUME:
                BotonPP.setText("Pausa");
                BotonStop.setEnabled(true);
                BotonOtraVez.setEnabled(true);
                CheckAvanceA.setEnabled(true);
                Main.reiniciar();
                estadoActual = Estados.PLAY;
                break;
            case PAUSA:
                BotonPP.setText("Play");
                BotonStop.setEnabled(true);
                BotonOtraVez.setEnabled(true);
                CheckAvanceA.setEnabled(true);
                Main.pausa();
                break;
        }
    }

    public void alterarMinutero(long microsec) {
        Integer hour, min, sec;
        sec = (int) (microsec / 1000000);
        min = sec / 60;
        sec = sec - min * 60;
        hour = min / 60;
        min = min - hour * 60;
        String shour, smin, ssec;
        if (sec < 10) {
            ssec = "0" + sec;
        } else {
            ssec = sec.toString();
        }
        if (min < 10) {
            smin = "0" + min;
        } else {
            smin = min.toString();
        }
        if (hour < 10) {
            shour = "0" + hour;
        } else {
            shour = hour.toString();
        }
        LabelMinutero.setText(shour + ":" + smin + ":" + ssec);
    }

    public static void rellenar(int ahora, int total, long microsec) {
        BarraProgreso.setMaximum(total);
        BarraProgreso.setValue(ahora);
        BarraProgreso.repaint();
        este.alterarMinutero(microsec);
        if (ahora == total) {
            este.estadoActual = Estados.STOP;
            if (este.repetir == Modos.REPETIR) {
                este.BotonOtraVez.doClick();
            } else {
                if (este.avance == Modos.AVANCEA) {
                    este.BotonAvanzar.doClick();
                }
            }
            este.alterarEstado();
        }
        este.repaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TextNombre = new javax.swing.JTextField();
        BotonAbrir = new javax.swing.JButton();
        BotonStop = new javax.swing.JButton();
        BotonOtraVez = new javax.swing.JButton();
        BotonPP = new javax.swing.JButton();
        CheckRepetir = new javax.swing.JCheckBox();
        CheckAvanceA = new javax.swing.JCheckBox();
        BarraProgreso = new javax.swing.JProgressBar();
        jSeparator1 = new javax.swing.JSeparator();
        BotonAvanzar = new javax.swing.JButton();
        BotonRetroceder = new javax.swing.JButton();
        LabelRuta = new javax.swing.JLabel();
        LabelMinutero = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuArchivo = new javax.swing.JMenu();
        jMenuArchivoAbrir = new javax.swing.JMenuItem();
        jMenuArchivoSalir = new javax.swing.JMenuItem();
        MenuAbout = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reproductor Cutre");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setResizable(false);

        TextNombre.setEditable(false);
        TextNombre.setText("Nombre.extension");

        BotonAbrir.setText("Abrir");
        BotonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAbrirActionPerformed(evt);
            }
        });

        BotonStop.setText("Stop");
        BotonStop.setEnabled(false);
        BotonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonStopActionPerformed(evt);
            }
        });

        BotonOtraVez.setText("Otra Vez");
        BotonOtraVez.setEnabled(false);
        BotonOtraVez.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonOtraVezActionPerformed(evt);
            }
        });

        BotonPP.setText("Play");
        BotonPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonPPActionPerformed(evt);
            }
        });

        CheckRepetir.setText("Repetir");
        CheckRepetir.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CheckRepetirStateChanged(evt);
            }
        });

        CheckAvanceA.setText("Avance Automatico");
        CheckAvanceA.setEnabled(false);
        CheckAvanceA.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CheckAvanceAStateChanged(evt);
            }
        });

        BotonAvanzar.setText("Avanzar");
        BotonAvanzar.setEnabled(false);
        BotonAvanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAvanzarActionPerformed(evt);
            }
        });

        BotonRetroceder.setText("Retroceder");
        BotonRetroceder.setEnabled(false);
        BotonRetroceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRetrocederActionPerformed(evt);
            }
        });

        LabelRuta.setText("Ruta del directorio");

        LabelMinutero.setText("00:00:00");

        MenuArchivo.setText("Archivo");

        jMenuArchivoAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuArchivoAbrir.setText("Abrir");
        jMenuArchivoAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuArchivoAbrirActionPerformed(evt);
            }
        });
        MenuArchivo.add(jMenuArchivoAbrir);

        jMenuArchivoSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuArchivoSalir.setText("Salir");
        jMenuArchivoSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuArchivoSalirActionPerformed(evt);
            }
        });
        MenuArchivo.add(jMenuArchivoSalir);

        jMenuBar1.add(MenuArchivo);

        MenuAbout.setText("Ayuda");

        jMenuItem1.setText("Acerca de ...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MenuAbout.add(jMenuItem1);

        jMenuBar1.add(MenuAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonPP)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BotonStop)
                        .addGap(219, 219, 219)
                        .addComponent(BotonOtraVez))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BotonAbrir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckRepetir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CheckAvanceA)))
                .addContainerGap(189, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonRetroceder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BotonAvanzar)
                .addGap(107, 107, 107))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LabelMinutero)
                .addGap(18, 18, 18)
                .addComponent(BarraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelRuta)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonAvanzar)
                    .addComponent(BotonRetroceder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(BotonPP)
                        .addGap(1, 1, 1)
                        .addComponent(BotonStop))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(BotonOtraVez)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonAbrir)
                    .addComponent(CheckRepetir)
                    .addComponent(CheckAvanceA))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelRuta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BarraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelMinutero))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuArchivoSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuArchivoSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuArchivoSalirActionPerformed

    private void BotonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAbrirActionPerformed
        abrir();
    }//GEN-LAST:event_BotonAbrirActionPerformed

    private void BotonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonStopActionPerformed
        stop();
    }//GEN-LAST:event_BotonStopActionPerformed

    private void BotonPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonPPActionPerformed
        if (estadoActual == Estados.PAUSA) {
            estadoActual = Estados.RESUME;
        } else if (estadoActual == Estados.PLAY) {
            estadoActual = Estados.PAUSA;
        } else {
            estadoActual = Estados.PLAY;
        }
        if (archivo == null && estadoActual == Estados.PLAY) {
            estadoActual = Estados.STOP;
            abrir();
        } else {
            alterarEstado();
        }
    }//GEN-LAST:event_BotonPPActionPerformed

    private void CheckRepetirStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CheckRepetirStateChanged
        if (CheckRepetir.isSelected()) {
            repetir = Modos.REPETIR;
        } else {
            repetir = Modos.SINGLE;
        }
    }//GEN-LAST:event_CheckRepetirStateChanged

    private void BotonAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAvanzarActionPerformed
        String siguiente = OrdenacionDeDirectorios.siguiente();
        archivo = siguiente;
        nombreArch = siguiente.substring(siguiente.lastIndexOf('\\') + 1);
        OrdenacionDeDirectorios.crear(siguiente.substring(0, siguiente.lastIndexOf('\\')), nombreArch);
        TextNombre.setText(nombreArch);
        estadoActual = Estados.PLAY;
        alterarEstado();
    }//GEN-LAST:event_BotonAvanzarActionPerformed

    private void BotonRetrocederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRetrocederActionPerformed
        String siguiente = OrdenacionDeDirectorios.anterior();
        archivo = siguiente;
        nombreArch = siguiente.substring(siguiente.lastIndexOf('\\') + 1);
        OrdenacionDeDirectorios.crear(siguiente.substring(0, siguiente.lastIndexOf('\\')), nombreArch);
        TextNombre.setText(nombreArch);
        estadoActual = Estados.PLAY;
        alterarEstado();
    }//GEN-LAST:event_BotonRetrocederActionPerformed

    private void BotonOtraVezActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonOtraVezActionPerformed
        Main.stop();
        /*estadoActual = Estados.STOP;
         alterarEstado();*/
        estadoActual = Estados.PLAY;
        alterarEstado();
    }//GEN-LAST:event_BotonOtraVezActionPerformed

    private void CheckAvanceAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CheckAvanceAStateChanged
        if (CheckAvanceA.isSelected()) {
            avance = Modos.AVANCEA;
        } else {
            avance = Modos.AVANCEN;
        }
    }//GEN-LAST:event_CheckAvanceAStateChanged

    private void jMenuArchivoAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuArchivoAbrirActionPerformed
        BotonAbrir.doClick();
    }//GEN-LAST:event_jMenuArchivoAbrirActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        About dialog = new About(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    static String argumentos = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if (args == null) {
            argumentos = null;
        } else {
            System.out.println(args.toString());
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    argumentos = args[i];
                } else {
                    argumentos = argumentos + " " + args[i];
                }
                argumentos = argumentos.replace("@", " ");
            }
        }
        System.out.println(argumentos);

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Principal Anonimo;
                Anonimo = new Principal();
                Anonimo.setVisible(true);
                Anonimo.entrada(argumentos);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JProgressBar BarraProgreso;
    private javax.swing.JButton BotonAbrir;
    private javax.swing.JButton BotonAvanzar;
    private javax.swing.JButton BotonOtraVez;
    private javax.swing.JButton BotonPP;
    private javax.swing.JButton BotonRetroceder;
    private javax.swing.JButton BotonStop;
    private javax.swing.JCheckBox CheckAvanceA;
    private javax.swing.JCheckBox CheckRepetir;
    private javax.swing.JLabel LabelMinutero;
    private javax.swing.JLabel LabelRuta;
    private javax.swing.JMenu MenuAbout;
    private javax.swing.JMenu MenuArchivo;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JMenuItem jMenuArchivoAbrir;
    private javax.swing.JMenuItem jMenuArchivoSalir;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
    private String archivo = null;
    private String nombreArch = null;
    private Estados estadoActual = Estados.STOP;
    public Modos repetir = Modos.SINGLE;
    public Modos avance = Modos.AVANCEN;
    private static Principal este;
    private Integer numErrores = 0;
}
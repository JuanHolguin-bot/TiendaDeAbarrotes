/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import Repositorios.GestorFactura;
import org.bson.Document;
import java.util.List;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author jose_
 */
public class Facturas extends javax.swing.JFrame {

    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private GestorFactura gestorFactura;

    /**
     * Creates new form Facturas
     */
    public Facturas() {
        initComponents();
        gestorFactura = new GestorFactura();
        configurarTabla();
        cargarFacturasEnTabla();
        configurarFiltro();
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"N° Factura", "Cliente", "Total Venta", "Fecha", "Vendedor"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtFactura.setModel(modeloTabla);

        // Agregar menú contextual
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuReimprimir = new JMenuItem("Reimprimir Factura");
        popupMenu.add(menuReimprimir);

        menuReimprimir.addActionListener(e -> {
            int filaSeleccionada = jtFactura.getSelectedRow();
            if (filaSeleccionada >= 0) {
                reimprimirFactura(filaSeleccionada);
            }
        });

        jtFactura.setComponentPopupMenu(popupMenu);
    }

    private void cargarFacturasEnTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla

        try {
            List<Document> facturas = gestorFactura.cargarFacturasDesdeBd();
            for (Document factura : facturas) {
                modeloTabla.addRow(new Object[]{
                    factura.getString("numeroFactura"),
                    factura.getString("cliente"),
                    factura.getDouble("totalVenta"),
                    factura.getString("fecha"),
                    factura.getString("Vendedor")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las facturas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarFiltro() {
        rowSorter = new TableRowSorter<>(modeloTabla);
        jtFactura.setRowSorter(rowSorter);

        // Agregar listeners para los radio buttons
        ActionListener radioListener = e -> filtrarTabla();
        rbMayorCero1.addActionListener(radioListener);
        rbHastaUnMillon.addActionListener(radioListener);
        rbHastaDiezMillones.addActionListener(radioListener);
        rbMayorDiezMillones.addActionListener(radioListener);
        rbMayorDiezMillones.addActionListener(radioListener);

        txtFiltro1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarTabla();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarTabla();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarTabla();
            }
        });
    }

    private void filtrarTabla() {
        String texto = txtFiltro1.getText();
        String columnaSeleccionada = (String) cboFiltro1.getSelectedItem();

        List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();

        // Filtro por texto
        if (!texto.trim().isEmpty()) {
            int columnaIndice = switch (columnaSeleccionada) {
                case "Numero Factura" ->
                    0;
                case "Cliente" ->
                    1;
                case "Vendedor" ->
                    4;
                default ->
                    0;
            };
            filters.add(RowFilter.regexFilter("(?i)" + texto, columnaIndice));
        }

        // Filtro por rango de precios
        RowFilter<DefaultTableModel, Object> precioFilter = crearFiltroPrecio();
        if (precioFilter != null) {
            filters.add(precioFilter);
        }

        // Aplicar filtros
        if (filters.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else if (filters.size() == 1) {
            rowSorter.setRowFilter(filters.get(0));
        } else {
            rowSorter.setRowFilter(RowFilter.andFilter(filters));
        }
        
        lblTotalCaracter.setText(columnaSeleccionada);
        
        double suma = 0.0;
        for (int i = 0; i < jtFactura.getRowCount(); i++) {
            Object valor = jtFactura.getValueAt(i, 2);
            if (valor != null) {
                try {
                    suma += Double.parseDouble(valor.toString());
                } catch (NumberFormatException e) {
                    
                }
            }
        }
        txtTotalCaracter.setText(String.format("%.2f", suma));
    }

    private RowFilter<DefaultTableModel, Object> crearFiltroPrecio() {
        return new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                try {
                    // Obtener el valor de la columna "Total Venta" (índice 2)
                    double totalVenta = Double.parseDouble(entry.getValue(2).toString());

                    if (rbMayorCero1.isSelected()) {
                        return totalVenta > 0;
                    } else if (rbHastaUnMillon.isSelected()) {
                        return totalVenta > 100_000 && totalVenta <= 1_000_000;
                    } else if (rbHastaDiezMillones.isSelected()) {
                        return totalVenta > 1_000_000 && totalVenta <= 10_000_000;
                    } else if (rbMayorDiezMillones.isSelected()) {
                        return totalVenta > 10_000_000;
                    }

                    return true; // Si ningún radio button está seleccionado
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        };
    }

    private void reimprimirFactura(int fila) {
        try {
            String numeroFactura = jtFactura.getValueAt(fila, 0).toString();
            Document factura = gestorFactura.obtenerFacturaPorNumero(numeroFactura);

            if (factura != null) {
                ColillaVenta colilla = new ColillaVenta();

                // Establecer datos básicos
                colilla.TxtCliente.setText(factura.getString("cliente"));
                colilla.TxtCajero.setText(factura.getString("Vendedor"));

                // Configurar la tabla de productos
                DefaultTableModel modeloColilla = new DefaultTableModel(
                        new String[]{"ID", "Producto", "Precio", "Cantidad", "Descuento", "Total"},
                        0
                );

                // Obtener y agregar productos
                List<Document> productos = (List<Document>) factura.get("productos");
                for (Document producto : productos) {
                    modeloColilla.addRow(new Object[]{
                        producto.getInteger("idProducto"),
                        producto.getString("nombre"),
                        producto.getDouble("precio"),
                        producto.getInteger("cantidad"),
                        producto.getDouble("descuento"),
                        producto.getDouble("Total producto")
                    });
                }
                // Agrega una fila vacía o separadora si quieres
                modeloColilla.addRow(new Object[]{"", "", "", "", "TOTAL VENTA:", factura.getDouble("totalVenta")});

                colilla.jTable2.setModel(modeloColilla);

                // Mostrar la colilla en una nueva ventana
                JFrame frame = new JFrame("Reimprimir Factura");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(colilla);
                frame.pack();
                frame.setSize(710, 550);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);

            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró la factura seleccionada",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al reimprimir la factura: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupFiltro = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        cboFiltro1 = new javax.swing.JComboBox<>();
        txtFiltro1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rbMayorDiezMillones = new javax.swing.JRadioButton();
        rbHastaDiezMillones = new javax.swing.JRadioButton();
        rbMayorCero1 = new javax.swing.JRadioButton();
        rbHastaUnMillon = new javax.swing.JRadioButton();
        lblTotalPor = new javax.swing.JLabel();
        lblTotalCaracter = new javax.swing.JLabel();
        txtTotalCaracter = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        btnQuitarFiltros5 = new javax.swing.JButton();
        btnSalir5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtFactura = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cboFiltro1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero Factura", "Cliente", "Vendedor" }));
        cboFiltro1.setAutoscrolls(true);
        cboFiltro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFiltro1ActionPerformed(evt);
            }
        });

        txtFiltro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltro1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Precio");

        btnGroupFiltro.add(rbMayorDiezMillones);
        rbMayorDiezMillones.setText("Mayor a 10.000.000");
        rbMayorDiezMillones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMayorDiezMillonesActionPerformed(evt);
            }
        });

        btnGroupFiltro.add(rbHastaDiezMillones);
        rbHastaDiezMillones.setText("Entre 1.000.000 - 10.000.000");

        btnGroupFiltro.add(rbMayorCero1);
        rbMayorCero1.setText("Mayor a 0");
        rbMayorCero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMayorCero1ActionPerformed(evt);
            }
        });

        btnGroupFiltro.add(rbHastaUnMillon);
        rbHastaUnMillon.setText("Entre 100.000 - 1.000.000");
        rbHastaUnMillon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbHastaUnMillonActionPerformed(evt);
            }
        });

        lblTotalPor.setText("Total por:");

        txtTotalCaracter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalCaracterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFiltro1)
                    .addComponent(cboFiltro1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbMayorDiezMillones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbHastaDiezMillones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbMayorCero1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
<<<<<<< HEAD
                    .addComponent(rbHastaUnMillon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalPor, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotalCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
=======
                    .addComponent(rbHastaUnMillon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
>>>>>>> Develop
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbMayorCero1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbHastaUnMillon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbHastaDiezMillones, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbMayorDiezMillones)
<<<<<<< HEAD
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 330, Short.MAX_VALUE)
                .addComponent(lblTotalPor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalCaracter)
                .addGap(18, 18, 18)
                .addComponent(txtTotalCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
=======
                .addContainerGap(425, Short.MAX_VALUE))
>>>>>>> Develop
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.setForeground(new java.awt.Color(51, 51, 51));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnQuitarFiltros5.setText("Quitar filtros");
        btnQuitarFiltros5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQuitarFiltros5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarFiltros5ActionPerformed(evt);
            }
        });

        btnSalir5.setText("Salir");
        btnSalir5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnQuitarFiltros5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitarFiltros5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FACTURAS");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jtFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
<<<<<<< HEAD
        ));
=======
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
>>>>>>> Develop
        jScrollPane1.setViewportView(jtFactura);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(978, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(190, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFiltro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltro1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFiltro1ActionPerformed

    private void btnConsultar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultar1ActionPerformed

    }//GEN-LAST:event_btnConsultar1ActionPerformed

    private void btnQuitarFiltros5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarFiltros5ActionPerformed
        // Deseleccionar todos los radio buttons
        ButtonGroup grupoBotones = new ButtonGroup();
        grupoBotones.add(rbMayorCero1);
        grupoBotones.add(rbHastaUnMillon);
        grupoBotones.add(rbHastaDiezMillones);
        grupoBotones.add(rbMayorDiezMillones);
        grupoBotones.add(rbMayorDiezMillones);
        grupoBotones.clearSelection();

        // Limpiar el filtro de texto
        txtFiltro1.setText("");

        // Actualizar la tabla
        filtrarTabla();
    }//GEN-LAST:event_btnQuitarFiltros5ActionPerformed

    private void btnSalir5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalir5ActionPerformed

    private void cboFiltro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFiltro1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFiltro1ActionPerformed

    private void rbMayorCero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMayorCero1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbMayorCero1ActionPerformed

    private void rbHastaUnMillonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbHastaUnMillonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbHastaUnMillonActionPerformed

    private void rbMayorDiezMillonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMayorDiezMillonesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbMayorDiezMillonesActionPerformed

    private void txtTotalCaracterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCaracterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCaracterActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Facturas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupFiltro;
    private javax.swing.JButton btnQuitarFiltros5;
    private javax.swing.JButton btnSalir5;
    private javax.swing.JComboBox<String> cboFiltro1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtFactura;
    private javax.swing.JLabel lblTotalCaracter;
    private javax.swing.JLabel lblTotalPor;
    private javax.swing.JRadioButton rbHastaDiezMillones;
    private javax.swing.JRadioButton rbHastaUnMillon;
    private javax.swing.JRadioButton rbMayorCero1;
    private javax.swing.JRadioButton rbMayorDiezMillones;
    private javax.swing.JTextField txtFiltro1;
    private javax.swing.JTextField txtTotalCaracter;
    // End of variables declaration//GEN-END:variables
}

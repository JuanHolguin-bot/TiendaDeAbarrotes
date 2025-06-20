/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;

import javax.swing.table.DefaultTableModel;
import Entities.Producto;
import gestioninventario.Service.IProductoManager;
import gestioninventario.Service.IStockManager;
import gestioninventario.Service.Inventario;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import reportes.ReporteBajoStock;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * public class RegistrarProducto extends javax.swing.JFrame { }
 *
 * @author jose_
 */
public class ListaProductos extends javax.swing.JFrame {

    private DefaultTableModel modeloTabla;
    private String usuario;
    private RegistrarProducto registrarProductoInstance; // Variable para almacenar la instancia
    private Ventas ventanaVentasInstance; // Variable para almacenar la instancia
    private IProductoManager productoManager;
    private IStockManager stockManager;

    private TableRowSorter<javax.swing.table.DefaultTableModel> rowSorter;
    private JPopupMenu popupMenu;

    // Declara estas variables en tu clase principal (por ejemplo, en la clase JFrame principal)
    private Facturas vistaFacturas;
    private ProductosBajoStock vistaStockBajo;
    private ProductosBajoStock productosBajoStockVista = null;
    private ReporteBajoStock reporteBajoStock = new ReporteBajoStock(); // Instancia lógica, solo una vez

    public ListaProductos() {
    }

    public ListaProductos(IProductoManager productoManager, IStockManager stockManager, String usuario) {
        this.productoManager = productoManager;
        this.stockManager = stockManager;
        this.usuario = usuario;
        initComponents();
        configurarTabla();
        cargarProductosEnTabla();
        configurarPopupMenu(); // Añadir esta línea

        // Suponiendo que tu modelo es DefaultTableModel
        rowSorter = new TableRowSorter<>((javax.swing.table.DefaultTableModel) jtProductos.getModel());
        jtProductos.setRowSorter(rowSorter);

        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
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

            private void filtrarTabla() {
                String texto = txtFiltro.getText();
                if (texto.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Tipo", "Precio", "Cantidad", "Proveedor", "Fecha Vencimiento"}, 0
        );
        jtProductos.setModel(modeloTabla);
    }

    public void cargarProductosEnTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar los productos

        for (Producto producto : productoManager.obtenerTodosLosProductos().values()) {
            modeloTabla.addRow(new Object[]{
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getTipoProducto(),
                producto.getPrecio(),
                stockManager.obtenerStock(producto.getIdProducto()),
                producto.getProveedor(),
                producto.getFechaVencimiento()
            });
        }
    }

    private void configurarPopupMenu() {
        popupMenu = new JPopupMenu();
        JMenuItem menuEditar = new JMenuItem("Editar producto");

        menuEditar.addActionListener((ActionEvent e) -> {
            int fila = jtProductos.getSelectedRow();
            if (fila >= 0) {
                int idProducto = (int) jtProductos.getValueAt(fila, 0);
                Producto producto = productoManager.obtenerProducto(idProducto);
                if (producto != null) {
                    abrirEdicionProducto(producto);
                }
            }
        });

        popupMenu.add(menuEditar);

        jtProductos.setComponentPopupMenu(popupMenu);
    }

    private void abrirEdicionProducto(Producto producto) {
        if (registrarProductoInstance == null || !registrarProductoInstance.isVisible()) {
            Inventario inventario = new Inventario(productoManager, stockManager);
            registrarProductoInstance = new RegistrarProducto(this, inventario);

            // Establecer los valores en los campos
            registrarProductoInstance.txtIdProducto.setText(String.valueOf(producto.getIdProducto()));
            registrarProductoInstance.txtIdProducto.setEditable(false); // El ID no debería cambiar
            registrarProductoInstance.txtNombre.setText(producto.getNombre());
            registrarProductoInstance.cboTipo.setSelectedItem(producto.getTipoProducto());
            registrarProductoInstance.txtPrecio.setText(String.valueOf(producto.getPrecio()));
            registrarProductoInstance.txtProveedor.setText(producto.getProveedor());
            registrarProductoInstance.txtCodigo3.setText(producto.getFechaVencimiento());
            registrarProductoInstance.txtCantidad.setText(String.valueOf(stockManager.obtenerStock(producto.getIdProducto())));

            registrarProductoInstance.setTitle("Editar Producto");
            registrarProductoInstance.btnRegistrar.setText("Actualizar");

            registrarProductoInstance.setVisible(true);
        } else {
            registrarProductoInstance.toFront();
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProductos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        btnFacturas = new javax.swing.JButton();
        stockBajoButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jtProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtProductos);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LISTADO PRODUCTOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        btnNuevo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnVenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnVenta.setText("Registrar Venta");
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });

        btnFacturas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFacturas.setText("Facturas");
        btnFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturasActionPerformed(evt);
            }
        });

        stockBajoButton.setText("Stock Bajo");
        stockBajoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockBajoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stockBajoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 250, Short.MAX_VALUE)
                .addComponent(btnFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnVenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFacturas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stockBajoButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("FILTRO:");

        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFiltro)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(81, 81, 81))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        if (registrarProductoInstance == null || !registrarProductoInstance.isVisible()) {
            Inventario inventario = new Inventario(productoManager, stockManager);

            registrarProductoInstance = new RegistrarProducto(this, inventario);
            registrarProductoInstance.setVisible(true);
        } else {
            registrarProductoInstance.toFront(); // Llevar la ventana al frente si ya está abierta
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        if (ventanaVentasInstance == null || !ventanaVentasInstance.isVisible()) {

            ventanaVentasInstance = new Ventas(productoManager, stockManager, usuario, this);
            ventanaVentasInstance.setVisible(true);
        } else {
            ventanaVentasInstance.toFront(); // Llevar la ventana al frente si ya está abierta
        }
    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturasActionPerformed
        if (vistaFacturas == null || !vistaFacturas.isDisplayable()) {
            vistaFacturas = new Facturas();
            vistaFacturas.setVisible(true);
        } else {
            vistaFacturas.toFront();
            vistaFacturas.requestFocus();
        }
    }//GEN-LAST:event_btnFacturasActionPerformed

    private void stockBajoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockBajoButtonActionPerformed
        if (productosBajoStockVista == null || !productosBajoStockVista.isDisplayable()) {
            try {
                // Obtén los datos del reporte
                Map<Integer, Integer> productosBajoStock = reporteBajoStock.getProductosBajoStock();
            } catch (Exception ex) {
                Logger.getLogger(ListaProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Crea la vista y pásale los datos
            productosBajoStockVista = new ProductosBajoStock(reporteBajoStock,productoManager,stockManager);
            productosBajoStockVista.setVisible(true);
        } else {
            productosBajoStockVista.toFront();
            productosBajoStockVista.requestFocus();
        }
    }//GEN-LAST:event_stockBajoButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFacturas;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtProductos;
    private javax.swing.JButton stockBajoButton;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}

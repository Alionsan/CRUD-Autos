import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AgenciaGUI extends JFrame {
    private Inventario inventario;
    private DefaultTableModel modeloTabla;
    private JTable tablaVehiculos;

    public AgenciaGUI() {
        inventario = new Inventario();
        setTitle("Gestión de Agencia Automotriz");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarInterfaz();
    }

    private void inicializarInterfaz() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Modelo de tabla
        modeloTabla = new DefaultTableModel(new String[]{"Tipo", "Marca", "Modelo", "Año", "ID", "Precio"}, 0);
        tablaVehiculos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaVehiculos);

        // Botones
        JButton agregarBtn = new JButton("Agregar Vehículo");
        agregarBtn.addActionListener(e -> agregarVehiculo());

        JButton guardarBtn = new JButton("Guardar Inventario");
        guardarBtn.addActionListener(e -> guardarInventario());

        JButton cargarBtn = new JButton("Cargar Inventario");
        cargarBtn.addActionListener(e -> cargarInventario());

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(agregarBtn);
        botonesPanel.add(guardarBtn);
        botonesPanel.add(cargarBtn);

        JButton eliminarBtn = new JButton("Eliminar Vehículo");
        eliminarBtn.addActionListener(e -> eliminarVehiculo());
        botonesPanel.add(eliminarBtn);

        JButton modificarBtn = new JButton("Modificar Vehículo");
        modificarBtn.addActionListener(e -> modificarVehiculo());
        botonesPanel.add(modificarBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(botonesPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void modificarVehiculo() {
        // Pedir al usuario el ID del vehículo a modificar
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del vehículo a modificar:");
        if (id == null || id.isBlank()) return; // Si el usuario cancela o no ingresa un ID

        Vehiculo vehiculo = inventario.buscarVehiculo(id);
        if (vehiculo == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un vehículo con el ID especificado.");
            return;
        }

        // Crear un panel para modificar los datos
        JPanel panel = new JPanel(new GridLayout(6, 2));
        JTextField campoMarca = new JTextField(vehiculo.getMarca());
        JTextField campoModelo = new JTextField(vehiculo.getModelo());
        JTextField campoAnio = new JTextField(String.valueOf(vehiculo.getAnioFabricacion()));
        JTextField campoPrecio = new JTextField(String.valueOf(vehiculo.getPrecio()));

        panel.add(new JLabel("Marca:"));
        panel.add(campoMarca);

        panel.add(new JLabel("Modelo:"));
        panel.add(campoModelo);

        panel.add(new JLabel("Año de Fabricación:"));
        panel.add(campoAnio);

        panel.add(new JLabel("Precio:"));
        panel.add(campoPrecio);

        if (JOptionPane.showConfirmDialog(this, panel, "Modificar Vehículo",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                // Validar y actualizar datos
                String nuevaMarca = campoMarca.getText().trim();
                String nuevoModelo = campoModelo.getText().trim();
                int nuevoAnio = Integer.parseInt(campoAnio.getText().trim());
                double nuevoPrecio = Double.parseDouble(campoPrecio.getText().trim());

                vehiculo.setMarca(nuevaMarca);
                vehiculo.setModelo(nuevoModelo);
                vehiculo.setAnioFabricacion(nuevoAnio);
                vehiculo.setPrecio(nuevoPrecio);

                // Actualizar tabla
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Vehículo modificado con éxito.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error en los campos numéricos: " + ex.getMessage());
            }
        }
    }


    private void agregarVehiculo() {
        // Panel para entrada de datos
        JPanel panel = new JPanel(new GridLayout(7, 2));
        JTextField campoMarca = new JTextField();
        JTextField campoModelo = new JTextField();
        JTextField campoAnio = new JTextField();
        JTextField campoId = new JTextField();
        JTextField campoPrecio = new JTextField();
        
        String[] opciones = {"Auto", "Motocicleta", "Camion"};
        JComboBox<String> tipoCombo = new JComboBox<>(opciones);
        
        panel.add(new JLabel("Tipo de Vehículo:"));
        panel.add(tipoCombo);
        
        panel.add(new JLabel("Marca:"));
        panel.add(campoMarca);
        
        panel.add(new JLabel("Modelo:"));
        panel.add(campoModelo);
        
        panel.add(new JLabel("Año de Fabricación:"));
        panel.add(campoAnio);
        
        panel.add(new JLabel("Número de Identificación:"));
        panel.add(campoId);
        
        panel.add(new JLabel("Precio:"));
        panel.add(campoPrecio);
        
        if (JOptionPane.showConfirmDialog(this, panel, "Agregar Vehículo",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                // Validación de campos vacíos
                String marca = campoMarca.getText().trim();
                String modelo = campoModelo.getText().trim();
                String anioStr = campoAnio.getText().trim();
                String id = campoId.getText().trim();
                String precioStr = campoPrecio.getText().trim();
        
                if (marca.isEmpty() || modelo.isEmpty() || anioStr.isEmpty() || id.isEmpty() || precioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados.");
                    return;
                }
        
                int anio = Integer.parseInt(anioStr);
                double precio = Double.parseDouble(precioStr);
        
                String tipoEspecifico = (String) tipoCombo.getSelectedItem();
                Vehiculo vehiculo = null;
                
                // Dependiendo del tipo, solicita atributos adicionales
                if (tipoEspecifico.equals("Auto")) {
                    int puertas = Integer.parseInt(JOptionPane.showInputDialog("Número de Puertas:"));
                    vehiculo = new Auto(marca, modelo, anio, id, precio, puertas);
                } else if (tipoEspecifico.equals("Motocicleta")) {
                    String cilindros = JOptionPane.showInputDialog("Número de Cilindros:");
                    vehiculo = new Motocicleta(marca, modelo, anio, id, precio, cilindros);
                } else if (tipoEspecifico.equals("Camion")) {
                    double capacidadCarga = Double.parseDouble(JOptionPane.showInputDialog("Capacidad de Carga (toneladas):"));
                    vehiculo = new Camion(marca, modelo, anio, id, precio, capacidadCarga);
                }
    
                if (vehiculo != null) {
                    // Agregar vehículo al inventario
                    inventario.agregarVehiculo(vehiculo);
    
                    // Actualizar la tabla
                    actualizarTabla();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error de formato en los campos numéricos.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar vehículo: " + ex.getMessage());
            }
        }
    }

    private void eliminarVehiculo() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del vehículo a eliminar:");
        if (id != null && !id.isBlank()) {
            boolean eliminado = inventario.eliminarPorId(id);
            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Vehículo eliminado con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un vehículo con el ID especificado.");
            }
        }
    }
    

    private void guardarInventario() {
        try {
            inventario.guardarInventario("inventario.csv");
            JOptionPane.showMessageDialog(this, "Inventario guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar inventario: " + ex.getMessage());
        }
    }

    private void cargarInventario() {
    try {
        // Intentamos cargar el inventario desde el archivo
        inventario.cargarInventario("inventario.csv");
        
        // Limpiamos la tabla para evitar duplicados o datos viejos
        modeloTabla.setRowCount(0);

        // Verificamos si hay vehículos en el inventario
        if (inventario.obtenerTodos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El inventario está vacío.");
            return;
        }

        // Iteramos sobre los vehículos y los añadimos a la tabla
        for (Vehiculo v : inventario.obtenerTodos()) {
            modeloTabla.addRow(new Object[]{
                    v.getClass().getSimpleName(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAnioFabricacion(),
                    v.getNumeroIdentificacion(),
                    v.getPrecio()
            });
        }
        
        // Informamos al usuario que el inventario se cargó correctamente
        JOptionPane.showMessageDialog(this, "Inventario cargado correctamente.");
    } catch (IOException ex) {
        // En caso de error en la lectura del archivo
        JOptionPane.showMessageDialog(this, "Error al cargar inventario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
        // En caso de error de formato de número en los campos
        JOptionPane.showMessageDialog(this, "Formato incorrecto en los datos del archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        // En caso de cualquier otro tipo de error
        JOptionPane.showMessageDialog(this, "Error inesperado al cargar inventario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    



private void actualizarTabla() {
    // Limpiar la tabla antes de agregar los nuevos datos
    modeloTabla.setRowCount(0);
    
    // Agregar todos los vehículos al modelo de la tabla
    for (Vehiculo vehiculo : inventario.getVehiculos()) {
        modeloTabla.addRow(new Object[]{
                vehiculo.getTipo(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnioFabricacion(),
                vehiculo.getNumeroIdentificacion(),
                vehiculo.getPrecio()
        });
    }
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgenciaGUI().setVisible(true));
    }
}
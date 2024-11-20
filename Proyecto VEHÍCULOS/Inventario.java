import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private ArrayList<Vehiculo> vehiculos;

    public Inventario() {
        this.vehiculos = new ArrayList<>();
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public void eliminarVehiculo(String id) {
        vehiculos.removeIf(v -> v.getNumeroIdentificacion().equals(id));
    }

     public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    public Vehiculo buscarVehiculo(String id) {
        for (Vehiculo v : vehiculos) {
            if (v.getNumeroIdentificacion().equals(id)) return v;
        }
        return null;
    }

    public ArrayList<Vehiculo> obtenerTodos() {
        return vehiculos;
    }

    // Guardar inventario en archivo CSV
    public void guardarInventario(String archivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Vehiculo v : vehiculos) {
                String linea = v.getClass().getSimpleName() + "," + v.getMarca() + "," + v.getModelo() + "," +
                               v.getAnioFabricacion() + "," + v.getNumeroIdentificacion() + "," + v.getPrecio();
                
                if (v instanceof Auto) {
                    Auto auto = (Auto) v;
                    linea += "," + auto.getNumeroPuertas(); // Añadimos número de puertas para Auto
                } else if (v instanceof Motocicleta) {
                    Motocicleta moto = (Motocicleta) v;
                    linea += "," + moto.getTipoMotor(); // Añadimos tipo de motor para Motocicleta
                } else if (v instanceof Camion) {
                    Camion camion = (Camion) v;
                    linea += "," + camion.getCapacidadCarga(); // Añadimos capacidad de carga para Camion
                }
    
                bw.write(linea + "\n"); // Escribimos la línea con todos los datos
            }
        }
    }
    
    
    public boolean eliminarPorId(String id) {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getNumeroIdentificacion().equals(id)) {
                vehiculos.remove(i);
                return true; // Éxito
            }
        }
        return false; // No encontrado
    }
    
    
    // Cargar inventario desde archivo CSV
    public void cargarInventario(String archivo) throws IOException {
        vehiculos.clear();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
        
                String[] partes = linea.split(",");
                if (partes.length < 6) {  // Aseguramos que haya al menos 6 columnas
                    throw new IOException("Formato incorrecto en la línea: " + linea);
                }
        
                try {
                    String tipo = partes[0];
                    String marca = partes[1];
                    String modelo = partes[2];
                    int anio = Integer.parseInt(partes[3]);
                    String id = partes[4];
                    double precio = Double.parseDouble(partes[5]);  // Leemos el precio correctamente
    
                    // Validar tipo de vehículo
                    switch (tipo) {
                        case "Auto":
                            if (partes.length < 7) throw new IOException("Faltan datos para el Auto en la línea: " + linea);
                            int puertas = Integer.parseInt(partes[6]);
                            vehiculos.add(new Auto(marca, modelo, anio, id, precio, puertas));
                            break;
                        case "Motocicleta":
                            if (partes.length < 7) throw new IOException("Faltan datos para la Motocicleta en la línea: " + linea);
                            String motor = partes[6];
                            vehiculos.add(new Motocicleta(marca, modelo, anio, id, precio, motor));
                            break;
                        case "Camion":
                            if (partes.length < 7) throw new IOException("Faltan datos para el Camion en la línea: " + linea);
                            double capacidad = Double.parseDouble(partes[6]);
                            vehiculos.add(new Camion(marca, modelo, anio, id, precio, capacidad));
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo de vehículo desconocido: " + tipo);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en la línea: " + linea);
                    throw new IOException("Error de formato numérico en la línea: " + linea, e);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error en el tipo de vehículo: " + linea);
                    throw new IOException("Tipo de vehículo desconocido en la línea: " + linea, e);
                }
            }
        }
    }
    
    public Vehiculo buscarPorId(String id) {
        for (Vehiculo v : vehiculos) {
            if (v.getNumeroIdentificacion().equals(id)) {
                return v;
            }
        }
        return null;
    }
   
    
}

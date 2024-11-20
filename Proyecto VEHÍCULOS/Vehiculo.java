public class Vehiculo {
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private String numeroIdentificacion;
    private double precio;

    public Vehiculo(String marca, String modelo, int anioFabricacion, String numeroIdentificacion, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getId() {
        return numeroIdentificacion;
    }

    public int getAnio() {
        return anioFabricacion;
    }

    public String getTipo() {
        return this.getClass().getSimpleName(); // Devolverá el nombre de la clase como tipo
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    

    public String mostrarInformacion() {
        return "Marca: " + marca + ", Modelo: " + modelo + ", Año: " + anioFabricacion + 
               ", ID: " + numeroIdentificacion + ", Precio: " + precio;
    }
}

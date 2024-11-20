public class Camion extends Vehiculo {
    private double capacidadCarga;

    public Camion(String marca, String modelo, int anioFabricacion, String numeroIdentificacion, double precio, double capacidadCarga) {
        super(marca, modelo, anioFabricacion, numeroIdentificacion, precio);
        this.capacidadCarga = capacidadCarga;
    }


    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + ", Capacidad de Carga: " + capacidadCarga + " toneladas";
    }
}

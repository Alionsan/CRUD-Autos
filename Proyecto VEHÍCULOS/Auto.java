public class Auto extends Vehiculo {
    private int numeroPuertas;

   public Auto(String marca, String modelo, int anioFabricacion, String numeroIdentificacion, double precio, int numeroPuertas) {
    super(marca, modelo, anioFabricacion, numeroIdentificacion, precio);
    this.numeroPuertas = numeroPuertas;
}


    public int getNumeroPuertas() {
        return numeroPuertas;
    }

    public void setNumeroPuertas(int numeroPuertas) {
        this.numeroPuertas = numeroPuertas;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + ", Puertas: " + numeroPuertas;
    }
}

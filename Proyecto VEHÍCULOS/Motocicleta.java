public class Motocicleta extends Vehiculo {
    private String tipoMotor;

    public Motocicleta(String marca, String modelo, int anioFabricacion, String numeroIdentificacion, double precio, String tipoMotor) {
        super(marca, modelo, anioFabricacion, numeroIdentificacion, precio);
        this.tipoMotor = tipoMotor;
    }


    public String getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + ", Tipo de Motor: " + tipoMotor;
    }
}

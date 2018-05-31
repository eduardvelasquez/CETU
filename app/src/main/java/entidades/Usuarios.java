package entidades;

public class Usuarios {

    private String nombre;
    private String usuario;
    private String password;
    private String telefono;
    private String nivel;
    private String estado;

    public Usuarios(){
    }


    //METODOS GETTERS

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEstado() {
        return estado;
    }

    //METODOS SETTERS


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

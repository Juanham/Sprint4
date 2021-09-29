/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistencia.ConexionBD;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Producto {

    private int IdProducto;
    private String Nombre;
    private double Precio;
    private double Cantidad ;
   

    public Producto() {
    }

    public Producto getProducto(int IdProducto) throws SQLException {
        this.IdProducto = IdProducto;
        return this.getProducto();
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int IdProducto) {
        this.IdProducto = IdProducto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
        
    }
    public double getPrecio() {
        return Precio;
    }
    
    public void setPrecio(double Precio){
        this.Precio = Precio;
    }
    public double getCantidad(){
        return Cantidad;
    }
    public double setCantidad(double Cantidad){
            this.Cantidad = Cantidad;
        return 0;
    }        
    

    public boolean guardarProducto() {
        ConexionBD conexion = new ConexionBD();
        
        String sentencia = "INSERT INTO contactos(IdProducto, Nombre, Precio, Cantidad) "
                + " VALUES ( '" + this.IdProducto + "','" + this.Nombre + "','"
                  + this.Precio + "','" + this.Cantidad +  "');  ";  
        
        if (conexion.setAutoCommitBD(false)) {              
            conexion.commitBD();

            if (conexion.insertarBD(sentencia)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean borrarProducto(int IdProducto) {
        String Sentencia = "DELETE FROM `contactos` WHERE `IdProducto`='" + IdProducto + "'";
        ConexionBD conexion = new ConexionBD();
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(Sentencia)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean actualizarProducto() {
        ConexionBD conexion = new ConexionBD();
        String Sentencia = "UPDATE `contactos` SET Nombre='" + this.Nombre + "',Precio='" + this.Precio + "',Cantidad='" + this.Cantidad
            + ";";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(Sentencia)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public List<Producto> listarProductos() throws SQLException {
        ConexionBD conexion = new ConexionBD();
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "select * from contactos order by IdProducto asc";
        ResultSet rs = conexion.consultarBD(sql);
        Producto c;
        while (rs.next()) {
            c = new Producto();
            c.setIdProducto(rs.getInt("IdIdentificacion"));
            c.setNombre(rs.getString("Nombre"));
            c.setPrecio(rs.getInt("Precio"));
            c.setCantidad(rs.getInt("Cantidad"));
            listaProductos.add(c);

        }
        conexion.cerrarConexion();
        return listaProductos;
    }

    public Producto getProducto() throws SQLException {
        ConexionBD conexion = new ConexionBD();
        String sql = "select * from contactos where IdProducto='" + this.IdProducto + "'";
        ResultSet rs = conexion.consultarBD(sql);
        if (rs.next()) {
            this.IdProducto = rs.getInt("IdProducto");
            this.Nombre = rs.getString("Nombre");
            this.Precio = rs.getInt("Precio");
            this.Cantidad = rs.getInt("Cantidad");
            conexion.cerrarConexion();
            return this;

        } else {
            conexion.cerrarConexion();
            return null;
        }

    }

    @Override
    public String toString() {
        return "Producto{" + "IdProducto=" + IdProducto + ", Nombre=" + Nombre + ", Precio=" + Precio + ", Cantidad=" + Cantidad + '}';
    }
}

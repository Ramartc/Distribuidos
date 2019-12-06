
import java.util.Date;

public class Entrenamiento {
	private Date fecha;
	private String nombre;
	private int repeticiones;
	private int series;
	private double peso;
	public Entrenamiento(Date fecha, String nombre,int repeticiones,int series,double peso) {
		this.fecha=fecha;
		this.nombre=nombre;
		this.repeticiones=repeticiones;
		this.series=series;
		this.peso=peso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getRepeticiones() {
		return repeticiones;
	}
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}
	public int getSeries() {
		return series;
	}
	public void setSeries(int series) {
		this.series = series;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
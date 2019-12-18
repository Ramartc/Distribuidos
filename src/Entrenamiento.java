
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Entrenamiento {
	private Date fecha;
	private String nombre;
	private ArrayList<Integer> repeticiones;
	private int series;
	private ArrayList<Double> peso;
	public Entrenamiento(Date fecha, String nombre,ArrayList<Integer> repeticiones,int series,ArrayList<Double> peso) {
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
	public ArrayList<Integer> getRepeticiones() {
		return repeticiones;
	}
	public void setRepeticiones(ArrayList<Integer> repeticiones) {
		this.repeticiones = repeticiones;
	}
	public int getSeries() {
		return series;
	}
	public void setSeries(int series) {
		this.series = series;
	}
	public ArrayList<Double> getPeso() {
		return peso;
	}
	public void setPeso(ArrayList<Double> peso) {
		this.peso = peso;
	}
	public Date getFecha() {
		return fecha;
	}
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String r = "";
		String p = "";
		for(Integer rep : repeticiones) {
			r = r + "-" + rep;
		}
		for(Double w : peso) {
			p =  p + "-" + w;
		}
		r = r.substring(1);
		p = p.substring(1);
		return sdf.format(fecha) + "," + nombre + "," + r + ","+ series + "," + p ;
	}
}
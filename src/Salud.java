import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Salud {
	private Date fecha;
	private double peso;
	private int pulsaciones;
	private double altura; //en m
	private double imc;
	private ArrayList<Double> l;
	
	public Salud(Date fecha,double peso,int pulsaciones,double altura,double imc) {
		this.setFecha(fecha);
		this.peso=peso;
		this.pulsaciones=pulsaciones;
		this.altura=altura;
		this.imc=imc;
	}
	
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public int getPulsaciones() {
		return pulsaciones;
	}
	public void setPulsaciones(int pulsaciones) {
		this.pulsaciones = pulsaciones;
	}
	public double getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public double getImc() {
		return this.peso/this.altura*this.altura;
	}
	public void setImc(int imc) {
		this.imc = imc;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
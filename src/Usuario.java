
public class Usuario {
	private int id;
	private String user;
	private String password;
	
	public Usuario(String u,String p,int id) {
		//Buscar en el fichero de usuarios si este usuario existe
		this.user = u;
		this.password = p;
		this.id = id;//numero que este pa este usuario en el fichero 
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		//cada vez que se haga un set actualizar el fichero
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		//cada vez que se haga un set actualizar el fichero
		this.password = password;
	}
	public int getId() {
		return id;
	}
}

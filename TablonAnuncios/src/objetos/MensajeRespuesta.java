package objetos;

public class MensajeRespuesta {//mensaje que se enviaria en caso de querer respuesta.
	
	private String contenido;
	private String titulo;
	private String emisario;
	private int n;//evitar repetidos de mismo emisario, con mismo titulo
	
	public MensajeRespuesta(String titulo,String contenido,String emisario) {
		
		this.contenido = contenido;
		this.titulo = titulo;
		this.setEmisario(emisario);
		this.setN(1);
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getEmisario() {
		return emisario;
	}
	public void setEmisario(String emisario) {
		this.emisario = emisario;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
}


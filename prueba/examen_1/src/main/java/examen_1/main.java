package examen_1;

import java.io.IOException;
import java.io.PrintStream;

public class main {

    public static void main(String[] args) throws IOException {
      
        examen ejercicios = new examen();

        ejercicios.repararArchivo();

        PrintStream ps = new PrintStream(System.out);

              while (true) {
           
            ps.println("Menú de opciones:");
            ps.println("1. Agregar datos nuevos al archivo");
            ps.println("2. Eliminar datos del archivo");
            ps.println("3. Datos existentes");
            ps.println("4. ADIOSITOO");
            ps.println("");
            ps.print("Elija una opcion por favor :D : ");

            String entrada = leerString();
            int opcion;

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                ps.println("Error de entrada. Intente otra vez");
                continue;
            }

            switch (opcion) {
                case 1:
                    ejercicios.agregar();
                    break;
                case 2:
                    ejercicios.eliminacion();
                    break; 
                case 3:
                    ejercicios.mostrar();
                    break;
                case 4:
                    ps.println("¡Gracias x venir <3!");
                    return; 
                default:
                    ps.println("Opción inválida");            }
        }
    }
    
    private static String leerString() throws IOException {
    	StringBuilder sb = new StringBuilder();
    	int c;
    	while ((c = System.in.read()) != '\n' && c != -1) {
    	sb.append((char) c);
    	}
    	return sb.toString().trim();
    	}}
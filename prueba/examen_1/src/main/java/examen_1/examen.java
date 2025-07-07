package examen_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import Utils.Colors;


public class examen {


private final String archivoOriginal = "datos.dat";
private final String archivoCSV = "tuti-fruti.csv";
private final String logErrores = "ERRORES.log";
private final PrintStream ps = new PrintStream(System.out);

//REPARACION :D
public void repararArchivo() {
    File original = new File(archivoOriginal);
    File destino = new File(archivoCSV);


    if (!original.exists()) return;


    try (
        BufferedReader br = new BufferedReader(new FileReader(original));
        BufferedWriter bw = new BufferedWriter(new FileWriter(destino))
    ) {
        bw.write("Letra;Color;Animal;Objeto;Alimento");
        bw.newLine();


        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("\\.");
            if (partes.length != 4) throw new IOException("formato incorrecto: " + linea);
            String letra = partes[0].substring(0, 1).toUpperCase();
            bw.write(letra + ";" + String.join(";", partes));
            bw.newLine();
        }
        bw.flush();
        original.delete();
        ps.println(Colors.ANSI_GREEN + "archivo reparado" + Colors.ANSI_RESET);


    } catch (IOException e) {
        guardarError("ERROR al reparar archivo: " + e.getMessage());
        ps.println(Colors.ANSI_RED + "error al reparar archivo. Ver ERRORES.log" + Colors.ANSI_RESET);
        System.exit(1);
    }
}

//eliminar datos :C
public void eliminacion() throws IOException {
    File archivo = new File(archivoCSV);
    if (!archivo.exists()) return;
    
    List<String> lineas = new ArrayList<>();
    BufferedReader br = new BufferedReader(new FileReader(archivo));
    String encabezado = br.readLine();
    String linea;
    while ((linea = br.readLine()) != null) lineas.add(linea);
    br.close();

    for (int i = 0; i < lineas.size(); i++) {
        ps.println((i + 1) + " - " + lineas.get(i).replace(";", " | "));
    }

    ps.print("Ingrese el numero de línea a eliminar: ");
    int index = Integer.parseInt(leerString()) - 1;

    if (index >= 0 && index < lineas.size()) {
        lineas.remove(index);
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(encabezado);
        bw.newLine();
        for (String l : lineas) {
            bw.write(l);
            bw.newLine();
        }
        bw.close();
        ps.println(Colors.ANSI_GREEN + "Línea eliminada correctamente." + Colors.ANSI_RESET);
    } else {
        ps.println(Colors.ANSI_RED + "Número inválido." + Colors.ANSI_RESET);
    }
}

//mostrar datos :0

public void mostrar() {
    File archivo = new File(archivoCSV);
    if (!archivo.exists()) return;


    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String encabezado = br.readLine();
        List<String[]> datos = new ArrayList<>();
        String linea;
        while ((linea = br.readLine()) != null) datos.add(linea.split(";"));
        datos.sort(Comparator.comparing(a -> a[0]));


        ps.println(Colors.ANSI_CYAN + encabezado + Colors.ANSI_RESET);
        boolean alterna = false;
        for (String[] fila : datos) {
            String color = alterna ? Colors.ANSI_BLACK : Colors.ANSI_YELLOW;
            ps.println(color + String.join("\t", fila) + Colors.ANSI_RESET);
            alterna = !alterna;
        }


    } catch (IOException e) {
        guardarError("ERROR al mostrar datos: " + e.getMessage());
        ps.println(Colors.ANSI_RED + "Error." + Colors.ANSI_RESET);
    }
}



//agregar datos :p
public void agregar() throws IOException {
    File archivo = new File(archivoCSV);
    if (!archivo.exists()) {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.write("Letra;Color;Animal;Objeto;Alimento");
        bw.newLine();
        bw.close();
    }


    BufferedReader br = new BufferedReader(new FileReader(archivo));
    Set<String> letrasUsadas = new HashSet<>();
    br.readLine();
    String linea;
    while ((linea = br.readLine()) != null) letrasUsadas.add(linea.split(";")[0]);
    br.close();


    String letra;
    while (true) {
        ps.print("Ingrese una letra para jugar: ");
        letra = leerString().toUpperCase();
        if (letra.length() == 1 && Character.isLetter(letra.charAt(0)) && !letrasUsadas.contains(letra)) break;
        ps.println(Colors.ANSI_RED + "Intente otra vez. Letra repetida" + Colors.ANSI_RESET);
    }


    String[] campos = {"Color", "Animal", "Objeto", "Alimento"};
    String[] datos = new String[4];
    for (int i = 0; i < campos.length; i++) {
        while (true) {
            ps.print("Ingrese " + campos[i] + ": ");
            String entrada = leerString();
            if (entrada.toUpperCase().startsWith(letra)) {
                datos[i] = entrada;
                break;
            } else {
                ps.println(Colors.ANSI_RED + " letra " + letra + Colors.ANSI_RESET);
            }
        }
    }


    BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
    bw.write(letra + ";" + String.join(";", datos));
    bw.newLine();
    bw.close();
    ps.println(Colors.ANSI_GREEN + "Datos agregados :D." + Colors.ANSI_RESET);
}









//guardar error
private void guardarError(String msg) {
    try (PrintWriter log = new PrintWriter(new FileWriter(logErrores, true))) {
        log.println(msg);
    } catch (IOException ignored) {}
}

private static String leerString() throws IOException {
    StringBuilder sb = new StringBuilder();
    int c;
    while ((c = System.in.read()) != '\n' && c != -1) {
        sb.append((char) c);
    }
    return sb.toString().trim();
}


}
package Database.Tools;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
// ********** ENGLSH VERSION STILL HAS TO BE IMPROVED AND FULLY CHANGED *******************
public class Util {

	public static String dateToString(LocalDate pDate) {
		return pDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	public static LocalDate readDateDMY() {
		boolean error;
		LocalDate date = null;
		String dateString;
		DateTimeFormatter formateador=DateTimeFormatter.ofPattern("dd/MM/yyyy");
		do {
			error = false;
			dateString = readText();
			try {
				date = LocalDate.parse(dateString, formateador);
			}catch (DateTimeParseException e){
				System.out.println("Error, introduce una fecha en formato dd/mm/aaaa ");
				error = true;
			}
		} while (error);
		return date;
	}

	public static LocalDate readDateYMD() {
		boolean error;
		LocalDate date = null;
		String dateString;
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		do{
			error=false;
			dateString=readText();
			try{
				date=LocalDate.parse(dateString, formateador);
			}catch(DateTimeParseException e){
				System.out.println("Error, uuuu/hh/ee ");
				error=true;
			}
		}while (error);
		return date;
	}
	public static char readChar(char opt1, char opt2) {
		char letra=' ';
		String cadena;
		boolean error;
		do{
			error=false;
			cadena=readText();
			if (cadena.length()!=1){
				System.out.println("Error, introduce un único caracter: ");
				error=true;
			}
			else{
				letra=cadena.charAt(0);
				letra=Character.toUpperCase(letra);
				if (letra != opt1 && letra != opt2) {
					System.out.println("Error, la opción introducida no es correcta, introduce "+ opt1+ " o "+ opt2);
					error=true;
				}
			}
		}while (error);
			
		return letra;
	}

	public static char readChar() {
		char letra=' ';
		String cadena;
		boolean error;
		do{
			error=false;
			cadena=readText();
			if (cadena.length()!=1){
				System.out.println("Error, introduce un �nico caracter: ");
				error=true;
			}
		}while (error);
		letra=cadena.charAt(0);
		return letra;
	}

	public static float readFloat() {
		float num = 0;
		boolean error;
		do{
			error=false;
			try{
				num=Float.parseFloat(readText());
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
			}
		}while (error);
		return num;
	}

	public static float readFloat(String message, float min, float max) {
		float num = 0;
		boolean error;
		System.out.println(message);
		do{
			error=false;
			try{
				num=Float.parseFloat(readText());
				
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
				num=min;
			}
			if(num<min || num>max){
				System.out.println("N� fuera de rango, introduce n� entre "+ min+ " y "+ max+": ");
				error=true;
			}
		}while (error);
		return num;
	}

	public static float readFloat(float min, float max) {
		float num = 0;
		boolean error;
		do{
			error=false;
			try{
				num=Float.parseFloat(readText());
				
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
				num=min;
			}
			if(num<min || num>max){
				System.out.println("N� fuera de rango, introduce n� entre "+ min+ " y "+ max+": ");
				error=true;
			}
		}while (error);
		return num;
	}

	public static int readInt(String message, int min, int max) {
		int num = 0;
		boolean error;
		System.out.println(message);
		do{
			error=false;
			try{
				num=Integer.parseInt(readText());
				
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
				num=min;
			}
			if(num<min || num>max){
				System.out.println("N� fuera de rango, introduce n� entre "+ min+ " y "+ max+": ");
				error=true;
			}
		}while (error);
		return num;
	}

	public static int readInt(int min, int max) {
		int num = 0;
		boolean error;
		do{
			error=false;
			try{
				num=Integer.parseInt(readText());
				
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
				num=min;
			}
			if(num<min || num>max){
				System.out.println("N� fuera de rango, introduce n� entre "+ min+ " y "+ max+": ");
				error=true;
			}
		}while (error);
		return num;
	}

	public static int readInt() {
		int num = 0;
		boolean error;
		do{
			error=false;
			try{
				num=Integer.parseInt(readText());
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
			}
		}while (error);
		return num;
	}

	public static String readText() {
		String cadena = "";
		boolean error;
		InputStreamReader entrada =new InputStreamReader(System.in);
		BufferedReader teclado= new BufferedReader(entrada);
		do{
			error=false;
			try {
				cadena=teclado.readLine();
			} catch (IOException e) {
				System.out.println("Error en la entrada de datos");
				error=true;
			}
		}while (error);
		return cadena;
	}

	// Returns the number of "lines" of a file.
	public static int fileLength(File fich){
		int cont=0;
		if (fich.exists()){
			FileInputStream fis=null;
			ObjectInputStream ois=null;
			try{
				fis=new FileInputStream(fich);
				ois=new ObjectInputStream(fis);
   
				Object aux=ois.readObject();
   
				while (aux!=null){
					cont++;
					aux=ois.readObject();
				}
				
   
			}catch(EOFException e1){
			   System.out.println("Has acabado de leer, tienes "+cont+" objetos");
			   
			}catch (Exception e2){
				e2.printStackTrace();
			}
			
			
			try {
			   ois.close();
			   fis.close();
		   } catch (IOException e) {
			   System.out.println("Error al cerrar los flujos");
			   
		   }
		}
		return cont;
	}
}
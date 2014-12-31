package red;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String sTexto = null;
		System.out.println("Introduzca Los numeros del DNI");
		try {
			sTexto = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Integer> entrada = new ArrayList<Integer>();
		for(int i=0; i<sTexto.length(); i++){
			String aux = String.valueOf(sTexto.charAt(i));
			entrada.add(Integer.parseInt(aux));
		}
		
		RedNeuronal redEstandar0 = new RedNeuronal("DNI.net", true);
		double salida = redEstandar0.Calcular(entrada);
		int letra = (int) salida;
		
		if(letra==0){
			System.out.println("Su Letra es la T");
		}
		else if(letra==1){
			System.out.println("Su Letra es la R");
		}
		else if(letra==2){
			System.out.println("Su Letra es la W");
		}
		else if(letra==3){
			System.out.println("Su Letra es la A");
		}
		else if(letra==4){
			System.out.println("Su Letra es la G");
		}
		else if(letra==5){
			System.out.println("Su Letra es la M");
		}
		else if(letra==6){
			System.out.println("Su Letra es la Y");
		}
		else if(letra==7){
			System.out.println("Su Letra es la F");
		}
		else if(letra==8){
			System.out.println("Su Letra es la P");
		}
		else if(letra==9){
			System.out.println("Su Letra es la D");
		}
		else if(letra==10){
			System.out.println("Su Letra es la X");
		}
		else if(letra==11){
			System.out.println("Su Letra es la B");
		}
		else if(letra==12){
			System.out.println("Su Letra es la N");
		}
		else if(letra==13){
			System.out.println("Su Letra es la J");
		}
		else if(letra==14){
			System.out.println("Su Letra es la Z");
		}
		else if(letra==15){
			System.out.println("Su Letra es la S");
		}
		else if(letra==16){
			System.out.println("Su Letra es la Q");
		}
		else if(letra==17){
			System.out.println("Su Letra es la V");
		}
		else if(letra==18){
			System.out.println("Su Letra es la H");
		}
		else if(letra==19){
			System.out.println("Su Letra es la L");
		}
		else if(letra==20){
			System.out.println("Su Letra es la C");
		}
		else if(letra==21){
			System.out.println("Su Letra es la K");
		}
		else if(letra==22){
			System.out.println("Su Letra es la E");
		}


	}
}

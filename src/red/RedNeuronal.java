package red;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Clase que lee un .net
 * 
 */
public class RedNeuronal {

	ArrayList<ArrayList<Double>> pesos;
	ArrayList<Double> bias;

	String nombre;
	String nombreArchivo;
	int numEntradas = 0;
	int numOcultas = 0;
	boolean salida;
	double normalizationMin;
	double normalizationMax;

	public RedNeuronal(String Archivo, boolean discreto) {
		this.nombreArchivo = Archivo;
		this.salida = discreto;

		pesos = new ArrayList<ArrayList<Double>>();
		bias = new ArrayList<Double>();

		FileReader fr = null;
		BufferedReader br = null;
		String linea;
		ArrayList<String> lineasBias = new ArrayList<String>();

		try {
			fr = new FileReader(nombreArchivo);
			br = new BufferedReader(fr);

			// Saltamos las 3 primeras lineas
			saltarLineas(br, 3);

			// Cogemos el nombre de la red en el archivo
			linea = br.readLine();
			this.nombre = linea.split(":\\s+")[1];

			// Saltamos otras 3 lineas
			saltarLineas(br, 23);

			// Almacenamos las lineas que contienen los BIAS y contamos el
			// numero de nodos
			// de entrada y de capa oculta

			boolean aux = false;
			
			while (aux == false) {
				linea = br.readLine();

				String[] lectura = linea.split("\\|");
				if (lectura[5].trim().equals("i")) {
					numEntradas++;
				} else if (lectura[5].trim().equals("h")) {
					numOcultas++;
					lineasBias.add(linea);
				} else if (lectura[5].trim().equals("o")) {
					aux = true;
					lineasBias.add(linea);
				}

			}

			// Ahora extraemos los BIAS de las lineas que hemos guardado
			for (int i = 0; i < lineasBias.size(); i++) {
				String str = lineasBias.get(i);

				String[] arr = str.split("\\|");

				bias.add(Double.parseDouble(arr[4].trim().replace(',', '.')));
			}

			// Saltamos 7 lineas
			saltarLineas(br, 7);

			// Ahora leemos los pesos de la capa oculta y del nodo de salida
			for (int i = 0; i < numOcultas + 1; i++) {
				linea = br.readLine();
				String[] partes = linea.split(",*\\s*[0-9]+:\\s*");

				pesos.add(new ArrayList<Double>());
				for (int j = partes.length - 1; j > 0; j--) {
					String peso = partes[j].trim();
					pesos.get(i).add(
							Double.parseDouble(peso.trim().replace(',', '.')));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Función creada para saltar lineas
	 */
	private void saltarLineas(BufferedReader br, int numero) {
		try {
			for (int i = 0; i < numero; i++) {
				br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double Calcular(ArrayList<Integer> Entrada) {
		double aux = 0;
		double salida = 0;

		// Calculamos la salida
		for (int i = 0; i < numOcultas; i++) {// Recorremos las distinas redes
												// de la capa oculta
			double valor = 0.0;

			for (int j = 0; j < Entrada.size(); j++) {// Multiplicamos cada
														// entrada por los pesos

				valor = valor + Entrada.get(j) * pesos.get(i).get(j);
			}

			aux = valor + bias.get(i);// Sumamos el bias de la neurona i de la
										// capa oculta
			aux = 1.0 / (1.0 + Math.exp(-aux));// Funcion matematica de la
												// formula
			salida = salida + aux * pesos.get(pesos.size() - 1).get(i);// Multiplicamos
																		// por
																		// el
																		// peso
																		// de la
																		// salida
																		// y
																		// acumulamos
		}
		return salida;
	}

}

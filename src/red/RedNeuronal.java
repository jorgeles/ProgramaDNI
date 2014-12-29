package red;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Clase de Lectura de un .net. Se ha añadido una clase para calcular la salida.
 * 
 */
public class RedNeuronal {

	public String name, netFile;
	public int inputCount = 0, hiddenCount = 0;
	public final boolean normalized, discreteOutput;
	public double normalizationMin, normalizationMax;

	public ArrayList<ArrayList<Double>> pesos;
	public ArrayList<Double> bias;

	public RedNeuronal(String netFile, boolean discreteOutput,
			boolean normalized, double... normalization) {
		this.netFile = netFile;
		this.normalized = normalized;
		this.discreteOutput = discreteOutput;
		if (normalized) {
			try {
				this.normalizationMin = normalization[0];
				this.normalizationMax = normalization[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err
						.println("Error al crear la red \nNo has indicado los valores minimo y maximo para desnormalizar");
				System.exit(1);
			}
		}

		pesos = new ArrayList<ArrayList<Double>>();
		bias = new ArrayList<Double>();

		FileReader fr = null;
		BufferedReader br = null;
		String linea;
		ArrayList<String> lineasBias = new ArrayList<String>();

		try {
			fr = new FileReader(netFile);
			br = new BufferedReader(fr);

			// Saltamos las 3 primeras lineas
			saltarLineas(br, 3);

			// Cogemos el nombre de la red
			linea = br.readLine();
			this.name = linea.split(":\\s+")[1];

			// Saltamos otras 3 lineas
			saltarLineas(br, 23);

			// Almacenamos las lineas que contienen los BIAS y contamos el
			// numero de nodos
			// de entrada y de capa oculta
			boolean biasLeidos = false;
			do {
				linea = br.readLine();

				String[] arr = linea.split("\\|");
				if (arr[5].trim().equals("i")) {
					inputCount++;
				} else if (arr[5].trim().equals("h")) {
					hiddenCount++;
					lineasBias.add(linea);
				} else if (arr[5].trim().equals("o")) {
					biasLeidos = true;
					lineasBias.add(linea);
				}

			} while (!biasLeidos);

			// Ahora extraemos los BIAS de las lineas que hemos guardado
			for (int i = 0; i < lineasBias.size(); i++) {
				String str = lineasBias.get(i);

				String[] arr = str.split("\\|");

				bias.add(Double.parseDouble(arr[4].trim().replace(',', '.')));
			}

			// Saltamos 7 lineas
			saltarLineas(br, 7);

			// Ahora leemos los pesos de la capa oculta y del nodo de salida
			for (int i = 0; i < hiddenCount + 1; i++) { // hiddenCount+1 por el
														// nodo de salida
				linea = br.readLine();
				String[] partes = linea.split(",*\\s*[0-9]+:\\s*");

				pesos.add(new ArrayList<Double>());
				for (int j = partes.length - 1; j > 0; j--) {
					// for (int j = 1; j < partes.length; j++) { // Sustituir
					// esta linea
					String peso = partes[j].trim();
					pesos.get(i).add(
							Double.parseDouble(peso.trim().replace(',', '.')));
				}
			}

		} catch (IOException e) {
			System.err.println("No se ha podido crear la red");
			e.printStackTrace();
		}
	}

	/**
	 * Salta un n�mero de l�neas de un {@link BufferedReader}
	 * 
	 * @param br
	 *            Buffer del que se quiere saltar l�neas
	 * @param numero
	 *            N�mero de lineas a saltar
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
		for (int i = 0; i < hiddenCount; i++) {// Recorremos las distinas redes de la capa oculta
			double valor = 0.0;

			for (int j = 0; j < Entrada.size(); j++) {// Multiplicamos cada entrada por los pesos
				
				valor = valor +Entrada.get(j) * pesos.get(i).get(j);
			}

			aux = valor + bias.get(i);//Sumamos el bias de la neurona i de la capa oculta
			aux = 1.0 / (1.0 + Math.exp(-aux));//Funcion matematica de la formula
			salida = salida + aux
					* pesos.get(pesos.size() - 1).get(i);//Multiplicamos por el peso de la salida y acumulamos
		}
		return salida;
	}

}

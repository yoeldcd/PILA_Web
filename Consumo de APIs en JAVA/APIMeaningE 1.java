package meaningcloud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class APIMeaningE {
    
    
        public static String filterStatementScore(String statementScoreTag){
            switch(statementScoreTag){
                 case "P+":
                     return "MUY POSITIVA (P+)";
                 case "P":
                     return "POSITIVA (P)";
                 case "NEU":
                     return "NEUTRA (NEU)";
                 case "N":
                     return "NEGATIVA (N)";
                 case "N+":
                     return "MUY NEGATIVA (N+)";
                 default:
                     return "INDEFINIDA (NONE)";
            }
        }
        
        public static String filterSegmentScore(String segmentScoreTag){
            switch(segmentScoreTag){
                 case "P+":
                     return "MUY POSITIVO (P+)";
                 case "P":
                     return "POSITIVO (P)";
                 case "NEU":
                     return "NEUTRO (NEU)";
                 case "N":
                     return "NEGATIVO (N)";
                 case "N+":
                     return "MUY NEGATIVO (N+)";
                 default:
                     return "INDEFINIDO (NONE)";
            }
        }
    
	public static void main(String[] args) {
		String url = "https://api.meaningcloud.com/sentiment-2.1?key=9fcbb2c8347505bef6bfe0bc511a7710&lang=es&txt=El%20hotel%20es%20muy%20bonito%20y%20limpio,%20sin%20embargo,%20es%20un%20poco%20caro";
		String respuesta = "";
		try {
			respuesta = peticionHttpGet(url);
			// System.out.println("La respuesta es:\n" + respuesta);
			JsonParser parser = new JsonParser();   // Instancia de JSON Object 
			
			// parse main JSON herarchy object
			JsonObject objetoPrincipal = parser.parse(respuesta).getAsJsonObject();
			
                        // Load propertie fields of LEVEL_0 instance and show
                        String statementScoreTag = objetoPrincipal.get("score_tag").getAsString();
			System.out.println("La opinion es "+filterStatementScore(statementScoreTag));
                        
                        // Get an colection of sub-statements using key "sentence_list"
			JsonArray subStatements = objetoPrincipal.get("sentence_list").getAsJsonArray();
                        
                        for (JsonElement subStatementElement : subStatements) {
                            
                            	//Cast an instance of SubStatementElement to JsonObject
				JsonObject subStatementObject = subStatementElement.getAsJsonObject();
				
                                // Load propertie fields of LEVEL_1 instance
                                String subStatementText = subStatementObject.get("text").getAsString();
                                String subStatementScoreTag = subStatementObject.get("score_tag").getAsString();
                                
                                // show instance properties values
                                System.out.println("================================");
				System.out.println("Oración: " + subStatementText);
				System.out.print("Sentimiento de la oración: Esta sentencia es "+filterStatementScore(subStatementScoreTag));
                                
                                // Get a colection of sub-statement-segments using key "segment_list"
                                JsonArray subStatementSegments = subStatementObject.get("segment_list").getAsJsonArray();
                                
                                for (JsonElement subStatementSegmentElement : subStatementSegments){
                                    
                                    //Cast an instance of SubStatementSegementElemnt to JsonObject
                                    JsonObject subStatementSegmentObject = subStatementSegmentElement.getAsJsonObject();
                                    
                                    // Load propertie fields of LEVEL_2 instance
                                    String subStatementSegementText = subStatementObject.get("text").getAsString();
                                    String subStatementSegementScoreTag = subStatementObject.get("score_tag").getAsString();
                                
                                    // show instance properties values
                                    System.out.println("================================");
                                    System.out.println("Segemento de sentencia: " + subStatementText);
                                    System.out.print("Este segmento es "+filterSegmentScore(subStatementScoreTag));
                                    
                                }
                                
                                
                                
                                
			}

		} catch (Exception e) {
			// Manejar excepción
			e.printStackTrace();
		}
	}

	public static String peticionHttpGet(String urlParaVisitar) throws Exception {
		// Esto es lo que vamos a devolver
		StringBuilder resultado = new StringBuilder();
		// Crear un objeto de tipo URL
		URL url = new URL(urlParaVisitar);

		// Abrir la conexión e indicar que será de tipo GET
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setRequestMethod("GET");
		// Búferes para leer
		BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		String linea;
		// Mientras el BufferedReader se pueda leer, agregar contenido a resultado
		while ((linea = rd.readLine()) != null) {
			resultado.append(linea);
		}
		// Cerrar el BufferedReader
		rd.close();
		// Regresar resultado, pero como cadena, no como StringBuilder
		return resultado.toString();
	}
}
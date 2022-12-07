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
		String response = "";
                
		try {
			
                        // 0 - Execute HTTP GET REQUEST 
                        response = peticionHttpGet(url);
                        
                        JsonParser parser = new JsonParser();
                        
			// 1 - Parse main herarchy OBJECT instance from JSON/TEXT RESPONSE
			JsonObject objetoPrincipal = parser.parse(response).getAsJsonObject();
			
                        // 2 - Load propertie fields of LEVEL_0 instance and show
                        String statementScoreTag = objetoPrincipal.get("score_tag").getAsString();
			System.out.println("La opinion es "+filterStatementScore(statementScoreTag));
                        
                        // 3 - Get an colection of sub-statements using key "sentence_list and process any of then"
			JsonArray subStatements = objetoPrincipal.get("sentence_list").getAsJsonArray();
                        
                        for (JsonElement subStatementElement : subStatements) {
                            
                            	// 3.1 - Cast an instance of SubStatementElement to JsonObject
				JsonObject subStatementObject = subStatementElement.getAsJsonObject();
				
                                // 3.2 - Load propertie fields of LEVEL_1 instance
                                String subStatementText = subStatementObject.get("text").getAsString();
                                String subStatementScoreTag = subStatementObject.get("score_tag").getAsString();
                                
                                // 3.3 - Show instance properties values
                                System.out.println("================================");
				System.out.println("Oración: " + subStatementText);
				System.out.print("Sentimiento de la oración: Esta sentencia es "+filterStatementScore(subStatementScoreTag));
                                
                                // 3.4 - Get a colection of sub-statement-segments using key "segment_list and process any of then"
                                JsonArray subStatementSegments = subStatementObject.get("segment_list").getAsJsonArray();
                                for (JsonElement subStatementSegmentElement : subStatementSegments){
                                    
                                    // 3.4.1 - Cast an instance of SubStatementSegementElement to JsonObject
                                    JsonObject subStatementSegmentObject = subStatementSegmentElement.getAsJsonObject();
                                    
                                    // 3.4.2 - Load propertie fields of LEVEL_2 instance
                                    String subStatementSegementText = subStatementSegmentObject.get("text").getAsString();
                                    String subStatementSegementScoreTag = subStatementSegmentObject.get("score_tag").getAsString();
                                
                                    // 3.4.3 - Show instance properties values
                                    System.out.println("================================");
                                    System.out.println("Segemento de sentencia: " + subStatementSegementText);
                                    System.out.print("Este segmento es "+filterSegmentScore(subStatementSegementScoreTag));
                                    
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
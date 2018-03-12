package dhbw.config;
//Abstact Message !

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigLoader {

	private static Logger log = Logger.getLogger(ConfigLoader.class.getPackage().getName());
	protected Config config;

	public static Config importGenConsConfig(String fileName) {
		Config config;
		try {
			log.info("Read config from file: " + fileName);
			ObjectMapper mapperIn = new ObjectMapper();
			mapperIn.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
			Reader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			config = mapperIn.readValue(in, Config.class);
			log.info("done importing config");
			return config;
		} catch (JsonParseException e) {
			log.error("Error during config import: ", e);
		} catch (JsonMappingException e) {
			log.error("Error during config import: ", e);
		} catch (IOException e) {
			log.error("Error during config import: ", e);
		}
		return null;
	}
}

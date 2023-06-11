package za.co.momentum.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

public class WithdrawalEngineServiceTestUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
    }

    private WithdrawalEngineServiceTestUtils() {
    }

    public static <T> T jsonStringToObject(String jsonString, Class<T> resultObjectType) throws Exception {
        try {
            return OBJECT_MAPPER.readValue(jsonString, resultObjectType);
        } catch (Exception e) {
            String errorMessage = String.format("Unable to convert json String to object [%s]", resultObjectType.getName());
            throw new Exception(errorMessage, e);
        }
    }

    public static <T> T jsonFileToObject(String pathToJsonFile, Class<T> resultObjectType) throws Exception {
        String json = readFile(pathToJsonFile);
        return jsonStringToObject(json, resultObjectType);
    }

    public static String readFile(String path) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);

            if (inputStream == null) {
                String errorMessage = String.format("Resource could not be found in path [%s].", path);
                throw new Exception(errorMessage);
            }
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            String errorMessage = String.format("Unable to readFile from path [%s]", path);
            throw new Exception(errorMessage, e);
        } finally {
            closeInputStream(inputStream);
        }
    }

    private static void closeInputStream(final InputStream aInputStream) {
        try {
            if (aInputStream != null) {
                aInputStream.close();
            }
        } catch (IOException ignored) {
            //ignore...
        }
    }
}

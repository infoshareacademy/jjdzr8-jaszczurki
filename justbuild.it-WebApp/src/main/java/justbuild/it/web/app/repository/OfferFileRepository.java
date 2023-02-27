package justbuild.it.web.app.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import justbuild.it.web.app.entity.Offer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static justbuild.it.web.app.entity.constants.AppConstants.LOG_READ_FROM_FILE;
import static justbuild.it.web.app.entity.constants.AppConstants.LOG_WRITE_TO_FILE;

@Repository
public class OfferFileRepository {

    private final ObjectMapper objectMapper;
    private final Logger LOGGER;

    public OfferFileRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.LOGGER = LogManager.getLogger(OfferFileRepository.class.getName());
    }

    public List<Offer> getOffersFromJsonFile(String filePath) {
        if (!Files.exists(Paths.get(filePath)) || new File(filePath).length() == 0) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.error(LOG_READ_FROM_FILE + filePath, e);
            return Collections.emptyList();
        }
    }

    public void saveOffersToJsonFile(List<Offer> offers, String filePath) {
        File file = new File(filePath);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, offers);
        } catch (IOException e) {
            LOGGER.error(LOG_WRITE_TO_FILE + file.getAbsolutePath(), e);
        }
    }
}

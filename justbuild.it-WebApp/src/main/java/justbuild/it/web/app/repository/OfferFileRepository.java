package justbuild.it.web.app.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import justbuild.it.web.app.model.Offer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class OfferFileRepository {
    private final ObjectMapper objectMapper;
    private final Logger logger;

    public OfferFileRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.logger = LogManager.getLogger(OfferFileRepository.class);
    }

    public List<Offer> getOffersFromJsonFile(String filePath) {
        File file = new File(filePath);
        List<Offer> offers = new LinkedList<>();
        if (file.exists() && file.length() > 0) {
            try {
                offers = objectMapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                logger.error("Error reading offers from file: " + filePath, e);
            }
        }
        return offers;
    }

    public void saveOffersToJsonFile(List<Offer> offers) {
        File file = new File("offers.json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, offers);
        } catch (IOException e) {
            logger.error("Error saving offers to file: " + file.getAbsolutePath(), e);
        }
    }
}

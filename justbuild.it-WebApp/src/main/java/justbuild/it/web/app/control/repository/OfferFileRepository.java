package justbuild.it.web.app.control.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import justbuild.it.web.app.entity.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static justbuild.it.web.app.entity.constants.AppConstants.LOG_READ_FROM_FILE;
import static justbuild.it.web.app.entity.constants.AppConstants.LOG_WRITE_TO_FILE;
import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Repository
public class OfferFileRepository {

    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferFileRepository.class);

    public OfferFileRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Offer> getOffersFromJsonFile(String filePath) {
        LOGGER.debug("Reading offers from Json file: {}", filePath);
        if (!Files.exists(Paths.get(filePath)) || new File(filePath).length() == 0) {
            return Collections.emptyList();
        }

        try {
            List<Offer> offers = objectMapper.readValue(new File(filePath), new TypeReference<>() {
            });
            LOGGER.debug("File {} read successfully. Loaded '{}' offers", filePath, offers.size());
            return offers;
        } catch (IOException e) {
            LOGGER.error(LOG_READ_FROM_FILE + filePath, e);
            return Collections.emptyList();
        }
    }

    public void saveOffersToJsonFile(List<Offer> offers, String filePath) {
        LOGGER.debug("Saving offers to Json file: {}", filePath);
        File file = new File(filePath);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, offers);
            LOGGER.debug("Saving offers to Json file: {} was successful", filePath);
        } catch (IOException e) {
            LOGGER.error(LOG_WRITE_TO_FILE + file.getAbsolutePath(), e);
        }
    }

    public Optional<Offer> findOfferById(Long id) {
        Optional<Offer> firstId = getOffersFromJsonFile(OFFERS_FILEPATH)
                .stream()
                .filter(offer -> offer.getOfferId().equals(id))
                .findFirst();
        LOGGER.debug("First founded Offer ID: {}", firstId);
        return firstId;
    }
}
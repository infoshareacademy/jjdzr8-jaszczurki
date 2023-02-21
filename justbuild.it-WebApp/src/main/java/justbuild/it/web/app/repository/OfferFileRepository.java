package justbuild.it.web.app.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import justbuild.it.web.app.model.Offer;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class OfferFileRepository {
    private final ObjectMapper objectMapper;

    public OfferFileRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Offer> getOffersFromJsonFile(String filePath) throws IOException {
        File file = new File(filePath);
        List<Offer> offers = new LinkedList<>();
        if (file.exists() && file.length() > 0) {
            offers = objectMapper.readValue(file, new TypeReference<>() {
            });
        }
        return offers;
    }

    public void saveOffersToJsonFile(List<Offer> offers) {
        File file = new File("offers.json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, offers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

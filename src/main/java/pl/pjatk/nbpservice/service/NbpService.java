package pl.pjatk.nbpservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.nbpservice.model.NbpResponse;
import pl.pjatk.nbpservice.model.Rate;
import pl.pjatk.nbpservice.model.Root;
import pl.pjatk.nbpservice.repository.NbpResponseRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NbpService {

    private final RestTemplate restTemplate;
    private final NbpResponseRepository nbpResponseRepository;

    public NbpService(RestTemplate restTemplate, NbpResponseRepository nbpResponseRepository) {
        this.restTemplate = restTemplate;
        this.nbpResponseRepository = nbpResponseRepository;
    }

    public NbpResponse calculateRootForCurrency(String currency, int numberOfDays) {
        String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/last/" + numberOfDays;
        Root root = restTemplate.getForObject(url, Root.class);
        double average = calculate(root.getRates());
        NbpResponse nbpResponse = getNbpResponse(currency, numberOfDays, average);
        return nbpResponseRepository.save(nbpResponse);
    }

    private NbpResponse getNbpResponse(String currency, int numberOfDays, double calculate) {
        NbpResponse nbpResponse = new NbpResponse();
        nbpResponse.setCurrency(currency);
        nbpResponse.setDays(numberOfDays);
        nbpResponse.setAverage(calculate);
        nbpResponse.setCreatedAt(LocalDateTime.now());
        return nbpResponse;
    }

    public double calculate(List<Rate> rateList) {
//        double sum = 0;
//        long count = 0;
//        for (Rate rate : rateList) {
//            double averageValue = rate.getAverageValue();
//            sum += averageValue;
//            count++;
//        }
//        return count > 0 ? sum / count : 0.0d;

        return rateList.stream()
                .mapToDouble(Rate::getMid)
                .average()
                .orElse(0.0d);
    }
}

package pl.pjatk.nbpservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.nbpservice.exceptionhandler.ApiError;
import pl.pjatk.nbpservice.model.NbpResponse;
import pl.pjatk.nbpservice.service.NbpService;

@RestController
@RequestMapping("/nbp")
public class NbpController {

    private final NbpService nbpService;

    public NbpController(NbpService nbpService) {
        this.nbpService = nbpService;
    }

    @ApiOperation(value = "Get root for given currency",
            response = NbpResponse.class,
            notes = "This method will return mid value for given currency and amount of days")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found", response = ApiError.class),
            @ApiResponse(code = 500, message = "Unexpected  error")
    })
    @GetMapping(value = "/{currency}/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NbpResponse> calculateRootFromCurrency(
            @ApiParam(name = "currency",
                    type = "String",
                    value = "description",
                    example = "eur",
                    required = true,
                    defaultValue = "eur")
            @PathVariable String currency,
            @RequestParam(defaultValue = "1") int numberOfDays) {
        return ResponseEntity.ok(nbpService.calculateRootForCurrency(currency, numberOfDays));
    }
}

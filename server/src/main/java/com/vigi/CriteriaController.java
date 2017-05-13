package com.vigi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by vigi on 5/13/2017.
 */
@RestController
@RequestMapping("/api/criteria")
class CriteriaController {

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEconomicEntity(@RequestBody Criterion criterion) {
        System.out.println(criterion);
        return ResponseEntity.ok("hodoro");
    }

}

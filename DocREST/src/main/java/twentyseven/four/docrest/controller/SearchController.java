package twentyseven.four.docrest.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twentyseven.four.docrest.model.File;
import twentyseven.four.docrest.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/find")
public class SearchController {

    private static final Logger logger = Logger.getLogger(SearchController.class);

    @Autowired
    SearchService searchService;

    @GetMapping("/by/{query}")
    public ResponseEntity<List<File>> search(@PathVariable String query) {
        logger.info("--- SEARCHING FOR --- " + query);
        try {
            List<File> result = searchService.search(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // Handle generic exceptions
            logger.error("--- BAD REQUEST ---");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

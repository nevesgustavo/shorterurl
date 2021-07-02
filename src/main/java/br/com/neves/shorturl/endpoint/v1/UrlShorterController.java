package br.com.neves.shorturl.endpoint.v1;

import br.com.neves.shorturl.exception.HashAlreadyExistsException;
import br.com.neves.shorturl.exception.UrlExpiredException;
import br.com.neves.shorturl.exception.UrlNotFoundException;
import br.com.neves.shorturl.model.CustomUrlCommand;
import br.com.neves.shorturl.model.ShortUrlCommand;
import br.com.neves.shorturl.service.ShorterUrlService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RequestMapping("/api/v1/shortUrl")
@RestController
public class UrlShorterController {
// ------------------------------ FIELDS ------------------------------

    @Autowired
    private ShorterUrlService shorterUrlService;

// -------------------------- OTHER METHODS --------------------------

    @PostMapping(value = "/custom/create")
    @ApiOperation(value = "Return a short url with the custom sent hash", response = String.class)
    public ResponseEntity<String> createCustomShortUrl(@Valid @RequestBody CustomUrlCommand shortUrlCommand) {
        try {
            return ResponseEntity.ok(shorterUrlService.createCustomShortUrl(shortUrlCommand.getHash(), shortUrlCommand.getOriginalUrl(), shortUrlCommand.getExpirationDays()));
        }catch (HashAlreadyExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/create")
    @ApiOperation(value = "Return a short url with a generated hash", response = String.class)
    public ResponseEntity<String> createShortUrl(@Valid @RequestBody ShortUrlCommand shortUrlCommand) {
        return ResponseEntity.ok(shorterUrlService.createShortUrl(shortUrlCommand.getLoginName(), shortUrlCommand.getOriginalUrl(),
                shortUrlCommand.getExpirationDays()));
    }

    @GetMapping("/getUrlByHash/{hash}")
    @ApiOperation(value = "Return the original url based on sent hash", response = String.class)
    public ResponseEntity<String> getUrlByHash(@PathVariable String hash) {
        String originalUrl;
        try {
            originalUrl = shorterUrlService.getOriginalUrlByHashAndSaveLog(hash);
        } catch (UrlNotFoundException | UrlExpiredException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(originalUrl);
    }
}

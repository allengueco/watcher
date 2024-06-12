package org.allengueco.watcher.service;

import org.allengueco.watcher.model.Summoner;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/riot/account/v1/accounts", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface AccountApi {
    @GetExchange("/by-puuid/{puuid}")
    Summoner getByPuuid(@PathVariable String puuid);

    @GetExchange("/by-riot-id/{gameName}/{tagLine}")
    Summoner getByRiotId(@PathVariable String gameName, @PathVariable String tagLine);
}

package no.ntnu.idatt2003.group22.millions.controller;

import no.ntnu.idatt2003.group22.millions.market.Exchange;
import no.ntnu.idatt2003.group22.millions.model.Player;

public class GameController {
    private Player player;
    private Exchange exchange;

    public GameController() {

    }

    public void handleStartGame(Player player, Exchange exchange) {
//lager ny spiller
//leser aksjer fra fil
//lager børs
//lagrer dette i controlleren
    }

    public String handleBuy(){
//finner aksjen
// oppretter andel/transaksjon
// gjennomfører kjøp
//returnerer eller lagrer en melding til GUI
    }

    public String HandleSell(){
//finner andelen
//oppretter salg
//gjennomfører salget
    }

    public String handleSearchStock(){
//kaller exchange.findStocks(...)
    }

    public String handleSellAllAndExit(){

    }
}

package dev.timatifey.stockaggregator

class Config {
    companion object {
        const val FINNHUB_BASE_URL = "https://finnhub.io/api/v1/"
        const val FINNHUB_TOKEN = "c12121v48v6p2grllkd0"
        val FINNHUB_BASE_SYMBOLS =
            listOf("YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA")
        val EXCHANGE_CODES = listOf(
            "AS\n" +
                    "AT\n" +
                    "AX\n" +
                    "BA\n" +
                    "BC\n" +
                    "BD\n" +
                    "BE\n" +
                    "BK\n" +
                    "BO\n" +
                    "BR\n" +
                    "CN\n" +
                    "CO\n" +
                    "CR\n" +
                    "DB\n" +
                    "DE\n" +
                    "DU\n" +
                    "F\n" +
                    "HE\n" +
                    "HK\n" +
                    "HM\n" +
                    "IC\n" +
                    "IR\n" +
                    "IS\n" +
                    "JK\n" +
                    "JO\n" +
                    "KL\n" +
                    "KQ\n" +
                    "KS\n" +
                    "L\n" +
                    "LN\n" +
                    "LS\n" +
                    "MC\n" +
                    "ME\n" +
                    "MI\n" +
                    "MU\n" +
                    "MX\n" +
                    "NE\n" +
                    "NL\n" +
                    "NS\n" +
                    "NZ\n" +
                    "OL\n" +
                    "PA\n" +
                    "PM\n" +
                    "PR\n" +
                    "QA\n" +
                    "RG\n" +
                    "SA\n" +
                    "SG\n" +
                    "SI\n" +
                    "SN\n" +
                    "SR\n" +
                    "SS\n" +
                    "ST\n" +
                    "SW\n" +
                    "SZ\n" +
                    "T\n" +
                    "TA\n" +
                    "TL\n" +
                    "TO\n" +
                    "TW\n" +
                    "US\n" +
                    "V\n" +
                    "VI\n" +
                    "VN\n" +
                    "VS\n" +
                    "WA\n" +
                    "HA\n" +
                    "SX\n" +
                    "TG \n" +
                    "SC".split("\n")
        )
    }
}
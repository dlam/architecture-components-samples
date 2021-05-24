/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package paging.android.example.com.pagingsample

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.*
import android.content.Context

/**
 * Singleton database object. Note that for a real app, you should probably use a Dependency
 * Injection framework or Service Locator to create the singleton database.
 */
@Database(entities = [Cheese::class], version = 1)
abstract class CheeseDb : RoomDatabase() {
    abstract fun cheeseDao(): CheeseDao

    companion object {
        private var instance: CheeseDb? = null
        @Synchronized
        fun get(context: Context): CheeseDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        CheeseDb::class.java, "CheeseDatabase")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                fillInDb(context.applicationContext)
                            }
                        }).build()
            }
            return instance!!
        }

        /**
         * fill database with list of cheeses
         */
        private fun fillInDb(context: Context) {
            // inserts in Room are executed on the current thread, so we insert in the background
            ioThread {
                get(context).cheeseDao().insert(
                        CHEESE_DATA.map { Cheese(id = 0, name = it) })
            }
        }
    }
}

private val CHEESE_DATA = arrayListOf(

        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
        "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
        "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
//
//    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
//    "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
//    "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
//    "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48",
//    "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
//    "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72",
//    "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84",
//    "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96",
//    "97", "98", "99", "100", "101", "102", "103", "104", "105", "106", "107", "108",
//    "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120",
//    "121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132",
//    "133", "134", "135", "136", "137", "138", "139", "140", "141", "142", "143", "144",
//    "145", "146", "147", "148", "149", "150", "151", "152", "153", "154", "155", "156",
//    "157", "158", "159", "160", "161", "162", "163", "164", "165", "166", "167", "168",
//    "169", "170", "171", "172", "173", "174", "175", "176", "177", "178", "179", "180",
//    "181", "182", "183", "184", "185", "186", "187", "188", "189", "190", "191", "192",
//    "193", "194", "195", "196", "197", "198", "199", "200", "201", "202", "203", "204",
//    "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216",
//    "217", "218", "219", "220", "221", "222", "223", "224", "225", "226", "227", "228",
//    "229", "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240",
//    "241", "242", "243", "244", "245", "246", "247", "248", "249", "250", "251", "252",
//    "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264",
//    "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276",
//    "277", "278", "279", "280", "281", "282", "283", "284", "285", "286", "287", "288",
//    "289", "290", "291", "292", "293", "294", "295", "296", "297", "298", "299", "300"

//        "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
//        "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
//        "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
//        "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
//        "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
//        "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
//        "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
//        "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
//        "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
//        "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
//        "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
//        "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
//        "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini", "Bocconcini (Australian)",
//        "Boeren Leidenkaas", "Bonchester", "Bosworth", "Bougon", "Boule Du Roves",
//        "Boulette d'Avesnes", "Boursault", "Boursin", "Bouyssou", "Bra", "Braudostur",
//        "Breakfast Cheese", "Brebis du Lavort", "Brebis du Lochois", "Brebis du Puyfaucon",
//        "Bresse Bleu", "Brick", "Brie", "Brie de Meaux", "Brie de Melun", "Brillat-Savarin",
//        "Brin", "Brin d' Amour", "Brin d'Amour", "Brinza (Burduf Brinza)",
//        "Briquette de Brebis", "Briquette du Forez", "Broccio", "Broccio Demi-Affine",
//        "Brousse du Rove", "Bruder Basil", "Brusselae Kaas (Fromage de Bruxelles)", "Bryndza",
//        "Buchette d'Anjou", "Buffalo", "Burgos", "Butte", "Butterkase", "Button (Innes)",
//        "Buxton Blue", "Cabecou", "Caboc", "Cabrales", "Cachaille", "Caciocavallo", "Caciotta",
//        "Caerphilly", "Cairnsmore", "Calenzana", "Cambazola", "Camembert de Normandie",
//        "Canadian Cheddar", "Canestrato", "Cantal", "Caprice des Dieux", "Capricorn Goat",
//        "Capriole Banon", "Carre de l'Est", "Casciotta di Urbino", "Cashel Blue", "Castellano",
//        "Castelleno", "Castelmagno", "Castelo Branco", "Castigliano", "Cathelain",
//        "Celtic Promise", "Cendre d'Olivet", "Cerney", "Chabichou", "Chabichou du Poitou",
//        "Chabis de Gatine", "Chaource", "Charolais", "Chaumes", "Cheddar",
//        "Cheddar Clothbound", "Cheshire", "Chevres", "Chevrotin des Aravis", "Chontaleno",
//        "Civray", "Coeur de Camembert au Calvados", "Coeur de Chevre", "Colby", "Cold Pack",
//        "Comte", "Coolea", "Cooleney", "Coquetdale", "Corleggy", "Cornish Pepper",
//        "Cotherstone", "Cotija", "Cottage Cheese", "Cottage Cheese (Australian)",
//        "Cougar Gold", "Coulommiers", "Coverdale", "Crayeux de Roncq", "Cream Cheese",
//        "Cream Havarti", "Crema Agria", "Crema Mexicana", "Creme Fraiche", "Crescenza",
//        "Croghan", "Crottin de Chavignol", "Crottin du Chavignol", "Crowdie", "Crowley",
//        "Cuajada", "Curd", "Cure Nantais", "Curworthy", "Cwmtawe Pecorino",
//        "Cypress Grove Chevre", "Danablu (Danish Blue)", "Danbo", "Danish Fontina",
//        "Daralagjazsky", "Dauphin", "Delice des Fiouves", "Denhany Dorset Drum", "Derby",
//        "Dessertnyj Belyj", "Devon Blue", "Devon Garland", "Dolcelatte", "Doolin",
//        "Doppelrhamstufel", "Dorset Blue Vinney", "Double Gloucester", "Double Worcester",
//        "Dreux a la Feuille", "Dry Jack", "Duddleswell", "Dunbarra", "Dunlop", "Dunsyre Blue",
//        "Duroblando", "Durrus", "Dutch Mimolette (Commissiekaas)", "Edam", "Edelpilz",
//        "Emental Grand Cru", "Emlett", "Emmental", "Epoisses de Bourgogne", "Esbareich",
//        "Esrom", "Etorki", "Evansdale Farmhouse Brie", "Evora De L'Alentejo", "Exmoor Blue",
//        "Explorateur", "Feta", "Feta (Australian)", "Figue", "Filetta", "Fin-de-Siecle",
//        "Finlandia Swiss", "Finn", "Fiore Sardo", "Fleur du Maquis", "Flor de Guia",
//        "Flower Marie", "Folded", "Folded cheese with mint", "Fondant de Brebis",
//        "Fontainebleau", "Fontal", "Fontina Val d'Aosta", "Formaggio di capra", "Fougerus",
//        "Four Herb Gouda", "Fourme d' Ambert", "Fourme de Haute Loire", "Fourme de Montbrison",
//        "Fresh Jack", "Fresh Mozzarella", "Fresh Ricotta", "Fresh Truffles", "Fribourgeois",
//        "Friesekaas", "Friesian", "Friesla", "Frinault", "Fromage a Raclette", "Fromage Corse",
//        "Fromage de Montagne de Savoie", "Fromage Frais", "Fruit Cream Cheese",
//        "Frying Cheese", "Fynbo", "Gabriel", "Galette du Paludier", "Galette Lyonnaise",
//        "Galloway Goat's Milk Gems", "Gammelost", "Gaperon a l'Ail", "Garrotxa", "Gastanberra",
//        "Geitost", "Gippsland Blue", "Gjetost", "Gloucester", "Golden Cross", "Gorgonzola",
//        "Gornyaltajski", "Gospel Green", "Gouda", "Goutu", "Gowrie", "Grabetto", "Graddost",
//        "Grafton Village Cheddar", "Grana", "Grana Padano", "Grand Vatel",
//        "Grataron d' Areches", "Gratte-Paille", "Graviera", "Greuilh", "Greve",
//        "Gris de Lille", "Gruyere", "Gubbeen", "Guerbigny", "Halloumi",
//        "Halloumy (Australian)", "Haloumi-Style Cheese", "Harbourne Blue", "Havarti",
//        "Heidi Gruyere", "Hereford Hop", "Herrgardsost", "Herriot Farmhouse", "Herve",
//        "Hipi Iti", "Hubbardston Blue Cow", "Hushallsost", "Iberico", "Idaho Goatster",
//        "Idiazabal", "Il Boschetto al Tartufo", "Ile d'Yeu", "Isle of Mull", "Jarlsberg",
//        "Jermi Tortes", "Jibneh Arabieh", "Jindi Brie", "Jubilee Blue", "Juustoleipa",
//        "Kadchgall", "Kaseri", "Kashta", "Kefalotyri", "Kenafa", "Kernhem", "Kervella Affine",
//        "Kikorangi", "King Island Cape Wickham Brie", "King River Gold", "Klosterkaese",
//        "Knockalara", "Kugelkase", "L'Aveyronnais", "L'Ecir de l'Aubrac", "La Taupiniere",
//        "La Vache Qui Rit", "Laguiole", "Lairobell", "Lajta", "Lanark Blue", "Lancashire",
//        "Langres", "Lappi", "Laruns", "Lavistown", "Le Brin", "Le Fium Orbo", "Le Lacandou",
//        "Le Roule", "Leafield", "Lebbene", "Leerdammer", "Leicester", "Leyden", "Limburger",
//        "Lincolnshire Poacher", "Lingot Saint Bousquet d'Orb", "Liptauer", "Little Rydings",
//        "Livarot", "Llanboidy", "Llanglofan Farmhouse", "Loch Arthur Farmhouse",
//        "Loddiswell Avondale", "Longhorn", "Lou Palou", "Lou Pevre", "Lyonnais", "Maasdam",
//        "Macconais", "Mahoe Aged Gouda", "Mahon", "Malvern", "Mamirolle", "Manchego",
//        "Manouri", "Manur", "Marble Cheddar", "Marbled Cheeses", "Maredsous", "Margotin",
//        "Maribo", "Maroilles", "Mascares", "Mascarpone", "Mascarpone (Australian)",
//        "Mascarpone Torta", "Matocq", "Maytag Blue", "Meira", "Menallack Farmhouse",
//        "Menonita", "Meredith Blue", "Mesost", "Metton (Cancoillotte)", "Meyer Vintage Gouda",
//        "Mihalic Peynir", "Milleens", "Mimolette", "Mine-Gabhar", "Mini Baby Bells", "Mixte",
//        "Molbo", "Monastery Cheeses", "Mondseer", "Mont D'or Lyonnais", "Montasio",
//        "Monterey Jack", "Monterey Jack Dry", "Morbier", "Morbier Cru de Montagne",
//        "Mothais a la Feuille", "Mozzarella", "Mozzarella (Australian)",
//        "Mozzarella di Bufala", "Mozzarella Fresh, in water", "Mozzarella Rolls", "Munster",
//        "Murol", "Mycella", "Myzithra", "Naboulsi", "Nantais", "Neufchatel",
//        "Neufchatel (Australian)", "Niolo", "Nokkelost", "Northumberland", "Oaxaca",
//        "Olde York", "Olivet au Foin", "Olivet Bleu", "Olivet Cendre",
//        "Orkney Extra Mature Cheddar", "Orla", "Oschtjepka", "Ossau Fermier", "Ossau-Iraty",
//        "Oszczypek", "Oxford Blue", "P'tit Berrichon", "Palet de Babligny", "Paneer", "Panela",
//        "Pannerone", "Pant ys Gawn", "Parmesan (Parmigiano)", "Parmigiano Reggiano",
//        "Pas de l'Escalette", "Passendale", "Pasteurized Processed", "Pate de Fromage",
//        "Patefine Fort", "Pave d'Affinois", "Pave d'Auge", "Pave de Chirac", "Pave du Berry",
//        "Pecorino", "Pecorino in Walnut Leaves", "Pecorino Romano", "Peekskill Pyramid",
//        "Pelardon des Cevennes", "Pelardon des Corbieres", "Penamellera", "Penbryn",
//        "Pencarreg", "Perail de Brebis", "Petit Morin", "Petit Pardou", "Petit-Suisse",
//        "Picodon de Chevre", "Picos de Europa", "Piora", "Pithtviers au Foin",
//        "Plateau de Herve", "Plymouth Cheese", "Podhalanski", "Poivre d'Ane", "Polkolbin",
//        "Pont l'Eveque", "Port Nicholson", "Port-Salut", "Postel", "Pouligny-Saint-Pierre",
//        "Pourly", "Prastost", "Pressato", "Prince-Jean", "Processed Cheddar", "Provolone",
//        "Provolone (Australian)", "Pyengana Cheddar", "Pyramide", "Quark",
//        "Quark (Australian)", "Quartirolo Lombardo", "Quatre-Vents", "Quercy Petit",
//        "Queso Blanco", "Queso Blanco con Frutas --Pina y Mango", "Queso de Murcia",
//        "Queso del Montsec", "Queso del Tietar", "Queso Fresco", "Queso Fresco (Adobera)",
//        "Queso Iberico", "Queso Jalapeno", "Queso Majorero", "Queso Media Luna",
//        "Queso Para Frier", "Queso Quesadilla", "Rabacal", "Raclette", "Ragusano", "Raschera",
//        "Reblochon", "Red Leicester", "Regal de la Dombes", "Reggianito", "Remedou",
//        "Requeson", "Richelieu", "Ricotta", "Ricotta (Australian)", "Ricotta Salata", "Ridder",
//        "Rigotte", "Rocamadour", "Rollot", "Romano", "Romans Part Dieu", "Roncal", "Roquefort",
//        "Roule", "Rouleau De Beaulieu", "Royalp Tilsit", "Rubens", "Rustinu", "Saaland Pfarr",
//        "Saanenkaese", "Saga", "Sage Derby", "Sainte Maure", "Saint-Marcellin",
//        "Saint-Nectaire", "Saint-Paulin", "Salers", "Samso", "San Simon", "Sancerre",
//        "Sap Sago", "Sardo", "Sardo Egyptian", "Sbrinz", "Scamorza", "Schabzieger", "Schloss",
//        "Selles sur Cher", "Selva", "Serat", "Seriously Strong Cheddar", "Serra da Estrela",
//        "Sharpam", "Shelburne Cheddar", "Shropshire Blue", "Siraz", "Sirene", "Smoked Gouda",
//        "Somerset Brie", "Sonoma Jack", "Sottocenare al Tartufo", "Soumaintrain",
//        "Sourire Lozerien", "Spenwood", "Sraffordshire Organic", "St. Agur Blue Cheese",
//        "Stilton", "Stinking Bishop", "String", "Sussex Slipcote", "Sveciaost", "Swaledale",
//        "Sweet Style Swiss", "Swiss", "Syrian (Armenian String)", "Tala", "Taleggio", "Tamie",
//        "Tasmania Highland Chevre Log", "Taupiniere", "Teifi", "Telemea", "Testouri",
//        "Tete de Moine", "Tetilla", "Texas Goat Cheese", "Tibet", "Tillamook Cheddar",
//        "Tilsit", "Timboon Brie", "Toma", "Tomme Brulee", "Tomme d'Abondance",
//        "Tomme de Chevre", "Tomme de Romans", "Tomme de Savoie", "Tomme des Chouans", "Tommes",
//        "Torta del Casar", "Toscanello", "Touree de L'Aubier", "Tourmalet",
//        "Trappe (Veritable)", "Trois Cornes De Vendee", "Tronchon", "Trou du Cru", "Truffe",
//        "Tupi", "Turunmaa", "Tymsboro", "Tyn Grug", "Tyning", "Ubriaco", "Ulloa",
//        "Vacherin-Fribourgeois", "Valencay", "Vasterbottenost", "Venaco", "Vendomois",
//        "Vieux Corse", "Vignotte", "Vulscombe", "Waimata Farmhouse Blue",
//        "Washed Rind Cheese (Australian)", "Waterloo", "Weichkaese", "Wellington",
//        "Wensleydale", "White Stilton", "Whitestone Farmhouse", "Wigmore", "Woodside Cabecou",
//        "Xanadu", "Xynotyro", "Yarg Cornish", "Yarra Valley Pyramid", "Yorkshire Blue",
//        "Zamorano", "Zanetti Grana Padano", "Zanetti Parmigiano Reggiano"
        )

db.createCollection("agency");

db.getCollection("agency").insertOne(
    {
        uuid: "4dfcc6ca-28ad-11ed-a261-0242ac120002",
        name: "Le Chamois",
        country: "France",
        countryCode: "FRA",
        city: "Paris",
        street: "Rue Bonaparte 7",
        settlementCurrency: "EUR",
        contactPerson: "Madame Beaufort"
    }
);

db.getCollection("agency").insertOne(
    {
        uuid: "a4dd5856-28ad-11ed-a261-0242ac120002",
        name: "The corner",
        country: "United Kingdom",
        countryCode: "GBR",
        city: "London",
        street: "Batty Street E1",
        settlementCurrency: "GBP",
        contactPerson: "Mister Buttercup"
    }
);

db.getCollection("agency").insertOne(
    {
        uuid: "4dfcc6ca-28ad-11ed-a261-0242ac120002",
        name: "Convention Tickets",
        country: "United States",
        countryCode: "USA",
        city: "Los Angeles",
        street: "Florence Ave",
        settlementCurrency: "USD",
        contactPerson: "Missis Summer s"
    }
);
db.getCollection("agency").insertOne(
    {
        uuid: "b1ec3b16-28ad-11ed-a261-0242ac120002",
        name: "Tabak und Zeitschriften Hubert",
        country: "Germany",
        countryCode: "DEU",
        city: "Münche n",
        street: "Frankfurte r Ring 33",
        settlementCurrency: "EUR",
        contactPerson: "Herr Hubert",
    }
);
db.getCollection("agency").insertOne(
    {
        uuid: "b821b4ca-28ad-11ed-a261-0242ac120002",
        name: "Porta Nuova Biglietti",
        country: "Italy",
        countryCode: "ITA",
        city: "Milano",
        street: "Piazza Castello 9",
        settlementCurrency: "EUR",
        contactPerson: "Signore Bruno",
    }
);

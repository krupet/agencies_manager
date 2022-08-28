export interface AgencyDetails {
  name: string;
  country: string;
  countryCode: string;
  city: string;
  street: string;
  settlementCurrency: string;
  contactPerson: string;
}

export class AgenciesService {
  remove = (id:string) => {
    this.agencies = this.agencies.filter(i => i.name !== id);
  }

  getAgencies = () => { return this.agencies }

  agencies: AgencyDetails[] = [
    {
      name: "Le Chamois",
      country: "France",
      countryCode: "FRA",
      city: "Paris",
      street: "Rue Bonaparte 7",
      settlementCurrency: "EUR",
      contactPerson: "Madame Beaufort",
    },
    {
      name: "The corner",
      country: "United Kingdom",
      countryCode: "GBR",
      city: "London",
      street: "Batty Street E1",
      settlementCurrency: "GBP",
      contactPerson: "Mister Buttercup",
    },
    {
      name: "Convention Tickets",
      country: "United States",
      countryCode: "USA",
      city: "Los Angeles",
      street: "Florence Ave",
      settlementCurrency: "USD",
      contactPerson: "Missis Summer s",
    },
    {
      name: "Tabak und Zeitschriften Hubert",
      country: "Germany",
      countryCode: "DEU",
      city: "Münche n",
      street: "Frankfurte r Ring 33",
      settlementCurrency: "EUR",
      contactPerson: "Herr Hubert",
    },
    {
      name: "Porta Nuova Biglietti",
      country: "Italy",
      countryCode: "ITA",
      city: "Milano",
      street: "Piazza Castello 9",
      settlementCurrency: "EUR",
      contactPerson: "Signore Bruno",
    }
  ];
}




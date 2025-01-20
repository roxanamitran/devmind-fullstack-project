import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import apiClient from "../api/axios";

function SalonDetails() {
  const { id } = useParams();
  const [salon, setSalon] = useState(null);
  const [salonOffers, setSalonOffers] = useState(null);

  useEffect(() => {
    const fetchSalonDetails = async () => {
      try {
        const salonResponse = await apiClient.get(`/salons/${id}`);
        const salonOffersResponse = await apiClient.get(
          `/salon-to-salon-offers/salonOffers/${id}`
        );

        setSalon(salonResponse.data);
        setSalonOffers(salonOffersResponse.data);
      } catch (error) {
        console.log("Error fetching salon details", error);
      }
    };

    fetchSalonDetails();
  }, [id]);

  if (!salon) {
    return <div>Loading salon details...</div>;
  }
  return (
    <div>
      <h1>{salon.name}</h1>
      <img src={salon.photoUrl} alt={salon.name}></img>
      <h4>Email: {salon.email}</h4>
      <h4>Telefon: {salon.phoneNumber}</h4>
      <h4>Servicii:</h4>
      <ul>
        {salonOffers && salonOffers.length > 0 ? (
          salonOffers.map((offer) => (
            <li key={offer.id}>
              <strong>{offer.salonOffer.name}</strong>
              <br />
              <strong>{offer.price}</strong>
              <br />
              <strong>{offer.duration}</strong>
            </li>
          ))
        ) : (
          <p>No offers available.</p>
        )}
      </ul>
    </div>
  );
}

export default SalonDetails;

import { useEffect, useState } from "react";
import apiClient from "../api/axios";
import Offer from "../components/Offer";
import { Row, Col } from "react-bootstrap";

function Offers() {
  const [salonOffers, setSalonOffers] = useState([]);

  useEffect(() => {
    const offers = async () => {
      try {
        const response = await apiClient.get("/salonOffers");
        setSalonOffers(response.data);
      } catch (error) {
        console.error("Error fetching salonOffers", error);
      }
    };
    offers();
  }, []);

  return (
    <Row xs={1} md={2} lg={3}>
      {salonOffers.map((offer) => (
        <Col key={offer.id}>
          <Offer offer={offer} />
        </Col>
      ))}
    </Row>
  );
}

export default Offers;

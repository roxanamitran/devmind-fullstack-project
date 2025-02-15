import { useEffect, useState } from "react";
import apiClient from "../api/axios";
import Offer from "../components/Offer";
import { Row, Col } from "react-bootstrap";

function Offers() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apiClient.get("/salonOffers");
        setData(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, []);
  return (
    <Row xs={1} md={2} lg={3}>
      {data.map((offer) => (
        <Col key={offer.id}>
          <Offer offer={offer} />
        </Col>
      ))}
    </Row>
  );
}

export default Offers;

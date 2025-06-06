import { useEffect, useState } from "react";
import Salon from "../components/Salon";
import apiClient from "../api/axios";
import { Row } from "react-bootstrap";
import { Col } from "react-bootstrap";
import { useLocation } from "react-router-dom";

function Salons() {
  const [salons, setSalons] = useState([]);

  const location = useLocation();

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);

    let endpointUrl = "/salons";
    if (Array.from(queryParams).length > 0) {
      endpointUrl = `${endpointUrl}?${queryParams.toString()}`;
    }

    const fetchData = async () => {
      try {
        const response = await apiClient.get(endpointUrl);
        setSalons(response.data);
      } catch (error) {
        console.error("Error fetching salons:", error);
      }
    };
    fetchData();
  }, []);

  return (
    <>
      <div className="centrat">
        <h1>Găsește salonul potrivit dorințelor tale!</h1>
      </div>
      <Row xs={1} md={2} lg={4} className="g-1">
        {salons.map((salon) => (
          <Col key={salon.id}>
            <Salon salon={salon} />
          </Col>
        ))}
      </Row>
    </>
  );
}

export default Salons;

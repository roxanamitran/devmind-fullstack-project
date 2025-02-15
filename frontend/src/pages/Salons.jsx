import { useEffect, useState } from "react";
import Salon from "../components/Salon";
import apiClient from "../api/axios";
import { Row } from "react-bootstrap";
import { Col } from "react-bootstrap";
import { useLocation } from "react-router-dom";

function Salons() {
  const location = useLocation();

  const [data, setData] = useState([]);

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);

    let endpointUrl = "/salons";
    if (Array.from(queryParams).length > 0) {
      endpointUrl = `${endpointUrl}?${queryParams.toString()}`;
    }

    const fetchData = async () => {
      try {
        const response = await apiClient.get(endpointUrl);

        setData(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, []);

  return (
    <>
      <header className="py-5npm ">
        <h1>Găsește salonul potrivit dorințelor tale!</h1>
      </header>
      <Row xs={1} md={2} lg={4} className="g-1">
        {data.map((salon) => (
          <Col key={salon.id}>
            <Salon salon={salon} />
          </Col>
        ))}
      </Row>
    </>
  );
}

export default Salons;

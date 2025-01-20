import { useEffect, useState } from "react";
import apiClient from "../api/axios";
import { useParams } from "react-router-dom";
import { Col } from "react-bootstrap";
import Salon from "../components/Salon";
function SalonsByOffer() {
  const { salonOfferId } = useParams();
  const [data, setData] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const respone = await apiClient.get(
          `salon-to-salon-offers/salons/${salonOfferId}`
        );
        setData(respone.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, [salonOfferId]);
  return data.map((item) => (
    <Col key={item.id}>
      <Salon salon={item} />
    </Col>
  ));
}

export default SalonsByOffer;

import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import apiClient from "../api/axios";
import { Button, Table, Col, Row, Container } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPhone,
  faEnvelope,
  faHouse
} from "@fortawesome/free-solid-svg-icons";

function SalonDetails() {
  const { id } = useParams();
  const [salon, setSalon] = useState(null);
  const [services, setServices] = useState([]);
  const navigate = useNavigate();

  function handleAppointment() {
    navigate(`/appointment?salon_id=${id}`);
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apiClient.get(`/salons/${id}`);
        setSalon(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
      try {
        const servicesResponse = await apiClient.get(
          `/salon-to-salon-offers/salonOffers/${id}`
        );
        setServices(servicesResponse.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, [id]);

  if (salon == null) {
    return <div>Salon inexistent.</div>;
  }
  return (
    <Container>
      <Row>
        <Col md={4}>
          <h1>{salon.name}</h1>
          <h3 className="bad-script-regular" style={{ color: "#963468 " }}>
            {salon.description}
          </h3>
          <img
            src={salon.photoUrl}
            alt={salon.name}
            className="img-fluid"
            width="100%"
          />
          <Table striped bordered hover variant="dark">
            <tbody>
              <tr>
                <td>
                  <FontAwesomeIcon icon={faPhone} /> {salon.phoneNumber}
                </td>
                <td>
                  <FontAwesomeIcon icon={faEnvelope} />
                  {salon.email}
                </td>
                <td>
                  <FontAwesomeIcon icon={faHouse} />
                  {salon.address}, {salon.city}
                </td>
              </tr>
            </tbody>
          </Table>
        </Col>

        <Col md={8}>
          <div>
            <h5>Program</h5>
            <Table striped bordered hover variant="dark">
              <tbody>
                {salon.schedules.map((schedule) => (
                  <tr key={schedule.id}>
                    <td>{schedule.day}</td>
                    <td>{schedule.startHour}</td>
                    <td>{schedule.endHour}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>

          <div>
            <h5>Servicii</h5>
            <Table striped bordered hover variant="dark">
              <thead>
                <tr>
                  <th>Serviciu</th>
                  <th>Tarif (lei)</th>
                  <th>Durata (minute)</th>
                  <th>Solicita o programare</th>
                </tr>
              </thead>
              <tbody>
                {services.map((service) => (
                  <tr key={service.id}>
                    <td>{service.salonOffer.name}</td>
                    <td>{service.price}</td>
                    <td>{service.duration}</td>
                    <td>
                      <Button variant="light" onClick={handleAppointment}>
                        Programeaza
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>
        </Col>
      </Row>
    </Container>
  );
}

export default SalonDetails;

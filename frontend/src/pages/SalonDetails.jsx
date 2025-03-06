import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import apiClient from "../api/axios";
import { Button, Table, Col, Row, Container } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { jwtDecode } from 'jwt-decode'
import {
  faPhone,
  faEnvelope,
  faHouse
} from "@fortawesome/free-solid-svg-icons";

function SalonDetails() {
  const [salon, setSalon] = useState(null);
  const [services, setServices] = useState([]);

  const token = localStorage.getItem("jsonwebtoken");
  const decodedToken = jwtDecode(token);

  const { id } = useParams();

  const navigate = useNavigate();

  function handleOffers() {
    navigate(`/add_salon_offer?salon_id=${id}`);
  }

  function handleEmployees() {
    navigate(`/asign_employees?salon_id=${id}`);
  }
  function handleAppointment(salonOfferId, duration) {
    navigate(`/appointment?salon_id=${id}&salon_offer_id=${salonOfferId}&duration=${duration}`);
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const salonResponse = await apiClient.get(`/salons/${id}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
          }
        });
        setSalon(salonResponse.data);
      } catch (error) {
        console.error("Error fetching salon:", error);
      }

      try {
        const servicesResponse = await apiClient.get(
          `/salon-to-salon-offers/salonOffers/${id}`
        );
        setServices(servicesResponse.data);
      } catch (error) {
        console.error("Error fetching salon offers:", error);
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
                      <Button
                        variant="light"
                        onClick={() => handleAppointment(service.id, service.duration)}
                      >
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
      {(decodedToken && decodedToken.sub === salon.manager.email) &&
      <>
        <Row>
          <Button variant="dark" size="lg" onClick={handleOffers}>
            Gestioneaza servicii
          </Button>
        </Row>
        <br />
        <Row>
          <Button variant="dark" size="lg" onClick={handleEmployees}>
            Gestioneaza angajatii
          </Button>
        </Row>
      </>
    }
    </Container>
  );
}

export default SalonDetails;

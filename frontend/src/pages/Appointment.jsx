import { useEffect, useState } from "react";
import { Col, Container, Row, ListGroup, Button, Modal } from "react-bootstrap";
import Calendar from "react-calendar/dist/cjs/Calendar.js";
import apiClient from "../api/axios";
import { useLocation } from "react-router-dom";

function Appointment() {
  const [date, setDate] = useState(new Date());
  const [employees, setEmployees] = useState([]);
  const [error, setError] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  function handleModal() {
    setIsModalOpen(!isModalOpen);
  }

  const location = useLocation();

  useEffect(() => {
    const fetchEmployees = async () => {
      const queryParams = new URLSearchParams(location.search);
      const salonId = queryParams.get("salon_id");

      try {
        const response = await apiClient.get(`/salons/${salonId}/employees`);
        console.log(response);
        setEmployees(response.data);
      } catch (err) {
        console.error("Error fetching employees:", err);
        setError("Nu am putut încărca lista de angajați.");
      }
    };

    fetchEmployees();
  }, []);

  const onChange = (newDate) => {
    setDate(newDate);
  };

  return (
    <>
      <h1>Alege profesionistul preferat și apoi vezi când este disponibil!</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <Container>
        <Row>
          <Col md={6}>
            <ListGroup>
              {employees.map((employee) => (
                <ListGroup.Item key={employee.id} value={employee.id}>
                  <img
                    src={employee.photoUrl}
                    alt={employee.firstName}
                    style={{
                      width: "90px",
                      height: "90px"
                    }}
                  />
                  <span style={{ marginRight: "20px", marginLeft: "20px" }}>
                    {employee.firstName} {employee.lastName}
                  </span>
                  <span style={{ marginRight: "20px", marginLeft: "100px" }}>
                    <Button variant="dark" onClick={handleModal}>
                      Solicita programare
                    </Button>
                  </span>
                </ListGroup.Item>
              ))}
            </ListGroup>
            {isModalOpen && (
              <div
                className="modal show"
                style={{ display: "block", position: "initial" }}
              >
                <Modal.Dialog>
                  <Modal.Header closeButton>
                    <Modal.Title>Alege ziua disponibila</Modal.Title>
                  </Modal.Header>

                  <Modal.Body>
                    <Calendar onChange={onChange} value={date} />
                  </Modal.Body>

                  <Modal.Footer>
                    <Button variant="dark">Trimite programarea</Button>
                  </Modal.Footer>
                </Modal.Dialog>
              </div>
            )}
          </Col>
          <Col md={6}></Col>
        </Row>
      </Container>
    </>
  );
}

export default Appointment;
